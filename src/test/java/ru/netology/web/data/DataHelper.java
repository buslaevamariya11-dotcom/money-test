package ru.netology.web.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo info) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        String number;

        public String getId() {
            return number.substring(number.length() - 4);
        }

        public String getNumberWithoutSpaces() {
            return number.replaceAll("\\s+", "");
        }
    }

    public static CardInfo getFirstCard() {
        return new CardInfo("5559 0000 0000 0001");
    }

    public static CardInfo getSecondCard() {
        return new CardInfo("5559 0000 0000 0002");
    }
}
