package ru.maxmorev.minioservice;

import io.minio.MinioClient;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinioConfiguration {
    @Value("${minio.endpoint}")
    private String minioEndpoint;
    @Value("${minio.accessKey}")
    private String  accessKey;
    @Value("${minio.secretKey}")
    private String secretKey;

    @Bean
    @SneakyThrows
    MinioClient getMinioClien(){
        return new MinioClient(minioEndpoint, accessKey, secretKey);
    }

}
