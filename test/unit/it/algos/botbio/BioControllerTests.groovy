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

import grails.test.mixin.*

@TestFor(BioController)
@Mock(Bio)
class BioControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/biografia/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.biografiaInstanceList.size() == 0
        assert model.biografiaInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.biografiaInstance != null
    }

    void testSave() {
        controller.save()

        assert model.biografiaInstance != null
        assert view == '/biografia/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/biografia/show/1'
        assert controller.flash.message != null
        assert Bio.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/biografia/list'

        populateValidParams(params)
        def biografia = new Bio(params)

        assert biografia.save() != null

        params.id = biografia.id

        def model = controller.show()

        assert model.biografiaInstance == biografia
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/biografia/list'

        populateValidParams(params)
        def biografia = new Bio(params)

        assert biografia.save() != null

        params.id = biografia.id

        def model = controller.edit()

        assert model.biografiaInstance == biografia
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/biografia/list'

        response.reset()

        populateValidParams(params)
        def biografia = new Bio(params)

        assert biografia.save() != null

        // test invalid parameters in update
        params.id = biografia.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/biografia/edit"
        assert model.biografiaInstance != null

        biografia.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/biografia/show/$biografia.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        biografia.clearErrors()

        populateValidParams(params)
        params.id = biografia.id
        params.version = -1
        controller.update()

        assert view == "/biografia/edit"
        assert model.biografiaInstance != null
        assert model.biografiaInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/biografia/list'

        response.reset()

        populateValidParams(params)
        def biografia = new Bio(params)

        assert biografia.save() != null
        assert Bio.count() == 1

        params.id = biografia.id

        controller.delete()

        assert Bio.count() == 0
        assert Bio.get(biografia.id) == null
        assert response.redirectedUrl == '/biografia/list'
    }
}
