package br.com.atlas.bigodeira.backend.service;

import br.com.atlas.bigodeira.backend.domainBase.EntidadeBase;
import org.springframework.data.repository.CrudRepository;



public interface CrudRepositoryCustom<EntityT extends EntidadeBase> extends CrudRepository<EntityT, Long> {
}
