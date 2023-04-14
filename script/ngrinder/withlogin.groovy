import groovy.json.JsonSlurper

import net.grinder.script.GTest
import net.grinder.script.Grinder
import static org.junit.Assert.*
import static org.hamcrest.Matchers.*
import static net.grinder.script.Grinder.grinder
import net.grinder.scriptengine.groovy.junit.GrinderRunner
import net.grinder.scriptengine.groovy.junit.annotation.BeforeProcess
import net.grinder.scriptengine.groovy.junit.annotation.BeforeThread
import net.grinder.plugin.http.HTTPPluginControl
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import groovy.json.JsonBuilder
import org.junit.BeforeClass

import org.ngrinder.http.HTTPRequest
import org.ngrinder.http.HTTPRequestControl
import org.ngrinder.http.HTTPResponse
import org.ngrinder.http.cookie.CookieManager

@RunWith(GrinderRunner)
class TestRunner {

    def toJSON = { new JsonSlurper().parseText(it) }

    public static GTest test
    public static HTTPRequest request
    public static Map<String, String> headers = [:]
    public static Map<String, Object> params = [:]

    // login
    public static HTTPRequest loginRequest
    public static String accessToken

    @BeforeProcess
    public static void beforeProcess() {
        grinder.logger.info("before process.")
        HTTPPluginControl.getConnectionDefaults().timeout = 6000
        test = new GTest(1, "127.0.0.1")
        request = new HTTPRequest()

        // login
        loginRequest = new HTTPRequest()
    }

    @BeforeThread
    public void beforeThread() {
        // Set Login
        // userId 값을 원하는 값으로 설정하세요.
        Long userId = 1L;
        HTTPResponse loginResponse = loginRequest.GET("http://127.0.0.1:8080/login/" + userId);
        grinder.logger.info("Login response body: " + loginResponse.getBodyText())
        accessToken = loginResponse.getBody(toJSON).accessToken

        test.record(this, "test")
        grinder.statistics.delayReports = true
        grinder.logger.info("before thread.")
    }

    @Before
    public void before() {
        headers.put("Authorization", "Bearer " + accessToken)
        headers.put("Content-Type", "application/json")
        request.setHeaders(headers)
        grinder.logger.info("before. init headers and cookies")
    }

    @Test
    public void test() {
        def jsonBody = new JsonBuilder(
                [
                        title: 'title',
                        content: 'content'
                ]
        ).toString()

        byte[] jsonData = jsonBody.getBytes("UTF-8")
        HTTPResponse response = request.POST("http://127.0.0.1:8080/api/posts", jsonData)

        if (response.statusCode == 301 || response.statusCode == 302) {
            grinder.logger.warn("Warning. The response may not be correct. The response code was {}.", response.statusCode)
        } else {
            assertThat(response.statusCode, is(201))
        }
    }

    @Test
    public void testCreateComment() {
        def jsonBody = new JsonBuilder(
                [
                        content: 'comment'
                ]
        ).toString()

        byte[] jsonData = jsonBody.getBytes("UTF-8")
        HTTPResponse response = request.POST("http://127.0.0.1:8080/api/posts/1/comments", jsonData)

        assertThat(response.statusCode, is(201))
    }

    @Test
    public void testCreateReply() {
        def jsonBody = new JsonBuilder(
                [
                        content: 're-comment'
                ]
        ).toString()

        byte[] jsonData = jsonBody.getBytes("UTF-8")
        HTTPResponse response = request.POST("http://127.0.0.1:8080/api/comments/1/replies", jsonData)

        assertThat(response.statusCode, is(201))
    }
}
