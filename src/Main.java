import javax.crypto.Cipher;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
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

        ByteArrayOutputStream encryptedOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[100];
        int readBytes = 0;

        ByteArrayInputStream inputBytesStream = new ByteArrayInputStream(fileBytes);

        while ((readBytes = inputBytesStream.read(buffer)) != -1) {
            byte[] encryptedBytes = cipher.doFinal(buffer, 0, readBytes);
            encryptedOutputStream.write(encryptedBytes);
        }

        byte[] encryptedBytes = encryptedOutputStream.toByteArray();

        // Cifrar los datos cifrados previamente con la clave privada del emisor
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, emisorPrivateKey);
        encryptedBytes = cipher.doFinal(encryptedBytes);
    }
}