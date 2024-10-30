//package uz.jvh.uzairways.service;
//
//import com.twilio.Twilio;
//import com.twilio.rest.lookups.v1.PhoneNumber;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.mail.Message;
//
//
//@Service
//public class SmsService {
//
//    @Value("${twilio.accountSid}")
//    private String accountSid;
//
//    @Value("${twilio.authToken}")
//    private String authToken;
//
//    @Value("${twilio.phoneNumber}")
//    private String fromPhoneNumber;
//
//    public SmsService() {
//        Twilio.init(accountSid, authToken);
//    }
//
//    public void sendSms(String toPhoneNumber, String messageText) {
//        Message message = Message.creator(
//                        new PhoneNumber(toPhoneNumber),
//                        new PhoneNumber(fromPhoneNumber),
//                        messageText)
//                .create();
//        System.out.println("SMS sent successfully with SID");
//    }
//}
