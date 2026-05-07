package com.threektechone.resorthub.config.redis;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisCacheConfig {

        @Bean
        public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
                RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                                .entryTtl(Duration.ofMinutes(10))
                                .disableCachingNullValues()
                                .serializeValuesWith(
                                                RedisSerializationContext.SerializationPair
                                                                .fromSerializer(RedisSerializer.json()))
                                .prefixCacheNameWith("cache:");

                Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
                cacheConfigurations.put(
                                "user-details-by-email",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(5)));

                cacheConfigurations.put(
                                "public-resort-detail",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(15)));

                cacheConfigurations.put(
                                "staff-dashboard",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(2)));

                cacheConfigurations.put(
                                "register-resort-detail",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(5)));

                cacheConfigurations.put(
                                "edit-request-detail",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(5)));

                cacheConfigurations.put(
                                "owner-dashboard-overview",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(2)));

                cacheConfigurations.put(
                                "owner-revenue-chart",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(5)));

                cacheConfigurations.put(
                                "owner-booking-chart",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(5)));

                cacheConfigurations.put(
                                "owner-booking-detail",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(1)));

                cacheConfigurations.put(
                                "customer-booking-detail",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(1)));

                cacheConfigurations.put(
                                "admin-user-dashboard",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(2)));

                cacheConfigurations.put(
                                "owner-resort-detail",
                                defaultCacheConfig.entryTtl(Duration.ofMinutes(10)));

                return RedisCacheManager.builder(connectionFactory)
                                .cacheDefaults(defaultCacheConfig)
                                .withInitialCacheConfigurations(cacheConfigurations)
                                .transactionAware()
                                .build();
        }
}
