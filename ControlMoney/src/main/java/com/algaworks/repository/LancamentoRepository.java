package com.algaworks.repository;

import com.algaworks.dto.LancamentoDto;
import com.algaworks.repository.Filter.LancamentoFilter;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
public class LancamentoRepository implements Serializable {

    private final transient SqlSession sqlSession;

    @Autowired
    public LancamentoRepository(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<LancamentoDto> findAllFilter(LancamentoFilter filter) {
        return this.sqlSession.selectList("LancamentoRepository.findAllFilter",filter.filters());
    }


}
