package com.algaworks.algashop.ordering.infrastructure.persistence;

import com.algaworks.algashop.ordering.infrastructure.config.hibernate.HibernateConfiguration;
import com.algaworks.algashop.ordering.infrastructure.config.auditing.SpringDataAuditingConfig;
import com.algaworks.algashop.ordering.utils.TestcontainerPostgreSQLConfig;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({TestcontainerPostgreSQLConfig.class, SpringDataAuditingConfig.class, HibernateConfiguration.class})
public abstract class AbstractPersistenceIT {

}