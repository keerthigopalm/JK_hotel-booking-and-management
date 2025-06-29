package com.JK.JKHotel.service.interfac;

import com.JK.JKHotel.dto.LoginRequest;
import com.JK.JKHotel.dto.Response;
import com.JK.JKHotel.entity.User;

public interface IUserService {
    Response register(User user);
    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response getUserBookingHistory(String userId);
    Response deleteUser(String userId);
    Response getUserById(String userId);
    Response getMyInfo(String email);

}
