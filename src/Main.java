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

        final SecretKey chaveSimetrica = new SecretKeySpec(Base64.getDecoder().decode(chave), "AES");

        if (decisao == 1) {

            System.out.println("Informe o texto plano a ser encriptado: ");
            String textoPlano = new Scanner(System.in).nextLine();

            final String textoEncriptado = encriptar(chaveSimetrica, textoPlano);

            System.out.println("Texto encriptado: " + textoEncriptado);

        } else if (decisao == 2) {

            System.out.println("Informe o texto encriptado a ser decriptado: ");
            final String textoEncriptado = new Scanner(System.in).nextLine();

            final String textoDecriptado = decriptar(chaveSimetrica, textoEncriptado);

            System.out.println("Texto decriptado: " + textoDecriptado);
        } else {
            System.out.println("eu nem te dei essa opção...");
        }
    }


    public static String encriptar(final SecretKey chaveSimetrica, final String textoPlano) {

        String textoEncriptado = "";

        try {

            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, chaveSimetrica);

            final byte[] mensagemEncriptada = cipher.doFinal(textoPlano.getBytes());

            textoEncriptado = Base64.getEncoder().encodeToString(mensagemEncriptada);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return textoEncriptado;
    }

    public static String decriptar(final SecretKey chaveSimetrica, final String textoEncriptado) {

        String textoDecriptado = "";

        try {

            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.DECRYPT_MODE, chaveSimetrica);

            final byte[] mensagemDecriptada = cipher.doFinal(Base64.getDecoder().decode(textoEncriptado));

            textoDecriptado = new String(mensagemDecriptada);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return textoDecriptado;
    }
}
