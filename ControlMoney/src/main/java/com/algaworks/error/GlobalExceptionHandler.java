package com.algaworks.error;

import com.algaworks.dto.ResponseDto;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.ibatis.builder.BuilderException;
import org.apache.ibatis.ognl.ExpressionSyntaxException;
import org.mybatis.spring.MyBatisSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

       LOGGER.error(" =============== Cliente passando campos desconhecido ==========================");

        List<String> camposAceitos = getCamposAceitos(ex);

        String camposNaoAceito = getErrorRoot(ex).getPropertyName();

        List<String> erros = Arrays.asList("Campos não aceitos: " +camposNaoAceito, "Campos aceitos: " + camposAceitos);

        ResponseDto res =  ResponseDto.response(ex.getCause() != null ? ex.getCause().getMessage() : ex.toString(),
                HttpStatus.BAD_REQUEST,"Passando campo desconhecido: " + camposNaoAceito, erros);

        return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
    }

    private UnrecognizedPropertyException getErrorRoot(HttpMessageNotReadableException ex) {
        return (UnrecognizedPropertyException) ExceptionUtils.getRootCause(ex);
    }

    private List<String> getCamposAceitos(HttpMessageNotReadableException ex) {
        return getPropriedadeErro(ex).stream().map(campo -> campo.toString()).collect(Collectors.toList());
    }

    private Collection<Object> getPropriedadeErro(HttpMessageNotReadableException ex) {
        return ((UnrecognizedPropertyException) ExceptionUtils.getRootCause(ex)).getKnownPropertyIds();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        LOGGER.error(" =============== Campos do Modelo que não passaram na validação ==========================");
        List<String> errosParaDesenvolvedor = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.toString()).collect(Collectors.toList());

        List<String> errosParaUsuario = ex.getBindingResult().getFieldErrors().stream()
                .map(e -> e.getDefaultMessage()).collect(Collectors.toList());

        ResponseDto res =  ResponseDto.response(errosParaDesenvolvedor,
                HttpStatus.BAD_REQUEST,"Favor verifique todos os campos com validação",errosParaUsuario);
        return handleExceptionInternal(ex, res, headers, HttpStatus.BAD_REQUEST, request);
    }


    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {

        String msgError = "";

        if(Objects.nonNull(ex.getMessage())) { msgError = ExceptionUtils.getRootCauseMessage(ex); }

        LOGGER.error(" =============== Objeto não encontrado ==========================");
        ResponseDto res =  ResponseDto.response(msgError,HttpStatus.NOT_FOUND, "Este recurso não foi encontrado");

        return handleExceptionInternal(ex, res, null, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    public ResponseEntity<Object> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {


        LOGGER.error(" =============== Objeto não encontrado ==========================");

        String msgError = "";
        if(Objects.nonNull(ex.getMessage())) { msgError = ex.toString(); }

        ResponseDto res =  ResponseDto.response(msgError,HttpStatus.NOT_FOUND, "Este recurso não foi encontrado");

        return handleExceptionInternal(ex, res, null, HttpStatus.NOT_FOUND, request);
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

    @ExceptionHandler({DataIntegrityViolationException.class, TransactionSystemException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex,
                                                                        WebRequest request) {

        LOGGER.error(" =============== DataIntegrityViolationException==========================");

        Object obj =  ResponseDto.response(ExceptionUtils.getRootCauseMessage(ex),
                HttpStatus.NO_CONTENT,ex.getMessage(), Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.OK, request);
    }


    @ExceptionHandler({MyBatisSystemException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(MyBatisSystemException ex,
                                                                        WebRequest request) {

        LOGGER.error(" =============== MYBATIS ERROR==========================");
        BuilderException eee = ((BuilderException) ex.getCause());
        String msg = ((ExpressionSyntaxException) eee.getCause()).getLocalizedMessage();

        ResponseDto res =  ResponseDto.response(ExceptionUtils.getRootCauseMessage(ex),
                HttpStatus.NO_CONTENT,"Erro no Mybatis", Arrays.asList(msg));
        return handleExceptionInternal(ex, res, null, HttpStatus.EXPECTATION_FAILED, request);
    }

    /**
     * EXCEÇÕES PERSONALIZADAS
     **/

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
        return handleExceptionInternal(ex, obj, null, HttpStatus.OK, request);
    }

    @ExceptionHandler({NegocioException.class})
    public ResponseEntity<Object> handleNegocioException(NegocioException ex,
                                                           WebRequest request) {

        LOGGER.error(" =============== NegocioException ==========================");

        Object obj =  ResponseDto.response(ex.toString(),
                HttpStatus.NO_CONTENT,ex.getMessage(), Arrays.asList(ex.getMessage()));
        return handleExceptionInternal(ex, obj, null, HttpStatus.OK, request);
    }



}
