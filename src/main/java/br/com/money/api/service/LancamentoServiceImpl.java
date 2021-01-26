package br.com.money.api.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import br.com.money.api.dto.LancamentoEstatisticaCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.money.api.event.RecursoCriadoEvent;
import br.com.money.api.model.Lancamento;
import br.com.money.api.repository.LancamentoRepository;
import br.com.money.api.repository.filter.LancamentoFilter;

@Service
public class LancamentoServiceImpl {

	private LancamentoRepository lancamentoRepository;
	private ApplicationEventPublisher publisher;

	@Autowired
	public LancamentoServiceImpl(LancamentoRepository lancamentoRepository, ApplicationEventPublisher publisher) {
		super();
		this.lancamentoRepository = lancamentoRepository;
		this.publisher = publisher;
	}

	

	public Page<Lancamento> listarTodos(LancamentoFilter lancamentoFilter, Pageable pageable) {

		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}

	// Faz busca pelo id
	public ResponseEntity<Lancamento> buscarPorId(Long id) {

		Optional<Lancamento> listarId = lancamentoRepository.findById(id);

		if (listarId.isPresent()) {
			return new ResponseEntity<Lancamento>(listarId.get(), HttpStatus.OK);
		}

		return ResponseEntity.noContent().build();

	}

	// Salva um registro
	public ResponseEntity<Lancamento> salvar(Lancamento lancamento, HttpServletResponse response) {

		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalvo);

	}

	// Deleta um registro
	public ResponseEntity<Void> deletar(Long id) {

		if (!lancamentoRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}

		lancamentoRepository.deleteById(id);

		return ResponseEntity.noContent().build();

	}

	// Retorna lista de estatisticas
	public List<LancamentoEstatisticaCategoria> porCategoriaService(){
		return this.lancamentoRepository.porCategoria(LocalDate.now());
	}

}
