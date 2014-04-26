/* Created by Algos s.r.l. */
/* Date: mag 2013 */
/* Il plugin Algos ha inserito (solo la prima volta) questo header per controllare */
/* le successive release (tramite il flag di controllo aggiunto) */
/* Tipicamente VERRA sovrascritto ad ogni nuova release del plugin */
/* per rimanere aggiornato */
/* Se vuoi che le prossime release del plugin NON sovrascrivano questo file, */
/* perdendo tutte le modifiche precedentemente effettuate, */
/* regola a false il flag di controllo flagOverwrite© */
/* flagOverwrite = true */

class FilterBootStrap {

    def init = { servletContext ->
        //--inietta un flag di controllo (statico) nel servletContext
        //--regolando questo flag, le pagine (list) GSP costruiscono i bottoni/menu
        //--di chiamata ai metodi di ricerca, filtro e selezione
        servletContext.usaFilter = true
    }// fine della closure

    def destroy = {
    }// fine della closure

}// fine della classe di tipo BootStrap
