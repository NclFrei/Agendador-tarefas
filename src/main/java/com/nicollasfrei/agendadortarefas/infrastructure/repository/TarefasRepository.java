package com.nicollasfrei.agendadortarefas.infrastructure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarefasRepository extends MongoRepository<TarefasRepository, String> {

}
