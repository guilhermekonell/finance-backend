package com.finance.backend.domains.transaction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
        name = "transactions",
        uniqueConstraints = @UniqueConstraint(name = "uk_transactions_hash", columnNames = "hash")
)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "transaction_date", nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false, unique = true)
    private String hash;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public static Transaction toEntity(TransactionDTO dto) {
        Transaction transaction = new Transaction();
        transaction.setDate(dto.date());
        transaction.setDescription(dto.description());
        transaction.setAmount(dto.amount());
        transaction.setBank(dto.bank());
        transaction.setHash(generateHash(dto));
        transaction.setCreatedAt(LocalDateTime.now());
        return transaction;
    }

    public static String generateHash(TransactionDTO dto) {
        String data = dto.date().toString() + dto.description() + dto.amount().toString() + dto.bank();
        return Integer.toHexString(data.hashCode());
    }

}