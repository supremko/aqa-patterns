import com.codeborne.selenide.*;
import com.github.javafaker.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class Delivery {
    private LocalDate localDate = LocalDate.now();
    private String formatNow = localDate.format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
    private String formatPlusDays = localDate.plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
    private String formatPlusDays2 = localDate.plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
    private SelenideElement form = $("form");
    private String url = "http://localhost:9999";
    private Faker faker;
    private String name;
    private String phone;


    @BeforeEach
    void setUpAll() {
        faker = new Faker(new Locale("ru"));
        name = faker.name().fullName();
        phone = faker.phoneNumber().phoneNumber();
    }


    @Test
    void checkPositiveDate() {
        open(url);
        form.$("[placeholder='Город']").setValue("Санкт-Петербург");
        form.$("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        form.$("[placeholder='Дата встречи']").setValue(formatPlusDays);
        form.$("[name='name']").setValue(name);
        form.$("[name='phone']").setValue(phone);
        form.$("[data-test-id=agreement]").click();
        form.$("[class='button__content']").click();
        $("[data-test-id='success-notification']").waitUntil(visible, 5000);
        form.$("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        form.$("[placeholder='Дата встречи']").setValue(formatPlusDays2);
        form.$("[class='button__content']").click();
        $(byText("Перепланировать")).click();
        $("[data-test-id='success-notification']").waitUntil(visible, 5000);
    }

    @Test
    void checkNegativeDate() {
        open(url);
        form.$("[placeholder='Город']").setValue("Санкт-Петербург");
        form.$("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        form.$("[placeholder='Дата встречи']").setValue(formatNow);
        form.$("[name='name']").setValue(name);
        form.$("[name='phone']").setValue(phone);
        form.$("[data-test-id=agreement]").click();
        form.$("[class='button__content']").click();
        form.$(withText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

}
