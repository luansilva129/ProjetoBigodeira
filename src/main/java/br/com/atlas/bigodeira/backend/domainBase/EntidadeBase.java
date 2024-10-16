package br.com.atlas.bigodeira.backend.domainBase;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class EntidadeBase {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String createUser;
    private String modifyUser;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
}
