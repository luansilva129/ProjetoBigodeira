package br.com.atlas.bigodeira.backend.domainBase;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.*;

@Entity
@Getter
@Setter
public class PessoaBase extends EntidadeBase {
    private String nome;
    private String cpf;
    private String telefone;

    @Override
    public String toString() {
        return nome;
    }
}
