<%--Created by Algos s.r.l.--%>
<%--Date: mag 2013--%>
<%--Il plugin Algos ha inserito (solo la prima volta) questo header per controllare--%>
<%--le successive release (tramite il flag di controllo aggiunto)--%>
<%--Tipicamente VERRA sovrascritto ad ogni nuova release del plugin--%>
<%--per rimanere aggiornato--%>
<%--Se vuoi che le prossime release del plugin NON sovrascrivano questo file,--%>
<%--perdendo tutte le modifiche precedentemente effettuate,--%>
<%--regola a false il flag di controllo flagOverwrite©--%>
<%--flagOverwrite = true--%>

<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="Algos"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
    <link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mainextra.css')}" type="text/css">
    <link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
    <g:layoutHead/>
    <r:layoutResources/>
</head>

<body>
<div id="algosLogo" role="banner">
    <a href="http://www.algos.it/">
        <img src="${resource(dir: 'images', file: 'logo_chiaro.png')}" height="60" alt="Algos"/>
    </a>
    <a href="http://www.algos.it/">
        <img src="${resource(dir: 'images', file: '')}" height="60" alt="${grailsApplication.metadata.'app.name'}"/>
    </a>
</div>
<g:layoutBody/>
<div class="footer" role="contentinfo">
    <table><td class="copyright">
        ${grailsApplication.metadata.'app.name'}
        <g:if test="${grailsApplication.metadata.'app.copy'}">
            by ${grailsApplication.metadata.'app.copy'}
        </g:if>
        <g:if test="${grailsApplication.metadata.'app.version'}">
            v.${grailsApplication.metadata.'app.version'}
        </g:if>
        <g:if test="${grailsApplication.metadata.'app.date'}">
            del ${grailsApplication.metadata.'app.date'}
        </g:if>
    </td></table>
</div>

<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
<g:javascript library="application"/>
<r:layoutResources/>
</body>
