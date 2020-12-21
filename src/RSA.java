import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {

    private static final Scanner teclado = new Scanner(System.in);

    static String kPublic = "";
    static String kPrivate = "";

    private static final String RSA = "RSA";

    private static final int TAMANHO_DA_CHAVE = 1024;

    public static void main(String[] args) {

        System.out.println("Entendemos que você já possui a sua chave super mega hiper secreta, o que pretende fazer com ela?");
        System.out.println("1. Encriptar \n2. Decriptar");
        final int decisao = teclado.nextInt();

        if (decisao == 1) {
            System.out.println("Digite a sua chave super mega hiper secreta a ser encriptada: ");
            final String texto = teclado.next();

            String textoEncriptado = "";

            try {
                textoEncriptado = encriptar(texto);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println("Texto encriptado: " + textoEncriptado);
            System.out.println("Chave de criptografia pública: " + kPublic);
            System.out.println("Chave de criptografia privada: " + kPrivate);

        } else if (decisao == 2) {

            System.out.println("Digite o texto encriptado: ");
            final String encriptado = teclado.next();

            System.out.println("Digite a chave de criptografia privada: ");
            final String chavePrivada = teclado.next();

            String textoNormal = "";

            try {
                textoNormal = decriptar(encriptado, chavePrivada);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println(textoNormal);
        } else {
            System.out.println("eu nem te dei essa opção...");
        }
    }

    public static String encriptar(String texto)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IllegalBlockSizeException, BadPaddingException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);

        kpg.initialize(TAMANHO_DA_CHAVE);

        KeyPair kp = kpg.genKeyPair();

        PublicKey publicKeyGerada = kp.getPublic();

        PrivateKey privateKeyGerada = kp.getPrivate();

        byte[] bytesChavePublica = publicKeyGerada.getEncoded();

        byte[] bytesChavePrivada = privateKeyGerada.getEncoded();

        kPublic = bytesToString(bytesChavePublica);
        kPrivate = bytesToString(bytesChavePrivada);

        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.ENCRYPT_MODE, publicKeyGerada);

        final byte[] bytesEncriptados = cipher.doFinal(texto.getBytes());

        return bytesToString(bytesEncriptados);
    }

    public static String decriptar(final String textoEncriptado, final String chavePrivada)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IllegalBlockSizeException, BadPaddingException {

        byte[] bytesChavePrivada = stringToBytes(chavePrivada);

        KeyFactory kf = KeyFactory.getInstance(RSA);

        PrivateKey privateKey = null;

        try {
            privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(bytesChavePrivada));

        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] bytesDecriptados = cipher.doFinal(stringToBytes(textoEncriptado));

        return new String(bytesDecriptados);
    }

    public static String bytesToString(byte[] b) {
        return Base64.getEncoder().encodeToString(b);
    }

    public static byte[] stringToBytes(String s) {
        return Base64.getDecoder().decode(s);
    }
}