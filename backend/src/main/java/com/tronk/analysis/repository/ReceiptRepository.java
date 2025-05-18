package com.tronk.analysis.repository;

import java.util.UUID;
import com.tronk.analysis.entity.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReceiptRepository extends JpaRepository<Receipt, UUID> {
    @Modifying
    @Query("UPDATE Receipt r SET r.semester.id = :semesterId WHERE r.id = :receiptId")
    int updateSemesterForReceipt(
            @Param("receiptId") UUID receiptId,
            @Param("semesterId") UUID semesterId
    );
}