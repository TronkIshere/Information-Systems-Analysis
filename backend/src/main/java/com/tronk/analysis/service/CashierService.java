package com.tronk.analysis.service;

import com.tronk.analysis.dto.request.cashier.UploadCashierRequest;
import com.tronk.analysis.dto.request.cashier.UpdateCashierRequest;
import com.tronk.analysis.dto.response.cashier.CashierResponse;
import java.util.UUID;
import java.util.List;

public interface CashierService {

	CashierResponse createCashier(UploadCashierRequest request);

	CashierResponse getCashierById(UUID id);

	List<CashierResponse> getAllCashiers();

	CashierResponse updateCashier(UpdateCashierRequest request);

	void deleteCashierById(UUID id);

	String softDeleteCashier(UUID id);

}