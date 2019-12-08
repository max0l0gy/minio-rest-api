package ru.maxmorev.minioservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadResponse {

    public enum Status{
        OK, ERROR
    }

    private String status;
    private String uri;
    private String message;


}
