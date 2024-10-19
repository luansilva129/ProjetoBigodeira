package br.com.atlas.bigodeira.backend.repository;


import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AgendamentoRepository extends JpaRepository<AgendamentoBase, Long> {


}
