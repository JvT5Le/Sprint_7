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

@RunWith(Parameterized.class)
public class LoginCourierParams {

    private final Login credentials; // pathToLogin - путь к JSON с учётными данными для логина курьера
    public LoginCourierParams(Login credentials) {
        this.credentials = credentials;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters
    public static Object[][] getCredentials() {
        return new Object[][] {
                {new Login("","tau_in")},
                {new Login("DarkSide","")}
        };
    }
    @Test
    @DisplayName("Check body message: \"Недостаточно данных для входа\"")
    @Description("Try to login without Login or Password params")
    public void notEnoughLoginRequestParams() {
        CreateUser createUser = new CreateUser("DarkSide","tau_in","Wider");
        create(createUser);
        Response response = login(credentials);
        // Проверить наличие ошибки при отправлении неверных учётных данных (отсутствие логина или пароля)
        response.then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }
}