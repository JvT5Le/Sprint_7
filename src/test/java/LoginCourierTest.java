import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.Login;
import static org.example.AppConfig.*;
import static org.example.Courier.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class LoginCourierTest {
    @Before
    public void setUp() {
        RestAssured.baseURI = APP_URL;
    }

    @Test
    @DisplayName("Check status code 200")
    @Description("Courier login response status code should be 200 Ok")
    public void courierLoginStatusCodeTest() {
        create(CREATE_COURIER);
        login(LOGIN_COURIER).then()
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
    }

    @Test
    @DisplayName("Check body message \"Учетная запись не найдена\"")
    @Description("Non-existent courier login response body message should contain text \"Учетная запись не найдена\"")
    public void nonExistentLoginBodyMessageTest() {
        Login invalidLoginName = new Login(faker.twinPeaks().character(), PASSWORD);
        Response response = login(invalidLoginName);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Check body message \"Учетная запись не найдена\"")
    @Description("Invalid courier login response body message should contain text \"Учетная запись не найдена\"")
    public void invalidPasswordBodyMessageTest() {
        Login invalidPasswordLogin = new Login(LOGIN, faker.internet().password());
        Response response = login(invalidPasswordLogin);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @After
    public void deleteChanges() {
       delete(LOGIN_COURIER);
    }
}