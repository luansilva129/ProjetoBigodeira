package br.com.atlas.bigodeira.backend.repository;


import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoBase, Long> {


    List<AgendamentoBase> findByClienteId(Long clienteId);
}
