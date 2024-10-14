package br.com.atlas.bigodeira.backend.domainBase;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class PessoaBase extends EntidadeBase{

    private String nome;
    private String cpf;
    private String telefone;

}
