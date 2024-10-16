package br.com.atlas.bigodeira.backend.domainBase;

import jakarta.persistence.Entity;
import jdk.jfr.Enabled;

@Entity
public class ServicosBase extends EntidadeBase {

    private Long id;
    private String nome;
    private String descricao;

}
