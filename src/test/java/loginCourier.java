import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.example.CreateUser;
import org.example.Login;
import static org.example.Courier.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;

public class LoginCourier {
    final String courierLogin = "Shin0101";
    final String password = "00aabb00";
    final String firstName = "Yurik";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check status code 200")
    @Description("Courier login response status code should be 200 Ok")
    public void courierLoginStatusCodeTest() {
        CreateUser createUser = new CreateUser(courierLogin, password, firstName);
        create(createUser);
        Login loginCourier = new Login(courierLogin, password);
        login(loginCourier).then()
                .assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);
        delete(loginCourier);
    }

    @Test
    @DisplayName("Check body message \"Учетная запись не найдена\"")
    @Description("Non-existent courier login response body message should contain text \"Учетная запись не найдена\"")
    public void nonExistentLoginBodyMessageTest() {
        Login loginCourier = new Login(courierLogin + "nonexistent", password);
        Response response = login(loginCourier);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Check body message \"Учетная запись не найдена\"")
    @Description("Invalid courier login response body message should contain text \"Учетная запись не найдена\"")
    public void invalidPasswordBodyMessageTest() {
        Login loginCourier = new Login(courierLogin, password + "invalid");
        Response response = login(loginCourier);
        response.then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }
}