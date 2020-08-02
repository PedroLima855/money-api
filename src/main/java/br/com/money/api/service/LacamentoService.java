package br.com.money.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.money.api.model.Lancamento;
import br.com.money.api.repository.filter.LancamentoFilter;

@Service
public interface LacamentoService {

	List<Lancamento> listarTodos(LancamentoFilter lancamentoFilter);
	ResponseEntity<Lancamento> buscarPorId(Long id);
	ResponseEntity<Lancamento> salvar(Lancamento lancamento, HttpServletResponse response);
	ResponseEntity<Void> deletar(Long id);
	
}
