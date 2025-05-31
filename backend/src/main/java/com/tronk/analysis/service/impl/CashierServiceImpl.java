package com.tronk.analysis.service.impl;

import com.tronk.analysis.dto.request.cashier.UpdateCashierRequest;
import com.tronk.analysis.dto.request.cashier.UploadCashierRequest;
import com.tronk.analysis.dto.response.cashier.CashierResponse;
import com.tronk.analysis.entity.Cashier;
import com.tronk.analysis.mapper.cashier.CashierMapper;
import com.tronk.analysis.repository.CashierRepository;
import com.tronk.analysis.service.CashierService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CashierServiceImpl implements CashierService {
	CashierRepository cashierRepository;
	PasswordEncoder passwordEncoder;
	@Override
	public CashierResponse createCashier(UploadCashierRequest request) {
		Cashier cashier = new Cashier();
		cashier.setName(request.getName());
		cashier.setEmail(request.getEmail());
		cashier.setPhoneNumber(request.getPhoneNumber());
		cashier.setLoginName(request.getEmail());
		cashier.setPassword(passwordEncoder.encode(request.getPassword()));
		cashier.setBirthDay(request.getBirthDay());
		cashier.setGender(request.isGender());
		cashier.setStatus(request.getStatus());
		cashier.setRoles("ROLE_CASHIER");
		cashier.setSalary(request.getSalary());
		cashier.setHireDate(request.getHireDate());
		Cashier savedEntity = cashierRepository.save(cashier);
		return CashierMapper.toResponse(savedEntity);
	}

	@Override
	public List<CashierResponse> getAllCashiers() {
		return CashierMapper.toResponseList(cashierRepository.findAll());
	}

	@Override
	public CashierResponse getCashierById(UUID id) {
		return CashierMapper.toResponse(
			cashierRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Cashier not found")));
	}

	@Override
	public CashierResponse updateCashier(UpdateCashierRequest request) {
		Cashier cashier = cashierRepository.findById(request.getId())
			.orElseThrow(() -> new EntityNotFoundException("Cashier not found"));
		cashier.setName(request.getName());
		cashier.setEmail(request.getEmail());
		cashier.setPhoneNumber(request.getPhoneNumber());
		cashier.setLoginName(request.getEmail());
		cashier.setPassword(passwordEncoder.encode(request.getPassword()));
		cashier.setBirthDay(request.getBirthDay());
		cashier.setGender(request.isGender());
		cashier.setStatus(request.getStatus());
		cashier.setSalary(request.getSalary());
		cashier.setHireDate(request.getHireDate());
		cashierRepository.save(cashier);
		return CashierMapper.toResponse(cashier);
	}

	@Override
	public void deleteCashierById(UUID id) {
		Cashier entity = cashierRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Cashier not found"));
		cashierRepository.delete(entity);
	}

	@Override
	public String softDeleteCashier(UUID id) {
		Cashier entity = cashierRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("Cashier not found"));
		entity.setDeletedAt(LocalDateTime.now());
		cashierRepository.save(entity);
		return "Cashier with ID " + id + " has been soft deleted";
	}
}
