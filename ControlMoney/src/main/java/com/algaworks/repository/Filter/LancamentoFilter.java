package com.algaworks.repository.Filter;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public @Data
class LancamentoFilter implements Serializable {

    private String descricao;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoDe;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataVencimentoAte;

    public Map<String,Object> filters(){
        Map<String,Object> filter = new HashMap<>();
        filter.put("descricao", getDescricao());
        filter.put("dataVencimentoDe", getDataVencimentoDe());
        filter.put("dataVencimentoAte", getDataVencimentoAte());
        return filter;
    }
}
