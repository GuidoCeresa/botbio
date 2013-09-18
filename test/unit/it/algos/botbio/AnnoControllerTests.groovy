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

@TestFor(AnnoController)
@Mock(Anno)
class AnnoControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/anno/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.annoInstanceList.size() == 0
        assert model.annoInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.annoInstance != null
    }

    void testSave() {
        controller.save()

        assert model.annoInstance != null
        assert view == '/anno/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/anno/show/1'
        assert controller.flash.message != null
        assert Anno.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/anno/list'

        populateValidParams(params)
        def anno = new Anno(params)

        assert anno.save() != null

        params.id = anno.id

        def model = controller.show()

        assert model.annoInstance == anno
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/anno/list'

        populateValidParams(params)
        def anno = new Anno(params)

        assert anno.save() != null

        params.id = anno.id

        def model = controller.edit()

        assert model.annoInstance == anno
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/anno/list'

        response.reset()

        populateValidParams(params)
        def anno = new Anno(params)

        assert anno.save() != null

        // test invalid parameters in update
        params.id = anno.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/anno/edit"
        assert model.annoInstance != null

        anno.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/anno/show/$anno.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        anno.clearErrors()

        populateValidParams(params)
        params.id = anno.id
        params.version = -1
        controller.update()

        assert view == "/anno/edit"
        assert model.annoInstance != null
        assert model.annoInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/anno/list'

        response.reset()

        populateValidParams(params)
        def anno = new Anno(params)

        assert anno.save() != null
        assert Anno.count() == 1

        params.id = anno.id

        controller.delete()

        assert Anno.count() == 0
        assert Anno.get(anno.id) == null
        assert response.redirectedUrl == '/anno/list'
    }
}
