package eu.hurion.hello.vaadin.server;

/**
 * Builder to define configuration to access to memcached.
 *
 * @author Nicolas Hurion
 */
public class MemcachedConfigurator {
    public static final int DEFAULT_MEMCACHEPORT = 11211;
    public static final String DEFAULT_URL = "127.0.0.1";

    private String username;
    private String password;
    private String url = DEFAULT_URL;
    private int port = DEFAULT_MEMCACHEPORT;

    private MemcachedConfigurator() {
    }

    public static MemcachedConfigurator memcachedConfig() {
        return new MemcachedConfigurator();
    }

    /**
     * Optional. Set the username to use to connect to memcached.
     */
    public MemcachedConfigurator username(final String username) {
        this.username = username;
        return this;
    }

    /**
     * Optional. Set the password to use to connect to memcached.
     */
    public MemcachedConfigurator password(final String password) {
        this.password = password;
        return this;
    }

    /**
     * Url to access memcached. Default to {@value #DEFAULT_URL} if not specified.
     */
    public MemcachedConfigurator url(final String memcachedUrl) {
        this.url = memcachedUrl;
        return this;
    }

    /**
     * Port to use to access memcahce. Default to {@value #DEFAULT_MEMCACHEPORT} if not specified
     */
    public MemcachedConfigurator port(final int port) {
        this.port = port;
        return this;
    }

    public MemcachedConfiguration build() {
        return new MemcachedConfiguration(username, password, url, port);
    }

    /**
     * Regroup configuration information to connect to memcached.
     * Immutable. Use {@link MemcachedConfigurator} to create an instance.
     *
     * @author Nicolas Hurion
     */
    final class MemcachedConfiguration {
        private final String username;
        private final String password;
        private final String url;
        private final int port;

        /**
         * @param username the username to connect to memcached.
         * @param password the password to connect ot memcached.
         * @param url      the url where memcached is. Without the port
         * @param port     the port on which memcached is listening.
         */
        private MemcachedConfiguration(final String username, final String password, final String url, final int port) {
            this.username = username;
            this.password = password;
            this.url = url;
            this.port = port;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public String getUrl() {
            return url;
        }

        public int getPort() {
            return port;
        }
    }

    /**
     * Configuration based on system properties set by the memcacheAddOn
     */
    public static MemcachedConfigurator memcacheAddOn() {
        return memcachedConfig()
                .username(System.getenv("MEMCACHE_USERNAME"))
                .password(System.getenv("MEMCACHE_PASSWORD"))
                .url(System.getenv("MEMCACHE_SERVERS"));
    }

    /**
     * Configuration based on system properties set by the memcachierAddOn
     */
    public static MemcachedConfigurator memcachierAddOn() {
        return memcachedConfig()
                .username(System.getenv("MEMCACHIER_USERNAME"))
                .password(System.getenv("MEMCACHIER_PASSWORD"))
                .url(System.getenv("MEMCACHIER_SERVERS"));
    }



}
