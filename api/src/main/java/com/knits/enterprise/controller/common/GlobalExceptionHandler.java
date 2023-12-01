package com.knits.enterprise.controller.common;

import com.knits.enterprise.dto.data.common.ExceptionDto;
import com.knits.enterprise.dto.data.common.ValidationErrorDto;
import com.knits.enterprise.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthorizationException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDto> handleUserException(AuthorizationException ex) {
        return wrapIntoResponseEntity(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDto> handleUserException(UserException ex) {
        if (ex.getCode() != null) {
            return wrapIntoResponseEntity(ex, HttpStatus.resolve(ex.getCode()));
        }
        return wrapIntoResponseEntity(ex, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<ValidationErrorDto> onConstraintViolationException(ConstraintViolationException e) {
        ValidationErrorDto validationErrorDto = new ValidationErrorDto();
        for (ConstraintViolation violation: e.getConstraintViolations()) {
            validationErrorDto.addViolation(violation.getPropertyPath().toString(), violation.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ValidationErrorDto> handleValidationErrors(MethodArgumentNotValidException ex) {
        ValidationErrorDto validationErrorDto = new ValidationErrorDto();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            validationErrorDto.addViolation(error.getField(), error.getDefaultMessage());
        });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(validationErrorDto);
    }

    @ExceptionHandler(SystemException.class)
    @ResponseBody
    public ResponseEntity<ExceptionDto> handleSystemException(SystemException ex) {
        return wrapIntoResponseEntity(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ExceptionDto> handleAllExceptions(Exception ex) {
        log.error(ex.getMessage(),ex);
        ExceptionDto exDto = new ExceptionDto(ExceptionCodes.UNMAPPED_EXCEPTION_CODE, ExceptionCodes.INTERNAL_EX_MESSAGE);
        return wrapIntoResponseEntity(exDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ExceptionDto> wrapIntoResponseEntity (AppException ex, HttpStatus status){
        log.error(ex.getMessage(),ex);
        return ResponseEntity
                .status(status)
                .body(new ExceptionDto(ex));
    }

    private ResponseEntity<ExceptionDto> wrapIntoResponseEntity (ExceptionDto exDto, HttpStatus status){
        return ResponseEntity
                .status(status)
                .body(exDto);
    }
}
