package br.com.atlas.bigodeira.backend.domainBase;

import br.com.atlas.bigodeira.backend.domainBase.domain.Cliente;
import br.com.atlas.bigodeira.backend.domainBase.domain.Colaborador;
import br.com.atlas.bigodeira.backend.domainBase.domain.TipoServico;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@Builder
public class AgendamentoBase extends EntidadeBase {

    private LocalDate data;
    private LocalTime horario;

    @ManyToOne
    @JoinColumn(name = "servicos_id")
    private ServicosBase servicosBase;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private Boolean status;


    public AgendamentoBase(LocalDate data, LocalTime horario, ServicosBase servicosBase, Colaborador colaborador, Cliente cliente, Boolean status) {
        this.data = data;
        this.horario = horario;
        this.servicosBase = servicosBase;
        this.colaborador = colaborador;
        this.cliente = cliente;
        this.status = status;
    }

    public AgendamentoBase() {

    }
}
