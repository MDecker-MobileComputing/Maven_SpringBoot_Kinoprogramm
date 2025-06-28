package de.eldecker.spring.kinoprogramm.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import de.eldecker.spring.kinoprogramm.db.KinoprogrammTable;
import de.eldecker.spring.kinoprogramm.logik.KinoProgrammService;


/**
 * Controller für Thymeleaf-Templates.
 */
@Controller
@RequestMapping( "/app/v1" )
public class ThymeleafWebController {

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
    
}
