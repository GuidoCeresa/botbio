













<%@ page import="it.algos.botbio.Giorno" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'giorno.label', default: 'Giorno')}"/>
    <title><g:message code="giorno.list.label" args="[entityName]" default="Elenco"/></title>
</head>

<body>
<a href="#list-giorno" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                                  default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/home')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="create" action="create"><g:message code="giorno.new.label"
                                                              args="[entityName]" default="Nuovo"/></g:link></li>
        <g:if test="${menuExtra}">
            <algos:menuExtra menuExtra="${menuExtra}"> </algos:menuExtra>
        </g:if>
    </ul>
</div>

<div id="list-giorno" class="content scaffold-list" role="main">
    <g:if test="${titoloLista}">
        <h1>${titoloLista}</h1>
    </g:if>
    <g:else>
        <h1><g:message code="giorno.list.label" args="[entityName]" default="Elenco"/></h1>
    </g:else>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <g:if test="${flash.error}">
        <div class="errors" role="status">${flash.error}</div>
    </g:if>
    <table>
        <thead>
        <g:if test="${campiLista}">
            <algos:titoliLista campiLista="${campiLista}"></algos:titoliLista>
        </g:if>
        <g:else>
            <tr>
                
                <g:sortableColumn property="normale"
                                  title="${message(code: 'giorno.normale.label', default: 'Normale')}"/>
                
                <g:sortableColumn property="bisestile"
                                  title="${message(code: 'giorno.bisestile.label', default: 'Bisestile')}"/>
                
                <g:sortableColumn property="nome"
                                  title="${message(code: 'giorno.nome.label', default: 'Nome')}"/>
                
                <g:sortableColumn property="titolo"
                                  title="${message(code: 'giorno.titolo.label', default: 'Titolo')}"/>
                
                <g:sortableColumn property="sporcoNato"
                                  title="${message(code: 'giorno.sporcoNato.label', default: 'Sporco Nato')}"/>
                
                <g:sortableColumn property="sporcoMorto"
                                  title="${message(code: 'giorno.sporcoMorto.label', default: 'Sporco Morto')}"/>
                
            </tr>
        </g:else>
        </thead>
        <tbody>
        <g:if test="${campiLista}">
            <g:each in="${giornoInstanceList}" status="i" var="giornoInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <algos:rigaLista campiLista="${campiLista}" rec="${giornoInstance}"></algos:rigaLista>
                </tr>
            </g:each>
        </g:if>
        <g:else>
            <g:each in="${giornoInstanceList}" status="i" var="giornoInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    
                    <td><g:link action="show"
                                id="${giornoInstance.id}">${fieldValue(bean: giornoInstance, field: "normale")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${giornoInstance.id}">${fieldValue(bean: giornoInstance, field: "bisestile")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${giornoInstance.id}">${fieldValue(bean: giornoInstance, field: "nome")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${giornoInstance.id}">${fieldValue(bean: giornoInstance, field: "titolo")}</g:link></td>
                    
                    <td><g:formatBoolean boolean="${giornoInstance.sporcoNato}"/></td>
                    
                    <td><g:formatBoolean boolean="${giornoInstance.sporcoMorto}"/></td>
                    
                </tr>
            </g:each>
        </g:else>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${giornoInstanceTotal}"/>
    </div>
    <g:if test="${application.usaExport}">
        <div class="buttons">
            <export:formats/>
        </div>
    </g:if>
</div>
</body>
</html>
