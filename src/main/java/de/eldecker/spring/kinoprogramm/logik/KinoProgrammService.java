package de.eldecker.spring.kinoprogramm.logik;

import static de.eldecker.spring.kinoprogramm.logik.DatumsHelferlein.getDatumHeute;
import static java.util.Comparator.comparing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.eldecker.spring.kinoprogramm.db.KinoprogrammRepository;
import de.eldecker.spring.kinoprogramm.db.KinoprogrammTable;


/**
 * Bean mit Geschäftslogik.
 */
@Service
public class KinoProgrammService {

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
        kinoProgrammListe.sort( comparing( KinoprogrammTable::getDatum) );
        
        return kinoProgrammListe;
    }

}
