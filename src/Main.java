import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws Exception {
        String inputFilePath = "emisor.txt";
        String outputFilePath = "destinatario.txt";

        //Lectura del archivo a cifrar en bytes
        byte[] fileBytes = Files.readAllBytes(new File(inputFilePath).toPath());

        //Generamos las claves
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        //Le damos los valores a 2 variables
        PrivateKey emisorPrivateKey = keyPair.getPrivate();
        PublicKey destinatarioPublicKey = keyPair.getPublic();

        Cipher cipher = Cipher.getInstance("RSA");

        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, emisorPrivateKey);

        int tamanoBloque = (((RSAPublicKey)destinatarioPublicKey).getModulus().bitLength() + 7) / 8 - 11;

        // Inicializar buffer de salida
        ByteArrayOutputStream bufferSalida = new ByteArrayOutputStream();

        // Cifrar el contenido en bloques
        int offset = 0;
        while (offset < fileBytes.length) {
            int tamanoBloqueActual = Math.min(tamanoBloque, fileBytes.length - offset);
            byte[] bloqueCifrado = cipher.doFinal(fileBytes, offset, tamanoBloqueActual);
            bufferSalida.write(bloqueCifrado);
            offset += tamanoBloqueActual;
        }

        byte[] encryptedBytes = bufferSalida.toByteArray();
        System.out.println(Base64.getEncoder().encodeToString(encryptedBytes));
    }
}