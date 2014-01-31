













<%@ page import="it.algos.botbio.Antroponimo" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'antroponimo.label', default: 'Antroponimo')}"/>
    <title><g:message code="antroponimo.list.label" args="[entityName]" default="Elenco"/></title>
</head>

<body>
<a href="#list-antroponimo" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                                  default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/home')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="create" action="create"><g:message code="antroponimo.new.label"
                                                              args="[entityName]" default="Nuovo"/></g:link></li>
        <g:if test="${menuExtra}">
            <algos:menuExtra menuExtra="${menuExtra}"> </algos:menuExtra>
        </g:if>
    </ul>
</div>

<div id="list-antroponimo" class="content scaffold-list" role="main">

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

    <g:if test="${titoloLista}">
        <h1>${titoloLista}</h1>
    </g:if>
    <g:else>
        <h1><g:message code="antroponimo.list.label" args="[entityName]" default="Elenco"/></h1>
    </g:else>

    <table>
        <thead>
        <g:if test="${campiLista}">
            <algos:titoliLista campiLista="${campiLista}"></algos:titoliLista>
        </g:if>
        <g:else>
            <tr>
                
                <g:sortableColumn property="nome"
                                  title="${message(code: 'antroponimo.nome.label', default: 'Nome')}"/>
                
                <g:sortableColumn property="voci"
                                  title="${message(code: 'antroponimo.voci.label', default: 'Voci')}"/>
                
                <g:sortableColumn property="lunghezza"
                                  title="${message(code: 'antroponimo.lunghezza.label', default: 'Lunghezza')}"/>
                
            </tr>
        </g:else>
        </thead>
        <tbody>
        <g:if test="${campiLista}">
            <g:each in="${antroponimoInstanceList}" status="i" var="antroponimoInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <algos:rigaLista campiLista="${campiLista}" rec="${antroponimoInstance}"></algos:rigaLista>
                </tr>
            </g:each>
        </g:if>
        <g:else>
            <g:each in="${antroponimoInstanceList}" status="i" var="antroponimoInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    
                    <td><g:link action="show"
                                id="${antroponimoInstance.id}">${fieldValue(bean: antroponimoInstance, field: "nome")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${antroponimoInstance.id}">${fieldValue(bean: antroponimoInstance, field: "voci")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${antroponimoInstance.id}">${fieldValue(bean: antroponimoInstance, field: "lunghezza")}</g:link></td>
                    
                </tr>
            </g:each>
        </g:else>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${antroponimoInstanceTotal}"/>
    </div>
    <g:if test="${application.usaExport}">
        <div class="buttons">
            <export:formats/>
        </div>
    </g:if>
</div>
</body>
</html>
