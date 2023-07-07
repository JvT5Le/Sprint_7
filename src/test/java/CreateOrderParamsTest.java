import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.example.CreateOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.example.Order.order;
import static org.hamcrest.Matchers.notNullValue;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.example.AppConfig.APP_URL;
import static org.example.AppConfig.CREATE_ORDER;

@RunWith(Parameterized.class)
public class CreateOrderParamsTest {

    private final CreateOrder orderRequestBody = CREATE_ORDER;
    public CreateOrderParamsTest(String[] orderRequestBody) {
        this.orderRequestBody.setColor(orderRequestBody);
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = APP_URL;
    }

    @Parameterized.Parameters
    public static Object[][] colorData() {
        return new Object[][] {
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{""}}
        };
    }

    @Test
    @DisplayName("Check bad request status 201")
    @Description("Status code should be 201 for successful order")
    public void createOrderTest() {
        Response response = order(orderRequestBody);
        response.then().assertThat().body("track", notNullValue())
                .and()
                .statusCode(SC_CREATED);
    }
}