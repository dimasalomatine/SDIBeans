package Utils;
/**
 *
 * @author Dmitry
 */
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

/**
 * This class defines methods for encrypting and decrypting using the Triple DES
 * algorithm and for generating, reading and writing Triple DES keys. It also
 * defines a main() method that allows these methods to be used from the command
 * line.
 */
public class sdi3DES {
    public sdi3DES()
    {
     System.out.println("Init Cipher....");
    }
    public static void main(String[] args) 
    {
        //testgenkey(args);
        //testencode(args);
        //testdecode(args);
        //testdecodef2l(args);
    }
     public static void testgenkey(String[] args)
    {
         args=new String[2];
        args[0]="-g";
        args[1]="c:\\key1.key";
        doit(args);
    }
    public static void testencode(String[] args)
    {
         args=new String[4];
        args[0]="-e";
        args[1]="c:\\key1.key";
        args[2]="c:\\plain3.txt";
        args[3]="c:\\plainenc3.txt";
        doit(args);
    }
    public static void testdecode(String[] args)
    {
         args=new String[4];
        args[0]="-d";
        args[1]="c:\\key1.key";
        args[3]="c:\\plaindec3.txt";
        args[2]="c:\\plainenc3.txt";
        java.util.List tlist=doit(args);
    }
    public static void testdecodef2l(String[] args)
    {
         args=new String[3];
        args[0]="-dl";
        args[1]="c:\\key1.key";
        args[2]="c:\\plainenc3.txt";
        java.util.List tlist=doit(args);
        for(int i=0;i<tlist.size();i++)System.out.println((String)tlist.get(i));
    }
  /**
   * The program. The first argument must be -e, -d, or -g to encrypt,
   * decrypt, or generate a key. The second argument is the name of a file
   * from which the key is read or to which it is written for -g. The -e and
   * -d arguments cause the program to read from standard input and encrypt or
   * decrypt to standard output.
   */
  public static java.util.List  doit(String[] args) {
    try {
      // Check to see whether there is a provider that can do TripleDES
      // encryption. If not, explicitly install the SunJCE provider.
      try {
        Cipher c = Cipher.getInstance("DESede");
      } catch (Exception e) {
        // An exception here probably means the JCE provider hasn't
        // been permanently installed on this system by listing it
        // in the $JAVA_HOME/jre/lib/security/java.security file.
        // Therefore, we have to install the JCE provider explicitly.
        System.err.println("Installing SunJCE provider.");
        Provider sunjce = new com.sun.crypto.provider.SunJCE();
        Security.addProvider(sunjce);
      }

      // This is where we'll read the key from or write it to
      File keyfile = new File(args[1]);

      // Now check the first arg to see what we're going to do
      if (args[0].equals("-g")) { // Generate a key
        System.out.print("Generating key. This may take some time...");
        System.out.flush();
        SecretKey key = generateKey();
        writeKey(key, keyfile);
        System.out.println("done.");
        System.out.println("Secret key written to " + args[1]
            + ". Protect that file carefully!");
      } else if (args[0].equals("-estd")) { // Encrypt stdin to stdout
        SecretKey key = readKey(keyfile);
        encrypt(key, System.in, System.out);
      } else if (args[0].equals("-dstd")) { // Decrypt stdin to stdout
        SecretKey key = readKey(keyfile);
        decrypt(key, System.in, System.out);
      }else if (args[0].equals("-e")) { // Encrypt filein to fileout
        SecretKey key = readKey(keyfile);
        File fin = new File(args[2]);
        File fout = new File(args[3]);
        FileOutputStream out = new FileOutputStream(fout);
        DataInputStream in = new DataInputStream(new FileInputStream(fin));
        encrypt(key, in, out);
      } else if (args[0].equals("-d")) { // Decrypt filein to fileout
        SecretKey key = readKey(keyfile);
        File fin = new File(args[2]);
        File fout = new File(args[3]);
        FileOutputStream out = new FileOutputStream(fout);
        DataInputStream in = new DataInputStream(new FileInputStream(fin));
        decrypt(key, in, out);
      }
      else if (args[0].equals("-dl")) { // Decrypt filein to list
        SecretKey key = readKey(keyfile);
        ReadFromFileURL fr=new ReadFromFileURL("file:"+args[2]);
        DataInputStream in = new DataInputStream(fr.is);
        String ts=decryptintostring(key, in);
        java.util.List tlist=fr.read(ts);
        return tlist;
      }
    } catch (Exception e) {
      System.err.println(e);
      System.err.println("Usage: java " + sdi3DES.class.getName()
          + " -d|-dl|-dstd|-e|-estd|-g <keyfile> filein fileout");
    }
    return null;
  }

  /** Generate a secret TripleDES encryption/decryption key */
  public static SecretKey generateKey() throws NoSuchAlgorithmException {
    // Get a key generator for Triple DES (a.k.a DESede)
    KeyGenerator keygen = KeyGenerator.getInstance("DESede");
    // Use it to generate a key
    return keygen.generateKey();
  }

  /** Save the specified TripleDES SecretKey to the specified file */
  public static void writeKey(SecretKey key, File f) throws IOException,
      NoSuchAlgorithmException, InvalidKeySpecException {
    // Convert the secret key to an array of bytes like this
    SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
    DESedeKeySpec keyspec = (DESedeKeySpec) keyfactory.getKeySpec(key,
        DESedeKeySpec.class);
    byte[] rawkey = keyspec.getKey();

    // Write the raw key to the file
    FileOutputStream out = new FileOutputStream(f);
    out.write(rawkey);
    out.close();
  }

  /** Read a TripleDES secret key from the specified file */
  public static SecretKey readKey(File f) throws IOException,
      NoSuchAlgorithmException, InvalidKeyException,
      InvalidKeySpecException {
    // Read the raw bytes from the keyfile
    DataInputStream in = new DataInputStream(new FileInputStream(f));
    byte[] rawkey = new byte[(int) f.length()];
    in.readFully(rawkey);
    in.close();

    // Convert the raw bytes to a secret key like this
    DESedeKeySpec keyspec = new DESedeKeySpec(rawkey);
    SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
    SecretKey key = keyfactory.generateSecret(keyspec);
    return key;
  }

  /**
   * Use the specified TripleDES key to encrypt bytes from the input stream
   * and write them to the output stream. This method uses CipherOutputStream
   * to perform the encryption and write bytes at the same time.
   */
  public static void encrypt(SecretKey key, InputStream in, OutputStream out)
      throws NoSuchAlgorithmException, InvalidKeyException,
      NoSuchPaddingException, IOException {
    // Create and initialize the encryption engine
    Cipher cipher = Cipher.getInstance("DESede");
    cipher.init(Cipher.ENCRYPT_MODE, key);

    // Create a special output stream to do the work for us
    CipherOutputStream cos = new CipherOutputStream(out, cipher);

    // Read from the input and write to the encrypting output stream
    byte[] buffer = new byte[2048];
    int bytesRead;
    while ((bytesRead = in.read(buffer)) != -1) {
      cos.write(buffer, 0, bytesRead);
    }
    cos.close();

    // For extra security, don't leave any plaintext hanging around memory.
    java.util.Arrays.fill(buffer, (byte) 0);
  }

  /**
   * Use the specified TripleDES key to decrypt bytes ready from the input
   * stream and write them to the output stream. This method uses uses Cipher
   * directly to show how it can be done without CipherInputStream and
   * CipherOutputStream.
   */
  public static void decrypt(SecretKey key, InputStream in, OutputStream out)
      throws NoSuchAlgorithmException, InvalidKeyException, IOException,
      IllegalBlockSizeException, NoSuchPaddingException,
      BadPaddingException {
    // Create and initialize the decryption engine
    Cipher cipher = Cipher.getInstance("DESede");
    cipher.init(Cipher.DECRYPT_MODE, key);

    // Read bytes, decrypt, and write them out.
    byte[] buffer = new byte[2048];
    int bytesRead;
    while ((bytesRead = in.read(buffer)) != -1) {
      out.write(cipher.update(buffer, 0, bytesRead));
    }

    // Write out the final bunch of decrypted bytes
    out.write(cipher.doFinal());
    out.flush();
  }

    private static String decryptintostring(SecretKey key, DataInputStream in) throws NoSuchAlgorithmException, InvalidKeyException, IOException,
      IllegalBlockSizeException, NoSuchPaddingException,
      BadPaddingException {
    // Create and initialize the decryption engine
    Cipher cipher = Cipher.getInstance("DESede");
    cipher.init(Cipher.DECRYPT_MODE, key);
  String ret="";
    // Read bytes, decrypt, and write them out.
    byte[] buffer = new byte[2048];
    int bytesRead;
    
    while ((bytesRead = in.read(buffer)) != -1) {
      ret=ret+new String(cipher.update(buffer, 0, bytesRead));
     // System.out.println(ret);
    }
     return ret;
  }
}


