package br.com.money.api.resource;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.money.api.model.Lancamento;
import br.com.money.api.service.LancamentoServiceImpl;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoResource {


	private LancamentoServiceImpl lancamentoServiceImpl;
	
	public LancamentoResource(LancamentoServiceImpl lancamentoServiceImpl) {
		super();
		this.lancamentoServiceImpl = lancamentoServiceImpl;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Lancamento> listar(){
		return lancamentoServiceImpl.listarTodos();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Lancamento> buscarId(@PathVariable Long id){
		return lancamentoServiceImpl.buscarPorId(id);
	}






}
