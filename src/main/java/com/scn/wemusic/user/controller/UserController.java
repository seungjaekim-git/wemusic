package com.scn.wemusic.user.controller;

import com.scn.wemusic.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/users")
@CrossOrigin(origins = {"*"}, exposedHeaders = {"Content-Disposition","content-disposition"}, allowedHeaders = {"*"})
public class UserController {

    private final UserService userService;



}
