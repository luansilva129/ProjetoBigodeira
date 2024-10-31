package br.com.atlas.bigodeira.backend.domainBase;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ServicosBase extends EntidadeBase {
    private String nome;
    private String descricao;
    private Double duracao;
    private Double preco;

    @Override
    public String toString() {
        return nome;
    }
}
