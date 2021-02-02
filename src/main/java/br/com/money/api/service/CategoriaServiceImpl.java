package br.com.money.api.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import br.com.money.api.dto.LancamentoEstatisticaCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.money.api.event.RecursoCriadoEvent;
import br.com.money.api.model.Categoria;
import br.com.money.api.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl {

	private CategoriaRepository categoriaRepository;
	private ApplicationEventPublisher publisher;

	@Autowired
	public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ApplicationEventPublisher publisher) {
		this.categoriaRepository = categoriaRepository;
		this.publisher = publisher;
	}

	// Lista as categorias
	public List<Categoria> listarTodos() {

		return categoriaRepository.findAll();
	}

	// Faz uma busca de categoria por id
	public Optional<Categoria> buscarPorId(Long id) {
		Optional<Categoria> categoria = categoriaRepository.findById(id);
		return categoria;
	}

	// Salva uma categoria
	public Categoria salvar(Categoria categoria, HttpServletResponse response) {

		Categoria categoriaSalva = categoriaRepository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));

		return categoriaSalva;
	}

}
