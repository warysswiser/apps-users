package com.warys.users.application.command.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@Getter
public class ErrorResponse implements Serializable {
    private Date timestamp;
    private String message;
    private String path;
    private String exception;
}
