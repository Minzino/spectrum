package com.spectrum.documentationtest;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.specification.RequestSpecification;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
@ActiveProfiles("test")
public class InitIntegrationDocsTest implements InitializingBean {

    private List<String> tableNames = new ArrayList<>();
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    protected ObjectMapper objectMapper;

    @LocalServerPort
    protected int port;

    protected RequestSpecification spec;

    {
        setUpRestAssured();
    }

    @BeforeEach
    void setUpRestDocs(RestDocumentationContextProvider restDocumentation) {
        RestAssured.port = port;

        this.spec = new RequestSpecBuilder()
            .setPort(port)
            .addFilter(documentationConfiguration(restDocumentation)
                .operationPreprocessors()
                .withRequestDefaults(prettyPrint())
                .withResponseDefaults(prettyPrint()))
            .build();
    }

    private void setUpRestAssured() {
        RestAssured.config = RestAssuredConfig.config()
            .objectMapperConfig(
                new ObjectMapperConfig(
                    new Jackson2Mapper((type, charset) -> objectMapper)
                )
            );
    }

    private void cleanUpDatabase(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = " + "0");
            for (String tableName : tableNames) {
                statement.executeUpdate("TRUNCATE TABLE " + tableName);
            }
            statement.executeUpdate("SET FOREIGN_KEY_CHECKS = " + "1");
        }
    }

    public void clear() {
        entityManager.unwrap(Session.class).doWork(this::cleanUpDatabase);
    }

    @AfterEach
    void teardown() {
        clear();
    }

    @Override
    public void afterPropertiesSet() {
        entityManager.unwrap(Session.class).doWork(this::extractTableNames);
    }

    private void extractTableNames(Connection connection) throws SQLException {
        List<String> tableNames = new ArrayList<>();
        ResultSet tables = connection.getMetaData()
            .getTables(connection.getCatalog(), null, null, new String[]{"TABLE"});

        try (tables) {
            while (tables.next()) {
                tableNames.add(tables.getString("table_name"));
            }
            this.tableNames = tableNames;
        }
    }
}
