package com.tronk.analysis.repository;

import java.util.UUID;
import com.tronk.analysis.entity.Cashier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashierRepository extends JpaRepository<Cashier, UUID> {
}