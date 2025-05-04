package com.wallet.notification;

import org.apache.kafka.common.utils.Java;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    SimpleMailMessage simpleMailMessage;

    @Autowired
    JavaMailSender javaMailSender;

    private static final String TXN_COMPLETE_TOPIC = "txn_complete";

    @KafkaListener(topics = {TXN_COMPLETE_TOPIC}, groupId = "grp")
    public void sendNotif(String msg) throws Exception {
        System.out.println("Send Notiifcation started");
        JSONObject jsonObject = (JSONObject) new JSONParser().parse(msg);
        String txnId = (String) jsonObject.get("txnId");
        String status = (String) jsonObject.get("status");
        String senderEmail = (String) jsonObject.get("sender");
        String receiverEmail = (String) jsonObject.get("receiver");
        Double amount = (Double) jsonObject.get("amount");

        simpleMailMessage.setText("Hi john your txn with id "+txnId+ " "+"got "+ status);
        simpleMailMessage.setTo(senderEmail);
        simpleMailMessage.setSubject("Payment Notification");
        simpleMailMessage.setFrom("rsdd04@gmail.com");

        javaMailSender.send(simpleMailMessage);

        if("SUCCESSFUL".equals(status)) {
            simpleMailMessage.setText("Hi you got amount "+ amount + " from user "+ senderEmail + " in your e-wallet");
            simpleMailMessage.setTo(receiverEmail);
            javaMailSender.send(simpleMailMessage);
        }


    }

}
