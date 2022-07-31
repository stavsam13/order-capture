package com.tui.proof.Exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.tui.proof.util.ErrorMessages.INVALID_REQUEST;

@ControllerAdvice
public class ValidationHandlerController extends ResponseEntityExceptionHandler{

    private static final Logger LOG = LoggerFactory.getLogger(ValidationHandlerController.class);

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        LOG.error("Bad Request",ex);
        var response = new ErrorResponse(INVALID_REQUEST);
        response.setErrors(errors);
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleRuntimeException(RuntimeException ex , WebRequest req) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        var response = new ErrorResponse("Runtime Exception ",details);
        LOG.error("Runtime Exception",ex);
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
