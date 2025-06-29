package de.eldecker.spring.kinoprogramm.web;

import static de.eldecker.spring.kinoprogramm.logik.DatumsHelferlein.formatiereDatumMitWochentag;
import static de.eldecker.spring.kinoprogramm.logik.DatumsHelferlein.getDatumHeute;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.eldecker.spring.kinoprogramm.db.KinoprogrammTable;
import de.eldecker.spring.kinoprogramm.logik.KinoProgrammService;


/**
 * Controller für Thymeleaf-Templates.
 */
@Controller
@RequestMapping( "/app/v1" )
public class ThymeleafWebController {

    /** Bean mit Geschäftslogik für Kinoprogramm. */
    @Autowired
    private KinoProgrammService _kinoService; 
    
    
    /**
     * Anzeige einer Liste mit Übersicht Programm an allen Tagen, an
     * denen es mindestens eine Vorstellung gibt.
     * 
     * @return Template "programmuebersicht" 
     */
    @GetMapping( "/uebersicht" )
    public String getProgrammAlleTage( Model model ) {
                
        
        final List<KinoprogrammTable>  kinoProgrammListe = _kinoService.getProgrammAbHeute();
        model.addAttribute( "liste", kinoProgrammListe );
        
        return "programmuebersicht";
    }
    
    
    /**
     * Anzeige Programmdetails für einen bestimmten Tag.
     * 
     * @param model Objekt für Platzhalterwerte in Template
     *  
     * @param datum Datum im Format "YYYY-MM-DD"; muss gültig sein
     * 
     * @return Template "programmdetails" oder "fehler"
     */
    @GetMapping( "/details/{datum}" )
    public String getProgrammDetail( Model model, 
                                     @PathVariable String datum ) {
        
        final Optional<KinoprogrammTable> kinoProgrammOptional = 
                             _kinoService.getProgrammFuerDatum( datum );
        
        if ( kinoProgrammOptional.isEmpty() ) {
         
            final String fehlertext = "Kein Programm für " + formatiereDatumMitWochentag( datum ) + " gefunden.";
            model.addAttribute( "fehlermeldung", fehlertext );
            
            return "fehler";
            
        } else {
            
            model.addAttribute( "programm", kinoProgrammOptional.get() );
            
            return "programmdetails";
        }
    }
    
}
