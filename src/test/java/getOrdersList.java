import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import io.qameta.allure.junit4.DisplayName;
import io.qameta.allure.Description;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class getOrdersList {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @DisplayName("Check status code of /api/v1/orders")
    @Description("Check status code is 200 for /api/v1/orders endpoint") // описание теста
    public void getOrdersCode() {
        given()
                .get("/api/v1/orders")
                .then().statusCode(200);
    }

    @Test
    @DisplayName("Check response body") // имя теста
    @Description("Check response body order list") // описание теста
    public void checkOrderList() {
        given()
                .get("/api/v1/orders")
                .then().assertThat().body("orders", notNullValue());
    }
}