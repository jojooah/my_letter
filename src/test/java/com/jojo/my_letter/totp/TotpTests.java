package com.jojo.my_letter.totp;

import com.jojo.my_letter.utils.OTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class TotpTests {

    @Test
    public void QR이미지생성하기() throws Exception {

        final String randomKey = OTPUtil.getSecretKey();
        log.info("randomKey = {}", randomKey);  // VWXQZVYKKPYHTETKKUAKUAO6GAEZUVL6

        final String otpUrl = OTPUtil.getGoogleOTPAuthURL(randomKey, "조주아", "자바개발");
        OTPUtil.getQRImage(otpUrl, "src/main/resources/static/qr.png", 300, 300);
    }

    @Test
    public void OTP검증하기() throws Exception {
        final String totpCode = OTPUtil.getTOTPCode("VWXQZVYKKPYHTETKKUAKUAO6GAEZUVL6");
        System.out.println("TOTP Code : " + totpCode);
    }
}