package com.algaworks.algashop.ordering.presentation.exceptionhandler;

import com.algaworks.algashop.ordering.domain.model.customer.exception.CustomerEmailAlreadyInUseException;
import com.algaworks.algashop.ordering.domain.model.generic.DomainEntityNotFoundException;
import com.algaworks.algashop.ordering.domain.model.generic.DomainException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(status.value());
        problemDetail.setTitle("Invalid fields");
        problemDetail.setDetail("One or more fields are invalid");
        problemDetail.setType(URI.create("/errors/invalid-fields"));
        Map<String, String> fieldErrors = ex.getBindingResult().getAllErrors().stream().collect(Collectors.toMap(
                objectError -> ((FieldError) objectError).getField(),
                objectError -> messageSource.getMessage(objectError, LocaleContextHolder.getLocale())
        ));

        problemDetail.setProperty("fields", fieldErrors);

        return super.handleExceptionInternal(ex, problemDetail, headers, status, request);
    }

    @ExceptionHandler(DomainEntityNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleDomainEntityNotFound(DomainEntityNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND.value());
        problemDetail.setTitle("Not Found");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/not-found"));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ProblemDetail> handleDomainException(DomainException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        problemDetail.setTitle("Unprocessable Entity");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/unprocessable-entity"));
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(problemDetail);
    }

    @ExceptionHandler(CustomerEmailAlreadyInUseException.class)
    public ResponseEntity<ProblemDetail> handleCustomerEmailAlreadyInUseException(CustomerEmailAlreadyInUseException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.CONFLICT.value());
        problemDetail.setTitle("Conflict");
        problemDetail.setDetail(ex.getMessage());
        problemDetail.setType(URI.create("/errors/conflict"));
        return ResponseEntity.status(HttpStatus.CONFLICT).body(problemDetail);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ProblemDetail> handleException(Exception ex) {
        log.error(ex.getMessage(), ex);
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setDetail("Unexpected internal error occurred");
        problemDetail.setType(URI.create("/errors/internal"));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(problemDetail);
    }

}
