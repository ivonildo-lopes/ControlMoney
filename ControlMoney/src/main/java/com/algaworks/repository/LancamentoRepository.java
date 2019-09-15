package com.algaworks.repository;

import com.algaworks.dto.LancamentoDto;
import com.algaworks.model.Lancamento;
import com.algaworks.repository.Filter.LancamentoFilter;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class LancamentoRepository implements Serializable {

    private final transient SqlSession sqlSession;

    @Autowired
    public LancamentoRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Page<LancamentoDto> findAllFilter(LancamentoFilter filter, Pageable pageable) {

        Map<String, Object> filter2 = new HashMap();
        filter2 = filter.filters();

        int paginaAtual = pageable.getPageNumber() == 0 ? 0 :pageable.getPageNumber() - 1;
        int totalRegistroPorPagina = pageable.getPageSize();
        int primeiroRegistro = paginaAtual * totalRegistroPorPagina;

        filter2.put("paginaAtual",paginaAtual);
        filter2.put("totalRegistroPorPagina",totalRegistroPorPagina);
        filter2.put("primeiroRegistro",primeiroRegistro);

        List<LancamentoDto> lista = this.sqlSession.selectList("LancamentoRepository.findAllFilter",filter2);

        return new PageImpl<LancamentoDto>(lista,pageable,lista.size());
    }


}
