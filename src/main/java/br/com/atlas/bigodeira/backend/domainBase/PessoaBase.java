package br.com.atlas.bigodeira.backend.domainBase;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Getter
@Setter
@Entity
public class PessoaBase extends EntidadeBase{

    private String nome;
    private String cpf;
    private String telefone;

}
