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
import br.com.money.api.model.Pessoa;
import br.com.money.api.repository.PessoaRepository;

@Service
public class PessoaServiceImpl {
	
	
	private PessoaRepository pessoaRepository;
	private ApplicationEventPublisher publisher;
	
	@Autowired
	public PessoaServiceImpl(PessoaRepository pessoaRepository, ApplicationEventPublisher publisher) {
		super();
		this.pessoaRepository = pessoaRepository;
		this.publisher = publisher;
	}

	// Lista todos
	public List<Pessoa> listar() {
		
		return pessoaRepository.findAll();
	}

	// Faz uma busca por Id
	public ResponseEntity<Pessoa> buscarPorId(Long id) {
		
		Optional<Pessoa> listarId = pessoaRepository.findById(id);

		if (listarId.isPresent()) {
			return new ResponseEntity<Pessoa>(listarId.get(), HttpStatus.OK);
		}
		
		return ResponseEntity.notFound().build();
	}

	// Salva um registro
	public ResponseEntity<Pessoa> salvarPessoa(Pessoa pessoa, HttpServletResponse response) {
		
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(pessoaSalva);
	}

	// Edita um registro
	public ResponseEntity<Pessoa> editarPessoa(Long id, Pessoa pessoa) {
	
		if (!pessoaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		pessoa.setId(id);
		pessoa = pessoaRepository.save(pessoa);

		return ResponseEntity.ok(pessoa);
	}

	// Deleta um registro
	public ResponseEntity<Void> deletarPessoa(Long id) {
	
		if (!pessoaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		pessoaRepository.deleteById(id);

		return ResponseEntity.noContent().build();
	}

	// Edita a propriedade ativo
	public ResponseEntity<Void> atualizarAtivo(Long id, Boolean ativo) {
		
		if(!pessoaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		
		Pessoa pessoa = pessoaRepository.getOne(id);
		pessoa.setAtivo(ativo);
		pessoaRepository.save(pessoa);
		
		return ResponseEntity.noContent().build();
	}
	

}
