package br.ufma.sppg.dto;

import lombok.Data;

@Data
public class AuthenticateRequest {
    
    public String email;
    public String password;
}
