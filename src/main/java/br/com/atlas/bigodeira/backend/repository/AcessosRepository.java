package br.com.atlas.bigodeira.backend.repository;

import br.com.atlas.bigodeira.backend.domainBase.AcessoBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AcessosRepository extends JpaRepository<AcessoBase, Long> {
}
