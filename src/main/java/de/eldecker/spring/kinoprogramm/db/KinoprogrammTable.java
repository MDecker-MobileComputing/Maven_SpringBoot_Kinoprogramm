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

    /** Datum im Format {@code YYYY-MM-DD}, z.B. {@code 2025-06-28}. */
    @PrimaryKey
    @Pattern( regexp = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$", 
              message = "Datum muss im Format YYYY-MM-DD sein (z.B. 2025-12-31)" )  
    private String datum;
    
    /** Liste der Vorstellungen an diesem Tag, kann auch leer sein. */
    private List<VorstellungUDF> vorstellungenList = new ArrayList<>();
    
    
    /** Default-Konstruktor */
    public KinoprogrammTable() {}
    

    /**
     * Konstruktor um Datum zu setzen.
     */
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
    

    public List<VorstellungUDF> getVorstellungenList() {
        
        return vorstellungenList;
    }

    public void setVorstellungenList( List<VorstellungUDF> vorstellungenList ) {
        
        this.vorstellungenList = vorstellungenList;
        sortiereVorstellungenNachStartzeit();
    }
    
    public void addVorstellung( VorstellungUDF vorstellung ) {
        
        this.vorstellungenList.add( vorstellung );
        sortiereVorstellungenNachStartzeit();
    }
       
    private void sortiereVorstellungenNachStartzeit() {
        
        vorstellungenList.sort( comparing(VorstellungUDF::getStartzeit) );
    }
    
    
    /**
     * String-Repräsentation.
     * 
     * @return String mit Datum und Anzahl Vorstellungen
     */
    @Override
    public String toString() {
        
        return format( "Am %s gibt es %d Vorstellung(en).", 
                       datum, vorstellungenList.size() );
    }

}
