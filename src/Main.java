import java.util.Scanner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Main {

    private static String chaveSimetrica;
    private static String mensagem;
    private static SecretKey key;
    private static byte[] mensagemEncriptada;
    private static byte[] mensagemDescriptada;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) {
/**
 * Solicita ao usuário que informe uma chave com caracteres:
 * (256 / 8 = 32) 32 caracteres = 256 bits
 * (192 / 8 = 192) 24 caracteres = 192 bits
 * (128 / 8 = 128) 16 caracteres = 128 bits
 */
        System.out.println("32 caracteres = chave com 256 bits"+ "\n24 caracteres = chave com 192 bits"+ "16 caracteres = chave com 128 bits"+ "\n Infomre uma Chave: ");
        chaveSimetrica = sc.nextLine();
        key = new SecretKeySpec(chaveSimetrica.getBytes(), "AES");

        try {
            Cipher cipher = Cipher.getInstance("AES");

            cipher.init(Cipher.ENCRYPT_MODE, key);
            /* Solicita ao usuŕio que informe sua mensagem a ser encriptada */
            System.out.println("Informe sua mensagem a ser encriptada: ");
            mensagem = sc.nextLine();
            /* Encripta a Mensagem */
            mensagemEncriptada = cipher.doFinal(mensagem.getBytes());
            /* Exibe Mensagem Encriptada */
            System.out.println(new String("Mensagem Encriptada: " + mensagemEncriptada));
            /* Informa ao objeto a ação de desencriptar */
            cipher.init(Cipher.DECRYPT_MODE, key);
            /* Recebe a mensagem encriptada e descripta */
            mensagemDescriptada = cipher.doFinal(mensagemEncriptada);
            /**
             * Converte para a base 64 e amazena a mensagem em uma variavel
             * auxiliar
             */
            String mensagemOriginal = new String(mensagemDescriptada);

            /* Exibe Mensagem Descriptada */
            System.out.println("Mensagem Descriptada: " + mensagemOriginal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
