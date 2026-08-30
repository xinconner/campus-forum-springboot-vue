package com.example.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.example.entity.vo.response.WeatherVO;
import com.example.service.WeatherService;
import com.example.utils.Const;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;

@Service
public class WeatherServiceImpl implements WeatherService {
    @Resource
    RestTemplate rest;

    @Value("${spring.weather.key}")
    String key;

    @Value("${spring.weather.api-host}")
    String apiHost;

    @Resource
    StringRedisTemplate template;

    public WeatherVO fetchWeather(double longitude, double latitude) {
        return fetchWeatherCache(longitude,latitude);
    }

    private WeatherVO fetchWeatherCache(double longitude, double latitude) {
        JSONObject geo = this.requestJson("/geo/v2/city/lookup?location=" + longitude + "," + latitude);
        if (geo==null) return null;
        JSONArray locations = geo.getJSONArray("location");
        if (locations == null || locations.isEmpty()) return null;
        JSONObject location = locations.getJSONObject(0);
        String id = location.getString("id");
        if (id == null) return null;
        String cacheKey = Const.FORUM_WEATHER_CACHE + id;
        String cache = template.opsForValue().get(cacheKey);
        if (cache!=null){
            return JSONObject.parseObject(cache).to(WeatherVO.class);
        }
        WeatherVO vo=this.fetchFromAPI(id,location);
        if (vo==null) return null;
        template.opsForValue().set(cacheKey,JSONObject.from(vo).toJSONString(),1, TimeUnit.HOURS);
        return vo;
    }

    private WeatherVO fetchFromAPI(String id,JSONObject location){
        WeatherVO vo = new WeatherVO();
        vo.setLocation(location);
        JSONObject now = this.requestJson("/v7/weather/now?location=" + id);
        if (now==null) return null;
        vo.setNow(now.getJSONObject("now"));
        JSONObject hourly = this.requestJson("/v7/weather/24h?location=" + id);
        if(hourly == null) return null;
        vo.setHourly(new JSONArray(hourly.getJSONArray("hourly").stream().limit(5).toList()));
        return vo;
    }

    private JSONObject requestJson(String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT_ENCODING, "gzip");
        headers.set("X-QW-Api-Key", key);
        try {
            ResponseEntity<byte[]> response = rest.exchange(
                    "https://" + apiHost + path,
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    byte[].class);
            return parseJson(response.getBody());
        } catch (RestClientException e) {
            return null;
        }
    }

    private JSONObject parseJson(byte[] data){
        if (data == null || data.length == 0) return null;
        boolean compressed = data.length > 1 && data[0] == (byte) 0x1f && data[1] == (byte) 0x8b;
        try (InputStream stream = compressed ? new GZIPInputStream(new ByteArrayInputStream(data))
                : new ByteArrayInputStream(data)) {
            return JSONObject.parseObject(new String(stream.readAllBytes(), StandardCharsets.UTF_8));
        } catch (IOException | RuntimeException e) {
            return null;
        }
    }
}
