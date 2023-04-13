package com.interview.challenge.pokerplanning.exception;

import lombok.Data;

@Data
public class ErrorMessage {

    private int statusCode;
    private String message;
}