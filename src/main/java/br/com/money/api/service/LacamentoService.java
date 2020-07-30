package br.com.money.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.money.api.model.Lancamento;

@Service
public interface LacamentoService {

	List<Lancamento> listarTodos();
	ResponseEntity<Lancamento> buscarPorId(Long id);
}
