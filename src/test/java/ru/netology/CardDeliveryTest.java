package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
//    LocalDate deliveryDate = LocalDate.now().plusDays(4);
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//    String formattedDeliveryDate = deliveryDate.format(formatter);


    @Test
    void shouldPlaceCardOrder(){
        Configuration.holdBrowserOpen=true;
        String planningDate = generateDate(4,"dd.MM.yyyy");
        open("http://localhost:7777");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "A" + Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue("Казимир Алмазов");
        $("[data-test-id=phone] input").setValue("+79876543211");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $(".notification__content")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(15))
                .shouldBe(Condition.visible);
//        $x("//div[contains(text(), 'Встреча успешно забронирована на ')]").should(Condition.appear, Duration.ofSeconds(15));

    }
}
