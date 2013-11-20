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
        <li><a class="home" href="${createLink(uri: '/home')}"><g:message code="default.home.label" default="Home"/></a>
        </li>
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
            <algos:titoliLista campiLista="${campiLista}"></algos:titoliLista>
        </g:if>
        <g:else>
            <tr>

                <g:sortableColumn property="pageid"
                                  title="${message(code: 'bioWiki.pageid.label', default: 'Pageid')}"/>

                <g:sortableColumn property="title"
                                  title="${message(code: 'bioWiki.title.label', default: 'Title')}"/>

                <g:sortableColumn property="nome"
                                  title="${message(code: 'bioWiki.ns.label', default: 'Nome')}"/>

                <g:sortableColumn property="cognome"
                                  title="${message(code: 'bioWiki.touched.label', default: 'Cognome')}"/>

                <g:sortableColumn property="giornoMeseNascita"
                                  title="${message(code: 'bioWiki.revid.label', default: 'Nascita')}"/>

                <g:sortableColumn property="giornoMeseMorte"
                                  title="${message(code: 'bioWiki.revid.label', default: 'Morte')}"/>

            </tr>
        </g:else>
        </thead>
        <tbody>
        <g:if test="${campiLista}">
            <g:each in="${bioGrailsInstanceList}" status="i" var="bioGrailsInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                    <algos:rigaLista campiLista="${campiLista}" rec="${bioGrailsInstance}"></algos:rigaLista>
                </tr>
            </g:each>
        </g:if>
        <g:else>
            <g:each in="${bioGrailsInstanceList}" status="i" var="bioGrailsInstance">
                <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                    <td><g:link action="show"
                                id="${bioGrailsInstance.id}">${fieldValue(bean: bioGrailsInstance, field: "pageid")}</g:link></td>

                    <td><g:link action="show"
                                id="${bioGrailsInstance.id}">${fieldValue(bean: bioGrailsInstance, field: "title")}</g:link></td>

                    <td><g:link action="show"
                                id="${bioGrailsInstance.id}">${fieldValue(bean: bioGrailsInstance, field: "nome")}</g:link></td>

                    <td><g:link action="show"
                                id="${bioGrailsInstance.id}">${fieldValue(bean: bioGrailsInstance, field: "cognome")}</g:link></td>

                    <td><g:link action="show"
                                id="${bioGrailsInstance.id}">${fieldValue(bean: bioGrailsInstance, field: "giornoMeseNascita")}</g:link></td>

                    <td><g:link action="show"
                                id="${bioGrailsInstance.id}">${fieldValue(bean: bioGrailsInstance, field: "giornoMeseMorte")}</g:link></td>

                </tr>
            </g:each>
        </g:else>
        </tbody>
    </table>
    <div class="pagination">
        <g:paginate total="${bioGrailsInstanceTotal}"/>
    </div>
</div>
</body>
</html>
