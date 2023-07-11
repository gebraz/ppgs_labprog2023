package br.ufma.sppg.Security;

import java.util.UUID;

import lombok.var;

public interface IJwtService {
    public String generateToken(UUID userId);
    public boolean isValidToken(String token, UUID userId);
}
