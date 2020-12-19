import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Main {

    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("1-Encriptar \n2-Decriptar");
        final int decisao = teclado.nextInt();

        System.out.println("Informe a chave: ");
        String chave = new Scanner(System.in).nextLine();

        // A chave simétrica vai estar encodada em Base64, e é usado para gerar uma Key para o algoritmo de
        // criptografia AES
        final SecretKey key = new SecretKeySpec(Base64.getDecoder().decode(chave), "AES");

        if(decisao == 1) {

            System.out.println("Informe o texto a ser encriptado: ");
            String texto = new Scanner(System.in).nextLine();

            final String textoEncriptado = encriptar(key, texto);

            System.out.println("Texto encriptado: " + textoEncriptado);

        } else if(decisao == 2) {

            System.out.println("Informe o texto a ser decriptado: ");
            final String texto = new Scanner(System.in).nextLine();

            final String textoDecriptado = decriptar(key, texto);

            System.out.println("Texto decriptado: " + textoDecriptado);
        } else {
            System.out.println("eu nem te dei essa opção...");
        }
    }


    public static String encriptar(final SecretKey chaveSimetrica, final String textoPlano) {

        // String que vai conter o texto encriptado
        String textoEncriptado = "";

        // Try catch é feito para caso ocorra uma exceção, a gente capture ela e printe na tela
        try {

            // Obtendo o algoritmo de criptografia informado (AES)
            Cipher cipher = Cipher.getInstance("AES");

            // Preparando o cipher para ENCRIPTAR com a chave informada
            cipher.init(Cipher.ENCRYPT_MODE, chaveSimetrica);

            // Executa o algoritmo para qual o cipher foi inicializado
            final byte[] mensagemEncriptada = cipher.doFinal(textoPlano.getBytes());

            // Texto encriptado é encodado para Base64
            textoEncriptado = Base64.getEncoder().encodeToString(mensagemEncriptada);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return textoEncriptado;
    }

    public static String decriptar(final SecretKey chaveSimetrica, final String textoEncriptado) {

        // String que vai conter o texto decriptado
        String textoDecriptado = "";

        // Try catch é feito para caso ocorra uma exceção, a gente capture ela e printe na tela
        try {

            // Obtendo o algoritmo de criptografia informado (AES)
            Cipher cipher = Cipher.getInstance("AES");

            // Preparando o cipher para DECRIPTAR com a chave informada
            cipher.init(Cipher.DECRYPT_MODE, chaveSimetrica);

            // Executa o algoritmo para qual o cipher foi inicializado, passando o texto encriptado decodificado
            final byte[] mensagemDecriptada = cipher.doFinal(Base64.getDecoder().decode(textoEncriptado));

            // Texto decriptado
            textoDecriptado = new String(mensagemDecriptada);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return textoDecriptado;
    }
}
