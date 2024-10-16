package br.com.atlas.bigodeira.backend.service;

import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
