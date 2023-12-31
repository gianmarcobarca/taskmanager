package com.barca.taskmanager.services.internal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.barca.taskmanager.dtos.UserCreationDto;
import com.barca.taskmanager.models.User;
import com.barca.taskmanager.repositories.TaskRepository;
import com.barca.taskmanager.repositories.UserRepository;
import com.barca.taskmanager.security.UserDetailsImpl;
import com.barca.taskmanager.services.UserService;

import lombok.RequiredArgsConstructor;

@Service("userService")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
  private final UserRepository userRepository;
  private final TaskRepository taskRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return new UserDetailsImpl(userRepository.findByEmail(username).orElseThrow());
  }

  @Override
  public void createUser(UserCreationDto dto) {

    var copy = User
        .builder()
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .email(dto.getEmail())
        .password(passwordEncoder.encode(dto.getPassword()))
        .build();

    userRepository.save(copy);
  }

  @Transactional
  @Override
  public void deleteUser(String userId) {

    taskRepository.deleteAllByUserId(userId);
    // TODO test behavior if exception is thrown here
    userRepository.deleteById(userId);
  }
}
