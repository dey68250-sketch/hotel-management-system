package com.hotel_management.payload;

import com.hotel_management.entity.BookingStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponse {

    private Long paymentId;
    private Long bookingId;
    private String guestName;
    private String screenshotPath;
    private BookingStatus status;
}
