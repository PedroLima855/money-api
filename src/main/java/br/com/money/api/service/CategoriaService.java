package br.com.money.api.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.money.api.model.Categoria;

@Service
public interface CategoriaService {
	
	List<Categoria> listarTodos();
	ResponseEntity<Categoria> buscarPorId(Long id);
	ResponseEntity<Categoria> salvar(Categoria categoria, HttpServletResponse response);

}
