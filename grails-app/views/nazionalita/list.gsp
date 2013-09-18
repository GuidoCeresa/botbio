













<%@ page import="it.algos.botbio.Nazionalita" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'nazionalita.label', default: 'Nazionalita')}"/>
    <title><g:message code="nazionalita.list.label" args="[entityName]" default="Elenco"/></title>
</head>

<body>
<a href="#list-nazionalita" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                                  default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/home')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="create" action="create"><g:message code="nazionalita.new.label"
                                                              args="[entityName]" default="Nuovo"/></g:link></li>
        <g:if test="${menuExtra}">
            <algos:menuExtra menuExtra="${menuExtra}"> </algos:menuExtra>
        </g:if>
    </ul>
</div>

<div id="list-nazionalita" class="content scaffold-list" role="main">
    <g:if test="${titoloLista}">
        <h1>${titoloLista}</h1>
    </g:if>
    <g:else>
        <h1><g:message code="nazionalita.list.label" args="[entityName]" default="Elenco"/></h1>
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
                
                <g:sortableColumn property="singolare"
                                  title="${message(code: 'nazionalita.singolare.label', default: 'Singolare')}"/>
                
                <g:sortableColumn property="plurale"
                                  title="${message(code: 'nazionalita.plurale.label', default: 'Plurale')}"/>
                
            </tr>
        </g:else>
        </thead>
        <tbody>
        <g:if test="${campiLista}">
            <g:each in="${nazionalitaInstanceList}" status="i" var="nazionalitaInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <algos:rigaLista campiLista="${campiLista}" rec="${nazionalitaInstance}"></algos:rigaLista>
                </tr>
            </g:each>
        </g:if>
        <g:else>
            <g:each in="${nazionalitaInstanceList}" status="i" var="nazionalitaInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    
                    <td><g:link action="show"
                                id="${nazionalitaInstance.id}">${fieldValue(bean: nazionalitaInstance, field: "singolare")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${nazionalitaInstance.id}">${fieldValue(bean: nazionalitaInstance, field: "plurale")}</g:link></td>
                    
                </tr>
            </g:each>
        </g:else>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${nazionalitaInstanceTotal}"/>
    </div>
    <g:if test="${application.usaExport}">
        <div class="buttons">
            <export:formats/>
        </div>
    </g:if>
</div>
</body>
</html>
