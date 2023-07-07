package org.example;
import com.github.javafaker.Faker;

public class AppConfig {
    public final static String APP_URL = "http://qa-scooter.praktikum-services.ru/";
    public static Faker faker = new Faker();
    public final static String LOGIN = "YuriyYu";
    public final static String PASSWORD = "password0";
    public final static String FIRST_NAME = "Yuriy";
    public final static String CUSTOMER_FIRST_NAME = "Shikamaru";
    public final static String CUSTOMER_LAST_NAME = "Nara";
    public final static String ADDRESS = "Konoha, 123 apt.";
    public final static int METRO_STATION = 4;
    public final static String PHONE_NUMBER = "+7-800-355-35-35";
    public final static int RENT_TIME = 5;
    public final static String DELIVERY_DATE = "2023-07-12";
    public final static String TEST_COMMENT = "Asuma, come back to Konoha";
    public final static String BASIC_COLOR = "BLACK";
    public final static Login LOGIN_COURIER = new Login (LOGIN,PASSWORD);
    public final static CreateUser CREATE_COURIER = new CreateUser (LOGIN,PASSWORD,FIRST_NAME);
    public final static CreateOrder CREATE_ORDER = new CreateOrder(
            CUSTOMER_FIRST_NAME,
            CUSTOMER_LAST_NAME,
            ADDRESS,
            METRO_STATION,
            PHONE_NUMBER, RENT_TIME,
            DELIVERY_DATE,TEST_COMMENT,
            new String[]{BASIC_COLOR}
    );

  /*    String FIRST_NAME = faker.name().firstName(); // Emory
    String lastName = faker.name().lastName(); // Barton

    String streetAddress = faker.address().streetAddress(); // 60018 Sawayn Brooks Suite 449
*/
}
