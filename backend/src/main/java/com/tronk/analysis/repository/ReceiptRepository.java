package com.tronk.analysis.repository;

import java.util.UUID;
import com.tronk.analysis.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {
}