package com.huertohogar.huerto_backend.controller;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.huertohogar.huerto_backend.model.PaymentTransaction;
import com.huertohogar.huerto_backend.payment.WebpayService;
import com.huertohogar.huerto_backend.repository.PaymentTransactionRepository;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "http://18.232.152.111"})
@RequestMapping("/api/payments")
public class PaymentController {

    private final WebpayService webpayService;
    private final PaymentTransactionRepository txRepo;

    public PaymentController(WebpayService webpayService, PaymentTransactionRepository txRepo) {
        this.webpayService = webpayService;
        this.txRepo = txRepo;
    }

    @PostMapping("/init")
    public ResponseEntity<?> init(@RequestBody Map<String, Object> body) {
        try {
            double amount = Double.parseDouble(String.valueOf(body.getOrDefault("amount", 0)));
            String buyOrder = String.valueOf(body.getOrDefault("buyOrder", String.valueOf(System.currentTimeMillis())));
            String sessionId = String.valueOf(body.getOrDefault("sessionId", "session-" + System.currentTimeMillis()));
            var resp = webpayService.initTransaction(buyOrder, sessionId, amount);
            // Persist initial transaction
            PaymentTransaction tx = new PaymentTransaction(buyOrder, sessionId, resp.getToken(), amount, "INITIATED");
            txRepo.save(tx);
            Map<String, Object> result = new HashMap<>();
            result.put("token", resp.getToken());
            result.put("url", resp.getUrl());
            result.put("buyOrder", buyOrder);
            result.put("sessionId", sessionId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/commit")
    public ResponseEntity<?> commit(@RequestParam("token") String token) {
        try {
            var resp = webpayService.commitTransaction(token);
            txRepo.findByToken(token).ifPresent(tx -> {
                tx.setStatus(resp.getStatus());
                tx.setAuthorizationCode(resp.getAuthorizationCode());
                tx.setPaymentTypeCode(resp.getPaymentTypeCode());
                tx.setAmount(resp.getAmount());
                tx.setCommittedAt(OffsetDateTime.now());
                txRepo.save(tx);
            });
            Map<String, Object> result = new HashMap<>();
            result.put("status", resp.getStatus());
            result.put("authorizationCode", resp.getAuthorizationCode());
            result.put("paymentTypeCode", resp.getPaymentTypeCode());
            result.put("amount", resp.getAmount());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/status")
    public ResponseEntity<?> status(@RequestParam("token") String token) {
        try {
            var resp = webpayService.statusTransaction(token);
            Map<String, Object> result = new HashMap<>();
            result.put("status", resp.getStatus());
            result.put("buyOrder", resp.getBuyOrder());
            result.put("sessionId", resp.getSessionId());
            result.put("amount", resp.getAmount());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Webpay return bridge: receives POST with token_ws and redirects to frontend with query
    @PostMapping("/return")
    public ResponseEntity<?> paymentReturn(@RequestParam("token_ws") String tokenWs) {
        String redirectUrl = "http://localhost:3000/payment/return?token_ws=" + tokenWs;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", redirectUrl);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }

    // Some flows may hit return via GET; support GET as well
    @GetMapping("/return")
    public ResponseEntity<?> paymentReturnGet(@RequestParam("token_ws") String tokenWs) {
        String redirectUrl = "http://localhost:3000/payment/return?token_ws=" + tokenWs;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", redirectUrl);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
