












<%@ page import="it.algos.botbio.Giorno" %>



<div class="fieldcontain ${hasErrors(bean: giornoInstance, field: 'normale', 'error')} required">
	<label for="normale">
		<g:message code="giorno.normale.label" default="Normale" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="normale" type="number" value="${giornoInstance.normale}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: giornoInstance, field: 'bisestile', 'error')} required">
	<label for="bisestile">
		<g:message code="giorno.bisestile.label" default="Bisestile" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="bisestile" type="number" value="${giornoInstance.bisestile}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: giornoInstance, field: 'nome', 'error')} ">
	<label for="nome">
		<g:message code="giorno.nome.label" default="Nome" />
		
	</label>
	










<g:textField name="nome" value="${giornoInstance?.nome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: giornoInstance, field: 'titolo', 'error')} ">
	<label for="titolo">
		<g:message code="giorno.titolo.label" default="Titolo" />
		
	</label>
	










<g:textField name="titolo" value="${giornoInstance?.titolo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: giornoInstance, field: 'sporcoNato', 'error')} ">
	<label for="sporcoNato">
		<g:message code="giorno.sporcoNato.label" default="Sporco Nato" />
		
	</label>
	










<g:checkBox name="sporcoNato" value="${giornoInstance?.sporcoNato}" />
</div>

<div class="fieldcontain ${hasErrors(bean: giornoInstance, field: 'sporcoMorto', 'error')} ">
	<label for="sporcoMorto">
		<g:message code="giorno.sporcoMorto.label" default="Sporco Morto" />
		
	</label>
	










<g:checkBox name="sporcoMorto" value="${giornoInstance?.sporcoMorto}" />
</div>

