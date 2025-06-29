package de.eldecker.spring.kinoprogramm.web;

import static de.eldecker.spring.kinoprogramm.logik.DatumsHelferlein.formatiereDatumMitWochentag;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.eldecker.spring.kinoprogramm.db.KinoprogrammTable;
import de.eldecker.spring.kinoprogramm.logik.KinoProgrammService;
import jakarta.validation.Valid;


/**
 * Controller für Thymeleaf-Templates.
 */
@Controller
@RequestMapping( "/app/v1" )
public class ThymeleafWebController {
    
    private static final Logger LOG = LoggerFactory.getLogger( ThymeleafWebController.class );
    

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
         
            final String fehlertext = 
                    "Kein Programm für " + formatiereDatumMitWochentag( datum ) + " gefunden.";
            LOG.error( fehlertext );
            model.addAttribute( "fehlermeldung", fehlertext );
            
            return "fehler";
            
        } else {
            
            model.addAttribute( "programm", kinoProgrammOptional.get() );
            
            return "programmdetails";
        }
    }
    
    
    /**
     * Formular zur Eingabe einer neuen Vorstellung anzeigen.
     * 
     * @param model Objekt für Platzhalterwerte in Template
     * 
     * @return Template "neu" mit leerem Vorstellung-Objekt
     */
    @GetMapping( "/neu" )
    public String getFormularNeueVorstellung( Model model ) {
        
        model.addAttribute( "vorstellung", new VorstellungFormular() );
        
        return "neu";
    }
    
    
    /**
     * Formulardaten für eine neue Vorstellung speichern.
     * 
     * @param vorstellung Formular-Objekt mit Daten zur neuen Vorstellung
     * 
     * @param bindingResult Objekt für evtl. Validierungsfehler
     * 
     * @return Weiterleitung zu "neu" bei Fehlern oder im Erfolgsfall zur Übersicht
     */
    @PostMapping( "/speichern" )
    public String speichereFormularNeueVorstellung( @ModelAttribute("vorstellung") @Valid VorstellungFormular vorstellung,
                                                    BindingResult bindingResult,
                                                    Model model) {
        if ( bindingResult.hasErrors() ) {
            
            LOG.error( "Fehler in Formulardaten fuer neue Vorstellung gefunden: {}",
                       vorstellung ); 

            return "neu";
        }
        
        LOG.info( "Neue Vorstellung: {}", vorstellung);
        
        return "redirect:/app/v1/uebersicht";
    }
    
}
