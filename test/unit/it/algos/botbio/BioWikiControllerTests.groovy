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

@TestFor(BioWikiController)
@Mock(BioWiki)
class BioWikiControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/bioWiki/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.bioWikiInstanceList.size() == 0
        assert model.bioWikiInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.bioWikiInstance != null
    }

    void testSave() {
        controller.save()

        assert model.bioWikiInstance != null
        assert view == '/bioWiki/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/bioWiki/show/1'
        assert controller.flash.message != null
        assert BioWiki.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/bioWiki/list'

        populateValidParams(params)
        def bioWiki = new BioWiki(params)

        assert bioWiki.save() != null

        params.id = bioWiki.id

        def model = controller.show()

        assert model.bioWikiInstance == bioWiki
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/bioWiki/list'

        populateValidParams(params)
        def bioWiki = new BioWiki(params)

        assert bioWiki.save() != null

        params.id = bioWiki.id

        def model = controller.edit()

        assert model.bioWikiInstance == bioWiki
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/bioWiki/list'

        response.reset()

        populateValidParams(params)
        def bioWiki = new BioWiki(params)

        assert bioWiki.save() != null

        // test invalid parameters in update
        params.id = bioWiki.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/bioWiki/edit"
        assert model.bioWikiInstance != null

        bioWiki.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/bioWiki/show/$bioWiki.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        bioWiki.clearErrors()

        populateValidParams(params)
        params.id = bioWiki.id
        params.version = -1
        controller.update()

        assert view == "/bioWiki/edit"
        assert model.bioWikiInstance != null
        assert model.bioWikiInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/bioWiki/list'

        response.reset()

        populateValidParams(params)
        def bioWiki = new BioWiki(params)

        assert bioWiki.save() != null
        assert BioWiki.count() == 1

        params.id = bioWiki.id

        controller.delete()

        assert BioWiki.count() == 0
        assert BioWiki.get(bioWiki.id) == null
        assert response.redirectedUrl == '/bioWiki/list'
    }
}
