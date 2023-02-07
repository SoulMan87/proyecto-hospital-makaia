package com.soulrebel.hospital.controller;


import com.soulrebel.hospital.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class BaseController {

    private static final Logger LOG = LoggerFactory.getLogger (BaseController.class);

    @ExceptionHandler(IllegalArgumentException.class)
    public final ResponseEntity<ErrorResponse> handleIllegalArgumentExceptions(IllegalArgumentException ex) {
        LOG.error ("IllegalArgumentException :  {}", ex.getMessage ());
        ErrorResponse error = new ErrorResponse ("Request No Valido", ex.getMessage ());
        return new ResponseEntity<> (error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ErrorResponse> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException ex) {
        LOG.error ("MethodArgumentNotValidException :  {}", ex.getMessage ());
        ErrorResponse error = new ErrorResponse ("Error de validación",
                ex.getBindingResult ().getFieldError ().getDefaultMessage ());
        return new ResponseEntity<> (error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        LOG.error ("handleAllExceptions :  {}", ex.getMessage ());
        ErrorResponse error = new ErrorResponse ("Server Error",
                "Un error ha ocurrido mientras se procesaba la respuesta");
        return new ResponseEntity<> (error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
