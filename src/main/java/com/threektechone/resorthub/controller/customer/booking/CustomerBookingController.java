package com.threektechone.resorthub.controller.customer.booking;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.threektechone.resorthub.common.exception.custom.InvalidPaymentException;
import com.threektechone.resorthub.common.response.ApiResponse;
import com.threektechone.resorthub.dto.customer.BookingCreatedResponseDTO;
import com.threektechone.resorthub.dto.customer.BookingRequestDTO;
import com.threektechone.resorthub.dto.customer.CustomerBookingDetailResponseDTO;
import com.threektechone.resorthub.dto.customer.CustomerBookingListResponseDTO;
import com.threektechone.resorthub.enums.BookingStatus;
import com.threektechone.resorthub.service.common.BookingPaymentWebhookService;
import com.threektechone.resorthub.service.customer.CustomerBookingService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerBookingController {

    private final CustomerBookingService bookingService;
    private final BookingPaymentWebhookService webhookService;
    
    @PostMapping("/resorts/{id}/booking")
    public ResponseEntity<ApiResponse<String>> createBooking(
            @Valid @RequestBody BookingRequestDTO dto,
            @PathVariable int id,
            Authentication authentication) {

        String email = authentication.getName();
        bookingService.createBooking(dto, email, id);

        ApiResponse<String> response = new ApiResponse<>(200, null, "Booking successfully!", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    private static String resolveClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            return xff.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

    @GetMapping("/bookings")
    public ResponseEntity<ApiResponse<Page<CustomerBookingListResponseDTO>>> getCustomerBookings(@RequestParam(required=false) String searchkey, @RequestParam(required=false) BookingStatus status,@PageableDefault(size=5) Pageable pageable,Authentication authentication) {
        String email = authentication.getName();

        Page<CustomerBookingListResponseDTO> dtoList = bookingService.getCustomerBookings(email, searchkey, status, pageable);

        ApiResponse<Page<CustomerBookingListResponseDTO>> response = new ApiResponse<>(200,null,dtoList,LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<ApiResponse<CustomerBookingDetailResponseDTO>> getCustomerBookingDetail(@PathVariable int id) {
        CustomerBookingDetailResponseDTO dto = bookingService.getCustomerBookingDetail(id);

        ApiResponse<CustomerBookingDetailResponseDTO> response = new ApiResponse<>(200,null,dto,LocalDateTime.now());

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/bookings/{id}/cancel")
    public ResponseEntity<ApiResponse<String>> cancelBooking(@PathVariable int id,Authentication authentication) {
        String email = authentication.getName();

        bookingService.cancelBookingByCustomer(id, email);

        ApiResponse<String> response = new ApiResponse<>(200,null,"Cancel booking successfully!",LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bookings/{id}/payment")
    public ResponseEntity<ApiResponse<BookingCreatedResponseDTO>> payBooking(
            @PathVariable int id,
            Authentication authentication,
            HttpServletRequest request) {

        String clientIp = resolveClientIp(request);
        String email = authentication.getName();
        BookingCreatedResponseDTO data = bookingService.payBooking(id, email, clientIp);

        ApiResponse<BookingCreatedResponseDTO> response = new ApiResponse<>(200, null, data, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
    

    @PostMapping("/payments/webhook/stripe")
    public ResponseEntity<ApiResponse<String>> stripeWebhook(HttpServletRequest request,@RequestBody String payload) {

        String sig = request.getHeader("Stripe-Signature");
        ApiResponse<String> response;
        try {
            webhookService.handleStripeWebhook(payload, sig);
            response = new ApiResponse<>(200, null, "Stripe webhook processed", LocalDateTime.now());
            return ResponseEntity.ok(response);
        }
        catch (InvalidPaymentException e) {
            response = new ApiResponse<>(400, null, "Stripe webhook rejected: " + e.getMessage(), LocalDateTime.now());
            return ResponseEntity.badRequest().body(response);
        }
    }

}
