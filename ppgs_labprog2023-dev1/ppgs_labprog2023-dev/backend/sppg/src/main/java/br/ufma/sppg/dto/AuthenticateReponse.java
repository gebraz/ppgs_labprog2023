package br.ufma.sppg.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class AuthenticateReponse {
    public UUID userID;
    public String token;
}