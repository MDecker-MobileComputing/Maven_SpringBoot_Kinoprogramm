package de.eldecker.spring.kinoprogramm.web;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 * Formular-Klasse mit Daten, die zur Erstellung einer neuen Vorstellung
 * benötigt werden.
 */
public class VorstellungFormular {

    /** Titel der Vorstellung, z.B. Name des Films oder "Sneak Preview". */
    @Size( min = 5, max = 100, message = "Name der Vorstellung muss zwischen 5 und 100 Zeichen lang sein." )
    private String titel;
    
    /** Datum der Vorstellung, z.B. "2023-10-31". */
    @NotNull( message = "Datum ist erforderlich" )
    @DateTimeFormat( pattern = "yyyy-MM-dd" ) 
    @FutureOrPresent( message = "Datum der Vorstellung darf nicht in der Vergangenheit liegen" )
    private LocalDate datum;

    
    public String getTitel() {
        
        return titel;
    }

    public void setTitel( String titel ) {
        
        this.titel = titel;
    }

    
    public LocalDate getDatum() {
        
        return datum;
    }

    public void setDatum( LocalDate datum ) {

        this.datum = datum;
    }
    
    
    /**
     * String-Repräsentation der Vorstellung.
     * 
     * @return String mit Titel und Datum der Vorstellung
     */
    @Override
    public String toString() {
        
        return String.format( "VorstellungFormular[titel=\"%s\", datum=%s]", titel, datum );
    }
    
}
