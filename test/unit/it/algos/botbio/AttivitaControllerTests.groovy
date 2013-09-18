/* Created by Algos s.r.l. */
/* Date: mag 2013 */
/* Il plugin Algos ha inserito (solo la prima volta) questo header per controllare */
/* le successive release (tramite il flag di controllo aggiunto) */
/* Tipicamente VERRA sovrascritto ad ogni nuova release del plugin */
/* per rimanere aggiornato */
/* Se vuoi che le prossime release del plugin NON sovrascrivano questo file, */
/* perdendo tutte le modifiche precedentemente effettuate, */
/* regola a false il flag di controllo flagOverwrite© */
/* flagOverwrite = true */

package it.algos.botbio



import org.junit.*
import grails.test.mixin.*

@TestFor(AttivitaController)
@Mock(Attivita)
class AttivitaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/attivita/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.attivitaInstanceList.size() == 0
        assert model.attivitaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.attivitaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.attivitaInstance != null
        assert view == '/attivita/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/attivita/show/1'
        assert controller.flash.message != null
        assert Attivita.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/attivita/list'

        populateValidParams(params)
        def attivita = new Attivita(params)

        assert attivita.save() != null

        params.id = attivita.id

        def model = controller.show()

        assert model.attivitaInstance == attivita
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/attivita/list'

        populateValidParams(params)
        def attivita = new Attivita(params)

        assert attivita.save() != null

        params.id = attivita.id

        def model = controller.edit()

        assert model.attivitaInstance == attivita
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/attivita/list'

        response.reset()

        populateValidParams(params)
        def attivita = new Attivita(params)

        assert attivita.save() != null

        // test invalid parameters in update
        params.id = attivita.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/attivita/edit"
        assert model.attivitaInstance != null

        attivita.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/attivita/show/$attivita.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        attivita.clearErrors()

        populateValidParams(params)
        params.id = attivita.id
        params.version = -1
        controller.update()

        assert view == "/attivita/edit"
        assert model.attivitaInstance != null
        assert model.attivitaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/attivita/list'

        response.reset()

        populateValidParams(params)
        def attivita = new Attivita(params)

        assert attivita.save() != null
        assert Attivita.count() == 1

        params.id = attivita.id

        controller.delete()

        assert Attivita.count() == 0
        assert Attivita.get(attivita.id) == null
        assert response.redirectedUrl == '/attivita/list'
    }
}
