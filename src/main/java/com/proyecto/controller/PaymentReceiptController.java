package com.proyecto.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.proyecto.entity.PaymentReceipt;
import com.proyecto.entity.TypeService;
import com.proyecto.entity.User;
import com.proyecto.entity.UserPrincipal;
import com.proyecto.service.PaymentReceiptService;
import com.proyecto.util.AppSettings;

@RestController
@RequestMapping("/url/paymentReceipt")
@CrossOrigin(origins = AppSettings.URL_CROSS_ORIGIN)
public class PaymentReceiptController {

	@Autowired
	private PaymentReceiptService paymentReceiptService;
		
	@GetMapping("/list")
	public ResponseEntity<List<PaymentReceipt>> getListPaymentReceipt(){
		List<PaymentReceipt> list = paymentReceiptService.findAll();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/listServices")
	public ResponseEntity<List<TypeService>> getListTypeServices(){
		List<TypeService> list = paymentReceiptService.listTypeServices();
		return ResponseEntity.ok(list);
	}
	
	@GetMapping("/paymentReceiptByParameters")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> listPaymentReceiptByParameter(
			@RequestParam(name="dni",defaultValue = "",required = false) String dni,
			@RequestParam(name="name",defaultValue = "",required = false) String name,
			@RequestParam(name="typeService",defaultValue = "0",required = false) Integer idTypeService,
			@RequestParam(name="status",defaultValue = "0",required = false) Integer status){
		
		Map<String,Object> response = new HashMap<>();
		
		try {
			List<PaymentReceipt> lstPaymentReceipt = paymentReceiptService.listPaymentReceiptParams(dni, name, idTypeService, status);
			if(CollectionUtils.isEmpty(lstPaymentReceipt)) {
				response.put("mensaje", "No existe datos para la consulta");
			}else {
				response.put("lista", lstPaymentReceipt);
				response.put("mensaje", "Existe = "+lstPaymentReceipt.size());
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.put("mensaje", "Ocurrio un error, No existe datos para mostrar");
		}
		return ResponseEntity.ok(response);
	}
	
	
	
	
	
	
	@PostMapping("/create")	
	@ResponseBody
	public ResponseEntity<Map<String, Object>> createPaymentReceipt(@RequestBody PaymentReceipt paymentReceipt, Authentication authentication) {
		
		Map<String, Object> response = new HashMap<>();
		
		try {		
										
			for (int i = 1; i <= 12; i++) {
								
				PaymentReceipt receipt = new PaymentReceipt();
				
				Date date = paymentReceipt.getPeriodDate();
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);
				cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
				cal.add(Calendar.MONTH, i);
				cal.add(Calendar.DATE, 5);
				
				Calendar payment = Calendar.getInstance();
				payment.setTime(date);
				payment.set(Calendar.DATE, payment.getActualMaximum(Calendar.DATE));
				payment.add(Calendar.MONTH, i);
				
				int month = payment.get(Calendar.MONTH) + 1;
				
				receipt.setMonth(month);
				receipt.setPeriodDate(payment.getTime());
				receipt.setPaymentDate(payment.getTime());
				receipt.setExpirationDate(cal.getTime());
				receipt.setAmount(paymentReceipt.getAmount());
				receipt.setTypeService(paymentReceipt.getTypeService());
				receipt.setDepartment(paymentReceipt.getDepartment());	
				
				var userPrincipal = (UserPrincipal) authentication.getPrincipal();
				User user = new User();
				user.setId(userPrincipal.getId());
				receipt.setUser(user);
				
				paymentReceiptService.createPaymentReceipt(receipt);
			}
			
			
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al crear la boleta de pago en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("Boleta de Pago", paymentReceipt);		
		response.put("mensaje", "Boleta de pago creado con éxito");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);		
	}
	
	
	@PutMapping("/updatePaymentReceipt")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updatePayment(@RequestBody PaymentReceipt obj) {
		
		Map<String, Object> salida = new HashMap<>();
		
		try {
			if (obj.getId() == 0) {
				salida.put("mensaje", "El ID del pago debe ser diferente cero");
				return ResponseEntity.ok(salida);
			}
			PaymentReceipt objSalida = paymentReceiptService.createPaymentReceipt(obj);
			if (objSalida == null) {
				salida.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
			} else {
				salida.put("mensaje", AppSettings.MENSAJE_REG_EXITOSO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			salida.put("mensaje", AppSettings.MENSAJE_REG_ERROR);
		}
		return ResponseEntity.ok(salida);
	}
	
	
}
