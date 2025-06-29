package de.eldecker.spring.kinoprogramm.logik;

import static java.time.format.DateTimeFormatter.ofPattern;
import static java.time.LocalDate.now;

import static java.util.Locale.GERMAN;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


/**
 * Klasse mit Hilfsfunktionen für Datumswerte.
 */
public class DatumZeitHelferlein {

    private static final DateTimeFormatter DATUMS_FORMATIERER = ofPattern( "yyyy-MM-dd" );

    private static final DateTimeFormatter UHRZEIT_FORMATIERER = ofPattern( "HH:mm" );
    
    
    /**
     * Datums-Strings für Heute + {@code anzTage} definieren.
     * 
     * @param anzTage Anzahl Tage, die auf heutiges Datum addiert wird;
     *                kann auch 0 sein für heutiges Datum
     * 
     * @return Datum im Format {@code YYYY-MM-DD}
     */
    public static String getDatumHeutePlusTage( int anzTage ) {
        
        final LocalDate heute     = now();
        final LocalDate zielDatum = heute.plusDays( anzTage );
        
        return DATUMS_FORMATIERER.format( zielDatum );
    }
    
    
    /** 
     * Datums-String für heutigen Tag.
     * 
     * @return Heutiges Datum im Format {@code YYYY-MM-DD}
     */
    public static String getDatumHeute() {
        
        return getDatumHeutePlusTage( 0 );
    }
    
    
    /**
     * Konvertiert ein Datum vom Format {@code YYYY-MM-DD} ins Format {@code DD.MM.YYYY (Wochentag)}.
     * 
     * @param datumString Datum im Format {@code YYYY-MM-DD}, z.B. "2025-12-25"
     * 
     * @return Datum im Format {@code DD.MM.YYYY (Wochentag)}, z.B. "25.12.2025 (Donnerstag)"
     * 
     * @throws DateTimeParseException wenn das Input-Format ungültig ist
     */
    public static String formatiereDatum( String datumString ) throws DateTimeParseException {
        
        final LocalDate datum = LocalDate.parse( datumString, DATUMS_FORMATIERER ); // DateTimeParseException
        
        final DateTimeFormatter ausgabeFormatierer = ofPattern( "dd.MM.yyyy (EEEE)", GERMAN );
        
        return ausgabeFormatierer.format( datum );
    }
    
    
    /**
     * Prüft, ob ein Datum das Format {@code YYYY-MM-DD} hat und
     * gültig ist.
     * 
     * @param datumString Zu überprüfendes Datum
     * 
     * @return {@code true} gdw. das Datum gültig ist
     */
    public static boolean istDatumOkay( String datumString ) {
        
        try {
            
            LocalDate.parse( datumString, DATUMS_FORMATIERER );
            return true;
            
        } catch ( DateTimeParseException ex ) {
            
            return false;
        }
    }

    
    public static LocalTime parseUhrzeit( String uhrzeitString ) throws DateTimeParseException {

        return LocalTime.parse( uhrzeitString, UHRZEIT_FORMATIERER );
    }
    
}
