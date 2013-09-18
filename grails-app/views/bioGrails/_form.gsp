












<%@ page import="it.algos.botbio.BioGrails" %>



<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'pageid', 'error')} required">
	<label for="pageid">
		<g:message code="bioGrails.pageid.label" default="Pageid" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="pageid" type="number" value="${bioGrailsInstance.pageid}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'titolo', 'error')} ">
	<label for="titolo">
		<g:message code="bioGrails.titolo.label" default="Titolo" />
		
	</label>
	










<g:textField name="titolo" value="${bioGrailsInstance?.titolo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'nome', 'error')} ">
	<label for="nome">
		<g:message code="bioGrails.nome.label" default="Nome" />
		
	</label>
	










<g:textField name="nome" value="${bioGrailsInstance?.nome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'cognome', 'error')} ">
	<label for="cognome">
		<g:message code="bioGrails.cognome.label" default="Cognome" />
		
	</label>
	










<g:textField name="cognome" value="${bioGrailsInstance?.cognome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'postCognome', 'error')} ">
	<label for="postCognome">
		<g:message code="bioGrails.postCognome.label" default="Post Cognome" />
		
	</label>
	










<g:textField name="postCognome" value="${bioGrailsInstance?.postCognome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'postCognomeVirgola', 'error')} ">
	<label for="postCognomeVirgola">
		<g:message code="bioGrails.postCognomeVirgola.label" default="Post Cognome Virgola" />
		
	</label>
	










<g:textField name="postCognomeVirgola" value="${bioGrailsInstance?.postCognomeVirgola}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'forzaOrdinamento', 'error')} ">
	<label for="forzaOrdinamento">
		<g:message code="bioGrails.forzaOrdinamento.label" default="Forza Ordinamento" />
		
	</label>
	










<g:textField name="forzaOrdinamento" value="${bioGrailsInstance?.forzaOrdinamento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'preData', 'error')} ">
	<label for="preData">
		<g:message code="bioGrails.preData.label" default="Pre Data" />
		
	</label>
	










<g:textField name="preData" value="${bioGrailsInstance?.preData}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'sesso', 'error')} ">
	<label for="sesso">
		<g:message code="bioGrails.sesso.label" default="Sesso" />
		
	</label>
	










<g:textField name="sesso" value="${bioGrailsInstance?.sesso}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'luogoNascita', 'error')} ">
	<label for="luogoNascita">
		<g:message code="bioGrails.luogoNascita.label" default="Luogo Nascita" />
		
	</label>
	










<g:textField name="luogoNascita" value="${bioGrailsInstance?.luogoNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'luogoNascitaLink', 'error')} ">
	<label for="luogoNascitaLink">
		<g:message code="bioGrails.luogoNascitaLink.label" default="Luogo Nascita Link" />
		
	</label>
	










<g:textField name="luogoNascitaLink" value="${bioGrailsInstance?.luogoNascitaLink}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'luogoNascitaAlt', 'error')} ">
	<label for="luogoNascitaAlt">
		<g:message code="bioGrails.luogoNascitaAlt.label" default="Luogo Nascita Alt" />
		
	</label>
	










<g:textField name="luogoNascitaAlt" value="${bioGrailsInstance?.luogoNascitaAlt}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'giornoMeseNascita', 'error')} ">
	<label for="giornoMeseNascita">
		<g:message code="bioGrails.giornoMeseNascita.label" default="Giorno Mese Nascita" />
		
	</label>
	










<g:textField name="giornoMeseNascita" value="${bioGrailsInstance?.giornoMeseNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'annoNascita', 'error')} ">
	<label for="annoNascita">
		<g:message code="bioGrails.annoNascita.label" default="Anno Nascita" />
		
	</label>
	










<g:textField name="annoNascita" value="${bioGrailsInstance?.annoNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'noteNascita', 'error')} ">
	<label for="noteNascita">
		<g:message code="bioGrails.noteNascita.label" default="Note Nascita" />
		
	</label>
	










<g:textField name="noteNascita" value="${bioGrailsInstance?.noteNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'luogoMorte', 'error')} ">
	<label for="luogoMorte">
		<g:message code="bioGrails.luogoMorte.label" default="Luogo Morte" />
		
	</label>
	










<g:textField name="luogoMorte" value="${bioGrailsInstance?.luogoMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'luogoMorteLink', 'error')} ">
	<label for="luogoMorteLink">
		<g:message code="bioGrails.luogoMorteLink.label" default="Luogo Morte Link" />
		
	</label>
	










<g:textField name="luogoMorteLink" value="${bioGrailsInstance?.luogoMorteLink}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'luogoMorteAlt', 'error')} ">
	<label for="luogoMorteAlt">
		<g:message code="bioGrails.luogoMorteAlt.label" default="Luogo Morte Alt" />
		
	</label>
	










<g:textField name="luogoMorteAlt" value="${bioGrailsInstance?.luogoMorteAlt}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'giornoMeseMorte', 'error')} ">
	<label for="giornoMeseMorte">
		<g:message code="bioGrails.giornoMeseMorte.label" default="Giorno Mese Morte" />
		
	</label>
	










<g:textField name="giornoMeseMorte" value="${bioGrailsInstance?.giornoMeseMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'annoMorte', 'error')} ">
	<label for="annoMorte">
		<g:message code="bioGrails.annoMorte.label" default="Anno Morte" />
		
	</label>
	










<g:textField name="annoMorte" value="${bioGrailsInstance?.annoMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'noteMorte', 'error')} ">
	<label for="noteMorte">
		<g:message code="bioGrails.noteMorte.label" default="Note Morte" />
		
	</label>
	










<g:textField name="noteMorte" value="${bioGrailsInstance?.noteMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'preAttivita', 'error')} ">
	<label for="preAttivita">
		<g:message code="bioGrails.preAttivita.label" default="Pre Attivita" />
		
	</label>
	










<g:textField name="preAttivita" value="${bioGrailsInstance?.preAttivita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivita', 'error')} ">
	<label for="attivita">
		<g:message code="bioGrails.attivita.label" default="Attivita" />
		
	</label>
	










<g:textField name="attivita" value="${bioGrailsInstance?.attivita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'epoca', 'error')} ">
	<label for="epoca">
		<g:message code="bioGrails.epoca.label" default="Epoca" />
		
	</label>
	










<g:textField name="epoca" value="${bioGrailsInstance?.epoca}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'epoca2', 'error')} ">
	<label for="epoca2">
		<g:message code="bioGrails.epoca2.label" default="Epoca2" />
		
	</label>
	










<g:textField name="epoca2" value="${bioGrailsInstance?.epoca2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'cittadinanza', 'error')} ">
	<label for="cittadinanza">
		<g:message code="bioGrails.cittadinanza.label" default="Cittadinanza" />
		
	</label>
	










<g:textField name="cittadinanza" value="${bioGrailsInstance?.cittadinanza}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivita2', 'error')} ">
	<label for="attivita2">
		<g:message code="bioGrails.attivita2.label" default="Attivita2" />
		
	</label>
	










<g:textField name="attivita2" value="${bioGrailsInstance?.attivita2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivita3', 'error')} ">
	<label for="attivita3">
		<g:message code="bioGrails.attivita3.label" default="Attivita3" />
		
	</label>
	










<g:textField name="attivita3" value="${bioGrailsInstance?.attivita3}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivitaAltre', 'error')} ">
	<label for="attivitaAltre">
		<g:message code="bioGrails.attivitaAltre.label" default="Attivita Altre" />
		
	</label>
	










<g:textField name="attivitaAltre" value="${bioGrailsInstance?.attivitaAltre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'nazionalita', 'error')} ">
	<label for="nazionalita">
		<g:message code="bioGrails.nazionalita.label" default="Nazionalita" />
		
	</label>
	










<g:textField name="nazionalita" value="${bioGrailsInstance?.nazionalita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'nazionalitaNaturalizzato', 'error')} ">
	<label for="nazionalitaNaturalizzato">
		<g:message code="bioGrails.nazionalitaNaturalizzato.label" default="Nazionalita Naturalizzato" />
		
	</label>
	










<g:textField name="nazionalitaNaturalizzato" value="${bioGrailsInstance?.nazionalitaNaturalizzato}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'postNazionalita', 'error')} ">
	<label for="postNazionalita">
		<g:message code="bioGrails.postNazionalita.label" default="Post Nazionalita" />
		
	</label>
	










<g:textField name="postNazionalita" value="${bioGrailsInstance?.postNazionalita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'categorie', 'error')} ">
	<label for="categorie">
		<g:message code="bioGrails.categorie.label" default="Categorie" />
		
	</label>
	










<g:textField name="categorie" value="${bioGrailsInstance?.categorie}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'fineIncipit', 'error')} ">
	<label for="fineIncipit">
		<g:message code="bioGrails.fineIncipit.label" default="Fine Incipit" />
		
	</label>
	










<g:textField name="fineIncipit" value="${bioGrailsInstance?.fineIncipit}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'punto', 'error')} ">
	<label for="punto">
		<g:message code="bioGrails.punto.label" default="Punto" />
		
	</label>
	










<g:textField name="punto" value="${bioGrailsInstance?.punto}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'immagine', 'error')} ">
	<label for="immagine">
		<g:message code="bioGrails.immagine.label" default="Immagine" />
		
	</label>
	










<g:textField name="immagine" value="${bioGrailsInstance?.immagine}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'didascalia', 'error')} ">
	<label for="didascalia">
		<g:message code="bioGrails.didascalia.label" default="Didascalia" />
		
	</label>
	










<g:textField name="didascalia" value="${bioGrailsInstance?.didascalia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'didascalia2', 'error')} ">
	<label for="didascalia2">
		<g:message code="bioGrails.didascalia2.label" default="Didascalia2" />
		
	</label>
	










<g:textField name="didascalia2" value="${bioGrailsInstance?.didascalia2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'dimImmagine', 'error')} ">
	<label for="dimImmagine">
		<g:message code="bioGrails.dimImmagine.label" default="Dim Immagine" />
		
	</label>
	










<g:textField name="dimImmagine" value="${bioGrailsInstance?.dimImmagine}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'giornoMeseNascitaLink', 'error')} ">
	<label for="giornoMeseNascitaLink">
		<g:message code="bioGrails.giornoMeseNascitaLink.label" default="Giorno Mese Nascita Link" />
		
	</label>
	










<g:select id="giornoMeseNascitaLink" name="giornoMeseNascitaLink.id" from="${it.algos.botbio.Giorno.list()}" optionKey="id" value="${bioGrailsInstance?.giornoMeseNascitaLink?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'giornoMeseMorteLink', 'error')} ">
	<label for="giornoMeseMorteLink">
		<g:message code="bioGrails.giornoMeseMorteLink.label" default="Giorno Mese Morte Link" />
		
	</label>
	










<g:select id="giornoMeseMorteLink" name="giornoMeseMorteLink.id" from="${it.algos.botbio.Giorno.list()}" optionKey="id" value="${bioGrailsInstance?.giornoMeseMorteLink?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'annoNascitaLink', 'error')} ">
	<label for="annoNascitaLink">
		<g:message code="bioGrails.annoNascitaLink.label" default="Anno Nascita Link" />
		
	</label>
	










<g:select id="annoNascitaLink" name="annoNascitaLink.id" from="${it.algos.botbio.Anno.list()}" optionKey="id" value="${bioGrailsInstance?.annoNascitaLink?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'annoMorteLink', 'error')} ">
	<label for="annoMorteLink">
		<g:message code="bioGrails.annoMorteLink.label" default="Anno Morte Link" />
		
	</label>
	










<g:select id="annoMorteLink" name="annoMorteLink.id" from="${it.algos.botbio.Anno.list()}" optionKey="id" value="${bioGrailsInstance?.annoMorteLink?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivitaLink', 'error')} ">
	<label for="attivitaLink">
		<g:message code="bioGrails.attivitaLink.label" default="Attivita Link" />
		
	</label>
	










<g:select id="attivitaLink" name="attivitaLink.id" from="${it.algos.botbio.Attivita.list()}" optionKey="id" value="${bioGrailsInstance?.attivitaLink?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivita2Link', 'error')} ">
	<label for="attivita2Link">
		<g:message code="bioGrails.attivita2Link.label" default="Attivita2 Link" />
		
	</label>
	










<g:select id="attivita2Link" name="attivita2Link.id" from="${it.algos.botbio.Attivita.list()}" optionKey="id" value="${bioGrailsInstance?.attivita2Link?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivita3Link', 'error')} ">
	<label for="attivita3Link">
		<g:message code="bioGrails.attivita3Link.label" default="Attivita3 Link" />
		
	</label>
	










<g:select id="attivita3Link" name="attivita3Link.id" from="${it.algos.botbio.Attivita.list()}" optionKey="id" value="${bioGrailsInstance?.attivita3Link?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'nazionalitaLink', 'error')} ">
	<label for="nazionalitaLink">
		<g:message code="bioGrails.nazionalitaLink.label" default="Nazionalita Link" />
		
	</label>
	










<g:select id="nazionalitaLink" name="nazionalitaLink.id" from="${it.algos.botbio.Nazionalita.list()}" optionKey="id" value="${bioGrailsInstance?.nazionalitaLink?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'allineata', 'error')} ">
	<label for="allineata">
		<g:message code="bioGrails.allineata.label" default="Allineata" />
		
	</label>
	










<g:checkBox name="allineata" value="${bioGrailsInstance?.allineata}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'annoMorteErrato', 'error')} ">
	<label for="annoMorteErrato">
		<g:message code="bioGrails.annoMorteErrato.label" default="Anno Morte Errato" />
		
	</label>
	










<g:checkBox name="annoMorteErrato" value="${bioGrailsInstance?.annoMorteErrato}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'annoMorteValido', 'error')} ">
	<label for="annoMorteValido">
		<g:message code="bioGrails.annoMorteValido.label" default="Anno Morte Valido" />
		
	</label>
	










<g:checkBox name="annoMorteValido" value="${bioGrailsInstance?.annoMorteValido}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'annoNascitaErrato', 'error')} ">
	<label for="annoNascitaErrato">
		<g:message code="bioGrails.annoNascitaErrato.label" default="Anno Nascita Errato" />
		
	</label>
	










<g:checkBox name="annoNascitaErrato" value="${bioGrailsInstance?.annoNascitaErrato}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'annoNascitaValido', 'error')} ">
	<label for="annoNascitaValido">
		<g:message code="bioGrails.annoNascitaValido.label" default="Anno Nascita Valido" />
		
	</label>
	










<g:checkBox name="annoNascitaValido" value="${bioGrailsInstance?.annoNascitaValido}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivita2Errato', 'error')} ">
	<label for="attivita2Errato">
		<g:message code="bioGrails.attivita2Errato.label" default="Attivita2 Errato" />
		
	</label>
	










<g:checkBox name="attivita2Errato" value="${bioGrailsInstance?.attivita2Errato}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivita2Valida', 'error')} ">
	<label for="attivita2Valida">
		<g:message code="bioGrails.attivita2Valida.label" default="Attivita2 Valida" />
		
	</label>
	










<g:checkBox name="attivita2Valida" value="${bioGrailsInstance?.attivita2Valida}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivita3Errato', 'error')} ">
	<label for="attivita3Errato">
		<g:message code="bioGrails.attivita3Errato.label" default="Attivita3 Errato" />
		
	</label>
	










<g:checkBox name="attivita3Errato" value="${bioGrailsInstance?.attivita3Errato}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivita3Valida', 'error')} ">
	<label for="attivita3Valida">
		<g:message code="bioGrails.attivita3Valida.label" default="Attivita3 Valida" />
		
	</label>
	










<g:checkBox name="attivita3Valida" value="${bioGrailsInstance?.attivita3Valida}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivitaErrato', 'error')} ">
	<label for="attivitaErrato">
		<g:message code="bioGrails.attivitaErrato.label" default="Attivita Errato" />
		
	</label>
	










<g:checkBox name="attivitaErrato" value="${bioGrailsInstance?.attivitaErrato}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'attivitaValida', 'error')} ">
	<label for="attivitaValida">
		<g:message code="bioGrails.attivitaValida.label" default="Attivita Valida" />
		
	</label>
	










<g:checkBox name="attivitaValida" value="${bioGrailsInstance?.attivitaValida}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'controllato', 'error')} ">
	<label for="controllato">
		<g:message code="bioGrails.controllato.label" default="Controllato" />
		
	</label>
	










<g:checkBox name="controllato" value="${bioGrailsInstance?.controllato}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'meseMorteErrato', 'error')} ">
	<label for="meseMorteErrato">
		<g:message code="bioGrails.meseMorteErrato.label" default="Mese Morte Errato" />
		
	</label>
	










<g:checkBox name="meseMorteErrato" value="${bioGrailsInstance?.meseMorteErrato}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'meseMorteValido', 'error')} ">
	<label for="meseMorteValido">
		<g:message code="bioGrails.meseMorteValido.label" default="Mese Morte Valido" />
		
	</label>
	










<g:checkBox name="meseMorteValido" value="${bioGrailsInstance?.meseMorteValido}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'meseNascitaErrato', 'error')} ">
	<label for="meseNascitaErrato">
		<g:message code="bioGrails.meseNascitaErrato.label" default="Mese Nascita Errato" />
		
	</label>
	










<g:checkBox name="meseNascitaErrato" value="${bioGrailsInstance?.meseNascitaErrato}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'meseNascitaValido', 'error')} ">
	<label for="meseNascitaValido">
		<g:message code="bioGrails.meseNascitaValido.label" default="Mese Nascita Valido" />
		
	</label>
	










<g:checkBox name="meseNascitaValido" value="${bioGrailsInstance?.meseNascitaValido}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'nazionalitaErrato', 'error')} ">
	<label for="nazionalitaErrato">
		<g:message code="bioGrails.nazionalitaErrato.label" default="Nazionalita Errato" />
		
	</label>
	










<g:checkBox name="nazionalitaErrato" value="${bioGrailsInstance?.nazionalitaErrato}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioGrailsInstance, field: 'nazionalitaValida', 'error')} ">
	<label for="nazionalitaValida">
		<g:message code="bioGrails.nazionalitaValida.label" default="Nazionalita Valida" />
		
	</label>
	










<g:checkBox name="nazionalitaValida" value="${bioGrailsInstance?.nazionalitaValida}" />
</div>

