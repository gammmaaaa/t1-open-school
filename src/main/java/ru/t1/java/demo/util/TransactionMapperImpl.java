package ru.t1.java.demo.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.t1.java.demo.dto.TransactionDTO;
import ru.t1.java.demo.dto.TransactionForAccept;
import ru.t1.java.demo.mapper.TransactionMapper;
import ru.t1.java.demo.model.Transaction;
import ru.t1.java.demo.service.AccountService;

@Component
@RequiredArgsConstructor
public class TransactionMapperImpl implements TransactionMapper {

    private final AccountService accountService;

    @Override
    public Transaction toEntity(TransactionDTO transactionDTO) {
        if (transactionDTO.getAmount() == null) {
            throw new NullPointerException();
        }
        return Transaction.builder()
                .account(accountService.getAccountById(transactionDTO.getAccountId()))
                .amount(transactionDTO.getAmount())
                .build();
    }

    @Override
    public TransactionDTO toDTO(Transaction transaction) {
        return TransactionDTO.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccount().getId())
                .amount(transaction.getAmount())
                .timeTransaction(transaction.getTimeTransaction())
                .status(transaction.getTransactionStatus().name())
                .build();
    }

    @Override
    public TransactionForAccept toAccept(Transaction transaction) {
        return TransactionForAccept.builder()
                .clientId(transaction.getAccount().getClient().getId())
                .accountId(transaction.getAccount().getId())
                .transactionId(transaction.getId())
                .timeTransaction(transaction.getTimeTransaction())
                .transactionAmount(transaction.getAmount())
                .accountBalance(transaction.getAccount().getBalance())
                .build();
    }
}
