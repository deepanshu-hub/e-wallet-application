//package com.wallet.transaction;
//
//import jakarta.persistence.*;
////import lombok.*;
////import org.hibernate.annotations.CreationTimestamp;
////import org.hibernate.annotations.UpdateTimestamp;
////
////import java.util.Date;
//
//import jakarta.persistence.Entity;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//
//import java.util.Date;
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//@Data
//public class Transaction {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    private String txnId;
//
//    private int senderUserId;
//
//    private int receiverUserId;
//
//    @Enumerated(value = EnumType.STRING)
//    private TransactionStatus transactionStatus;
//
//    @CreationTimestamp
//    private Date createdOn;
//
//    @UpdateTimestamp
//    private Date updatedOn;
//
//    private Double amount;
//
//    private String purpose;
//
//
//
//}


package com.wallet.transaction;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

//import javax.persistence.*;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String txnId;

    @Enumerated(value = EnumType.STRING)
    private TransactionStatus transactionStatus;

    @CreationTimestamp
    private Date createdOn;

    @UpdateTimestamp
    private Date updatedOn;

    private int senderUserId;

    private int receiverUserId;

    private Double amount;

    private String purpose;
}
