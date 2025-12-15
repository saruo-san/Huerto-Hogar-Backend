package com.huertohogar.huerto_backend.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(name = "PAYMENT_TRANSACTION", indexes = {
    @Index(name = "idx_payment_token", columnList = "token", unique = true),
    @Index(name = "idx_payment_buyorder", columnList = "buyOrder")
})
public class PaymentTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String buyOrder;

    @Column(nullable = false)
    private String sessionId;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status; // INITIATED, AUTHORIZED, FAILED, etc.

    private String authorizationCode;

    private String paymentTypeCode;

    @Column(nullable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    private OffsetDateTime committedAt;

    public PaymentTransaction() {}

    public PaymentTransaction(String buyOrder, String sessionId, String token, Double amount, String status) {
        this.buyOrder = buyOrder;
        this.sessionId = sessionId;
        this.token = token;
        this.amount = amount;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBuyOrder() { return buyOrder; }
    public void setBuyOrder(String buyOrder) { this.buyOrder = buyOrder; }
    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getAuthorizationCode() { return authorizationCode; }
    public void setAuthorizationCode(String authorizationCode) { this.authorizationCode = authorizationCode; }
    public String getPaymentTypeCode() { return paymentTypeCode; }
    public void setPaymentTypeCode(String paymentTypeCode) { this.paymentTypeCode = paymentTypeCode; }
    public OffsetDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(OffsetDateTime createdAt) { this.createdAt = createdAt; }
    public OffsetDateTime getCommittedAt() { return committedAt; }
    public void setCommittedAt(OffsetDateTime committedAt) { this.committedAt = committedAt; }
}
