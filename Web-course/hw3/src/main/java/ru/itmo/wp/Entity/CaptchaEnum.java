package ru.itmo.wp.Entity;

public enum CaptchaEnum {

    PASSED ("passed"),
    CORRECT_CAPTCHA ("correctCaptcha");

    private final String title;
    CaptchaEnum(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return title;
    }
}
