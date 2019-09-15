package com.algaworks.error;

import com.algaworks.dto.ResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

       LOGGER.error(" =============== Cliente passando campos desconhecido ==========================");
        Object obj =  ResponseDto.response(ex.getCause() != null ? ex.getCause().getMessage() : ex.toString(),HttpStatus.BAD_REQUEST,"Cliente passando campos desconhecidos");

        return handleExceptionInternal(ex, obj, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errosParaDesenvolvedor = new ArrayList<>();
        LOGGER.error(" =============== Campos do Modelo que não passaram na validação ==========================");
        for (FieldError erro: ex.getBindingResult().getFieldErrors() ) {
            errosParaDesenvolvedor.add(erro.toString());
        }

        List<String> errosParaUsuario = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getDefaultMessage()).collect(Collectors.toList());
        LOGGER.error(" =============== ERROS ENCONTRADOS ==========================");

        errosParaDesenvolvedor.stream().forEach(e -> {
            LOGGER.error(e);
        });

        LOGGER.error(" =============== FIM DOS ERROS ENCONTRADOS ==========================");
        Object obj =  ResponseDto.response(errosParaDesenvolvedor,
                HttpStatus.BAD_REQUEST,"Favor verifique todos os campos com validação",errosParaUsuario);
        return handleExceptionInternal(ex, obj, headers, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        String msgError = "";

        if(Objects.nonNull(ex.getMessage())) { msgError = ex.toString(); }

        LOGGER.error(" =============== Objeto não encontrado ==========================");
        Object obj =  ResponseDto.response(msgError,HttpStatus.NOT_FOUND,
                "Este recurso não foi encontrado");

        return handleExceptionInternal(ex, obj, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {

        String msgError = "";

        if(Objects.nonNull(ex.getMessage())) { msgError = ex.toString(); }

        LOGGER.error(" =============== Objeto não encontrado ==========================");
        Object obj =  ResponseDto.response(msgError,HttpStatus.NOT_FOUND,
                "Este recurso não foi encontrado");

        return handleExceptionInternal(ex, obj, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                    WebRequest request) {

        LOGGER.error(" =============== Campos DTO que não passaram na validação ==========================");
        List<String> errosParaDesenvolvedor = ex.getConstraintViolations().stream()
                .map(e ->  e.toString()).collect(Collectors.toList());

        List<String> errosParaUsuario = ex.getConstraintViolations().stream()
                .map(e -> e.getMessage()).collect(Collectors.toList());

        Object obj =  ResponseDto.response(errosParaDesenvolvedor,
                HttpStatus.BAD_REQUEST,"Favor verifique todos os campos com validação",errosParaUsuario);
        return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({BadValueException.class})
    public ResponseEntity<Object> handleBadValueException(BadValueException ex,
                                                                     WebRequest request) {

        LOGGER.error(" =============== Bad request front ==========================");

        Object obj =  ResponseDto.response(ex.toString(),
                HttpStatus.BAD_REQUEST,ex.getMessage(), Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NoContentException.class})
    public ResponseEntity<Object> handleNoContentException(NoContentException ex,
                                                          WebRequest request) {

        LOGGER.error(" =============== No content request front ==========================");

        Object obj =  ResponseDto.response(ex.toString(),
                HttpStatus.NO_CONTENT,ex.getMessage(), Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.NO_CONTENT, request);
    }
}
