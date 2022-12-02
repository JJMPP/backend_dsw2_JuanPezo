package com.proyecto.service;

import java.util.List;
import java.util.Optional;

import com.proyecto.entity.PaymentReceipt;
import com.proyecto.entity.TypeService;

public interface PaymentReceiptService {
	
	public Optional<PaymentReceipt> findById(Integer id);
	
	public PaymentReceipt createPaymentReceipt(PaymentReceipt obj);
	public List<PaymentReceipt> findAll();
	
	public List<TypeService> listTypeServices();
	
	public abstract List<PaymentReceipt> listPaymentReceiptParams(String dni, String nom, int typeService, Integer status);

}
