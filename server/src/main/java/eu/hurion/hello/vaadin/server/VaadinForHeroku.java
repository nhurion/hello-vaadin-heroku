package eu.hurion.hello.vaadin.server;

import com.bsb.common.vaadin.embed.EmbedVaadinConfig;
import com.bsb.common.vaadin.embed.EmbedVaadinServer;
import com.bsb.common.vaadin.embed.EmbedVaadinServerBuilder;
import com.bsb.common.vaadin.embed.application.ApplicationBasedEmbedVaadinTomcat;
import com.vaadin.Application;
import de.javakaffee.web.msm.MemcachedBackupSessionManager;
import eu.hurion.hello.vaadin.server.MemcachedConfigurator.MemcachedConfiguration;

import java.util.Properties;

/**
 * Customized {@code EmbedVaadinApplication} to accommodate specialities of Heroku.
 *
 * @author Nicolas Hurion
 */
public class VaadinForHeroku extends EmbedVaadinServerBuilder<VaadinForHeroku, EmbedVaadinServer> {

    public static final int DEFAULT_PORT = 8080;
    public static final String PORT = "PORT";

    private final Class<? extends Application> applicationClass;
    private EmbedVaadinConfig config;

    private MemcachedConfigurator memcachedConfigurator;

    /**
     * Creates a new instance for the specified application.
     *
     * @param applicationClass the class of the application to deploy
     */
    public VaadinForHeroku(final Class<? extends Application> applicationClass) {
        super();
        assertNotNull(applicationClass, "applicationClass could not be null.");
        this.applicationClass = applicationClass;
        withConfigProperties(EmbedVaadinConfig.loadProperties());
    }

    /**
     * Returns the {@link Application} type that was used to initialize this instance, if any.
     *
     * @return the application class or <tt>null</tt> if a component was set
     */
    protected Class<? extends Application> getApplicationClass() {
        return applicationClass;
    }

    @Override
    protected VaadinForHeroku self() {
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

    @Override
    public final VaadinForHeroku withConfigProperties(final Properties properties) {
        this.config = new EmbedVaadinConfig(properties);
        return self();
    }


    @Override
    protected EmbedVaadinConfig getConfig() {
        return config;
    }

    public static VaadinForHeroku forApplication(final Class<? extends Application> applicationClass) {
        return new VaadinForHeroku(applicationClass);
    }

    public VaadinForHeroku withMemcachedSessionManager(final MemcachedConfigurator memcachedConfigurator) {
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

    /**
     * Server configured for local development for the given Application
     *
     * @param applicationClass the class of the application to deploy
     */
    public static VaadinForHeroku devServer(final Class<? extends Application> applicationClass) {

        return VaadinForHeroku.forApplication(applicationClass).withHttpPort(DEFAULT_PORT)
                .withProductionMode(false)
                .openBrowser(true);
    }

    /**
     * Server configured for Heroku servers for the given Application
     *
     * @param applicationClass the class of the application to deploy
     */
    public static VaadinForHeroku prodServer(final Class<? extends Application> applicationClass) {

        return VaadinForHeroku.forApplication(applicationClass)
                .withMemcachedSessionManager(MemcachedConfigurator.memcacheAddOn())
                .withHttpPort(Integer.parseInt(System.getenv(PORT)))
                .withProductionMode(true)
                .openBrowser(false);
    }


}
