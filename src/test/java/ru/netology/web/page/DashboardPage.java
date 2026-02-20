package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {

    private ElementsCollection cards =
            $$("ul li div[data-test-id]").filter(visible);

    public DashboardPage() {
        cards.shouldHave(sizeGreaterThanOrEqual(2), Duration.ofSeconds(5));
    }

    public int getCardBalance(String masked) {
        SelenideElement card = cards.find(text(masked)).shouldBe(visible);
        String full = card.getText();
        return extractBalance(full);
    }

    public ru.netology.web.page.TopUpPage selectCardToTopUp(String masked) {
        SelenideElement card = cards.find(text(masked)).shouldBe(visible);
        card.$("[data-test-id=action-deposit]").click();
        return new ru.netology.web.page.TopUpPage();
    }

    private int extractBalance(String text) {
        String balancePart = text.substring(text.indexOf("баланс:") + 7, text.indexOf("р.")).trim();
        return Integer.parseInt(balancePart);
    }
}