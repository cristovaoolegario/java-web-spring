package com.cristovaoolegario.healthcare.healthcareapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaAdmin.NewTopics;

@Configuration
public class KafkaConfig {

  @Bean
  public KafkaAdmin.NewTopics topics() {
    return new NewTopics(
        TopicBuilder.name("doctor").build());
  }

}
