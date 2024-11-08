package br.com.atlas.bigodeira.backend.repository;


import br.com.atlas.bigodeira.backend.domainBase.AgendamentoBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<AgendamentoBase, Long> {


    List<AgendamentoBase> findByClienteId(Long clienteId);

    List<AgendamentoBase> findByClienteNomeContainingIgnoreCaseAndStatusIn(String clienteName, List<String> statuses);

    List<AgendamentoBase> findByColaboradorNomeContainingIgnoreCaseAndStatusIn(String colaboradorName, List<String> statuses);

    @Query("SELECT a FROM AgendamentoBase a WHERE " +
            "(a.cliente.nome LIKE :clienteName OR :clienteName IS NULL) AND " +
            "(a.colaborador.nome LIKE :colaboradorName OR :colaboradorName IS NULL) AND " +
            "(a.status LIKE :status OR :status IS NULL)")
    List<AgendamentoBase> filterAgendamentos(
            @Param("clienteName") String clienteName,
            @Param("colaboradorName") String colaboradorName,
            @Param("status") String status
    );

    @Query("SELECT COUNT(a) FROM AgendamentoBase a WHERE a.status = :status")
    long countByStatus(@Param("status") String status);

    @Query("SELECT a FROM AgendamentoBase a WHERE a.status = :status")
    String agendamentosAguardando(@Param("status") String status);
}
