package com.mty.hometogo.controller;

import com.mty.hometogo.model.Stay;
import com.mty.hometogo.model.User;
import com.mty.hometogo.service.StayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@RestController
public class StayController {
    private StayService stayService;

    @Autowired
    public StayController(StayService stayService) {
        this.stayService = stayService;
    }

    @GetMapping(value = "/stays")
    public List<Stay> listStays(Principal principal) {
        return stayService.listByUser(principal.getName());
    }

    @GetMapping(value = "/stays/{stayId}")
    public Stay getStay(@PathVariable Long stayId) {
        return stayService.findByIdAndHost(stayId);
    }

    @PostMapping("/stays")
    public void addStay(   @RequestParam("name") String name,
                           @RequestParam("address") String address,
                           @RequestParam("description") String description,
                           @RequestParam("guest_number") int guestNumber,
                           @RequestParam("images") MultipartFile[] images,
                           Principal principal) {

        Stay stay = new Stay.Builder().setName(name)
                .setAddress(address)
                .setDescription(description)
                .setGuestNumber(guestNumber)
                .setHost(new User.Builder().setUsername(principal.getName()).build())
                .build();
        stayService.add(stay, images);
    }

    @DeleteMapping("/stays/{stayId}")
    public void deleteStay(@PathVariable Long stayId) {
        stayService.delete(stayId);
    }


}
