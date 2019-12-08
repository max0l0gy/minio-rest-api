package ru.maxmorev.minioservice.domain;

import java.util.List;

public class Message {

    public final static String SUCCES="success";
    public final static String ERROR="error";

    private final String status;
    private final String url;
    private final String message;
    private List<ErrorDetail> errors;

    public static class ErrorDetail{
        final String field;
        final String message;
        public ErrorDetail(String f, String m){
            this.field = f;
            this.message = m;
        }

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }

    public Message(String status, String url, Exception message, List<ErrorDetail> errors) {
        this.status = status;
        this.url = url;
        this.message = message.getMessage();
        this.errors = errors;
    }

    public Message(String status, String url, String message, List<ErrorDetail> errors) {
        this.status = status;
        this.url = url;
        this.message = message;
        this.errors = errors;
    }

    public Message(String status, String message){
        this.status = status;
        this.message = message;
        this.url = null;
    }

    public String getStatus() {
        return status;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    public List<ErrorDetail> getErrors() {
        return errors;
    }

}