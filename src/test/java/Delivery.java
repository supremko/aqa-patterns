import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class Delivery {
    private SelenideElement form = $("form");
    private LocalDate localDate = LocalDate.now();
    private String formatDate = localDate.format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
    private String formatDatePlus3 = localDate.plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));
    private String formatDatePlus5 = localDate.plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.YYYY"));

    public void checkPositiveDate(Info.AuthInfo authInfo) {
        open(authInfo.getUrl());
        form.$("[placeholder='Город']").setValue(authInfo.getCity());
        form.$("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        form.$("[placeholder='Дата встречи']").setValue(formatDatePlus3);
        form.$("[name='name']").setValue(authInfo.getName());
        form.$("[name='phone']").setValue(authInfo.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$("[class='button__content']").click();
        $("[data-test-id='success-notification']").waitUntil(visible, 5000);
        form.$("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        form.$("[placeholder='Дата встречи']").setValue(formatDatePlus5);
        form.$("[class='button__content']").click();
        $(byText("Перепланировать")).click();
        $("[data-test-id='success-notification']").waitUntil(visible, 5000);
    }

    public void checkNegativeDate(Info.AuthInfo authInfo) {
        open(authInfo.getUrl());
        form.$("[placeholder='Город']").setValue(authInfo.getCity());
        form.$("[placeholder='Дата встречи']").sendKeys(Keys.chord(Keys.CONTROL, "a", Keys.DELETE));
        form.$("[placeholder='Дата встречи']").setValue(formatDate);
        form.$("[name='name']").setValue(authInfo.getName());
        form.$("[name='phone']").setValue(authInfo.getPhone());
        form.$("[data-test-id=agreement]").click();
        form.$("[class='button__content']").click();
        form.$(withText("Заказ на выбранную дату невозможен")).shouldBe(visible);
    }

}
