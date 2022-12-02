package com.proyecto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.proyecto.entity.PaymentReceipt;

public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipt, Integer> {
	
	@Query("select p from PaymentReceipt p where (:p_dni is '' or p.department.user.dni = :p_dni)"
										  + "and (:p_name is '' or p.department.user.name = :p_name)"
										  + "and (:p_typeservice is 0 or p.typeService.id = :p_typeservice)"
										  + "and (:p_status is 0 or p.status like :p_status)")
	public List<PaymentReceipt> listPaymentReceiptByParameter(@Param("p_dni") String dni, 
															  @Param("p_name") String name,
															  @Param("p_typeservice") int typeService, 
															  @Param("p_status") Integer status);
	
	
}
