package io.github.kprasad99.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@Configuration
@EnableConfigurationProperties(PersonServiceProperties.class)
public class GrpcClientConfig {

    @Bean
    public ManagedChannel managedChannelBeanPort(PersonServiceProperties props) {
        return ManagedChannelBuilder.forAddress(props.getHost(), props.getPorts().getGrpc()).usePlaintext().build();
    }

}
