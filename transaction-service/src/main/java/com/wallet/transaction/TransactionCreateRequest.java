package com.wallet.transaction;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionCreateRequest {

         @Positive
         private Integer senderUserId;

         @Positive
         private Integer receiverUserId;

         @Positive
         private Double amount;

         private String purpose;

    public Transaction to(){
        return Transaction.builder()
                .senderUserId(senderUserId)
                .receiverUserId(receiverUserId)
                .amount(amount)
                .purpose(purpose)
                .build();
    }

}
