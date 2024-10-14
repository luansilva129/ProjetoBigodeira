package br.com.atlas.bigodeira.backend.service;

import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

    

}
