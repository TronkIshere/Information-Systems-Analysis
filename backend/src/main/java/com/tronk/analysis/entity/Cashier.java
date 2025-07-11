package com.tronk.analysis.entity;

import com.tronk.analysis.entity.common.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Cashier extends User {
    BigDecimal salary;
    LocalDate hireDate;

    @OneToMany(mappedBy = "cashier", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Receipt> receipts = new HashSet<>();
}
