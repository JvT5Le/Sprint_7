import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.example.CreateUser;
import org.example.CreateOrder;
import org.example.Login;
import static io.restassured.RestAssured.given;
import static org.example.AppConfig.*;
import static org.example.Courier.*;
import static org.example.Order.*;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.SC_OK;

public class GetOrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = APP_URL;
        create(CREATE_COURIER);
        order(CREATE_ORDER);
    };

    @Test
    @DisplayName("Check status code 200 Ok")
    @Description("Order list should contain orders and status code should be 200 Ok")
    public void getAllOrdersTest() {
        Response response = given().get("/api/v1/orders");
        response.then()
                .assertThat()
                .statusCode(SC_OK)
                .and()
                .body("orders", hasSize(greaterThan(0)));
    }

    @Test
    @DisplayName("Check status code 200 for Courier login ID")
    @Description("Courier login Orders response status code should be 200 Ok")
    public void getOrdersByCourierLoginIdTest() {
        int loginId = getCourierIdByLogin(LOGIN_COURIER);
        Response response = listOrder(loginId);
        response.then()
                .assertThat()
                .statusCode(SC_OK)
                .body("orders", notNullValue());
    }

    @After
    public void deleteChanges() {
        delete(LOGIN_COURIER);

    }
}
