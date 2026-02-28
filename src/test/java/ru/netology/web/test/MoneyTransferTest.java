package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoney() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val code = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(code);

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

        int amount = balance1 / 2;

        val transferPage = dashboard.selectCard(secondCard);
        val dashboardAfter = transferPage.validTransfer(amount, firstCard.getNumber());

        assertEquals(balance1 - amount,
                dashboardAfter.getCardBalance(maskedFirst));

        assertEquals(balance2 + amount,
                dashboardAfter.getCardBalance(maskedSecond));
    }
}