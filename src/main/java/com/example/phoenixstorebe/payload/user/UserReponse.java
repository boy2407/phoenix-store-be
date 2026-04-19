package com.example.phoenixstorebe.payload.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReponse {
    private Long id;
    private String username;
    private String lastname;
    private String firstname;
    private String email;
    private String phone;
}
