<%--Created by Algos s.r.l.--%>
<%--Date: mag 2013--%>
<%--Il plugin Algos ha inserito (solo la prima volta) questo header per controllare--%>
<%--le successive release (tramite il flag di controllo aggiunto)--%>
<%--Tipicamente VERRA sovrascritto ad ogni nuova release del plugin--%>
<%--per rimanere aggiornato--%>
<%--Se vuoi che le prossime release del plugin NON sovrascrivano questo file,--%>
<%--perdendo tutte le modifiche precedentemente effettuate,--%>
<%--regola a false il flag di controllo flagOverwrite©--%>
<%--flagOverwrite = false--%>

<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>${grailsApplication.metadata.'app.name'}</title>
    <style type="text/css" media="screen">
    #status {
        background-color: #eee;
        border: .2em solid #fff;
        margin: 2em 2em 1em;
        padding: 1em;
        width: 12em;
        float: left;
        -moz-box-shadow: 0px 0px 1.25em #ccc;
        -webkit-box-shadow: 0px 0px 1.25em #ccc;
        box-shadow: 0px 0px 1.25em #ccc;
        -moz-border-radius: 0.6em;
        -webkit-border-radius: 0.6em;
        border-radius: 0.6em;
    }

    .ie6 #status {
        display: inline; /* float double margin fix http://www.positioniseverything.net/explorer/doubled-margin.html */
    }

    #status ul {
        font-size: 0.9em;
        list-style-type: none;
        margin-bottom: 0.6em;
        padding: 0;
    }

    #status li {
        line-height: 1.3;
    }

    #status h1 {
        text-transform: uppercase;
        font-size: 1.1em;
        margin: 0 0 0.3em;
    }

    #page-body {
        margin-top: 2em;
        margin-right: 1em;
        margin-bottom: 1em;
        margin-left: 2em;
    }

    h2 {
        margin-top: 1em;
        margin-bottom: 0.3em;
        font-size: 1em;
    }

    p {
        line-height: 1.5;
        margin: 0.25em 0;
    }

    #controller-list ul {
        list-style-position: inside;
    }

    #controller-list li {
        line-height: 1.3;
        list-style-position: inside;
        margin: 0.25em 0;
    }

    @media screen and (max-width: 480px) {
        #status {
            display: none;
        }

        #page-body {
            margin: 0 1em 1em;
        }

        #page-body h1 {
            margin-top: 0;
        }
    }
    </style>
</head>

<body>
<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/home')}"><g:message code="default.home.label" default="Home"/></a>
        </li>
    </ul>
</div>

<div id="page-body" role="main">
    <h1>Pagina di manutenzione e controllo</h1>

    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:if test="${flash.error}">
        <div class="errors" role="status">${flash.error}</div>
    </g:if>
    <g:if test="${flash.messages}">
        <g:each in="${flash.messages}" status="i" var="singoloMessaggio">
            <div class="message" role="status">${singoloMessaggio}</div>
        </g:each>
    </g:if>
    <g:if test="${flash.errors}">
        <g:each in="${flash.errors}" status="i" var="singoloErrore">
            <div class="errors" role="status">${singoloErrore}</div>
        </g:each>
    </g:if>

    <div id="controller-list" role="navigation">
        <h2>Operazioni possibili</h2>
        <br/>
        <ul>
            <li class="controller"><g:link controller="bio" action="parsessoassente">Controllo parametro sesso assente</g:link></li>
            <li class="controller"><g:link controller="bio" action="parsessoerrato">Controllo parametro sesso errato</g:link></li>
            <li class="controller"><g:link controller="bio" action="pargiorno">Controllo giorni errati</g:link></li>
            <li class="controller"><g:link controller="bio" action="parOrdinamentoVuoti">Controllo parametro forzaOrdinamento in BioGrails</g:link></li>
            <li class="controller"><g:link controller="bio" action="didascaliaListe">Controllo parametro didascaliaListe in BioGrails</g:link></li>
            <li class="controller"><g:link controller="bio" action="seleziona">Selezione di una pagina dal title o dal pageId</g:link></li>
            <li class="controller"><g:link controller="bio" action="test">Test di prova</g:link></li>
        </ul>
    </div>
</div>
</body>
</html>
