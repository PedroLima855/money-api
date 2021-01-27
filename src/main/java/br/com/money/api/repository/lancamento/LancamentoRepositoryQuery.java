package br.com.money.api.repository.lancamento;

import br.com.money.api.dto.LancamentoEstatisticaCategoria;
import br.com.money.api.dto.LancamentoEstatisticaDia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import br.com.money.api.model.Lancamento;
import br.com.money.api.repository.filter.LancamentoFilter;

public interface LancamentoRepositoryQuery {

	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia);
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia);
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
}
