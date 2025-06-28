package de.eldecker.spring.kinoprogramm.logik;

import static de.eldecker.spring.kinoprogramm.logik.DatumsHelferlein.getDatumHeutePlusTage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.eldecker.spring.kinoprogramm.db.KinoprogrammRepository;
import de.eldecker.spring.kinoprogramm.db.KinoprogrammTable;
import de.eldecker.spring.kinoprogramm.db.VorstellungUDF;


@Component
public class DemoDatenLader implements ApplicationRunner {

    private static final Logger LOG = LoggerFactory.getLogger( DemoDatenLader.class );
    
    /** Repo für Zugriff Tabelle in Cassandra-DB. */
    @Autowired
    private KinoprogrammRepository _kinoProgrammRepo;
    
    
    /**
     * Diese Methode wird unmittelbar nach dem Start der Anwendung ausgeführt.
     * 
     * @param args wird nicht ausgewertet
     */
    @Override
    public void run( ApplicationArguments args ) throws Exception {
        
        final long anzahlAlt = _kinoProgrammRepo.count();
        if ( anzahlAlt > 0 ) {
            
            LOG.info( "Es ist schon für {} Tage ein Kinoprogramm gespeichert, lade keine Demo-Daten.", 
                      anzahlAlt );            
        } else {
            
            LOG.info( "Noch keine Kinoprogramme gespeichert, lade Demo-Daten." );
                        
            createProgrammMorgen();
            createProgrammUebermorgen();
            
            final long anzahlNeu = _kinoProgrammRepo.count();
            LOG.info( "Es ist jetzt für {} Tage ein Kinoprogramm gespeichert.", anzahlNeu ); 
        }
    }
    
    private void createProgrammMorgen() {

        final String datumMorgen = getDatumHeutePlusTage( 1 );
        
        final KinoprogrammTable programm = new KinoprogrammTable( datumMorgen );
        
        final VorstellungUDF vorstellung = new VorstellungUDF( "Überraschungsfilm", 120, 20, 15 );
        programm.addVorstellung( vorstellung );
        
        _kinoProgrammRepo.save( programm );
    }

    private void createProgrammUebermorgen() {

        final String datumMorgen = getDatumHeutePlusTage( 2 );
        
        final KinoprogrammTable programm = new KinoprogrammTable( datumMorgen );
        
        final VorstellungUDF vorstellung = new VorstellungUDF( "Saat des Erbrechens VIII", 120, 19, 30 );
        programm.addVorstellung( vorstellung );
        
        _kinoProgrammRepo.save( programm );
    }

}
