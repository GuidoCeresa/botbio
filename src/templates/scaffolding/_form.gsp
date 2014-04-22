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

<%=packageName%>
<% import grails.persistence.Event %>

<%  excludedProps = Event.allEvents.toList() << 'version' << 'dateCreated' << 'lastUpdated'
	persistentPropNames = domainClass.persistentProperties*.name
	boolean hasHibernate = pluginManager?.hasGrailsPlugin('hibernate')
	props = domainClass.properties.findAll { persistentPropNames.contains(it.name) && !excludedProps.contains(it.name) }
	Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
	for (p in props) {
		if (p.embedded) {
			def embeddedPropNames = p.component.persistentProperties*.name
			def embeddedProps = p.component.properties.findAll { embeddedPropNames.contains(it.name) && !excludedProps.contains(it.name) }
			Collections.sort(embeddedProps, comparator.constructors[0].newInstance([p.component] as Object[]))
			%><fieldset class="embedded"><legend><g:message code="${domainClass.propertyName}.${p.name}.label" default="${p.naturalName}" /></legend><%
				for (ep in p.component.properties) {
					renderFieldForProperty(ep, p.component, "${p.name}.")
				}
			%></fieldset><%
		} else {
			renderFieldForProperty(p, domainClass)
		}
	}

private renderFieldForProperty(p, owningClass, prefix = "") {
	boolean hasHibernate = pluginManager?.hasGrailsPlugin('hibernate')
	boolean display = true
	boolean required = false
	if (hasHibernate) {
		cp = owningClass.constrainedProperties[p.name]
		display = (cp ? cp.display : true)
		required = (cp ? !(cp.propertyType in [boolean, Boolean]) && !cp.nullable && (cp.propertyType != String || !cp.blank) : false)
	}
	if (display) { %>
<div class="fieldcontain \${hasErrors(bean: ${propertyName}, field: '${prefix}${p.name}', 'error')} ${required ? 'required' : ''}">
	<label for="${prefix}${p.name}">
		<g:message code="${domainClass.propertyName}.${prefix}${p.name}.label" default="${p.naturalName}" />
		<% if (required) { %><span class="required-indicator">*</span><% } %>
	</label>
	${renderEditor(p)}
</div>
<%  }   } %>
