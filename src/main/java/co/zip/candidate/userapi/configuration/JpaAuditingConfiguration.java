package co.zip.candidate.userapi.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Enable JPA auditing meant to facilitate implicit storage created and updated time for database changes
 * */
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfiguration {
}
