package br.com.money.api.repository.lancamento;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.com.money.api.dto.LancamentoEstatisticaCategoria;
import br.com.money.api.dto.LancamentoEstatisticaDia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import br.com.money.api.model.Lancamento;
import br.com.money.api.repository.filter.LancamentoFilter;

public class LancamentoRepositoryImpl implements LancamentoRepositoryQuery {

	@PersistenceContext
	private EntityManager manager;

	@Override
	public List<LancamentoEstatisticaDia> porDia(LocalDate mesReferencia) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaDia> criteria = criteriaBuilder.createQuery(LancamentoEstatisticaDia.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(criteriaBuilder.construct(LancamentoEstatisticaDia.class,
				root.get("tipo"), root.get("dataVencimento"), criteriaBuilder.sum(root.get("valor"))));

		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

		criteria.where(criteriaBuilder.greaterThanOrEqualTo(root.get("dataVencimento"), primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get("dataVencimento"), ultimoDia));

		criteria.groupBy(root.get("tipo"), root.get("dataVencimento"));

		TypedQuery<LancamentoEstatisticaDia> query = manager.createQuery(criteria);

		return query.getResultList();
	}

	@Override
	public List<LancamentoEstatisticaCategoria> porCategoria(LocalDate mesReferencia) {

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<LancamentoEstatisticaCategoria> criteria = criteriaBuilder.createQuery(LancamentoEstatisticaCategoria.class);

		Root<Lancamento> root = criteria.from(Lancamento.class);

		criteria.select(criteriaBuilder.construct(LancamentoEstatisticaCategoria.class,
				root.get("categoria"), criteriaBuilder.sum(root.get("valor"))));

		LocalDate primeiroDia = mesReferencia.withDayOfMonth(1);
		LocalDate ultimoDia = mesReferencia.withDayOfMonth(mesReferencia.lengthOfMonth());

		criteria.where(criteriaBuilder.greaterThanOrEqualTo(root.get("dataVencimento"), primeiroDia),
				criteriaBuilder.lessThanOrEqualTo(root.get("dataVencimento"), ultimoDia));

		criteria.groupBy(root.get("categoria"));

		TypedQuery<LancamentoEstatisticaCategoria> query = manager.createQuery(criteria);

		return query.getResultList();
	}

	@Override
	public Page<Lancamento> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Lancamento> criteria = builder.createQuery(Lancamento.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		TypedQuery<Lancamento> query = manager.createQuery(criteria);
		adicionarRestricoesDePaginacao(query, pageable);
		
		return new PageImpl<>(query.getResultList(), pageable, total(lancamentoFilter));
	}

	private Predicate[] criarRestricoes(LancamentoFilter lancamentoFilter, CriteriaBuilder builder,
			Root<Lancamento> root) {
		List<Predicate> predicates = new ArrayList<>();
		
		if (!StringUtils.isEmpty(lancamentoFilter.getDescricao())) {
			predicates.add(builder.like(
					builder.lower(root.get("descricao")), "%" + lancamentoFilter.getDescricao().toLowerCase() + "%"));
		}
		
		if (lancamentoFilter.getDataVencimentoDe() != null) {
			predicates.add(
					builder.greaterThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoDe()));
		}
		
		if (lancamentoFilter.getDataVencimentoAte() != null) {
			predicates.add(
					builder.lessThanOrEqualTo(root.get("dataVencimento"), lancamentoFilter.getDataVencimentoAte()));
		}
		
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private void adicionarRestricoesDePaginacao(TypedQuery<Lancamento> query, Pageable pageable) {
		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;
		
		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);
	}
	
	private Long total(LancamentoFilter lancamentoFilter) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
		Root<Lancamento> root = criteria.from(Lancamento.class);
		
		Predicate[] predicates = criarRestricoes(lancamentoFilter, builder, root);
		criteria.where(predicates);
		
		criteria.select(builder.count(root));
		return manager.createQuery(criteria).getSingleResult();
	}
	
}