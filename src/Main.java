import java.util.Base64;
import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Main {

    private static SecretKey key;
    private static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {

        System.out.println("1-Encriptar \n2-Decriptar");
        final int decisao = teclado.nextInt();

        if(decisao == 1) {

            System.out.println("Informe a chave: ");
            String chave = new Scanner(System.in).nextLine();

            System.out.println("Informe o texto a ser encriptado: ");
            String texto = new Scanner(System.in).nextLine();

            final String textoEncriptado = encriptar(chave, texto);

            System.out.println("Texto encriptado: " + textoEncriptado);
        } else if(decisao == 2) {

            System.out.println("Informe a chave: ");
            final String chave = new Scanner(System.in).nextLine();

            System.out.println("Informe o texto a ser decriptado: ");
            final String texto = new Scanner(System.in).nextLine();

            final String textoDecriptado = decriptar(chave, texto);

            System.out.println("Texto decriptado: " + textoDecriptado);
        } else {
            System.out.println("eu nem te dei essa opção...");
        }
    }


    public static String encriptar(final String chaveSimetrica, final String textoPlano) {

        key = new SecretKeySpec(Base64.getDecoder().decode(chaveSimetrica), "AES");

        String textoEncriptado = "";

        try {

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            final byte[] mensagemEncriptada = cipher.doFinal(textoPlano.getBytes());

            textoEncriptado = Base64.getEncoder().encodeToString(mensagemEncriptada);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return textoEncriptado;
    }

    public static String decriptar(final String chaveSimetrica, final String textoEncriptado) {

        key = new SecretKeySpec(Base64.getDecoder().decode(chaveSimetrica), "AES");

        String textoDecriptado = "";

        try {

            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            final byte[] mensagemDescriptada = cipher.doFinal(Base64.getDecoder().decode(textoEncriptado));
            textoDecriptado = new String(mensagemDescriptada);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return textoDecriptado;
    }
}
