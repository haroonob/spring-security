package com.haroonob.spring.security.springsecurity.dto;


import com.haroonob.spring.security.springsecurity.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstName;
  private String lastName;
  private String userName;
  private String password;
  private List<Role> roles;
}
