package com.mycompany.property_management.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class UserDTO {
    private  Long id;
    private  String ownerName;
    private String ownerEmail;
    private String phone;
    private  String password;
}
