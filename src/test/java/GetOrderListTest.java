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
import static org.example.Courier.*;
import static org.example.Order.*;
import static org.hamcrest.Matchers.*;
import static org.apache.http.HttpStatus.SC_OK;

public class GetOrderListTest {
    Login loginCourier = new Login("Wider","202220");
    CreateOrder createOrder = new CreateOrder("Shikamaru","Nara","Konoha, 123 apt.",
                                            4,"+7-800-355-35-35", 5,
                                            "2023-06-30","Sasuke, come back to Konoha",
                                                       new String[]{"BLACK"}
                                             );
    CreateUser createCourier = new CreateUser("Wider","202220","Dart");

    @Before
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        create(createCourier);
        order(createOrder);
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
        int loginId = getCourierIdByLogin(loginCourier);
        Response response = listOrder(loginId);
        response.then()
                .assertThat()
                .statusCode(SC_OK);
    }

    @After
    public void deleteChanges() {
        delete(loginCourier);

    }
}
