```text
2023-02-24 08:46:36 2023-02-23 23:46:36,389 WARN  [liquibase.database.DatabaseFactory] (main) Unknown database: MySQL
2023-02-24 08:46:36 2023-02-23 23:46:36,414 WARN  [org.keycloak.connections.jpa.updater.liquibase.lock.CustomLockDatabaseChangeLogGenerator] (main) No direct support for database liquibase.database.core.UnsupportedDatabase . Database lock may not work correctly
2023-02-24 08:46:36 2023-02-23 23:46:36,415 WARN  [liquibase.database.DatabaseFactory] (main) Unknown database: MySQL
2023-02-24 08:46:36 2023-02-23 23:46:36,426 INFO  [org.keycloak.quarkus.runtime.storage.legacy.liquibase.QuarkusJpaUpdaterProvider] (main) Initializing database schema. Using changelog META-INF/jpa-changelog-master.xml
2023-02-24 08:46:37 2023-02-23 23:46:37,110 ERROR [org.keycloak.quarkus.runtime.cli.ExecutionExceptionHandler] (main) ERROR: Failed to start server in (development) mode
2023-02-24 08:46:37 2023-02-23 23:46:37,110 ERROR [org.keycloak.quarkus.runtime.cli.ExecutionExceptionHandler] (main) Error details:: java.lang.RuntimeException: Failed to update database
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.storage.legacy.liquibase.QuarkusJpaUpdaterProvider.update(QuarkusJpaUpdaterProvider.java:122)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.storage.legacy.liquibase.QuarkusJpaUpdaterProvider.update(QuarkusJpaUpdaterProvider.java:85)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.storage.legacy.database.LegacyJpaConnectionProviderFactory.update(LegacyJpaConnectionProviderFactory.java:298)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.storage.legacy.database.LegacyJpaConnectionProviderFactory.createOrUpdateSchema(LegacyJpaConnectionProviderFactory.java:264)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.storage.legacy.database.LegacyJpaConnectionProviderFactory.postInit(LegacyJpaConnectionProviderFactory.java:130)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.integration.QuarkusKeycloakSessionFactory.init(QuarkusKeycloakSessionFactory.java:104)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.integration.jaxrs.QuarkusKeycloakApplication.startup(QuarkusKeycloakApplication.java:42)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.integration.QuarkusLifecycleObserver.onStartupEvent(QuarkusLifecycleObserver.java:37)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.integration.QuarkusLifecycleObserver_Observer_onStartupEvent_b0e82415b143738dc1f986a5fa4668e83d0a5dea.notify(Unknown Source)
2023-02-24 08:46:37     at io.quarkus.arc.impl.EventImpl$Notifier.notifyObservers(EventImpl.java:326)
2023-02-24 08:46:37     at io.quarkus.arc.impl.EventImpl$Notifier.notify(EventImpl.java:308)
2023-02-24 08:46:37     at io.quarkus.arc.impl.EventImpl.fire(EventImpl.java:76)
2023-02-24 08:46:37     at io.quarkus.arc.runtime.ArcRecorder.fireLifecycleEvent(ArcRecorder.java:131)
2023-02-24 08:46:37     at io.quarkus.arc.runtime.ArcRecorder.handleLifecycleEvents(ArcRecorder.java:100)
2023-02-24 08:46:37     at io.quarkus.deployment.steps.LifecycleEventsBuildStep$startupEvent1144526294.deploy_0(Unknown Source)
2023-02-24 08:46:37     at io.quarkus.deployment.steps.LifecycleEventsBuildStep$startupEvent1144526294.deploy(Unknown Source)
2023-02-24 08:46:37     at io.quarkus.runner.ApplicationImpl.doStart(Unknown Source)
2023-02-24 08:46:37     at io.quarkus.runtime.Application.start(Application.java:101)
2023-02-24 08:46:37     at io.quarkus.runtime.ApplicationLifecycleManager.run(ApplicationLifecycleManager.java:110)
2023-02-24 08:46:37     at io.quarkus.runtime.Quarkus.run(Quarkus.java:70)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.KeycloakMain.start(KeycloakMain.java:103)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.cli.command.AbstractStartCommand.run(AbstractStartCommand.java:37)
2023-02-24 08:46:37     at picocli.CommandLine.executeUserObject(CommandLine.java:1939)
2023-02-24 08:46:37     at picocli.CommandLine.access$1300(CommandLine.java:145)
2023-02-24 08:46:37     at picocli.CommandLine$RunLast.executeUserObjectOfLastSubcommandWithSameParent(CommandLine.java:2358)
2023-02-24 08:46:37     at picocli.CommandLine$RunLast.handle(CommandLine.java:2352)
2023-02-24 08:46:37     at picocli.CommandLine$RunLast.handle(CommandLine.java:2314)
2023-02-24 08:46:37     at picocli.CommandLine$AbstractParseResultHandler.execute(CommandLine.java:2179)
2023-02-24 08:46:37     at picocli.CommandLine$RunLast.execute(CommandLine.java:2316)
2023-02-24 08:46:37     at picocli.CommandLine.execute(CommandLine.java:2078)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.cli.Picocli.parseAndRun(Picocli.java:93)
2023-02-24 08:46:37     at org.keycloak.quarkus.runtime.KeycloakMain.main(KeycloakMain.java:89)
2023-02-24 08:46:37     at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
2023-02-24 08:46:37     at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
2023-02-24 08:46:37     at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
2023-02-24 08:46:37     at java.base/java.lang.reflect.Method.invoke(Method.java:566)
2023-02-24 08:46:37     at io.quarkus.bootstrap.runner.QuarkusEntryPoint.doRun(QuarkusEntryPoint.java:61)
2023-02-24 08:46:37     at io.quarkus.bootstrap.runner.QuarkusEntryPoint.main(QuarkusEntryPoint.java:32)
2023-02-24 08:46:37 Caused by: liquibase.exception.LiquibaseException: liquibase.exception.MigrationFailedException: Migration failed for change set META-INF/jpa-changelog-1.2.0.Beta1.xml::1.2.0.Beta1::psilva@redhat.com:
2023-02-24 08:46:37      Reason: liquibase.exception.DatabaseException: You have an error in your SQL syntax; check the manual that corresponds to your MariaDB server version for the right syntax to use near ' NAME VARCHAR(255) NOT NULL)' at line 1 [Failed SQL: (1064) CREATE TABLE keycloak.PROTOCOL_MAPPER_CONFIG (PROTOCOL_MAPPER_ID VARCHAR(36) NOT NULL, VALUE CLOB, NAME VARCHAR(255) NOT NULL)]
```
