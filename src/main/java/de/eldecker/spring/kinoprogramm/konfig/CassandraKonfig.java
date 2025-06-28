package de.eldecker.spring.kinoprogramm.konfig;

import static org.springframework.data.cassandra.config.SchemaAction.CREATE_IF_NOT_EXISTS;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

/**
 * Konfiguration für Cassandra-Verbindung.
 */
@Configuration
@EnableCassandraRepositories(basePackages = "de.eldecker.spring.kinoprogramm.db" )
public class CassandraKonfig extends AbstractCassandraConfiguration {

    
    @Value("${spring.cassandra.keyspace-name}")
    private String _keyspaceName;

    @Override
    protected String getKeyspaceName() {

        return _keyspaceName;
    }
    
    
    @Override
    public SchemaAction getSchemaAction() {
        
        return CREATE_IF_NOT_EXISTS;
    }

    
    @Override
    public String[] getEntityBasePackages() {
        
        return new String[] { "de.eldecker.spring.kinoprogramm.db" };
    }

    
    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        
        return Collections.singletonList(
            CreateKeyspaceSpecification.createKeyspace( _keyspaceName )
                .ifNotExists()
                .with( KeyspaceOption.DURABLE_WRITES, true )
                .withSimpleReplication( 1 ) // Replication factor = 1
        );
    }    
}
