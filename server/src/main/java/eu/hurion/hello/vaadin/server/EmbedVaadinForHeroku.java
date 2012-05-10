package eu.hurion.hello.vaadin.server;

import com.bsb.common.vaadin.embed.EmbedVaadinConfig;
import com.bsb.common.vaadin.embed.EmbedVaadinServer;
import com.bsb.common.vaadin.embed.application.ApplicationBasedEmbedVaadinTomcat;
import com.bsb.common.vaadin.embed.application.EmbedVaadinApplication;
import com.vaadin.Application;
import de.javakaffee.web.msm.MemcachedBackupSessionManager;
import eu.hurion.hello.vaadin.server.MemcachedConfigurator.MemcachedConfiguration;

/**
 * Customized {@code EmbedVaadinApplication} to accommodate specialities of Heroku.
 *
 * @author Nicolas Hurion
 */
public class EmbedVaadinForHeroku extends EmbedVaadinApplication {


    private MemcachedConfigurator memcachedConfigurator;

    /**
     * Creates a new instance for the specified application.
     *
     * @param applicationClass the class of the application to deploy
     */
    public EmbedVaadinForHeroku(final Class<? extends Application> applicationClass) {
        super(applicationClass);
    }

    @Override
    protected EmbedVaadinForHeroku self() {
        return this;
    }

    @Override
    public EmbedVaadinServer build() {
        MemcachedConfiguration memcahcedConfig = null;
        if (this.memcachedConfigurator != null) {
            memcahcedConfig = memcachedConfigurator.build();
        }
        return new EmbedVaadinWithSessionManagement(getConfig(), getApplicationClass(),
                memcahcedConfig);
    }

    public static EmbedVaadinForHeroku forApplication(final Class<? extends Application> applicationClass) {
        return new EmbedVaadinForHeroku(applicationClass);
    }

    public EmbedVaadinForHeroku withMemcachedSessionManager(final MemcachedConfigurator memcachedConfigurator) {
        this.memcachedConfigurator = memcachedConfigurator;
        return this;
    }


    /**
     * An {@link com.bsb.common.vaadin.embed.EmbedVaadinServer} implementation that will configure tomcat to store session in memcached.
     */
    private static final class EmbedVaadinWithSessionManagement extends ApplicationBasedEmbedVaadinTomcat {

        private final MemcachedConfiguration memcachedConfiguration;

        /**
         * Creates a new instance.
         *
         * @param memcachedConfiguration the ocnfiguration to access memcached.
         *                               If null, memcached-session-manager is not used at all and session are only stored in memory.
         * @param config                 the config to use
         * @param applicationClass       the class of the application to handle
         */
        private EmbedVaadinWithSessionManagement(final EmbedVaadinConfig config,
                                                 final Class<? extends Application> applicationClass,
                                                 final MemcachedConfiguration memcachedConfiguration) {
            super(config, applicationClass);
            this.memcachedConfiguration = memcachedConfiguration;
        }

        @Override
        protected void configure() {
            super.configure();
            if (memcachedConfiguration != null) {
                final MemcachedBackupSessionManager manager = new MemcachedBackupSessionManager();
                manager.setMemcachedNodes(memcachedConfiguration.getUrl() + ":" + memcachedConfiguration.getPort());
                manager.setUsername(memcachedConfiguration.getUsername());
                manager.setPassword(memcachedConfiguration.getPassword());
                manager.setSticky(false);
                manager.setMemcachedProtocol("binary");
                manager.setRequestUriIgnorePattern(".*\\.(png|gif|jpg|css|js)$");
                getContext().setManager(manager);
            }
        }
    }

}
