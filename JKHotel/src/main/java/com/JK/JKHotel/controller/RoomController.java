package com.JK.JKHotel.controller;

import com.JK.JKHotel.dto.Response;
import com.JK.JKHotel.service.interfac.IBookingService;
import com.JK.JKHotel.service.interfac.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static java.time.temporal.WeekFields.ISO;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private IRoomService roomService;
    @Autowired
    private IBookingService bookingService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewRoom(
            @RequestParam(value = "photo", required = false)MultipartFile photo,
            @RequestParam(value = "roomType", required = false)String roomType,
            @RequestParam(value = "roomPrice", required = false)BigDecimal roomPrice,
            @RequestParam(value = "roomDescription", required = false)String roomDescription
            ){
        if(photo ==null || photo.isEmpty() || roomType== null || roomType.isBlank() || roomPrice==null){
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide the value for all fields(photo,roomType, roomPrice)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = roomService.addNewRoom(photo,roomType, roomPrice, roomDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Response> getAllRooms(){
        Response response =roomService.getAllRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getRoomTypes(){
        return roomService.getAllRoomTypes();
    }

    @GetMapping("/room-by-id/{roomId}")
    public ResponseEntity<Response> getRoomById(@PathVariable Long roomId){
        Response response = roomService.getRoomById(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/all-available-rooms")
    public ResponseEntity<Response> getAllAvailableRooms(){
        Response response = roomService.getAvailableRooms();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/available-rooms-by-date-and-type")
    public ResponseEntity<Response> getAvailableRoomsByDateAndType(
            @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate checkInDate,
            @RequestParam(required = false) @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
            @RequestParam(required = false)  String roomType
            ){
        if(checkInDate == null || roomType == null || roomType.isBlank() || checkOutDate == null){
            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Please provide the value for all fields(checkInDate,roomType, checkOutDate)");
            return ResponseEntity.status(response.getStatusCode()).body(response);
        }

        Response response = roomService.getAvailableRoomsByDataAndType(checkInDate, checkOutDate, roomType);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @PutMapping("/update/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> updateRoom(@PathVariable Long roomId,
                                               @RequestParam(value = "photo", required = false)MultipartFile photo,
                                               @RequestParam(value = "roomType", required = false)String roomType,
                                               @RequestParam(value = "roomPrice", required = false)BigDecimal roomPrice,
                                               @RequestParam(value = "roomDescription", required = false)String roomDescription){
        Response response = roomService.updateRoom(roomId, roomDescription, roomType, roomPrice, photo);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @DeleteMapping("/delete/{roomId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable Long roomId){
        Response response = roomService.deleteRoom(roomId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
