package br.com.atlas.bigodeira.backend.domainBase.domain;

import br.com.atlas.bigodeira.backend.domainBase.PessoaBase;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Colaborador extends PessoaBase {
    private String especialidade;
    private LocalTime horarioInicio;
    private LocalTime horarioFim;
    private String diasDaSemana;

}
