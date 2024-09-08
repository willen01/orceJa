package com.willen.OrceJa.exceptions;

import org.springframework.http.HttpStatusCode;

import java.time.Instant;

public class StandardError {

    private Instant timestamp;

    private HttpStatusCode Status;

    private String error;

    private String path;

    public StandardError() {
    }

    public StandardError(Instant timestamp, HttpStatusCode status, String error, String path) {
        this.timestamp = timestamp;
        Status = status;
        this.error = error;
        this.path = path;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public HttpStatusCode getStatus() {
        return Status;
    }

    public void setStatus(HttpStatusCode status) {
        Status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
