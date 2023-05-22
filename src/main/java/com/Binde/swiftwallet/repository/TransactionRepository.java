package com.Binde.swiftwallet.repository;

import com.Binde.swiftwallet.entity.TransactionLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<TransactionLog, String> {

}
