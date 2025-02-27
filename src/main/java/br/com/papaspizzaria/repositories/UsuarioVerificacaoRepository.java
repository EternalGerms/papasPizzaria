package br.com.papaspizzaria.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.papaspizzaria.entities.UsuarioVerificacao;

public interface UsuarioVerificacaoRepository extends JpaRepository<UsuarioVerificacao, Long>{
	
	public Optional<UsuarioVerificacao> findByUuid(UUID uuid);

}
