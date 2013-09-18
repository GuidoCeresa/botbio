












<%@ page import="it.algos.botbio.BioWiki" %>



<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'pageid', 'error')} required">
	<label for="pageid">
		<g:message code="bioWiki.pageid.label" default="Pageid" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="pageid" type="number" value="${bioWikiInstance.pageid}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'titolo', 'error')} ">
	<label for="titolo">
		<g:message code="bioWiki.titolo.label" default="Titolo" />
		
	</label>
	










<g:textField name="titolo" value="${bioWikiInstance?.titolo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'nome', 'error')} ">
	<label for="nome">
		<g:message code="bioWiki.nome.label" default="Nome" />
		
	</label>
	










<g:textField name="nome" value="${bioWikiInstance?.nome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'cognome', 'error')} ">
	<label for="cognome">
		<g:message code="bioWiki.cognome.label" default="Cognome" />
		
	</label>
	










<g:textField name="cognome" value="${bioWikiInstance?.cognome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'postCognome', 'error')} ">
	<label for="postCognome">
		<g:message code="bioWiki.postCognome.label" default="Post Cognome" />
		
	</label>
	










<g:textField name="postCognome" value="${bioWikiInstance?.postCognome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'postCognomeVirgola', 'error')} ">
	<label for="postCognomeVirgola">
		<g:message code="bioWiki.postCognomeVirgola.label" default="Post Cognome Virgola" />
		
	</label>
	










<g:textField name="postCognomeVirgola" value="${bioWikiInstance?.postCognomeVirgola}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'forzaOrdinamento', 'error')} ">
	<label for="forzaOrdinamento">
		<g:message code="bioWiki.forzaOrdinamento.label" default="Forza Ordinamento" />
		
	</label>
	










<g:textField name="forzaOrdinamento" value="${bioWikiInstance?.forzaOrdinamento}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'preData', 'error')} ">
	<label for="preData">
		<g:message code="bioWiki.preData.label" default="Pre Data" />
		
	</label>
	










<g:textField name="preData" value="${bioWikiInstance?.preData}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'sesso', 'error')} ">
	<label for="sesso">
		<g:message code="bioWiki.sesso.label" default="Sesso" />
		
	</label>
	










<g:textField name="sesso" value="${bioWikiInstance?.sesso}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'luogoNascita', 'error')} ">
	<label for="luogoNascita">
		<g:message code="bioWiki.luogoNascita.label" default="Luogo Nascita" />
		
	</label>
	










<g:textField name="luogoNascita" value="${bioWikiInstance?.luogoNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'luogoNascitaLink', 'error')} ">
	<label for="luogoNascitaLink">
		<g:message code="bioWiki.luogoNascitaLink.label" default="Luogo Nascita Link" />
		
	</label>
	










<g:textField name="luogoNascitaLink" value="${bioWikiInstance?.luogoNascitaLink}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'luogoNascitaAlt', 'error')} ">
	<label for="luogoNascitaAlt">
		<g:message code="bioWiki.luogoNascitaAlt.label" default="Luogo Nascita Alt" />
		
	</label>
	










<g:textField name="luogoNascitaAlt" value="${bioWikiInstance?.luogoNascitaAlt}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'giornoMeseNascita', 'error')} ">
	<label for="giornoMeseNascita">
		<g:message code="bioWiki.giornoMeseNascita.label" default="Giorno Mese Nascita" />
		
	</label>
	










<g:textField name="giornoMeseNascita" value="${bioWikiInstance?.giornoMeseNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'annoNascita', 'error')} ">
	<label for="annoNascita">
		<g:message code="bioWiki.annoNascita.label" default="Anno Nascita" />
		
	</label>
	










<g:textField name="annoNascita" value="${bioWikiInstance?.annoNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'noteNascita', 'error')} ">
	<label for="noteNascita">
		<g:message code="bioWiki.noteNascita.label" default="Note Nascita" />
		
	</label>
	










<g:textField name="noteNascita" value="${bioWikiInstance?.noteNascita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'luogoMorte', 'error')} ">
	<label for="luogoMorte">
		<g:message code="bioWiki.luogoMorte.label" default="Luogo Morte" />
		
	</label>
	










<g:textField name="luogoMorte" value="${bioWikiInstance?.luogoMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'luogoMorteLink', 'error')} ">
	<label for="luogoMorteLink">
		<g:message code="bioWiki.luogoMorteLink.label" default="Luogo Morte Link" />
		
	</label>
	










<g:textField name="luogoMorteLink" value="${bioWikiInstance?.luogoMorteLink}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'luogoMorteAlt', 'error')} ">
	<label for="luogoMorteAlt">
		<g:message code="bioWiki.luogoMorteAlt.label" default="Luogo Morte Alt" />
		
	</label>
	










<g:textField name="luogoMorteAlt" value="${bioWikiInstance?.luogoMorteAlt}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'giornoMeseMorte', 'error')} ">
	<label for="giornoMeseMorte">
		<g:message code="bioWiki.giornoMeseMorte.label" default="Giorno Mese Morte" />
		
	</label>
	










<g:textField name="giornoMeseMorte" value="${bioWikiInstance?.giornoMeseMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'annoMorte', 'error')} ">
	<label for="annoMorte">
		<g:message code="bioWiki.annoMorte.label" default="Anno Morte" />
		
	</label>
	










<g:textField name="annoMorte" value="${bioWikiInstance?.annoMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'noteMorte', 'error')} ">
	<label for="noteMorte">
		<g:message code="bioWiki.noteMorte.label" default="Note Morte" />
		
	</label>
	










<g:textField name="noteMorte" value="${bioWikiInstance?.noteMorte}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'preAttivita', 'error')} ">
	<label for="preAttivita">
		<g:message code="bioWiki.preAttivita.label" default="Pre Attivita" />
		
	</label>
	










<g:textField name="preAttivita" value="${bioWikiInstance?.preAttivita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'attivita', 'error')} ">
	<label for="attivita">
		<g:message code="bioWiki.attivita.label" default="Attivita" />
		
	</label>
	










<g:textField name="attivita" value="${bioWikiInstance?.attivita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'epoca', 'error')} ">
	<label for="epoca">
		<g:message code="bioWiki.epoca.label" default="Epoca" />
		
	</label>
	










<g:textField name="epoca" value="${bioWikiInstance?.epoca}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'epoca2', 'error')} ">
	<label for="epoca2">
		<g:message code="bioWiki.epoca2.label" default="Epoca2" />
		
	</label>
	










<g:textField name="epoca2" value="${bioWikiInstance?.epoca2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'cittadinanza', 'error')} ">
	<label for="cittadinanza">
		<g:message code="bioWiki.cittadinanza.label" default="Cittadinanza" />
		
	</label>
	










<g:textField name="cittadinanza" value="${bioWikiInstance?.cittadinanza}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'attivita2', 'error')} ">
	<label for="attivita2">
		<g:message code="bioWiki.attivita2.label" default="Attivita2" />
		
	</label>
	










<g:textField name="attivita2" value="${bioWikiInstance?.attivita2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'attivita3', 'error')} ">
	<label for="attivita3">
		<g:message code="bioWiki.attivita3.label" default="Attivita3" />
		
	</label>
	










<g:textField name="attivita3" value="${bioWikiInstance?.attivita3}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'attivitaAltre', 'error')} ">
	<label for="attivitaAltre">
		<g:message code="bioWiki.attivitaAltre.label" default="Attivita Altre" />
		
	</label>
	










<g:textField name="attivitaAltre" value="${bioWikiInstance?.attivitaAltre}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'nazionalita', 'error')} ">
	<label for="nazionalita">
		<g:message code="bioWiki.nazionalita.label" default="Nazionalita" />
		
	</label>
	










<g:textField name="nazionalita" value="${bioWikiInstance?.nazionalita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'nazionalitaNaturalizzato', 'error')} ">
	<label for="nazionalitaNaturalizzato">
		<g:message code="bioWiki.nazionalitaNaturalizzato.label" default="Nazionalita Naturalizzato" />
		
	</label>
	










<g:textField name="nazionalitaNaturalizzato" value="${bioWikiInstance?.nazionalitaNaturalizzato}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'postNazionalita', 'error')} ">
	<label for="postNazionalita">
		<g:message code="bioWiki.postNazionalita.label" default="Post Nazionalita" />
		
	</label>
	










<g:textField name="postNazionalita" value="${bioWikiInstance?.postNazionalita}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'categorie', 'error')} ">
	<label for="categorie">
		<g:message code="bioWiki.categorie.label" default="Categorie" />
		
	</label>
	










<g:textField name="categorie" value="${bioWikiInstance?.categorie}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'fineIncipit', 'error')} ">
	<label for="fineIncipit">
		<g:message code="bioWiki.fineIncipit.label" default="Fine Incipit" />
		
	</label>
	










<g:textField name="fineIncipit" value="${bioWikiInstance?.fineIncipit}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'punto', 'error')} ">
	<label for="punto">
		<g:message code="bioWiki.punto.label" default="Punto" />
		
	</label>
	










<g:textField name="punto" value="${bioWikiInstance?.punto}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'immagine', 'error')} ">
	<label for="immagine">
		<g:message code="bioWiki.immagine.label" default="Immagine" />
		
	</label>
	










<g:textField name="immagine" value="${bioWikiInstance?.immagine}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'didascalia', 'error')} ">
	<label for="didascalia">
		<g:message code="bioWiki.didascalia.label" default="Didascalia" />
		
	</label>
	










<g:textField name="didascalia" value="${bioWikiInstance?.didascalia}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'didascalia2', 'error')} ">
	<label for="didascalia2">
		<g:message code="bioWiki.didascalia2.label" default="Didascalia2" />
		
	</label>
	










<g:textField name="didascalia2" value="${bioWikiInstance?.didascalia2}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'dimImmagine', 'error')} ">
	<label for="dimImmagine">
		<g:message code="bioWiki.dimImmagine.label" default="Dim Immagine" />
		
	</label>
	










<g:textField name="dimImmagine" value="${bioWikiInstance?.dimImmagine}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'wikiUrl', 'error')} ">
	<label for="wikiUrl">
		<g:message code="bioWiki.wikiUrl.label" default="Wiki Url" />
		
	</label>
	










<g:textField name="wikiUrl" value="${bioWikiInstance?.wikiUrl}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'title', 'error')} ">
	<label for="title">
		<g:message code="bioWiki.title.label" default="Title" />
		
	</label>
	










<g:textField name="title" value="${bioWikiInstance?.title}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'testoTemplate', 'error')} ">
	<label for="testoTemplate">
		<g:message code="bioWiki.testoTemplate.label" default="Testo Template" />
		
	</label>
	










<g:textField name="testoTemplate" value="${bioWikiInstance?.testoTemplate}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'ns', 'error')} required">
	<label for="ns">
		<g:message code="bioWiki.ns.label" default="Ns" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="ns" type="number" value="${bioWikiInstance.ns}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'touched', 'error')} ">
	<label for="touched">
		<g:message code="bioWiki.touched.label" default="Touched" />
		
	</label>
	










<g:datePicker name="touched" precision="day"  value="${bioWikiInstance?.touched}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'revid', 'error')} required">
	<label for="revid">
		<g:message code="bioWiki.revid.label" default="Revid" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="revid" type="number" value="${bioWikiInstance.revid}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'size', 'error')} required">
	<label for="size">
		<g:message code="bioWiki.size.label" default="Size" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="size" type="number" value="${bioWikiInstance.size}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'user', 'error')} ">
	<label for="user">
		<g:message code="bioWiki.user.label" default="User" />
		
	</label>
	










<g:textField name="user" value="${bioWikiInstance?.user}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'timestamp', 'error')} ">
	<label for="timestamp">
		<g:message code="bioWiki.timestamp.label" default="Timestamp" />
		
	</label>
	











</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'comment', 'error')} ">
	<label for="comment">
		<g:message code="bioWiki.comment.label" default="Comment" />
		
	</label>
	










<g:textField name="comment" value="${bioWikiInstance?.comment}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'logNote', 'error')} ">
	<label for="logNote">
		<g:message code="bioWiki.logNote.label" default="Log Note" />
		
	</label>
	










<g:textField name="logNote" value="${bioWikiInstance?.logNote}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'logErr', 'error')} ">
	<label for="logErr">
		<g:message code="bioWiki.logErr.label" default="Log Err" />
		
	</label>
	










<g:textField name="logErr" value="${bioWikiInstance?.logErr}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'langlinks', 'error')} required">
	<label for="langlinks">
		<g:message code="bioWiki.langlinks.label" default="Langlinks" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="langlinks" type="number" value="${bioWikiInstance.langlinks}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'ultimaLettura', 'error')} ">
	<label for="ultimaLettura">
		<g:message code="bioWiki.ultimaLettura.label" default="Ultima Lettura" />
		
	</label>
	











</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'ultimaScrittura', 'error')} ">
	<label for="ultimaScrittura">
		<g:message code="bioWiki.ultimaScrittura.label" default="Ultima Scrittura" />
		
	</label>
	











</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'extra', 'error')} ">
	<label for="extra">
		<g:message code="bioWiki.extra.label" default="Extra" />
		
	</label>
	










<g:checkBox name="extra" value="${bioWikiInstance?.extra}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'extraLista', 'error')} ">
	<label for="extraLista">
		<g:message code="bioWiki.extraLista.label" default="Extra Lista" />
		
	</label>
	










<g:textField name="extraLista" value="${bioWikiInstance?.extraLista}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'graffe', 'error')} ">
	<label for="graffe">
		<g:message code="bioWiki.graffe.label" default="Graffe" />
		
	</label>
	










<g:checkBox name="graffe" value="${bioWikiInstance?.graffe}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'note', 'error')} ">
	<label for="note">
		<g:message code="bioWiki.note.label" default="Note" />
		
	</label>
	










<g:checkBox name="note" value="${bioWikiInstance?.note}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'nascosto', 'error')} ">
	<label for="nascosto">
		<g:message code="bioWiki.nascosto.label" default="Nascosto" />
		
	</label>
	










<g:checkBox name="nascosto" value="${bioWikiInstance?.nascosto}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'errori', 'error')} ">
	<label for="errori">
		<g:message code="bioWiki.errori.label" default="Errori" />
		
	</label>
	










<g:textField name="errori" value="${bioWikiInstance?.errori}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'sizeBio', 'error')} required">
	<label for="sizeBio">
		<g:message code="bioWiki.sizeBio.label" default="Size Bio" />
		<span class="required-indicator">*</span>
	</label>
	










<g:field name="sizeBio" type="number" value="${bioWikiInstance.sizeBio}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'allineata', 'error')} ">
	<label for="allineata">
		<g:message code="bioWiki.allineata.label" default="Allineata" />
		
	</label>
	










<g:checkBox name="allineata" value="${bioWikiInstance?.allineata}" />
</div>

<div class="fieldcontain ${hasErrors(bean: bioWikiInstance, field: 'controllato', 'error')} ">
	<label for="controllato">
		<g:message code="bioWiki.controllato.label" default="Controllato" />
		
	</label>
	










<g:checkBox name="controllato" value="${bioWikiInstance?.controllato}" />
</div>

