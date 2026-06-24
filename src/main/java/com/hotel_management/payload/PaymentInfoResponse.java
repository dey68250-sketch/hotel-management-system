package com.hotel_management.payload;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentInfoResponse {

    private String upiId;
    private String upiName;
    private String qrImageUrl;
}