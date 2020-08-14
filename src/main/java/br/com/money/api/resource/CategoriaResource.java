package br.com.money.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.money.api.model.Categoria;
import br.com.money.api.service.CategoriaServiceImpl;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {

	private CategoriaServiceImpl categoriaServiceImpl;

	public CategoriaResource(CategoriaServiceImpl categoriaServiceImpl) {
		super();
		this.categoriaServiceImpl = categoriaServiceImpl;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
	public List<Categoria> listar() {
		return categoriaServiceImpl.listarTodos();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA')")
	public ResponseEntity<Categoria> buscarPorId(@PathVariable Long id) {
		return categoriaServiceImpl.buscarPorId(id);
	}

	@PostMapping
	@PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA')")
	public ResponseEntity<Categoria> criarCategoria(@Valid @RequestBody Categoria categoria,
			HttpServletResponse response) {
		return categoriaServiceImpl.salvar(categoria, response);
	}

}
