package com.wallet.wallet;

import netscape.javascript.JSObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    @Autowired
    WalletRepository walletRepo;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    private static final String USER_CREATE = "user_create";
 //   private static final String TXN_CREATE = "txn_create";
    private static final String TXN_CREATE_TOPIC = "txn_create";

    private static final String WALLET_UPDATE = "wallet_update";

    int onboardingAmount = 50;

    @KafkaListener(topics = {USER_CREATE}, groupId = "grp")
    public void createWallet(String message) throws Exception {

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(message);

        if(!jsonObj.containsKey("userId")) {
            throw new Exception("User is not present in the user event");
        }

   //     int userId = (Integer) jsonObj.get("userId");
        int userId = ((Long) jsonObj.get("userId")).intValue();

        Wallet wallet = Wallet.builder()
                              .balance(onboardingAmount)
                              .userId(userId)
                              .build();

        walletRepo.save(wallet);

    }

    @KafkaListener(topics = TXN_CREATE_TOPIC, groupId = "grp")
    public void updateWallet(String message) throws Exception{

        JSONObject jsonObj = (JSONObject) new JSONParser().parse(message);

        if(!jsonObj.containsKey("senderId") || !jsonObj.containsKey("receiverId") || !jsonObj.containsKey("txnId")
           || !jsonObj.containsKey("amount")) {
            throw new Exception("Some of the details are not present in the txn event");
        }

        int receiverId = ((Long) jsonObj.get("receiverId")).intValue();
        int senderId = ((Long) jsonObj.get("senderId")).intValue();
        double amount = ((Number) jsonObj.get("amount")).doubleValue();
        String txnId = (String) jsonObj.get("txnId");

        JSONObject walletUpdateEvent = new JSONObject();
        walletUpdateEvent.put("txnId", txnId);
        Wallet wallet = walletRepo.findByUserId(senderId);

        if(wallet.getBalance() < amount) {

            walletUpdateEvent.put("status", "FAILED");
        } else {
            walletRepo.updateWallet(receiverId, amount);
            walletRepo.updateWallet(senderId, 0 - amount);
            walletUpdateEvent.put("status", "SUCCESSFUL");
        }

        kafkaTemplate.send(WALLET_UPDATE, walletUpdateEvent.toJSONString());

    }

}
