package br.com.money.api.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.money.api.event.RecursoCriadoEvent;
import br.com.money.api.model.Categoria;
import br.com.money.api.repository.CategoriaRepository;

@Service
public class CategoriaServiceImpl implements CategoriaService {
	
	private CategoriaRepository categoriaRepository;
	private ApplicationEventPublisher publisher;
	
	@Autowired
	public CategoriaServiceImpl(CategoriaRepository categoriaRepository, ApplicationEventPublisher publisher) {
		super();
		this.categoriaRepository = categoriaRepository;
		this.publisher = publisher;
	}

	@Override
	public List<Categoria> listarTodos() {
	
		return categoriaRepository.findAll();
	}

	@Override
	public ResponseEntity<Categoria> buscarPorId(Long id) {
		Optional<Categoria> buscarId = categoriaRepository.findById(id);
		
		if (buscarId.isPresent()) {
			return new ResponseEntity<Categoria>(buscarId.get(), HttpStatus.OK);
		}

		return ResponseEntity.notFound().build();
	}

	@Override
	public ResponseEntity<Categoria> salvar(Categoria categoria, HttpServletResponse response) {
	
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
	}

}
