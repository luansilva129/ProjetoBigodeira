package br.com.atlas.bigodeira.backend.service;

import br.com.atlas.bigodeira.backend.domainBase.EntidadeBase;
import br.com.atlas.bigodeira.backend.repository.CrudRepositoryCustom;

import java.util.Optional;

public class CrudRepositoryImpl<EntityT extends EntidadeBase> implements CrudRepositoryCustom<EntityT> {

    @Override
    public <S extends EntityT> S save(S entity) {
        return null;
    }

    @Override
    public <S extends EntityT> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<EntityT> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<EntityT> findAll() {
        return null;
    }

    @Override
    public Iterable<EntityT> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(EntityT entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends EntityT> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
