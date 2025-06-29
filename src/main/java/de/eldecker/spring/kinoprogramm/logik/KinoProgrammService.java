package de.eldecker.spring.kinoprogramm.logik;

import static de.eldecker.spring.kinoprogramm.logik.DatumZeitHelferlein.istDatumOkay;
import static de.eldecker.spring.kinoprogramm.logik.DatumZeitHelferlein.getDatumHeute;
import static java.util.Comparator.comparing;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.eldecker.spring.kinoprogramm.db.KinoprogrammRepository;
import de.eldecker.spring.kinoprogramm.db.KinoprogrammTable;


/**
 * Bean mit Geschäftslogik.
 */
@Service
public class KinoProgrammService {
    
    private static final Logger LOG = LoggerFactory.getLogger( KinoProgrammService.class );

    
    /** Bean für Zugriff auf Cassandra-Datenbank. */
    @Autowired
    private KinoprogrammRepository _repo; 
    
    
    /**
     * Gibt Liste aller zukünftigen Kinoprogramme zurück.
     * 
     * @return Liste Kinoprogramme, aufsteigend nach Datum sortiert
     */
    public List<KinoprogrammTable> getProgrammAbHeute() {
        
        final String datumHeute = getDatumHeute();
        
        final List<KinoprogrammTable> kinoProgrammListe = 
                                _repo.findeAbStartDatum( datumHeute );
        
        // Wir müssen mit Java sortieren, weil Cassandra im vorliegenden
        // Fall nicht nach dem Schlüssel sortieren kann (wir können den
        // Schlüssel nicht mit EQ auf einen einzelnen Wert oder mit IN
        // auf mehrere Werte einschränken); die Datensätze sind nämlich
        // im allgemeinen Fall auf verschiedene Cluster-Knoten verteilt
        kinoProgrammListe.sort( comparing( KinoprogrammTable::getDatum ) );
        
        return kinoProgrammListe;
    }

    
    /**
     * Gibt das Kinoprogramm für einen bestimmten Tag zurück.
     * 
     * @param datum Datum im Format "YYYY-MM-DD"; muss gültig sein
     * 
     * @return Optional mit Kinoprogramm für den angegebenen Tag;
     *         leeres Optional, wenn das Datum ungültig ist oder
     *         kein Programm für diesen Tag existiert
     */
    public Optional<KinoprogrammTable> getProgrammFuerDatum( String datum ) {

        if ( !istDatumOkay( datum ) ) {
            
            LOG.info( "Ungültiges Datum \"{}\" für Abfrage Programm.", datum );
            return Optional.empty();
        }

        return  _repo.findById( datum );
    }

}
