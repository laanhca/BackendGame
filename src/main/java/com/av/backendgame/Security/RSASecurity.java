package com.av.backendgame.Security;

import com.av.backendgame.BackendGameApplication;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class RSASecurity {
    private static RSASecurity instance;
    public RSASecurity() {
//        SecurityKeyPairGenerator();
    }

    public static RSASecurity getInstance(){
        return instance = instance == null? new RSASecurity(): instance;
    }
    /////////////////////////////////////////
    public static final String RESOURCESPATH = "/home/server/security";
        public void SecurityKeyPairGenerator() {
        File pubKey = new File(RESOURCESPATH +"/publicKey.rsa");
        File priKey = new File(RESOURCESPATH+ "/privateKey.rsa");
        if(pubKey.exists() || priKey.exists())return;

        try {


            SecureRandom sr = new SecureRandom();

            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048, sr);

            KeyPair kp = kpg.genKeyPair();
            // PublicKey
            PublicKey publicKey = kp.getPublic();
            // PrivateKey
            PrivateKey privateKey = kp.getPrivate();

            File publicKeyFile = createKeyFile(new File(RESOURCESPATH +"/publicKey.rsa"));
            File privateKeyFile = createKeyFile(new File(RESOURCESPATH+ "/privateKey.rsa"));

            // save Public Key
            FileOutputStream fos = new FileOutputStream(publicKeyFile);
            fos.write(publicKey.getEncoded());
            fos.close();

            // save Private Key
            fos = new FileOutputStream(privateKeyFile);
            fos.write(privateKey.getEncoded());
            fos.close();

            System.out.println("Generate key successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static File createKeyFile(File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("create new key");
        }
        return file;
    }


    public String Encryption(Map<String, Object> data) {
        try {
            FileInputStream fis = new FileInputStream(RESOURCESPATH +"/publicKey.rsa");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();

            X509EncodedKeySpec spec = new X509EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = factory.generatePublic(spec);

            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.ENCRYPT_MODE, pubKey);
            String mss = data.get("UserId").toString()+"@"+data.get("TimeOut");
            byte encryptOut[] = c.doFinal(mss.getBytes());
            String strEncrypt = Base64.getEncoder().encodeToString(encryptOut);
            return strEncrypt;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    public Map<String,String> Decryption(String tokenEncrypt) {
        try {
            FileInputStream fis = new FileInputStream(RESOURCESPATH+ "/privateKey.rsa");
            byte[] b = new byte[fis.available()];
            fis.read(b);
            fis.close();

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(b);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey priKey = factory.generatePrivate(spec);

            Cipher c = Cipher.getInstance("RSA");
            c.init(Cipher.DECRYPT_MODE, priKey);
            byte decryptOut[] = c.doFinal(Base64.getDecoder().decode(tokenEncrypt));
            String mss=  new String(decryptOut);
            String[] mssSplit = mss.split("@");
            Map<String,String> result = new HashMap<String,String>();
            result.put("UserId", mssSplit[0]);
            result.put("TimeOut", mssSplit[1]);
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
