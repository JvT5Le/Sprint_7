import org.example.CreateUser;
import org.example.Login;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import static org.example.Courier.*;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.apache.http.HttpStatus.SC_OK;

public class CreateCourier{

    final String courierLogin = "Shin0101";
    final String password = "00aabb00";
    final String firstName = "Yurik";

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check successful status code")
    @Description("Status code should be 200 for current courier or 201 for new courier")
    public void createCourierTest() {
        CreateUser createUser = new CreateUser(courierLogin,password,firstName);
        Login login = new Login(courierLogin,password);
        if (login(login).getStatusCode() == SC_OK) {
            delete(login);
        } else {
            Response response = create(createUser);
            response.then().assertThat().body("ok", equalTo(true))
                    .and()
                    .statusCode(SC_CREATED);
        }
    }

    @Test
    @DisplayName("Check status code 409")
    @Description("Create courier with same Login name should be 409 Ok")
    public void sameLoginTest() {
        CreateUser createUser = new CreateUser(courierLogin,password,firstName);
        create(createUser);
        Response response = create(createUser);
        response.then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);
    }

    @After
    public void deleteTestData() {
        Login login = new Login(courierLogin,password);
        delete(login);
    }
}