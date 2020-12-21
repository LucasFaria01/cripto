import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class GeradorDeChave {

    public static final int ITERACOES = 65536;
    public static final int TAMANHO_CHAVE = 256;

    public static void main(String[] args)
        throws NoSuchAlgorithmException, InvalidKeySpecException {

        System.out.println("Informe uma senha: ");
        final String senha = new Scanner(System.in).nextLine();

        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] sal = new byte[16];
        sr.nextBytes(sal);

        SecretKeyFactory fabrica = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        KeySpec especificacao = new PBEKeySpec(senha.toCharArray(), sal, ITERACOES, TAMANHO_CHAVE);
        SecretKey secretGerada = fabrica.generateSecret(especificacao);

        SecretKey secret = new SecretKeySpec(secretGerada.getEncoded(), "AES");

        System.out.println(Base64.getEncoder().encodeToString(secret.getEncoded()));
    }
}
