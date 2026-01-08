package com.codejago.jasyptspringboot.configuration;

import com.codejago.jasyptspringboot.EncryptablePropertySourceConverter;
import com.codejago.jasyptspringboot.caching.RefreshScopeRefreshedEventListener;
import com.codejago.jasyptspringboot.properties.JasyptEncryptorConfigurationProperties;
import com.codejago.jasyptspringboot.util.Singleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * <p>CachingConfiguration class.</p>
 *
 * @author Sergio.U.Bocchio
 * @version $Id: $Id
 */
@Configuration
public class CachingConfiguration {
    /**
     * <p>refreshScopeRefreshedEventListener.</p>
     *
     * @param environment a {@link org.springframework.core.env.ConfigurableEnvironment} object
     * @param converter a {@link com.codejago.jasyptspringboot.EncryptablePropertySourceConverter} object
     * @param config a {@link com.codejago.jasyptspringboot.util.Singleton} object
     * @return a {@link com.codejago.jasyptspringboot.caching.RefreshScopeRefreshedEventListener} object
     */
    @Bean
    public RefreshScopeRefreshedEventListener refreshScopeRefreshedEventListener(ConfigurableEnvironment environment, EncryptablePropertySourceConverter converter, Singleton<JasyptEncryptorConfigurationProperties> config) {
        return new RefreshScopeRefreshedEventListener(environment, converter, config.get());
    }
}
