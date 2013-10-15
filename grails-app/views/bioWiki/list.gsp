













<%@ page import="it.algos.botbio.BioWiki" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'bioWiki.label', default: 'BioWiki')}"/>
    <title><g:message code="bioWiki.list.label" args="[entityName]" default="Elenco"/></title>
</head>

<body>
<a href="#list-bioWiki" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                                                  default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/home')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="create" action="create"><g:message code="bioWiki.new.label"
                                                              args="[entityName]" default="Nuovo"/></g:link></li>
        <g:if test="${menuExtra}">
            <algos:menuExtra menuExtra="${menuExtra}"> </algos:menuExtra>
        </g:if>
    </ul>
</div>

<div id="list-bioWiki" class="content scaffold-list" role="main">

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
        <h1><g:message code="bioWiki.list.label" args="[entityName]" default="Elenco"/></h1>
    </g:else>

    <table>
        <thead>
        <g:if test="${campiLista}">
            <algos:titoliLista campiLista="${campiLista}"> </algos:titoliLista>
        </g:if>
        <g:else>
            <tr>
                
                <g:sortableColumn property="wikiUrl"
                                  title="${message(code: 'bioWiki.wikiUrl.label', default: 'Wiki Url')}"/>
                
                <g:sortableColumn property="testoTemplate"
                                  title="${message(code: 'bioWiki.testoTemplate.label', default: 'Testo Template')}"/>
                
                <g:sortableColumn property="pageid"
                                  title="${message(code: 'bioWiki.pageid.label', default: 'Pageid')}"/>
                
                <g:sortableColumn property="title"
                                  title="${message(code: 'bioWiki.title.label', default: 'Title')}"/>
                
                <g:sortableColumn property="ns"
                                  title="${message(code: 'bioWiki.ns.label', default: 'Ns')}"/>
                
                <g:sortableColumn property="touched"
                                  title="${message(code: 'bioWiki.touched.label', default: 'Touched')}"/>
                
                <g:sortableColumn property="revid"
                                  title="${message(code: 'bioWiki.revid.label', default: 'Revid')}"/>
                
                <g:sortableColumn property="size"
                                  title="${message(code: 'bioWiki.size.label', default: 'Size')}"/>
                
            </tr>
        </g:else>
        </thead>
        <tbody>
        <g:if test="${campiLista}">
            <g:each in="${bioWikiInstanceList}" status="i" var="bioWikiInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <algos:rigaLista campiLista="${campiLista}" rec="${bioWikiInstance}"> </algos:rigaLista>
                </tr>
            </g:each>
        </g:if>
        <g:else>
            <g:each in="${bioWikiInstanceList}" status="i" var="bioWikiInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    
                    <td><g:link action="show"
                                id="${bioWikiInstance.id}">${fieldValue(bean: bioWikiInstance, field: "wikiUrl")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioWikiInstance.id}">${fieldValue(bean: bioWikiInstance, field: "testoTemplate")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioWikiInstance.id}">${fieldValue(bean: bioWikiInstance, field: "pageid")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioWikiInstance.id}">${fieldValue(bean: bioWikiInstance, field: "title")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioWikiInstance.id}">${fieldValue(bean: bioWikiInstance, field: "ns")}</g:link></td>
                    
                    <td><g:formatDate date="${bioWikiInstance.touched}"/></td>
                    
                    <td><g:link action="show"
                                id="${bioWikiInstance.id}">${fieldValue(bean: bioWikiInstance, field: "revid")}</g:link></td>
                    
                    <td><g:link action="show"
                                id="${bioWikiInstance.id}">${fieldValue(bean: bioWikiInstance, field: "size")}</g:link></td>
                    
                </tr>
            </g:each>
        </g:else>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${bioWikiInstanceTotal}"/>
    </div>
    <g:if test="${application.usaExport}">
        <div class="buttons">
            <export:formats/>
        </div>
    </g:if>
</div>
</body>
</html>
