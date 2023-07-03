import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.CreateUser;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.example.Courier.*;
import static org.hamcrest.Matchers.equalTo;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;

@RunWith(Parameterized.class)
public class CreateCourierParams {
    private final CreateUser credential;
    public CreateCourierParams(CreateUser credential) {
        this.credential = credential;
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Parameterized.Parameters
    public static Object[][] credentialsVariable() {
        return new Object[][] {
                {new CreateUser("","00aabb00","Yurik")},
                {new CreateUser("Shin0101","","Yurik")}
        };
    }

    @Test
    @DisplayName("Check bad request status 400")
    @Description("Status code should be 400 for courier without necessary params")
    public void badRequestCreateCourierTest() {
        Response response = create(credential);
        response.then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }
}