package org.example;
import io.restassured.response.Response;
import java.util.Map;
import static io.restassured.RestAssured.given;
public class Order {
    private final static String ordersPath = "/api/v1/orders";
    private final static String ordersTrackPath = "/api/v1/orders/track";
    public static Response order(CreateOrder createOrder) {
        Response response = given()
               .header("Content-type", "application/json")
               .body(createOrder)
               .when()
               .post(ordersPath);
       return response;
    }
    public static Response getOrderByTrackId(int trackId) {
        Response response = given()
               .header("Content-type", "application/json")
               .queryParam("t",trackId)
               .when()
               .get(ordersTrackPath);
        return response;
        }
    public static int parseOrderIdFromResponse(Response response) {
        int trackId = response.jsonPath().get("track");
        Response responseOrder = getOrderByTrackId(trackId);
        return responseOrder.jsonPath().get("order.id");
        }

    public static Response acceptOrder(int courierId, int orderId) {
            return given()
                   .header("Content-type", "application/json")
                   .queryParam("courierId", courierId)
                   .when()
                   .put(String.format(ordersPath + "/accept/%s",orderId));
            //return response;
        }
    public static Response listOrder(int courierId) {
            return given()
                   .header("Content-type", "application/json")
                   .queryParam("courierId", courierId)
                   .when()
                   .get(ordersPath);
           // return response;
        }
    public static Response listOrder(Map<String,String> queryParams) {
        return given()
                   .header("Content-type", "application/json")
                   .queryParams(queryParams)
                   .when()
                   .get(ordersPath);
           // return response;
        }

        public static Response deleteOrder(int orderId) {
            return given()
                   .header("Content-type", "application/json")
                   .when()
                   .put(String.format(ordersPath + "/finish/%s",orderId));
           // return response;
        }

        public static Response deleteOrder(CreateOrder createOrder) {
            return deleteOrder(createOrder);
        }
}