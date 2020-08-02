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
import br.com.money.api.model.Lancamento;
import br.com.money.api.repository.LancamentoRepository;
import br.com.money.api.repository.filter.LancamentoFilter;

@Service
public class LancamentoServiceImpl implements LacamentoService {

	private LancamentoRepository lancamentoRepository;
	private ApplicationEventPublisher publisher;

	@Autowired
	public LancamentoServiceImpl(LancamentoRepository lancamentoRepository, ApplicationEventPublisher publisher) {
		super();
		this.lancamentoRepository = lancamentoRepository;
		this.publisher = publisher;
	}

	// Retorna todos
	@Override
	public List<Lancamento> listarTodos(LancamentoFilter lancamentoFilter) {

		return lancamentoRepository.filtrar(lancamentoFilter);
	}

	// Faz busca pelo id
	@Override
	public ResponseEntity<Lancamento> buscarPorId(Long id) {

		Optional<Lancamento> listarId = lancamentoRepository.findById(id);

		if (listarId.isPresent()) {
			return new ResponseEntity<Lancamento>(listarId.get(), HttpStatus.OK);
		}

		return ResponseEntity.noContent().build();

	}

	// Salva um registro
	@Override
	public ResponseEntity<Lancamento> salvar(Lancamento lancamento, HttpServletResponse response) {

		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);

	}

	@Override
	public ResponseEntity<Void> deletar(Long id) {

		if (!lancamentoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		lancamentoRepository.deleteById(id);

		return ResponseEntity.noContent().build();

	}

}
