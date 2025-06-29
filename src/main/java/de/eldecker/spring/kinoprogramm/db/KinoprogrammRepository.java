package de.eldecker.spring.kinoprogramm.db;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * Repository für {@link KinoprogrammTable}-Objekte mit {@code String} als Key.
 */
@Repository
public interface KinoprogrammRepository 
            extends CassandraRepository<KinoprogrammTable, String> {   
    
    /**
     * Findet alle Einträge mit Datum größer oder gleich angegebener Wert.
     * <br><br>
     * 
     * Die annotierte Query ist in "CQL" (Cassandra Query Language) geschrieben.
     * 
     * @param datum Startdatum (inklusiv) im Format "YYYY-MM-DD"
     * 
     * @return Liste mit Programm für alle Tage ab dem angegebenen Datum
     */
    @Query( "SELECT * FROM kinoprogramm WHERE datum >= ?0 ALLOW FILTERING" )
    List<KinoprogrammTable> findeAbStartDatum( String startDatum );
    
}
