package tn.esprit.crud.utils;

public class OTPUtil {
    private static int otp;

    public static int getOtp() {
        return otp;
    }

    public static void setOtp(int otp) {
        OTPUtil.otp = otp;
    }
}
