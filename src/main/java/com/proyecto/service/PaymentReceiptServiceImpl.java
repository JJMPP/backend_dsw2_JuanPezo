package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyecto.entity.PaymentReceipt;
import com.proyecto.entity.TypeService;
import com.proyecto.repository.PaymentReceiptRepository;
import com.proyecto.repository.TypeServicesRepository;

@Service
public class PaymentReceiptServiceImpl implements PaymentReceiptService {

	@Autowired
	private PaymentReceiptRepository paymentReceiptRepository;	

	@Autowired
	private TypeServicesRepository repository;
	
	@Override
	public PaymentReceipt createPaymentReceipt(PaymentReceipt obj) {
		return paymentReceiptRepository.save(obj);
	}

	@Override
	public List<PaymentReceipt> findAll() {
		return paymentReceiptRepository.findAll();
	}

	@Override
	public List<PaymentReceipt> listPaymentReceiptParams(String dni, String nom, int typeService, Integer status) {
		return paymentReceiptRepository.listPaymentReceiptByParameter(dni, nom, typeService, status);
	}
	
	@Override
	public List<TypeService> listTypeServices() {
		return repository.findAll();
	}

	@Override
	public Optional<PaymentReceipt> findById(Integer id) {
		return paymentReceiptRepository.findById(id);
	}

}
