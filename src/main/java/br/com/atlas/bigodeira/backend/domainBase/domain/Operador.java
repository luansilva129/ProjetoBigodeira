package br.com.atlas.bigodeira.backend.domainBase.domain;


import br.com.atlas.bigodeira.backend.domainBase.EntidadeBase;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Operador extends EntidadeBase {

    private String email;
    private String senha;
    private String nome;


}
