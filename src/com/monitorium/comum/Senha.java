package com.monitorium.comum;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** Transforma a senha em um código irreversível (SHA-256), para não gravá-la como foi digitada. */
public final class Senha {

    private Senha() {
    }

    public static String hash(String senha) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = digest.digest(senha.getBytes(StandardCharsets.UTF_8));
            StringBuilder hex = new StringBuilder();
            for (byte b : bytes) {
                hex.append(String.format("%02x", b));
            }
            return hex.toString();
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 existe em todo JDK; este erro não deve acontecer
            throw new MonitoriumException("Não foi possível proteger a senha.", e);
        }
    }

    public static boolean confere(String senhaDigitada, String hashGuardado) {
        byte[] a = hash(senhaDigitada).getBytes(StandardCharsets.UTF_8);
        byte[] b = hashGuardado.getBytes(StandardCharsets.UTF_8);
        return MessageDigest.isEqual(a, b);
    }
}
