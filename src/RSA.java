import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSA {

    static String kPublic = "";
    static String kPrivate = "";

//    public static void main(String[] args) {
//
//        Scanner teclado = new Scanner(System.in);
//        System.out.println("Digite um texto a ser encriptado: ");
//        final String texto = teclado.nextLine();
//
//        String textoEncriptado = "";
//
//        try {
//            textoEncriptado = encrypt(texto);
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println("Texto encriptado: " + textoEncriptado);
//        System.out.println("Chave pública: " + kPublic);
//        System.out.println("Chave privada: " + kPrivate);
//    }

    public static void main(String[] args) {

        Scanner teclado = new Scanner(System.in);
        System.out.println("Digite o texto encriptado: ");
        final String encriptado = teclado.nextLine();

        System.out.println("Digite a chave privada: ");
        final String chavePrivada = teclado.nextLine();

        String textoNormal = "";

        try {
            textoNormal = decrypt(encriptado, chavePrivada);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(textoNormal);
    }

    public static String encrypt(String plain)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IllegalBlockSizeException, BadPaddingException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair kp = kpg.genKeyPair();

        PublicKey publicKey = kp.getPublic();
        PrivateKey privateKey = kp.getPrivate();

        byte[] publicKeyBytes = publicKey.getEncoded();
        byte[] privateKeyBytes = privateKey.getEncoded();

        kPublic = bytesToString(publicKeyBytes);
        kPrivate = bytesToString(privateKeyBytes);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        final byte[] encryptedBytes = cipher.doFinal(plain.getBytes());

        final String encrypted = bytesToString(encryptedBytes);
        return encrypted;
    }

    public static String decrypt(final String textoEncriptado, final String chavePrivada)
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
        IllegalBlockSizeException, BadPaddingException {

        byte[] decryptedBytes;

        byte[] byteKeyPrivate = stringToBytes(chavePrivada);

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PrivateKey privateKey = null;

        try {

            privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(byteKeyPrivate));

        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        String decrypted;

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        decryptedBytes = cipher.doFinal(stringToBytes(textoEncriptado));
        decrypted = new String(decryptedBytes);
        return decrypted;
    }

    public static String bytesToString(byte[] b) {

        byte[] b2 = new byte[b.length + 1];
        b2[0] = 1;
        System.arraycopy(b, 0, b2, 1, b.length);
        return new BigInteger(b2).toString(36);
    }

    public static byte[] stringToBytes(String s) {

        byte[] b2 = new BigInteger(s, 36).toByteArray();
        return Arrays.copyOfRange(b2, 1, b2.length);
    }
}