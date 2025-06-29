package de.eldecker.spring.kinoprogramm.web;

import jakarta.validation.constraints.Size;


/**
 * Formular-Klasse mit Daten, die zur Erstellung einer neuen Vorstellung
 * benötigt werden.
 */
public class VorstellungFormular {

    /** Titel der Vorstellung, z.B. Name des Films oder "Sneak Preview". */
    @Size( min = 5, max = 100, message = "Name der Vorstellung muss zwischen 5 und 100 Zeichen lang sein." )
    private String titel;

    public String getTitel() {
        
        return titel;
    }

    public void setTitel( String titel ) {
        
        this.titel = titel;
    }
    
    
    
}
