package com.algaworks.dto;

import com.google.common.collect.Lists;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResponseDto<T> implements Serializable {

    private T data;

    private List<String> errors;
    private List<String> warns;
    private List<String> infos;
    private String mensagem;
    private URI uri;
    private ResponseEntity<?> response;

    private Integer status;

    public ResponseDto() {

    }

    public String getMensagem(){
        return this.mensagem;
    }

    public ResponseDto<T> setMensagem(String mensagem){
        this.mensagem = mensagem;
        return this;
    }

    public T getData() {
        return data;
    }

    public ResponseDto<T> setData(T data) {
        this.data = data;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public ResponseDto<T> setStatus(HttpStatus status) {
        this.status = status.value();
        return this;
    }

    public URI  getUri() {
        return uri;
    }

    public ResponseDto<T>  setUri(URI uri) {
        this.uri = uri;
        return this;
    }

    public ResponseEntity<?> getResponse() {
        return response;
    }

    public ResponseDto<T> setResponse(ResponseEntity response){
        this.response = response;
        return this;
    }

    public List<String> getErrors() {
        if (Objects.isNull(this.errors)) {
            this.errors = new ArrayList<>();
        }
        return errors;
    }

    public ResponseDto<T> setErrors(List<String> errors) {
        this.errors = errors;
        return this;
    }

//    public ResponseDto<T> setErrors(String... errors) {
//        this.errors = Lists.newArrayList(errors);
//        return this;
//    }

    public ResponseDto<T> setWarns(String... warns) {
        this.warns = Lists.newArrayList(warns);
        return this;
    }

    public ResponseDto<T> setWarns(List<String> warns) {
        this.warns = warns;
        return this;
    }

    public List<String> getWarns() {
        if (Objects.isNull(this.warns)) {
            this.warns = new ArrayList<>();
        }
        return warns;
    }

//    public ResponseDto<T> setInfos(String... infos) {
//        this.infos = Lists.newArrayList(infos);
//        return this;
//    }

    public ResponseDto<T> setInfos(List<String> infos) {
        this.infos = infos;
        return this;
    }

    public List<String> getInfos() {
        if (Objects.isNull(this.infos)) {
            this.infos = new ArrayList<>();
        }
        return infos;
    }

    public static ResponseDto response(Object data, HttpStatus status, String msg ){
        return new ResponseDto()
                .setData(data)
                .setStatus(status)
                .setMensagem(msg);
    }

    public static ResponseDto response(Object data, HttpStatus status, String msg, URI uri ){
        return new ResponseDto()
                .setData(data)
                .setStatus(status)
                .setUri(uri)
                .setMensagem(msg);
    }

    public static ResponseDto response(Object data, HttpStatus status, String msg, List<String> errors ){
        return new ResponseDto()
                .setData(data)
                .setStatus(status)
                .setErrors(errors)
                .setMensagem(msg);
    }

}
