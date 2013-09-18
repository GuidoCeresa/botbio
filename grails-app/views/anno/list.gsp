













<%@ page import="it.algos.botbio.Anno" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'anno.label', default: 'Anno')}"/>
    <title><g:message code="anno.list.label" args="[entityName]" default="Elenco"/></title>
</head>

<body>
<a href="#list-anno" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                                  default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/home')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="create" action="create"><g:message code="anno.new.label"
                                                              args="[entityName]" default="Nuovo"/></g:link></li>
        <g:if test="${menuExtra}">
            <algos:menuExtra menuExtra="${menuExtra}"> </algos:menuExtra>
        </g:if>
    </ul>
</div>

<div id="list-anno" class="content scaffold-list" role="main">
    <g:if test="${titoloLista}">
        <h1>${titoloLista}</h1>
    </g:if>
    <g:else>
        <h1><g:message code="anno.list.label" args="[entityName]" default="Elenco"/></h1>
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
                
                <g:sortableColumn property="progressivoCategoria"
                                  title="${message(code: 'anno.progressivoCategoria.label', default: 'Progressivo Categoria')}"/>
                
                <g:sortableColumn property="titolo"
                                  title="${message(code: 'anno.titolo.label', default: 'Titolo')}"/>
                
                <g:sortableColumn property="sporcoNato"
                                  title="${message(code: 'anno.sporcoNato.label', default: 'Sporco Nato')}"/>
                
                <g:sortableColumn property="sporcoMorto"
                                  title="${message(code: 'anno.sporcoMorto.label', default: 'Sporco Morto')}"/>
                
            </tr>
        </g:else>
        </thead>
        <tbody>
        <g:if test="${campiLista}">
            <g:each in="${annoInstanceList}" status="i" var="annoInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <algos:rigaLista campiLista="${campiLista}" rec="${annoInstance}"></algos:rigaLista>
                </tr>
            </g:each>
        </g:if>
        <g:else>
            <g:each in="${annoInstanceList}" status="i" var="annoInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    
                    <td><g:link action="show"
                                id="${annoInstance.id}">${fieldValue(bean: annoInstance, field: "progressivoCategoria")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${annoInstance.id}">${fieldValue(bean: annoInstance, field: "titolo")}</g:link></td>
                    
                    <td><g:formatBoolean boolean="${annoInstance.sporcoNato}"/></td>
                    
                    <td><g:formatBoolean boolean="${annoInstance.sporcoMorto}"/></td>
                    
                </tr>
            </g:each>
        </g:else>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${annoInstanceTotal}"/>
    </div>
    <g:if test="${application.usaExport}">
        <div class="buttons">
            <export:formats/>
        </div>
    </g:if>
</div>
</body>
</html>
