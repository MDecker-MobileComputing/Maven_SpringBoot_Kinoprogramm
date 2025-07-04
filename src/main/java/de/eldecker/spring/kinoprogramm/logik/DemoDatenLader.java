package de.eldecker.spring.kinoprogramm.logik;

import static de.eldecker.spring.kinoprogramm.logik.DatumZeitHelferlein.getDatumHeutePlusTage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import de.eldecker.spring.kinoprogramm.db.KinoprogrammRepository;
import de.eldecker.spring.kinoprogramm.db.KinoprogrammTable;
import de.eldecker.spring.kinoprogramm.db.VorstellungUDT;


/**
 * Diese Bean lädt beim Start der Anwendung einige Vorstellungen in die Cassandra-Datenbank.
 */
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
                        
            createProgrammHeutePlus1();
            createProgrammHeutePlus2();
            createProgrammHeutePlus3();
            
            final long anzahlNeu = _kinoProgrammRepo.count();
            LOG.info( "Es ist jetzt für {} Tage ein Kinoprogramm gespeichert.", anzahlNeu ); 
        }
    }
    
    
    /**
     * Erzeugt Programm für morgen.
     */
    private void createProgrammHeutePlus1() {

        final String datum = getDatumHeutePlusTage( 1 );
        
        final KinoprogrammTable programm = new KinoprogrammTable( datum );
        
        final VorstellungUDT vorstellung = new VorstellungUDT( "Überraschungsfilm", 120, "20:15" );
        programm.addVorstellung( vorstellung );
        
        _kinoProgrammRepo.save( programm );
    }

    
    /**
     * Erzeugt Programm für übermorgen.
     */
    private void createProgrammHeutePlus2() {

        final String datum = getDatumHeutePlusTage( 2 );
        
        final KinoprogrammTable programm = new KinoprogrammTable( datum );
        
        final VorstellungUDT vorstellung = new VorstellungUDT( "Saat des Erbrechens (Teil VIII)", 90, "19:30" );
        programm.addVorstellung( vorstellung );
        
        _kinoProgrammRepo.save( programm );
    }

    
    /**
     * Erzeugt Programm für heute in drei Tagen.
     */
    private void createProgrammHeutePlus3() {

        final String datum = getDatumHeutePlusTage( 3 );
        
        final KinoprogrammTable programm = new KinoprogrammTable( datum );
        
        final VorstellungUDT vorstellung1 = new VorstellungUDT( "Käpt’n Krümel und die Keks-Piraten",  75, "15:00" );
        final VorstellungUDT vorstellung2 = new VorstellungUDT( "Der Spion im Hörsaal"              , 100, "20:00" );
        
        programm.addVorstellung( vorstellung1 );
        programm.addVorstellung( vorstellung2 );
        
        _kinoProgrammRepo.save( programm );
    }

}
