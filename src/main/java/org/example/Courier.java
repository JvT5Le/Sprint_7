package org.example;
import io.restassured.response.Response;
import io.restassured.response.ResponseBodyExtractionOptions;
import static io.restassured.RestAssured.given;

public class Courier {

        private final static String courierPath = "/api/v1/courier";
        private final static String courierPathLogin = "/api/v1/courier/login";
        public static Response create(CreateUser createUser) {
            Response response =  given()
                    .header("Content-type", "application/json")
                    .body(createUser)
                    .when()
                    .post(courierPath);
            return response;
        }
        public static Response login(Login courierLogin) {
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierLogin)
                .when()
                .post(courierPathLogin);
        return response;
        }
        public static int getCourierIdByLogin(Login courierLogin) {

                Response response = login(courierLogin);
                return response.jsonPath().get("id");

        }
        private static Response delete(int courierId) {
            return given()
                   .header("Content-type", "application/json")
                   .when()
                   .delete(String.format(courierPath + "%s", courierId));
           // return response;
        }
        public static Response delete(Login courierLogin) {
            return delete(getCourierIdByLogin(courierLogin));
        }
}