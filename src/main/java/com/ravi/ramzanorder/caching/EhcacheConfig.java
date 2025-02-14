package com.ravi.ramzanorder.caching;

import com.ravi.ramzanorder.modal.Order;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.MemoryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.spi.CachingProvider;
import java.time.Duration;

@Configuration
@EnableCaching
public class EhcacheConfig {

    public static final String CACHE_STORE_NAME = "myCache";

    @Bean
    public CacheManager ehcacheManager() {
        org.ehcache.config.CacheConfiguration<Integer, Order> cacheConfig = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Integer.class,
                        Order.class,
                        ResourcePoolsBuilder.newResourcePoolsBuilder()
                                .offheap(2, MemoryUnit.MB)
                                .build())
                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofSeconds(10)))
                .build();

        CachingProvider cachingProvider = Caching.getCachingProvider();
        CacheManager cacheManager = cachingProvider.getCacheManager();

        javax.cache.configuration.Configuration<Integer, Order> configuration = Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig);
        cacheManager.createCache(CACHE_STORE_NAME, configuration);
        return cacheManager;
    }
}
