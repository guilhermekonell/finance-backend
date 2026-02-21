package com.finance.backend.services;

import com.finance.backend.domains.transaction.Transaction;
import com.finance.backend.domains.transaction.TransactionDTO;
import com.finance.backend.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository repository;

    public void saveIfNotExists(List<TransactionDTO> transactionsDTO) {
        Map<String, TransactionDTO> dtoByHash = transactionsDTO.stream()
                .collect(Collectors.toMap(
                        Transaction::generateHash,
                        dto -> dto,
                        (a, _) -> a // em caso de duplicado no mesmo arquivo
                ));

        Set<String> hashes = dtoByHash.keySet();

        Set<String> existingHashes = repository.findAllByHashIn(hashes)
                .stream()
                .map(Transaction::getHash)
                .collect(Collectors.toSet());

        List<Transaction> entitiesToSave = dtoByHash.entrySet()
                .stream()
                .filter(entry -> !existingHashes.contains(entry.getKey()))
                .map(entry -> {
                    Transaction transaction = Transaction.toEntity(entry.getValue());
                    transaction.setHash(entry.getKey());
                    return transaction;
                })
                .toList();

        repository.saveAll(entitiesToSave);
    }
}
