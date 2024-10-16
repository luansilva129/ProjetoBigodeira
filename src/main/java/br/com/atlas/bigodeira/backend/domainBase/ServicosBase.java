package br.com.atlas.bigodeira.backend.domainBase;

import jakarta.persistence.Entity;

@Entity
public class ServicosBase extends EntidadeBase {
    private String nome;
    private String descricao;
}
