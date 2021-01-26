package br.com.money.api.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

import br.com.money.api.dto.LancamentoEstatisticaCategoria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.money.api.model.Lancamento;
import br.com.money.api.repository.filter.LancamentoFilter;
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
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO')")
	public Page<Lancamento> listar(LancamentoFilter lancamentoFilter, Pageable pageable){
		return lancamentoServiceImpl.listarTodos(lancamentoFilter, pageable);
	}

	@GetMapping("/estatisticas/por-categoria")
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO')")
	public List<LancamentoEstatisticaCategoria> estatisticaPorCategoria(){
		return lancamentoServiceImpl.porCategoriaService();
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO')")
	public ResponseEntity<Lancamento> buscarId(@PathVariable Long id){
		return lancamentoServiceImpl.buscarPorId(id);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO')")
	public ResponseEntity<Lancamento> salvarLancamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response){
		return lancamentoServiceImpl.salvar(lancamento, response);
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO')")
	public ResponseEntity<Void> deletarLancamento(@PathVariable Long id){
		return lancamentoServiceImpl.deletar(id);
	}



}
