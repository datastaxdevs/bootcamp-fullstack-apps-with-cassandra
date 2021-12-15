package com.datastax.workshop.todo;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

/**
 * For Basic operations you can leverage on Interface only repository
 */
@Repository
public interface TodoRepositoryCassandra extends CassandraRepository<TodoEntity, UUID> {

}
