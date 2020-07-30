package br.com.money.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.money.api.model.Pessoa;

@Service
public interface PessoaService {
	
	List<Pessoa> listar();
	ResponseEntity<Pessoa> buscarPorId(Long id);
	ResponseEntity<Pessoa> salvarPessoa(Pessoa pessoa, HttpServletResponse response);
	ResponseEntity<Pessoa> editarPessoa(Long id, Pessoa pessoa);
	ResponseEntity<Void> deletarPessoa(Long id);
	ResponseEntity<Void> atualizarAtivo (Long id, Boolean ativo);

}
