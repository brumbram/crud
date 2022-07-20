package co.zip.candidate.userapi.util;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailUtil {

    public static boolean isEmailValid(String email){
        try {
            // Create InternetAddress object and validated the supplied email address.
            InternetAddress internetAddress = new InternetAddress(email, true); // strict
            internetAddress.validate();
            return true;
        } catch (AddressException e) {
            System.out.println("Bad eMail address: " + email);
        }
        return false;

    }
}
