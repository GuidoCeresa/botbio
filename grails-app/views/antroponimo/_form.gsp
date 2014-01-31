












<%@ page import="it.algos.botbio.Antroponimo" %>



<div class="fieldcontain ${hasErrors(bean: antroponimoInstance, field: 'nome', 'error')} ">
	<label for="nome">
		<g:message code="antroponimo.nome.label" default="Nome" />
		
	</label>
	










<g:textField name="nome" value="${antroponimoInstance?.nome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: antroponimoInstance, field: 'voci', 'error')} required">
	<label for="voci">
		<g:message code="antroponimo.voci.label" default="Voci" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="voci" type="number" value="${antroponimoInstance.voci}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: antroponimoInstance, field: 'lunghezza', 'error')} required">
	<label for="lunghezza">
		<g:message code="antroponimo.lunghezza.label" default="Lunghezza" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="lunghezza" type="number" value="${antroponimoInstance.lunghezza}" required=""/>
</div>

