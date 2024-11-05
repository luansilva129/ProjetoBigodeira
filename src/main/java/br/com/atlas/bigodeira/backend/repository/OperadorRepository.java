package br.com.atlas.bigodeira.backend.repository;

import br.com.atlas.bigodeira.backend.domainBase.domain.Operador;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperadorRepository extends JpaRepository<Operador, Integer> {


    Operador findByEmail(String email);
}
