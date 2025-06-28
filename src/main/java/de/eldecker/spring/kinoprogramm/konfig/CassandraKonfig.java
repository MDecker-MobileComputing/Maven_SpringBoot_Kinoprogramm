package de.eldecker.spring.kinoprogramm.konfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;


@Configuration
@EnableCassandraRepositories(basePackages = "de.eldecker.spring.kinoprogramm.db" )
public class CassandraKonfig extends AbstractCassandraConfiguration {

    @Override
    protected String getKeyspaceName() {

        return "kinoprogramm";
    }
    
    
    @Override
    public SchemaAction getSchemaAction() {
        
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    
    @Override
    public String[] getEntityBasePackages() {
        
        return new String[] { "de.eldecker.spring.kinoprogramm.db" };
    }
}
