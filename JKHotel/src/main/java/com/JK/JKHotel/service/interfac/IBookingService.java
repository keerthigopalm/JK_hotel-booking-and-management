package com.JK.JKHotel.service.interfac;

import com.JK.JKHotel.dto.Response;
import com.JK.JKHotel.entity.Booking;

public interface IBookingService {
    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);
    Response findBookingByConfirmationCode(String confirmationCode);
    Response getAllBooking();
    Response cancleBooking(Long bookingId);
}
