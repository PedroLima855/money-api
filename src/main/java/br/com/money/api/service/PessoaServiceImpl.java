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
	public Optional<Pessoa> buscarPorId(Long id) {
		Optional<Pessoa> pessoa = pessoaRepository.findById(id);
		return pessoa;
	}

	// Salva um registro
	public Pessoa salvarPessoa(Pessoa pessoa, HttpServletResponse response) {
		Pessoa pessoaSalva = pessoaRepository.save(pessoa);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));

		return pessoaSalva;
	}

	// Edita um registro
	public Pessoa editarPessoa(Long id, Pessoa pessoa) {
		pessoa.setId(id);
		pessoa = pessoaRepository.save(pessoa);

		return pessoa;
	}

	// Deleta um registro
	public void deletarPessoa(Long id) {
		pessoaRepository.deleteById(id);
	}

	// Edita a propriedade ativo
	public ResponseEntity<Void> atualizarAtivo(Long id, Boolean ativo) {

		if (!pessoaRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		Pessoa pessoa = pessoaRepository.getOne(id);
		pessoa.setAtivo(ativo);
		pessoaRepository.save(pessoa);

		return ResponseEntity.noContent().build();
	}

}
