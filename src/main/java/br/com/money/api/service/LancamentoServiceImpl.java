package br.com.money.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.money.api.model.Lancamento;
import br.com.money.api.repository.LancamentoRepository;

@Service
public class LancamentoServiceImpl implements LacamentoService {

	private LancamentoRepository lancamentoRepository;

	public LancamentoServiceImpl(LancamentoRepository lancamentoRepository) {
		super();
		this.lancamentoRepository = lancamentoRepository;
	}

	// Retorna todos
	@Override
	public List<Lancamento> listarTodos() {

		return lancamentoRepository.findAll();
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


}
