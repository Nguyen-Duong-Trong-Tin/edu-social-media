package com.eduSocialMedia.exceptions;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eduSocialMedia.dtos.ResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseDto<Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    List<String> errors = ex.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(error -> error.getDefaultMessage())
        .toList();

    return ResponseDto.badRequest(errors);
  }

  @ExceptionHandler(UnauthorizedException.class)
  public ResponseEntity<ResponseDto<Object>> handleUnauthorized(UnauthorizedException ex) {
    return ResponseDto.unauthorized();
  }

  @ExceptionHandler(ForbiddenException.class)
  public ResponseEntity<ResponseDto<Object>> handleForbidden(ForbiddenException ex) {
    return ResponseDto.forbidden();
  }
}
