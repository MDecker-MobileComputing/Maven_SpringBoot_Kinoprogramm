package de.eldecker.spring.kinoprogramm.web;

import static java.lang.String.format;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


/**
 * Formular-Klasse mit Daten, die zur Erstellung einer neuen Vorstellung
 * benötigt werden.
 */
public class VorstellungFormular {

    /** Titel der Vorstellung, z.B. Name des Films oder "Sneak Preview". */
    @Size( min = 5, max = 100, message = "Name der Vorstellung muss zwischen 5 und 100 Zeichen lang sein." )
    @NotBlank( message = "Titel darf nicht leer sein" )
    private String titel;
    
    /** Datum der Vorstellung, z.B. "2023-10-31; ISO8601-Format von Browser. */
    @NotNull( message = "Datum ist erforderlich" )
    @DateTimeFormat( pattern = "yyyy-MM-dd" ) 
    @FutureOrPresent( message = "Datum der Vorstellung darf nicht in der Vergangenheit liegen" )
    private LocalDate datum;

    
    /** Uhrzeit der Vorstellung, z.B. "20:30". */
    @NotNull( message = "Uhrzeit ist erforderlich" )
    @DateTimeFormat( pattern = "HH:mm" )
    private LocalTime uhrzeit;
    
    
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
    
    
    public LocalTime getUhrzeit() {
        
        return uhrzeit;
    }
    
    public void setUhrzeit( LocalTime uhrzeit ) {
        
        this.uhrzeit = uhrzeit;
    }
    
    
    /**
     * String-Repräsentation der Vorstellung.
     * 
     * @return String mit Titel und Datum der Vorstellung
     */
    @Override
    public String toString() {
        
        return format( "VorstellungFormular[titel=\"%s\", datum=%s, uhrzeit=%s]]", 
                       titel, datum, uhrzeit );
    }
    
}
