import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.CreateUser;
import org.example.Login;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.example.Courier.*;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.example.AppConfig.APP_URL;
import static org.example.AppConfig.LOGIN;
import static org.example.AppConfig.PASSWORD;
import static org.example.AppConfig.FIRST_NAME;

@RunWith(Parameterized.class)
public class LoginCourierParamsTest {

    private final Login credentials;
    public LoginCourierParamsTest(Login credentials) {
        this.credentials = credentials;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = APP_URL;
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][] {
                {new Login("",PASSWORD)},
                {new Login(LOGIN,"")}
        };
    }
    @Test
    @DisplayName("Check body message: \"Недостаточно данных для входа\"")
    @Description("Try to login without Login or Password params")
    public void notEnoughLoginRequestParams() {
        CreateUser createUser = new CreateUser(LOGIN,PASSWORD,FIRST_NAME);
        create(createUser);
        Response response = login(credentials);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }
}