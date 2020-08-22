package io.github.kprasad99.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix="person.service")
@Data
public class PersonServiceProperties {

    private String host= "localhost";
    private String path = "/";
    private Port ports;

    @Data
    public static class Port {
        private int http;
        private int grpc;
    }
}
