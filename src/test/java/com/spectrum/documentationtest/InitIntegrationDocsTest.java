package com.spectrum.documentationtest;

import com.spectrum.IntegerationRestAssuredAndRestDocsTest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.ObjectMapperConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.test.context.ActiveProfiles;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.restassured3.RestAssuredRestDocumentation.documentationConfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
@IntegerationRestAssuredAndRestDocsTest
@ActiveProfiles("test")
public class InitIntegrationDocsTest {

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
}
