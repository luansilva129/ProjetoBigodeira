package br.com.atlas.bigodeira.backend.repository;

import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {


}
