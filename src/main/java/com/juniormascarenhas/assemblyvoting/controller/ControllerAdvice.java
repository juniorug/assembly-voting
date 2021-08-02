package com.juniormascarenhas.assemblyvoting.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.juniormascarenhas.assemblyvoting.exception.MessageError;
import com.juniormascarenhas.assemblyvoting.exception.Messages;
import com.juniormascarenhas.assemblyvoting.exception.NotFoundException;
import com.juniormascarenhas.assemblyvoting.exception.PreconditionFailedException;
import com.juniormascarenhas.assemblyvoting.exception.ServiceUnavailableException;
import com.juniormascarenhas.assemblyvoting.exception.UnprocessableEntityException;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestControllerAdvice
public class ControllerAdvice {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<List<MessageError>> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException methodArgumentNotValidException) {
    List<MessageError> messageErrors = Optional.ofNullable(methodArgumentNotValidException)
        .filter(argumentNotValidException -> !ObjectUtils.isEmpty(argumentNotValidException.getBindingResult()))
        .map(MethodArgumentNotValidException::getBindingResult)
        .filter(bindingResult -> !ObjectUtils.isEmpty(bindingResult.getAllErrors())).map(BindingResult::getAllErrors)
        .stream().flatMap(Collection::stream).filter(objectError -> !ObjectUtils.isEmpty(objectError))
        .map(o -> new MessageError(o.getDefaultMessage(), ((FieldError) o).getField())).collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(messageErrors);
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<List<MessageError>> handleBindException(BindException bindException) {
    List<MessageError> messageErrors = Optional.ofNullable(bindException)
        .filter(bind -> !ObjectUtils.isEmpty(bind.getBindingResult())).map(BindException::getBindingResult)
        .filter(bindingResult -> !ObjectUtils.isEmpty(bindingResult.getAllErrors())).map(BindingResult::getAllErrors)
        .stream().flatMap(Collection::stream).filter(objectError -> !ObjectUtils.isEmpty(objectError))
        .map(o -> new MessageError(Messages.INVALID_REQUEST_PARAM_FIELD, ((FieldError) o).getField()))
        .collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(messageErrors);
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<List<MessageError>> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException httpMessageNotReadableException) {
    List<MessageError> errors = Arrays.asList(new MessageError(Messages.INVALID_BODY_ERROR));
    return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errors);
  }

  @ExceptionHandler(PreconditionFailedException.class)
  public ResponseEntity<List<MessageError>> handlePreconditionException(PreconditionFailedException preCondEx) {
    return ResponseEntity.status(preCondEx.getStatus()).body(preCondEx.getErrors());
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<Void> handleNotFoundException(NotFoundException notFoundException) {
    return ResponseEntity.status(notFoundException.getStatus()).build();
  }

  @ExceptionHandler(ServiceUnavailableException.class)
  public ResponseEntity<Void> serviceUnavailableException(ServiceUnavailableException serviceUnavailableException) {
    return new ResponseEntity<>(serviceUnavailableException.getStatus());
  }

  @ExceptionHandler(UnprocessableEntityException.class)
  public ResponseEntity<MessageError> handleUnprocessableEntityException(UnprocessableEntityException unpEx) {
    MessageError error = new MessageError(unpEx.getCode(), unpEx.getArgs());
    return ResponseEntity.status(unpEx.getStatus()).body(error);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<Void> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException httpRequestMethodNotSupportedException) {
    return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<Void> handleHttpMediaTypeNotSupportedException(
      HttpMediaTypeNotSupportedException httpMediaTypeNotSupportedException) {
    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<List<MessageError>> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {
    List<MessageError> errors = List
        .of(new MessageError(Messages.INVALID_REQUEST_PARAM_FIELD, methodArgumentTypeMismatchException.getName()));
    return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errors);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<Void> handleException(Exception exception) {
    log.error(exception.getMessage(), exception);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
  }

}
