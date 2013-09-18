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

@TestFor(BioGrailsController)
@Mock(BioGrails)
class BioGrailsControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/bioGrails/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.bioGrailsInstanceList.size() == 0
        assert model.bioGrailsInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.bioGrailsInstance != null
    }

    void testSave() {
        controller.save()

        assert model.bioGrailsInstance != null
        assert view == '/bioGrails/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/bioGrails/show/1'
        assert controller.flash.message != null
        assert BioGrails.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/bioGrails/list'

        populateValidParams(params)
        def bioGrails = new BioGrails(params)

        assert bioGrails.save() != null

        params.id = bioGrails.id

        def model = controller.show()

        assert model.bioGrailsInstance == bioGrails
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/bioGrails/list'

        populateValidParams(params)
        def bioGrails = new BioGrails(params)

        assert bioGrails.save() != null

        params.id = bioGrails.id

        def model = controller.edit()

        assert model.bioGrailsInstance == bioGrails
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/bioGrails/list'

        response.reset()

        populateValidParams(params)
        def bioGrails = new BioGrails(params)

        assert bioGrails.save() != null

        // test invalid parameters in update
        params.id = bioGrails.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/bioGrails/edit"
        assert model.bioGrailsInstance != null

        bioGrails.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/bioGrails/show/$bioGrails.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        bioGrails.clearErrors()

        populateValidParams(params)
        params.id = bioGrails.id
        params.version = -1
        controller.update()

        assert view == "/bioGrails/edit"
        assert model.bioGrailsInstance != null
        assert model.bioGrailsInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/bioGrails/list'

        response.reset()

        populateValidParams(params)
        def bioGrails = new BioGrails(params)

        assert bioGrails.save() != null
        assert BioGrails.count() == 1

        params.id = bioGrails.id

        controller.delete()

        assert BioGrails.count() == 0
        assert BioGrails.get(bioGrails.id) == null
        assert response.redirectedUrl == '/bioGrails/list'
    }
}
