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

@TestFor(GiornoController)
@Mock(Giorno)
class GiornoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/giorno/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.giornoInstanceList.size() == 0
        assert model.giornoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.giornoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.giornoInstance != null
        assert view == '/giorno/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/giorno/show/1'
        assert controller.flash.message != null
        assert Giorno.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/giorno/list'

        populateValidParams(params)
        def giorno = new Giorno(params)

        assert giorno.save() != null

        params.id = giorno.id

        def model = controller.show()

        assert model.giornoInstance == giorno
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/giorno/list'

        populateValidParams(params)
        def giorno = new Giorno(params)

        assert giorno.save() != null

        params.id = giorno.id

        def model = controller.edit()

        assert model.giornoInstance == giorno
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/giorno/list'

        response.reset()

        populateValidParams(params)
        def giorno = new Giorno(params)

        assert giorno.save() != null

        // test invalid parameters in update
        params.id = giorno.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/giorno/edit"
        assert model.giornoInstance != null

        giorno.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/giorno/show/$giorno.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        giorno.clearErrors()

        populateValidParams(params)
        params.id = giorno.id
        params.version = -1
        controller.update()

        assert view == "/giorno/edit"
        assert model.giornoInstance != null
        assert model.giornoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/giorno/list'

        response.reset()

        populateValidParams(params)
        def giorno = new Giorno(params)

        assert giorno.save() != null
        assert Giorno.count() == 1

        params.id = giorno.id

        controller.delete()

        assert Giorno.count() == 0
        assert Giorno.get(giorno.id) == null
        assert response.redirectedUrl == '/giorno/list'
    }
}
