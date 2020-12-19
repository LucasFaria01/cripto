import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;

public class RSA {
    private static final Scanner teclado = new Scanner(System.in);

    static String kPublic = "";
    static String kPrivate = "";

    private static final int RADIX = 36;

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

        //Recebe um algoritmo para gerar uma instância com o par de chaves para criptografar
        KeyPairGenerator kpg = KeyPairGenerator.getInstance(RSA);

        //Iniciar a chave para que tenha um tamanho específico (número de bits)
        kpg.initialize(TAMANHO_DA_CHAVE);

        //Devolve o objeto contendo o par de chaves
        KeyPair kp = kpg.genKeyPair();

        //Pega o objeto que representa a chave pública
        PublicKey publicKeyGerada = kp.getPublic();

        //Pega o objeto que representa a chave privada
        PrivateKey privateKeyGerada = kp.getPrivate();

        //Pega o valor do código da chave pública (array de bytes)
        byte[] bytesChavePublica = publicKeyGerada.getEncoded();

        //Pega o valor do código da chave privada (array de bytes)
        byte[] bytesChavePrivada = privateKeyGerada.getEncoded();

        kPublic = bytesToString(bytesChavePublica);
        kPrivate = bytesToString(bytesChavePrivada);

        //Inicializa um objeto cipher poder criptografar utilizando o algortimo RSA
        Cipher cipher = Cipher.getInstance(RSA);

        //Prepara o cipher para encriptar através da chave informada
        cipher.init(Cipher.ENCRYPT_MODE, publicKeyGerada);

        //Vai finalmente executar o algoritmo informado, criptografando o texto através da chave
        //Este método recebe um array de bytes, então para isso utilizamos o getBytes do texto passado por parâmetro
        final byte[] bytesEncriptados = cipher.doFinal(texto.getBytes());

        return bytesToString(bytesEncriptados);
    }

    public static String decriptar(final String textoEncriptado, final String chavePrivada)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {

        byte[] bytesChavePrivada = stringToBytes(chavePrivada);

        //Instancia um objeto KeyFactory aplicando o algoritmo RSA, retornando um fabricador de chaves
        KeyFactory kf = KeyFactory.getInstance(RSA);

        //Cria um objeto nulo que representa a chave privada
        PrivateKey privateKey = null;

        try {
            //Gera um objeto PrivateKey através do fabricador anterior, recebendo o array de bytes da chavePrivada
            //Vai instanciar um novo objeto, contendo os bytes da chave privada no formato PKCS # 8
            privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(bytesChavePrivada));

        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        //Inicializa um objeto cipher poder criptografar utilizando o algortimo RSA
        Cipher cipher = Cipher.getInstance(RSA);

        //Prepara o cipher para decriptar através da chave informada
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        //Vai finalmente executar o algoritmo informado, criptografando o texto através da chave
        //Este método recebe um array de bytes, então para isso utilizamos o getBytes do texto passado por parâmetro
        byte[] bytesDecriptados = cipher.doFinal(stringToBytes(textoEncriptado));

        //Vai gerar uma string a partir dos bytes decriptados
        return new String(bytesDecriptados);
    }

    public static String bytesToString(byte[] b) {
        return Base64.getEncoder().encodeToString(b);
    }

    public static byte[] stringToBytes(String s) {
        return Base64.getDecoder().decode(s);
    }
}