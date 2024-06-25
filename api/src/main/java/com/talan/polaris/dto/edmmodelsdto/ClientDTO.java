package com.talan.polaris.dto.edmmodelsdto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long id;
    private String login;
    private String application;
    private String role;
    private String password;

}
