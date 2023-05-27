package com.project.service;

import com.project.exception.RecordNotFoundException;
import com.project.model.Order;
import com.project.model.OrderDto;
import com.project.model.User;
import com.project.repository.CartRepository;
import com.project.repository.OrderRepo;

import jakarta.mail.internet.MimeMessage;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class OrderSercieImpl implements OrderService {

	@Autowired
	OrderRepo orderRepo;

	@Autowired
	CartRepository cartRepo;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private IAuthenticationFacade authFacade;

	@Value("${spring.mail.username}")
	private String sender;

	@Override
	public Order save(Order order) {
		
		Authentication auth = authFacade.getAuthentication();
		User user = (User) auth.getPrincipal();
		
		order.setUserId(user.getId());
		
		Order o = orderRepo.save(order);
//		sendEmail(o); Emial service is currently diabled by me only
		new java.util.Timer().schedule(new java.util.TimerTask() {
			@Override
			public void run() {
				try {
					sendEmail(o);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 2000);
		// Send email

		return o;
	}

	@Override
	public List<Order> orderList() throws RecordNotFoundException {
		
		List<Order> orders = orderRepo.findAll(Sort.by(Sort.Direction.DESC,"lastUpdated"));

		if (orders != null) {

			return orders;
		} else {
			throw new RecordNotFoundException("Data Not found");
		}
	}

	@Override
	public List<Order> orderListByUserId() {
		Authentication authetication = authFacade.getAuthentication();
		User dbUser = (User) authetication.getPrincipal();
		List<Order> orders = orderRepo.findByUserIdOrderByDateCreatedDesc(dbUser.getId());
		return orders;
	}

	public void sendEmail(Order order) {

		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(order.getCustomerEmail());

			mailMessage.setText("Hi " + order.getCustomerName() + " your Order is successfully Placed,"
					+ " It will be delivered shortly on your Address which is " + order.getCustomerAddress()
					+ " You will get Call from our delivery person on Your Mobile No." + order.getCustomerMobile()
					+ " Total Order Items in Your list is " + order.getTotalQuantity()
					+ " And Total amount You have to Pay for Your Order is ₹ " + order.getTotalPrice());

			mailMessage.setSubject(order.getCustomerName() + " Your Order is Placed...");

			javaMailSender.send(mailMessage);

		} catch (Exception e) {
			throw e;
		}
	}

	public void sendEmailWithAttachment(Order order) throws Exception {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;



		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);

			mimeMessageHelper.setFrom(sender);

			mimeMessageHelper.setTo(order.getCustomerEmail());

			mimeMessageHelper.setText("Hi " + order.getCustomerName() + " your Order is successfully Placed,"
					+ " It will be delivered shortly on your Address which is " + order.getCustomerAddress()
					+ " You will get Call from our delivery person on Your Mobile No." + order.getCustomerMobile()
					+ " Total Order Items in Your list is " + order.getTotalQuantity()
					+ " And Total amount You have to Pay for Your Order is ₹ " + order.getTotalPrice());

			mimeMessageHelper.setSubject(order.getCustomerName() + " Your Order is Placed...");

			String filename = "C:\\Users\\vishwajeet.malusare\\Downloads\\Invoice" + order.getId() + ".pdf";
//			System.out.println(filename);
			FileSystemResource file = new FileSystemResource(new File(filename));

			mimeMessageHelper.addAttachment(file.getFilename(), file);

			javaMailSender.send(mimeMessage);

		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public Order orderById(Long orderId) {
		Optional<Order> o = orderRepo.findById(orderId);
		return o.get();
	}

	@Override
	public Order updateOrder(Long orderId, String status, String remark) {
		Order updatedOrder = orderRepo.findById(orderId).get();
		updatedOrder.setStatus(status);
		updatedOrder.setRemark(remark);
		Order newOrder = orderRepo.save(updatedOrder);
		return newOrder;
	}

	@Override
	public List<Order> filterOrders(OrderDto orderDto) {
		Date obj = orderDto.getToDate();
		if(obj != null) {
			List<Order> list = orderRepo.findByLastUpdatedBetween(orderDto.getFromDate(), orderDto.getToDate());
			System.out.println("Date Filter Result: "+list.get(0));
			return list;
		}
		else {
		List<Order> list = orderRepo.filterOrder(orderDto.getDate(), orderDto.getStatus(),  orderDto.getUserId());
		return list;
		}
	}
	

}

