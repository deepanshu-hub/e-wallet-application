package com.wallet.transaction;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class TransactionService {

    private static final String TXN_CREATE_TOPIC = "txn_create";
    private static final String TXN_COMPLETE_TOPIC = "txn_complete";
    private static final String WALLET_UPDATE_TOPIC = "wallet_update";


    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    RestTemplate restTemplate;

    public String createTxn(Transaction transaction){
        transaction.setTxnId(UUID.randomUUID().toString());
        transaction.setTransactionStatus(TransactionStatus.PENDING);

        transactionRepository.save(transaction);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("senderId", transaction.getSenderUserId());
        jsonObject.put("receiverId", transaction.getReceiverUserId());
        jsonObject.put("amount", transaction.getAmount());
        jsonObject.put("txnId", transaction.getTxnId());

        kafkaTemplate.send(TXN_CREATE_TOPIC, jsonObject.toJSONString());

        return transaction.getTxnId();
    }

    @KafkaListener(topics = {WALLET_UPDATE_TOPIC}, groupId = "grp")
    public void updateTxn(String msg) throws Exception{

        JSONObject jsonObject = (JSONObject) new JSONParser().parse(msg);

        String txnId = (String) jsonObject.get("txnId");
        String status = (String) jsonObject.get("status");

        TransactionStatus transactionStatus;

        if("FAILED".equals(status)){
            transactionStatus = TransactionStatus.FAILED;
        }else{
            transactionStatus = TransactionStatus.SUCCESSFUL;
        }

        Transaction transaction = transactionRepository.findByTxnId(txnId);
        transaction.setTransactionStatus(transactionStatus);

        transactionRepository.save(transaction);

        Integer receiverId = transaction.getReceiverUserId();
        Integer senderId = transaction.getSenderUserId();

        System.out.println("Receiver id = "+receiverId);
        System.out.println("Sender id = "+senderId);

        JSONObject sender = restTemplate.getForObject("http://localhost:9000/user?id=" + senderId, JSONObject.class);
        String senderEmail = (String) sender.get("email");

        JSONObject receiver = restTemplate.getForObject("http://localhost:9000/user?id=" + receiverId, JSONObject.class);
        String receiverEmail = (String) receiver.get("email");

        System.out.println("email id = "+receiverEmail);

        JSONObject txnCompleteEvent = new JSONObject();

        System.out.println("txnID = "+txnId);
        System.out.println("sender = "+senderEmail);
        System.out.println("receiver = "+receiverEmail);
        System.out.println("status = "+ transaction.getTransactionStatus().name());
        System.out.println("amount = "+transaction.getAmount());

        txnCompleteEvent.put("txnId", txnId);
        txnCompleteEvent.put("sender", senderEmail);
        txnCompleteEvent.put("receiver", receiverEmail);
        txnCompleteEvent.put("status", transaction.getTransactionStatus().name());
        txnCompleteEvent.put("amount", transaction.getAmount());


        kafkaTemplate.send(TXN_COMPLETE_TOPIC, txnCompleteEvent.toJSONString());

    }
}
