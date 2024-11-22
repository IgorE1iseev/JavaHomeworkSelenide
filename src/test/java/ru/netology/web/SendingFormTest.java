package ru.netology.web;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SendingFormTest {

    public String createDate(int additionalDays, String format) {
        return LocalDate.now().plusDays(additionalDays).format(DateTimeFormatter.ofPattern(format));
    }

    @Test
    public void shouldReserve() {
        open("http://localhost:9999/");
        $("[data-test-id='city'] input").setValue("Пермь");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        String validDate = createDate(5, "dd.MM.yyyy");
        $("[data-test-id='date'] input").setValue(validDate);
        $("[data-test-id='name'] input").setValue("Петров-Антонов Николай");
        $("[data-test-id='phone'] input").setValue("+79990000000");
        $("[data-test-id='agreement']").click();
        $("button.button").click();
        $("div.notification__content").shouldBe(Condition.visible, Duration.ofSeconds(20)).shouldHave(Condition.text("Встреча успешно забронирована на " + validDate));

    }
}

