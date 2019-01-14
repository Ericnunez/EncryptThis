import javax.crypto.Cipher;
import java.security.*;
import javax.crypto.*;
import java.io.*;
import java.util.Scanner;
import javax.crypto.spec.SecretKeySpec;

public class EncryptThis
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("EncryptThis:");
        System.out.println("A tool to encrypt and decrypt files");
        System.out.println();
        int choice;
        String fileName;
        String keyFile;
        
        do{
        System.out.println("Press 1 to encrypt");
        System.out.println("Press 2 to decrypt");
        choice = input.nextInt();
        input.nextLine();
        }while(choice < 1 || choice > 2);
        
        if(choice == 1)
        {
            System.out.println("Enter the filename to encrypt (use extension)");
            fileName = input.nextLine();
            encrypt(fileName);
        }
        else if(choice == 2)
        {
            System.out.println("Enter the filename to decrypt (use extension)");
            fileName = input.nextLine();
            System.out.println("Enter the key filename needed to decrypt (use extension)");
            keyFile = input.nextLine();
            decrypt(fileName, keyFile);
        }
    }
    
    public static void encrypt(String fileName)
    {
        try
        {
            Cipher newCipher = Cipher.getInstance("AES");
            
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey key = keyGen.generateKey();
            
            FileOutputStream keyOutput = new FileOutputStream("key.txt");
            keyOutput.write(key.getEncoded());
            
            newCipher.init(Cipher.ENCRYPT_MODE, key );
            
            File file = new File(fileName);
            
            FileInputStream inputStream = new FileInputStream(file);
            
            byte[] dataBytes = new byte[(int)file.length()];
            int db = inputStream.read(dataBytes);
            
            byte[] encryptedBytes = newCipher.doFinal(dataBytes);
            
            FileOutputStream output = new FileOutputStream("encrypted_" + fileName);
            output.write(encryptedBytes);
            System.out.println("Encryption successful - check folder");
            
            keyOutput.close();
            inputStream.close();
            output.close();
            
        }
        catch(NoSuchAlgorithmException e)
        {
            System.out.println("There was a problem with the algorithm");
        }
        catch(javax.crypto.NoSuchPaddingException e)
        {
            System.out.println("There is no such padding!");
        }
        catch(InvalidKeyException e)
        {
            System.out.println("There is a problem with the key");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File was not found!");
        }
        catch(GeneralSecurityException e)
        {
            System.out.println("There is a problem with the encryption");
        }
        catch (IOException e) 
        {
			System.out.println("Error reading file.");
		} 
    }
    
    public static void decrypt(String fileName, String keyFileName)
    {
        File encryptedFile = new File(fileName);
        File keyFile = new File(keyFileName);
        
        try
        {
            FileInputStream input = new FileInputStream(encryptedFile);
            FileInputStream keyInput = new FileInputStream(keyFile);
            FileOutputStream output = new FileOutputStream("decrypted_" + fileName);
            
            byte[] dataBytes = new byte[(int)encryptedFile.length()];
            int db = input.read(dataBytes);
            
            byte[] keyBytes = new byte[(int)keyFile.length()];
            int keyb = keyInput.read(keyBytes);
            
            SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
            
            Cipher newCipher = Cipher.getInstance("AES");  
            newCipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decryptedBytes = newCipher.doFinal(dataBytes);
            
            output.write(decryptedBytes);
            System.out.println("Decryption successful - check folder");
            
            input.close();
			keyInput.close();
			output.close();
            
        }
        catch(NoSuchAlgorithmException e)
        {
            System.out.println("There was a problem with the algorithm");
        }
        catch(javax.crypto.NoSuchPaddingException e)
        {
            System.out.println("There is no such padding!");
        }
        catch(InvalidKeyException e)
        {
            System.out.println("There is a problem with the key");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File was not found!");
        }
        catch(GeneralSecurityException e)
        {
            System.out.println("There is a problem with the encryption");
        }
        catch (IOException e) 
        {
			System.out.println("Error reading file.");
		} 
    }
}
