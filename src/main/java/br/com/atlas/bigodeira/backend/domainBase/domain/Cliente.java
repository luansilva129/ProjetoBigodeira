package br.com.atlas.bigodeira.backend.domainBase.domain;

import br.com.atlas.bigodeira.backend.domainBase.PessoaBase;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Cliente extends PessoaBase {
    private String email;
    private String senha;

}
