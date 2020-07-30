package br.com.money.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.money.api.model.Pessoa;
import br.com.money.api.service.PessoaServiceImpl;

@RestController
@RequestMapping("/pessoas")
public class PessoaResource {


	private PessoaServiceImpl pessoaServiceImpl;
	
	public PessoaResource(PessoaServiceImpl pessoaServiceImpl) {
		super();
		this.pessoaServiceImpl = pessoaServiceImpl;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<Pessoa> listar() {
		return pessoaServiceImpl.listar();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Pessoa> buscarId(@PathVariable Long id) {
		return pessoaServiceImpl.buscarPorId(id);
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> salvar(@Valid @RequestBody Pessoa pessoa, HttpServletResponse response){
		return pessoaServiceImpl.salvarPessoa(pessoa, response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Pessoa> editarPessoa(@Valid @RequestBody Pessoa pessoa, @PathVariable Long id){
		return pessoaServiceImpl.editarPessoa(id, pessoa);
	}
	
	@PutMapping("/{id}/ativo")
	public ResponseEntity<Void> editarAtivo(@PathVariable Long id, @RequestBody Boolean ativo){
		return pessoaServiceImpl.atualizarAtivo(id, ativo);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id){
		return pessoaServiceImpl.deletarPessoa(id);
	}
	
	

}
