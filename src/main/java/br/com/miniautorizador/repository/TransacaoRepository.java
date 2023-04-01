package br.com.miniautorizador.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.miniautorizador.domain.Transacao;

@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long> {
}
