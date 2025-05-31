package com.tronk.analysis.service;

import java.util.UUID;

public interface EmailService {
    void sendReceiptForStudent(UUID receiptId);

    void sendRPaymentReceiptInfoForStudent(UUID receiptId);
}
