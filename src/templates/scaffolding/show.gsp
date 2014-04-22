<%/* Created by Algos s.r.l. */%>
<%/* Date: mag 2013 */%>
<%/* Il plugin Algos ha creato o sovrascritto il templates che ha creato questo file. */%>
<%/* L'header del templates serve per controllare le successive release */%>
<%/* (tramite il flag di controllo aggiunto) */%>
<%/* Tipicamente VERRA sovrascritto (il template, non il file) ad ogni nuova release */%>
<%/* del plugin per rimanere aggiornato. */%>
<%/* Se vuoi che le prossime release del plugin NON sovrascrivano il template che */%>
<%/* genera questo file, perdendo tutte le modifiche precedentemente effettuate, */%>
<%/* regola a false il flag di controllo flagOverwrite© del template stesso. */%>
<%/* (non quello del singolo file) */%>
<%/* flagOverwrite = true */%>

<% import grails.persistence.Event %>
<%=packageName%>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="\${message(code: '${domainClass.propertyName}.label', default: '${className}')}" />
    <title><g:message code="${domainClass.propertyName}.show.label" args="[entityName]" default="Mostra"/></title>
</head>
<body>
<a href="#show-${domainClass.propertyName}" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="\${createLink(uri: '/')}"><g:message code="default.home.label" default="Home"/></a></li>
        <li><g:link class="list" action="list"><g:message code="${domainClass.propertyName}.list.label" args="[entityName]" default="Elenco"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="${domainClass.propertyName}.new.label" args="[entityName]" default="Nuovo"/></g:link></li>
    </ul>
</div>
<div id="show-${domainClass.propertyName}" class="content scaffold-show" role="main">
    <h1><g:message code="${domainClass.propertyName}.show.label" args="[entityName]" default="Mostra"/></h1>
    <g:if test="\${flash.message}">
        <div class="message" role="status">\${flash.message}</div>
    </g:if>
    <ol class="property-list ${domainClass.propertyName}">
        <%  excludedProps = Event.allEvents.toList() << 'id' << 'version'
        allowedNames = domainClass.persistentProperties*.name << 'dateCreated' << 'lastUpdated'
        props = domainClass.properties.findAll { allowedNames.contains(it.name) && !excludedProps.contains(it.name) }
        Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
        props.each { p -> %>
        <g:if test="\${${propertyName}?.${p.name}}">
            <li class="fieldcontain">
                <span id="${p.name}-label" class="property-label"><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></span>
                <%  if (p.isEnum()) { %>
                <span class="property-value" aria-labelledby="${p.name}-label"><g:fieldValue bean="\${${propertyName}}" field="${p.name}"/></span>
                <%  } else if (p.oneToMany || p.manyToMany) { %>
                <g:each in="\${${propertyName}.${p.name}}" var="${p.name[0]}">
                    <span class="property-value" aria-labelledby="${p.name}-label"><g:link controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${p.name[0]}.id}">\${${p.name[0]}?.encodeAsHTML()}</g:link></span>
                </g:each>
                <%  } else if (p.manyToOne || p.oneToOne) { %>
                <span class="property-value" aria-labelledby="${p.name}-label"><g:link controller="${p.referencedDomainClass?.propertyName}" action="show" id="\${${propertyName}?.${p.name}?.id}">\${${propertyName}?.${p.name}?.encodeAsHTML()}</g:link></span>
                <%  } else if (p.type == Boolean || p.type == boolean) { %>
                <span class="property-value" aria-labelledby="${p.name}-label"><g:formatBoolean boolean="\${${propertyName}?.${p.name}}" /></span>
                <%  } else if (p.type == Date || p.type == java.sql.Date || p.type == java.sql.Time || p.type == Calendar) { %>
                <span class="property-value" aria-labelledby="${p.name}-label"><g:formatDate date="\${${propertyName}?.${p.name}}" /></span>
                <%  } else if (!p.type.isArray()) { %>
                <span class="property-value" aria-labelledby="${p.name}-label"><g:fieldValue bean="\${${propertyName}}" field="${p.name}"/></span>
                <%  } %>
            </li>
        </g:if>
        <%  } %>
    </ol>
    <g:form>
        <g:if test="\${usaSpostamento}">
            <fieldset class="buttons">
                <g:hiddenField name="id" value="\${${propertyName}?.id}"/>
                <g:link class="create" action="moveFirst">Primo record</g:link>
                <g:link class="edit" action="movePrec" id="\${${propertyName}?.id}">Precedente</g:link>
                <g:link class="edit" action="moveSucc" id="\${${propertyName}?.id}">Successivo</g:link>
                <g:link class="create" action="moveLast">Ultimo record</g:link>
            </fieldset>
        </g:if>
    </g:form>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="\${${propertyName}?.id}" />
            <g:link class="edit" action="edit" id="\${${propertyName}?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
            <g:actionSubmit class="delete" action="delete" value="\${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('\${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
        </fieldset>
    </g:form>
</div>
</body>
</html>
