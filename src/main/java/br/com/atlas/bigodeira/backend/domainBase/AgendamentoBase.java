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
@AllArgsConstructor
@NoArgsConstructor
public class AgendamentoBase extends ServicosBase {

    private LocalDate data;
    private LocalTime horario;

    @Embedded
    private TipoServico tipoServico;

    @ManyToOne
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private Boolean status;
}
