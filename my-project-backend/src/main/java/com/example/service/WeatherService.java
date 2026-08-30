package com.example.service;

import com.example.entity.vo.response.WeatherVO;

public interface WeatherService {
    WeatherVO fetchWeather(double longitude,double latitude);
}
