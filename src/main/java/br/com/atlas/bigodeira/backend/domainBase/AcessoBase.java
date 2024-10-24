package br.com.atlas.bigodeira.backend.domainBase;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class AcessoBase extends EntidadeBase {
    private String acao;
    private String status;
}
