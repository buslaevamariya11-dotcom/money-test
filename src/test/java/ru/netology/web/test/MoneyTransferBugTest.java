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
    @DisplayName("БАГ — можно уйти в минус при переводе сверх баланса")
    void shouldNotAllowTransferMoreThanBalance() {
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verifyCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboard = verificationPage.validVerify(verifyCode);

        int balance1 = dashboard.getCardBalance("0001");
        int balance2 = dashboard.getCardBalance("0002");

        int amount = balance1 + 5000;

        val topUp = dashboard.selectCard(1);
        val dashboardAfter = topUp.validTransfer(amount, DataHelper.getFirstCard().getNumber());

        int newBalance1 = dashboardAfter.getCardBalance("0001");
        int newBalance2 = dashboardAfter.getCardBalance("0002");

        assertEquals(balance1, newBalance1, "БАГ! Баланс первой карты изменился, хотя перевод был сверх лимита!");
        assertEquals(balance2, newBalance2, "БАГ! Баланс второй карты изменился, хотя перевод был сверх лимита!");
    }
}
