package br.com.atlas.bigodeira.backend.service;

import br.com.atlas.bigodeira.backend.domainBase.ServicosBase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicosRepository extends JpaRepository<ServicosBase, Long> {
}