package br.com.atlas.bigodeira.backend.domainBase.domain;

import br.com.atlas.bigodeira.backend.domainBase.PessoaBase;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Colaborador extends PessoaBase {
    private String especialidade;
    private LocalTime horario;
    private String diasDaSemana;


}
