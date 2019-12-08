package ru.maxmorev.minioservice.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileUploadResponse {

    public enum Status{
        OK, FAIL
    }

    private String status;
    private String uri;



}
