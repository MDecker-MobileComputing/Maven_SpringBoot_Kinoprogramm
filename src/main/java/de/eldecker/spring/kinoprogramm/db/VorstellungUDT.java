package de.eldecker.spring.kinoprogramm.db;

import static de.eldecker.spring.kinoprogramm.logik.DatumZeitHelferlein.parseUhrzeit;
import static java.lang.String.format;

import java.time.LocalTime;

import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


/**
 * Eine bestimmte Vorstellung, i.d.R. ein Film, evtl. aber auch mehrere Filme
 * (z.B. Vor- und Hauptfilm). Wir gehen von einem kleinen Kino mit nur einem
 * Saal aus.
 */
@UserDefinedType( "Vorstellung" )
public class VorstellungUDT {

    /** Titel der Vorstellung, z.B. Name Film, "Sneak Preview" oder "Star-Trek-Nacht". */
    @NotBlank( message = "Titel der Vorstellung darf nicht leer sein" )
    private String titel;
        
    @NotNull( message = "Startzeit darf nicht null sein" )
    private LocalTime startzeit;

    /** Dauer der Vorstellung in Minuten, z.B. 90 Minuten. */
    @Min( value =  20, message = "Vorstellung muss mindestens 20 Minuten dauern" )
    @Max( value = 600, message = "Vorstellung darf höchstens 10 Stunden dauern"  )
    private int dauerMinuten;
    
    
    /** Leerer Default-Konstruktor */
    public VorstellungUDT() {}
    
    
    /**
     * Konstruktor um alle Attribute der Vorstellung zu setzen.
     */
    public VorstellungUDT( String titel, int dauerMinuten, LocalTime startzeit ) {
        
        this.titel        = titel;
        this.dauerMinuten = dauerMinuten;
        this.startzeit    = startzeit;
    }

    public VorstellungUDT( String titel, int dauerMinuten, String startzeitStr ) {
        
        this.titel        = titel;
        this.dauerMinuten = dauerMinuten;
        this.startzeit    = parseUhrzeit( startzeitStr );
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

    
    public LocalTime getStartzeit() {
        
        return startzeit;
    }

    public void setStartzeit( LocalTime startzeit ) {
        
        this.startzeit = startzeit;
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
