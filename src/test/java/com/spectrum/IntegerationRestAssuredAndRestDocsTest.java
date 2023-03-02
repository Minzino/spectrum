package com.spectrum;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.RestDocumentationExtension;

@Target(ElementType.TYPE) // 어노테이션이 적용될 대상이 클래스임을 지정
@Retention(RetentionPolicy.RUNTIME) // 어노테이션 정보가 런타임에도 유지되도록 지정
@Documented // 어노테이션 정보가 javadoc으로 생성될 수 있도록 지정
@IntegerationTest // 커스텀 어노테이션으로, 통합 테스트임을 지정합니다.
@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(RestDocumentationExtension.class)
public @interface IntegerationRestAssuredAndRestDocsTest {

}
