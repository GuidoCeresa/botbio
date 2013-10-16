/* Created by Algos s.r.l. */
/* Date: mag 2013 */
/* Il plugin Algos ha inserito (solo la prima volta) questo header per controllare */
/* le successive release (tramite il flag di controllo aggiunto) */
/* Tipicamente NON verrà più sovrascritto dalle successive release del plugin */
/* in quanto POTREBBE essere personalizzato in questa applicazione */
/* Se vuoi che le prossime release del plugin sovrascrivano questo file, */
/* perdendo tutte le modifiche precedentemente effettuate, */
/* regola a true il flag di controllo flagOverwrite© */
/* flagOverwrite = false */

// DEVI modificare qui il nome del database (da 'algosTest' a 'nomeDelDatabaseCheVoglioUsare')
// DEVI creare il database esternamente
// Devi abilitare la riga:
// runtime 'mysql:mysql-connector-java:5.1.22'
// nel file /grails-app/conf/BuildConfig.groovy

def dataBase = 'wiki'

dataSource {
    pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
    //dialect = "org.hibernate.dialect.MySQLMyISAMDialect"
    dialect = "org.hibernate.dialect.MySQL5InnoDBDialect"
    username = "root"
    password = ""
} // end of dataSource

hibernate {
    cache.use_second_level_cache = false
    cache.use_query_cache = false
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
} // end of hibernate

// environment specific settings
environments {
    development {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost/${dataBase}?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true"
        } // end of dataSource
    } // end of development

    test {
        dataSource {
            dbCreate = "update"
            url = "jdbc:h2:mem:testDb;MVCC=TRUE;LOCK_TIMEOUT=10000"
        } // end of dataSource
    } // end of production

    production {
        dataSource {
            dbCreate = "update" // one of 'create', 'create-drop','update'
            url = "jdbc:mysql://localhost/${dataBase}?useUnicode=yes&characterEncoding=UTF-8&autoReconnect=true"
        } // end of dataSource
    } // end of test
} // end of environments
