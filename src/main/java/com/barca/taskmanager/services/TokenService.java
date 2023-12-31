package com.barca.taskmanager.services;

import com.barca.taskmanager.dtos.JwtDto;
import com.barca.taskmanager.security.CustomUserDetails;

public interface TokenService {

  JwtDto createToken(CustomUserDetails userDetails);

}
