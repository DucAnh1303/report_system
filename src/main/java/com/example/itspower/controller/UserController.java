package com.example.itspower.controller;

import com.example.itspower.filter.JwtToken;
import com.example.itspower.model.entity.UserEntity;
import com.example.itspower.response.CheckIsReportResponse;
import com.example.itspower.response.InforUser;
import com.example.itspower.response.SuccessResponse;
import com.example.itspower.response.search.AddToUserForm;
import com.example.itspower.response.search.UserAulogin;
import com.example.itspower.service.UserService;
import com.example.itspower.service.impl.UserLoginConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserLoginConfig userLoginConfig;


    @PostMapping("/api/save")
    public ResponseEntity<Object> saveData(@Valid @RequestBody AddToUserForm addToUserForm) {
        try {
            UserEntity data = userService.save(addToUserForm);
            return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(1, "register success", data));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/api/getInfo")
    public ResponseEntity<Object> getInfo(@RequestParam String loginName) {
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(HttpStatus.OK.value(),
                "get user Infor", userService.getInfoUser(loginName)));
    }

    @PostMapping("/api/login")
    public ResponseEntity<Object> login(@Valid @RequestBody UserAulogin userAulogin) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userAulogin.getUserLogin(), userAulogin.getPassword()));
        UserDetails userDetails = userLoginConfig.loadUserByUsername(userAulogin.getUserLogin());
        String token = jwtToken.generateToken(userDetails);
        InforUser inforUser = userService.getInfoUser(userDetails.getUsername());
        Date date = new Date();
        String newDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        CheckIsReportResponse checkReported = userService.checkIsReport(inforUser.getGroupId(),newDate);
        if(checkReported.getNum().intValue() >=1){
            inforUser.setReported(true);
        }else{
            inforUser.setReported(false);
        }
        inforUser.setToken(token);
        return ResponseEntity.status(HttpStatus.OK).body(new SuccessResponse<>(HttpStatus.OK.value(), "login success",
                inforUser));
    }
}
