package com.example.config;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

@Configuration
public class ElasticConfiguration extends ElasticsearchConfiguration {//把证书给它配置了

    @Value("${spring.elasticsearch.uris}")
    String[] uris;

    @Value("${spring.elasticsearch.username}")
    String username;

    @Value("${spring.elasticsearch.password}")
    String password;

    @Value("classpath:es/http_ca.crt")
    Resource cert;

    @Override
    @SneakyThrows
    public ClientConfiguration clientConfiguration() {

        Certificate certificate = CertificateFactory.getInstance("X.509")
                .generateCertificate(cert.getInputStream());

        String keyStoreType = KeyStore.getDefaultType();
        KeyStore keyStore = KeyStore.getInstance(keyStoreType);
        keyStore.load(null, null);
        keyStore.setCertificateEntry("ca", certificate);

        String algorithm = TrustManagerFactory.getDefaultAlgorithm();
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(algorithm);
        trustManagerFactory.init(keyStore);

        SSLContext context = SSLContext.getInstance("TLS");
        context.init(null, trustManagerFactory.getTrustManagers(), null);

        return ClientConfiguration.builder()
                .connectedTo(uris)
                .usingSsl(context, (h, s) -> true)
                .withBasicAuth(username, password)
                .build();
    }

}
