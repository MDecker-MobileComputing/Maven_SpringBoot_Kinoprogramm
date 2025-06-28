package de.eldecker.spring.kinoprogramm.db;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository für {@link KinoprogrammTable}-Objekte mit {@code String} als Key.
 */
@Repository
public interface KinoprogrammRepository 
            extends CassandraRepository<KinoprogrammTable, String> {   
}
