package org.quanta.mongodb.config;

import lombok.RequiredArgsConstructor;
import org.quanta.mongodb.utils.MongodbUtils;
import org.quanta.mongodb.utils.impl.MongodbUtilsImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Description:
 * Param:
 * return:
 * Author: wzf
 * Date: 2024/1/28
 */
@Configuration
@RequiredArgsConstructor
public class MongodbConfig {
    private final MongoTemplate mongoTemplate;

    @Bean
    public MongodbUtils mongodbUtils() {
        return new MongodbUtilsImpl(mongoTemplate);
    }
}
