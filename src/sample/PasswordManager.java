package sample;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.util.Base64;


   public class PasswordManager
    {

        private static  SecretKey createAndSaveSecretKey() throws Exception
        {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            SecretKey secretKey =  generator.generateKey();

            String strKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());
            FileWriter writer  =  new FileWriter("src\\sample\\SecretKey.txt" , false);

            writer.write(strKey);
            writer.close();

            return secretKey;
        }

        private static  SecretKey getSecretKey()
        {
            try {
                FileReader reader = new FileReader("src\\sample\\SecretKey.txt");

                String strSecretKey = "";
                int letter;
                while( (letter = reader.read()) != -1)
                {
                    strSecretKey += (char)letter;

                }
                reader.close();

                byte secretBytes[] = Base64.getDecoder().decode(strSecretKey);

                SecretKey secretKey = new SecretKeySpec(secretBytes , "AES");
                return secretKey;
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }


        }

        private static String getUserName()
        {
            try {
                BufferedReader reader = new BufferedReader(new FileReader("src\\sample\\UserName.txt"));

                String userName = reader.readLine();
                return userName;

            }
            catch(Exception e)
            {
                System.out.println("UserName.txt file not found");
                e.printStackTrace();
                return null;
            }


        }

        private static void saveUserName(String userName) throws IOException
        {

            FileWriter writer = new FileWriter("src\\sample\\UserName.txt" , false);

            writer.write(userName);
            writer.flush();
            writer.close();

        }

        private  static String getEncryptedPassword() throws IOException
        {
            BufferedReader reader = new BufferedReader(new FileReader("src\\sample\\Password.txt"));

            return reader.readLine();

        }

        private static String encryptPassword(String password) throws Exception
        {

            SecretKey secretKey  = getSecretKey();

            if(secretKey == null)
            {
                System.out.println("Someone has deleted the secret key");
                return null;
            }

            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE , secretKey);
            byte [] encrptdPassword = aesCipher.doFinal(password.getBytes());

            return new String(encrptdPassword);

        }

        private static void  encryptAndSavePassword(String password) throws Exception
        {
            SecretKey secretKey = createAndSaveSecretKey();

            Cipher aesCipher = Cipher.getInstance("AES");
            aesCipher.init(Cipher.ENCRYPT_MODE , secretKey);
            byte [] encrtdPassword = aesCipher.doFinal(password.getBytes());

            FileWriter writer = new FileWriter(new File("src\\sample\\Password.txt") , false);
            writer.write(new String(encrtdPassword));
            writer.flush();
            writer.close();

        }

        public static  boolean matches(String username , String password) throws Exception
        {
            if( getUserName().equals(username) && getEncryptedPassword().equals(encryptPassword(password)))
                return true;
            return false;

        }

        public  static void updatePassword(String userName , String password) throws Exception
        {
            saveUserName(userName);
            encryptAndSavePassword(password);

        }



    }
