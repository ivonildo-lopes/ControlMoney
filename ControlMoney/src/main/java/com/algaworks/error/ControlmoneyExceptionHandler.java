package com.algaworks.error;

import com.algaworks.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControlmoneyExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(ControlmoneyExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

       LOGGER.error(" =============== Cliente passando campos desconhecido ==========================");
        Object obj =  ResponseDto.response(ex.getCause().getMessage(),HttpStatus.BAD_REQUEST,"Cliente passando campos desconhecidos");

        return handleExceptionInternal(ex, obj, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> erros = new ArrayList<>();
        LOGGER.error(" =============== Campos sem validação ==========================");
        for (FieldError erro: ex.getBindingResult().getFieldErrors() ) {
            erros.add(erro.toString());
        }

        List<String> errs = ex.getBindingResult().getFieldErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
        LOGGER.error(" =============== ERROS ENCONTRADOS ==========================");

        erros.stream().forEach(e -> {
            LOGGER.error(e);
        });

        LOGGER.error(" =============== FIM DOS ERROS ENCONTRADOS ==========================");
        Object obj =  ResponseDto.response(erros,
                HttpStatus.BAD_REQUEST,"Favor verifique todos os campos com validação",errs);
        return handleExceptionInternal(ex, obj, headers, HttpStatus.BAD_REQUEST, request);
    }
}
