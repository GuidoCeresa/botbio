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

@TestFor(NazionalitaController)
@Mock(Nazionalita)
class NazionalitaControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/nazionalita/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.nazionalitaInstanceList.size() == 0
        assert model.nazionalitaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.nazionalitaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.nazionalitaInstance != null
        assert view == '/nazionalita/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/nazionalita/show/1'
        assert controller.flash.message != null
        assert Nazionalita.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/nazionalita/list'

        populateValidParams(params)
        def nazionalita = new Nazionalita(params)

        assert nazionalita.save() != null

        params.id = nazionalita.id

        def model = controller.show()

        assert model.nazionalitaInstance == nazionalita
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/nazionalita/list'

        populateValidParams(params)
        def nazionalita = new Nazionalita(params)

        assert nazionalita.save() != null

        params.id = nazionalita.id

        def model = controller.edit()

        assert model.nazionalitaInstance == nazionalita
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/nazionalita/list'

        response.reset()

        populateValidParams(params)
        def nazionalita = new Nazionalita(params)

        assert nazionalita.save() != null

        // test invalid parameters in update
        params.id = nazionalita.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/nazionalita/edit"
        assert model.nazionalitaInstance != null

        nazionalita.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/nazionalita/show/$nazionalita.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        nazionalita.clearErrors()

        populateValidParams(params)
        params.id = nazionalita.id
        params.version = -1
        controller.update()

        assert view == "/nazionalita/edit"
        assert model.nazionalitaInstance != null
        assert model.nazionalitaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/nazionalita/list'

        response.reset()

        populateValidParams(params)
        def nazionalita = new Nazionalita(params)

        assert nazionalita.save() != null
        assert Nazionalita.count() == 1

        params.id = nazionalita.id

        controller.delete()

        assert Nazionalita.count() == 0
        assert Nazionalita.get(nazionalita.id) == null
        assert response.redirectedUrl == '/nazionalita/list'
    }
}
