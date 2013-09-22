package it.algos.botbio

import it.algos.algoswiki.StatoPagina

/**
 * Created with IntelliJ IDEA.
 * User: Gac
 * Date: 19-9-13
 * Time: 11:33
 */
public enum StatoBio {

    //--uguali a StatoPagina
    indeterminata('pagina non elaborata', 'Pagina non ancora controllata'),
    maiEsistita('pagina mai esistita', 'Pagina che non è mai stata creata'),
    cancellata('pagina cancellata', 'Pagina esistente ma poi cancellata'),
    vuota('pagina vuota', 'Pagina senza nessun contenuto'),
    redirect('pagina di redirect', 'Pagina con redirect ad altra pagina'),
    disambigua('pagina di disambigua', 'Pagina con disambigua iniziale'),
    illeggibile('pagina illeggibile', 'Pagina che non si riesce a leggere'),
    normale('pagina normale', 'Pagina con testo normale'),
    //--specifici di StatoBio
    senzaBio('voce senza template bio', 'Voce con testo ma senza template Bio'),
    bioErrato('voce con template Bio errato', 'Template che non si riesce a leggere correttamente'),
    bioIncompleto('voce con template Bio incompleto', 'Template leggibile, ma senza alcuni campi chiave'),
    bioNormale('voce con template Bio normale', 'Template normale')

    String tag = ''
    String description = ''

    /**
     * Costruttore completo con parametri.
     *
     * @param tag utilizzato per chiarezza
     * @param description usato solo qui
     */
    StatoBio(String tag, String description) {
        this.setTag(tag)
        this.setDescription(description)
    } // fine del costruttore

    public static StatoBio get(StatoPagina statoPagina) {
        StatoBio statoBio = null
        String paginaTxt
        String bioTxt

        if (statoPagina) {
            paginaTxt = statoPagina.toString()
            def a=StatoBio.values()
            values().each {
                bioTxt = (String) it
                if (bioTxt.equals(paginaTxt)) {
                    statoBio = it
                }// fine del blocco if
            } // fine del ciclo each
        }// fine del blocco if

        return statoBio
    } // fine del metodo

    public String getTag() {
        return tag
    }

    private void setTag(String tag) {
        this.tag = tag
    }

    private String getDescription() {
        return description
    }

    private void setDescription(String description) {
        this.description = description
    }

} // fine della Enumeration
