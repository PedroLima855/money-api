package br.com.money.api.service;

import java.io.InputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import br.com.money.api.dto.LancamentoEstatisticaCategoria;
import br.com.money.api.dto.LancamentoEstatisticaDia;
import br.com.money.api.dto.LancamentoEstatisticaPessoa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.money.api.event.RecursoCriadoEvent;
import br.com.money.api.model.Lancamento;
import br.com.money.api.repository.LancamentoRepository;
import br.com.money.api.repository.filter.LancamentoFilter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

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

	public byte[] relatorioPorPessoa(LocalDate inicio, LocalDate fim) throws JRException {
		List<LancamentoEstatisticaPessoa> dados = lancamentoRepository.porPessoa(inicio, fim);

		Map<String, Object> parametros = new HashMap<>();
		parametros.put("DT_INICIO", Date.valueOf(inicio));
		parametros.put("DT_FIM", Date.valueOf(fim));
		parametros.put("REPORT_LOCALE", new Locale("pt", "BR"));

		InputStream inputStream = this.getClass().getResourceAsStream("/relatorios/lancamento-por-pessoa.jasper");

		JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros,
				new JRBeanCollectionDataSource(dados));

		return JasperExportManager.exportReportToPdf(jasperPrint);
	}

	public Page<Lancamento> listarTodos(LancamentoFilter lancamentoFilter, Pageable pageable) {

		return lancamentoRepository.filtrar(lancamentoFilter, pageable);
	}

	// Retorna lista de estatisticas por categoria
	public List<LancamentoEstatisticaCategoria> porCategoriaService() {
		return this.lancamentoRepository.porCategoria(LocalDate.now());
	}

	// Retorna lista de estatisticas por dia
	public List<LancamentoEstatisticaDia> porDiaService() {
		return this.lancamentoRepository.porDia(LocalDate.now());
	}

	// Faz busca pelo id
	public Optional<Lancamento> buscarPorId(Long id) {

		Optional<Lancamento> lancamento = lancamentoRepository.findById(id);

		return lancamento;
	}

	// Salva um registro
	public Lancamento salvar(Lancamento lancamento, HttpServletResponse response) {

		Lancamento lancamentoSalvo = lancamentoRepository.save(lancamento);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalvo.getId()));

		return lancamentoSalvo;
	}

	// Deleta um registro
	public void deletar(Long id) {
		lancamentoRepository.deleteById(id);
	}

}
