package de.eldecker.spring.kinoprogramm.db;

import static java.lang.String.format;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;


/**
 * Eine bestimmte Vorstellung, i.d.R. ein Film, evtl. aber auch mehrere Filme
 * (z.B. Vor- und Hauptfilm). Wir gehen von einem kleinen Kino mit nur einem
 * Saal aus.
 */
@UserDefinedType( "Vorstellung" )
public class VorstellungUDF {

    /** Titel der Vorstellung, z.B. Name Film, "Sneak Preview" oder "Star-Trek-Nacht". */
    @NotBlank( message = "Titel der Vorstellung darf nicht leer sein" )
    private String titel;
        
    @Min( value = 0 , message = "Stunde von Uhrzeit darf nicht negativ sein"       )
    @Max( value = 23, message = "Stunde von Uhrzeit darf nicht größer als 23 sein" )
    private int startStunde;

    @Min( value = 0 , message = "Minuten von Uhrzeit darf nicht negativ sein"  )
    @Max( value = 59, message = "Minute von Uhrzeit darf nicht größer 59 sein" )
    private int startMinute;

    /** Dauer der Vorstellung in Minuten, z.B. 90 Minuten. */
    @Min( value =  20, message = "Vorstellung muss mindestens 20 Minuten dauern" )
    @Max( value = 600, message = "Vorstellung darf höchstens 10 Stunden dauern"  )
    private int dauerMinuten;
    
    
    /** Leerer Default-Konstruktor */
    public VorstellungUDF() {}
    
    
    /**
     * Konstruktor um alle Attribute der Vorstellung zu setzen.
     */
    public VorstellungUDF( String titel, int dauerMinuten, int startStunde, int startMinute ) {
        
        this.titel        = titel;
        this.dauerMinuten = dauerMinuten;
        this.startStunde  = startStunde;
        this.startMinute  = startMinute;
    }

    public String getTitel() {
        
        return titel;
    }

    public void setTitel( String titel ) {
        
        this.titel = titel;
    }

    public int getDauerMinuten() {
        
        return dauerMinuten;
    }

    public void setDauerMinuten( int dauerMinuten ) {
        
        this.dauerMinuten = dauerMinuten;
    }

    public int getStartStunde() {
        
        return startStunde;
    }

    public void setStartStunde( int startStunde ) {
        
        this.startStunde = startStunde;
    }

    public int getStartMinute() {
        
        return startMinute;
    }

    public void setStartMinute( int startMinute ) {
        
        this.startMinute = startMinute;
    }

    
    /**
     * Convenience-Methode für Abfrage von Startzeit der Vorstellung.
     * 
     * @return Startzeit von Vorstellung als String im Format {@code hh:mm Uhr},
     *         z.B. {@code 09:00 Uhr } oder {@code 12:15 Uhr}.
     */
    public String getStartzeit() {
        
        return format( "%02d:%02d", startStunde, startMinute );
    }
    
    /**
     * String-Repräsentation.
     * 
     * @return String mit Titel, Startzeit und Dauer der Vorstellung 
     */
    @Override
    public String toString() {
        
        return format( "%s (Beginn: %s, %d Minuten)", 
                       titel, getStartzeit(), dauerMinuten );
    }

}
