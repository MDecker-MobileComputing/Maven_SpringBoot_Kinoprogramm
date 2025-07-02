package de.eldecker.spring.kinoprogramm.db;

import static de.eldecker.spring.kinoprogramm.logik.DatumZeitHelferlein.formatiereDatum;
import static java.lang.String.format;
import static java.util.Comparator.comparing;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import jakarta.validation.constraints.Pattern;


/**
 * Ein Objekt dieser Klasse enthält die Vorstellung/Filmen
 * an einem bestimmten Tag. 
 */


@Table( "kinoprogramm" )
public class KinoprogrammTable {

    @PrimaryKey
    private String datum;
    
    private List<VorstellungUDT> vorstellungenList = new ArrayList<>();
    
    
    public KinoprogrammTable() {}
    

    public KinoprogrammTable( String datum ) {
        
        this.datum = datum;
    }
    
    
    
    public String getDatum() {
        
        return datum;
    }

    public void setDatum( String datum ) {
        
        this.datum = datum;
    }

    /**
     * Convenience-Methode für formatiertes Datum
     * 
     * @return Datum mit Format {@code dd.MM.yyyy (Wochentag)}
     */
    public String getDatumFormatiert() {
        
        return formatiereDatum( datum ); 
    }
    

    public List<VorstellungUDT> getVorstellungenList() {
        
        return vorstellungenList;
    }

    public void setVorstellungenList( List<VorstellungUDT> vorstellungenList ) {
        
        this.vorstellungenList = vorstellungenList;
        sortiereVorstellungenNachStartzeit();
    }
    
    public void addVorstellung( VorstellungUDT vorstellung ) {
        
        this.vorstellungenList.add( vorstellung );
        sortiereVorstellungenNachStartzeit();
    }
       
    private void sortiereVorstellungenNachStartzeit() {
        
        vorstellungenList.sort( comparing( VorstellungUDT::getStartzeit ) );
    }
    
    
    /**
     * String-Repräsentation.
     * 
     * @return String mit Datum und Anzahl der Vorstellungen
     */
    @Override
    public String toString() {
        
        return format( "Am %s gibt es %d Vorstellung(en).", 
                       datum, vorstellungenList.size() );
    }

}
