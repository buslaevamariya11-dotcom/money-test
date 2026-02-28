package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferBugTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Нельзя перевести сумму больше остатка")
    void shouldNotAllowTransferMoreThanBalance() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verifyCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verifyCode);

        val firstCard = DataHelper.getFirstCard();
        val secondCard = DataHelper.getSecondCard();

        String maskedFirst = "**** " +
                firstCard.getNumber().replace(" ", "")
                        .substring(firstCard.getNumber().replace(" ", "").length() - 4);

        String maskedSecond = "**** " +
                secondCard.getNumber().replace(" ", "")
                        .substring(secondCard.getNumber().replace(" ", "").length() - 4);

        int balance1 = dashboard.getCardBalance(maskedFirst);
        int balance2 = dashboard.getCardBalance(maskedSecond);

        int amount = balance1 + 1;

        val transferPage = dashboard.selectCard(secondCard);
        val dashboardAfter = transferPage.validTransfer(amount, firstCard.getNumber());

        assertEquals(balance1,
                dashboardAfter.getCardBalance(maskedFirst));

        assertEquals(balance2,
                dashboardAfter.getCardBalance(maskedSecond));
    }
}
