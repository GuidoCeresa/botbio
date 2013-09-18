













<%@ page import="it.algos.botbio.Bio" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'bio.label', default: 'Bio')}"/>
    <title><g:message code="bio.list.label" args="[entityName]" default="Elenco"/></title>
</head>

<body>
<a href="#list-bio" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                                  default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/home')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="create" action="create"><g:message code="bio.new.label"
                                                              args="[entityName]" default="Nuovo"/></g:link></li>
        <g:if test="${menuExtra}">
            <algos:menuExtra menuExtra="${menuExtra}"> </algos:menuExtra>
        </g:if>
    </ul>
</div>

<div id="list-bio" class="content scaffold-list" role="main">
    <g:if test="${titoloLista}">
        <h1>${titoloLista}</h1>
    </g:if>
    <g:else>
        <h1><g:message code="bio.list.label" args="[entityName]" default="Elenco"/></h1>
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
                
                <g:sortableColumn property="pageid"
                                  title="${message(code: 'bio.pageid.label', default: 'Pageid')}"/>
                
                <g:sortableColumn property="titolo"
                                  title="${message(code: 'bio.titolo.label', default: 'Titolo')}"/>
                
                <g:sortableColumn property="nome"
                                  title="${message(code: 'bio.nome.label', default: 'Nome')}"/>
                
                <g:sortableColumn property="cognome"
                                  title="${message(code: 'bio.cognome.label', default: 'Cognome')}"/>
                
                <g:sortableColumn property="postCognome"
                                  title="${message(code: 'bio.postCognome.label', default: 'Post Cognome')}"/>
                
                <g:sortableColumn property="postCognomeVirgola"
                                  title="${message(code: 'bio.postCognomeVirgola.label', default: 'Post Cognome Virgola')}"/>
                
                <g:sortableColumn property="forzaOrdinamento"
                                  title="${message(code: 'bio.forzaOrdinamento.label', default: 'Forza Ordinamento')}"/>
                
                <g:sortableColumn property="preData"
                                  title="${message(code: 'bio.preData.label', default: 'Pre Data')}"/>
                
            </tr>
        </g:else>
        </thead>
        <tbody>
        <g:if test="${campiLista}">
            <g:each in="${bioInstanceList}" status="i" var="bioInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <algos:rigaLista campiLista="${campiLista}" rec="${bioInstance}"></algos:rigaLista>
                </tr>
            </g:each>
        </g:if>
        <g:else>
            <g:each in="${bioInstanceList}" status="i" var="bioInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    
                    <td><g:link action="show"
                                id="${bioInstance.id}">${fieldValue(bean: bioInstance, field: "pageid")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioInstance.id}">${fieldValue(bean: bioInstance, field: "titolo")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioInstance.id}">${fieldValue(bean: bioInstance, field: "nome")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioInstance.id}">${fieldValue(bean: bioInstance, field: "cognome")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioInstance.id}">${fieldValue(bean: bioInstance, field: "postCognome")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioInstance.id}">${fieldValue(bean: bioInstance, field: "postCognomeVirgola")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioInstance.id}">${fieldValue(bean: bioInstance, field: "forzaOrdinamento")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioInstance.id}">${fieldValue(bean: bioInstance, field: "preData")}</g:link></td>
                    
                </tr>
            </g:each>
        </g:else>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${bioInstanceTotal}"/>
    </div>
    <g:if test="${application.usaExport}">
        <div class="buttons">
            <export:formats/>
        </div>
    </g:if>
</div>
</body>
</html>
