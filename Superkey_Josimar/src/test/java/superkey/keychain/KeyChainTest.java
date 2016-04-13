/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkey.keychain;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Josimar Cassandra - 66109
 */
public class KeyChainTest {

    private KeyEntry entryA, entryB;
    private final String KEYCHAIN_FILE = "Keychain.txt";
    private final String KEYCHAIN_MASTER_KEY = "#wisper"; // "#wisper";
    private final File userKeychainFile = new File(KEYCHAIN_FILE);
    private KeyChain originalkeyChain;

    public KeyChainTest() {
    }

    @Before
    public void setUp() {
        System.out.println("Start test{");
        entryA = new KeyEntry();
        entryA.setApplicationName("appx");
        //entryA.setUsername("xx");
        //entryA.setPassword("secret@@@");

        try {
            originalkeyChain = KeyChain.from(userKeychainFile, new CipherTool(KEYCHAIN_MASTER_KEY));
        } catch (IOException ex) {
            Logger.getLogger(KeyChainTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @After
    public void tearDown() {
        //revert file to original state
       	originalkeyChain.save();
        System.out.println("}End test\n");
    }

    /**
     * Test of put method, of class KeyChain.
     */
    @Test
    public void testValidMasterKey() {
        System.out.println("testValidMasterKey");
        try {
            KeyChain.from(userKeychainFile, new CipherTool(KEYCHAIN_MASTER_KEY));
        } catch (IOException ex) {
            fail("cannot create keyChain!");
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testDuplicatePut() {
        System.out.println("testDuplicatePut");
        KeyChain keyChain = null;
        try {
            keyChain = KeyChain.from(userKeychainFile, new CipherTool(KEYCHAIN_MASTER_KEY));
        } catch (IOException ex) {
            fail("cannot create keyChain!");
        }
        keyChain.put(entryA);
        //keyChain.put(entryA);

    }

    @Test
    public void testPut() {
        System.out.println("testPut");
        KeyChain keyChain = null;
        try {
            keyChain = KeyChain.from(userKeychainFile, new CipherTool(KEYCHAIN_MASTER_KEY));
        } catch (IOException ex) {
            fail("cannot create keyChain.");
        }
        //keyChain.put(entryA);

        assertEquals("failed to put keyEntry on keyChain", entryA.key(), keyChain.find(entryA.key()).key());

    }

    /**
     * Test of find method, of class KeyChain.
     *
     * @throws java.security.InvalidKeyException
     */
    @Test(expected = InvalidKeyException.class)
    public void testInvalidMasterKey() throws InvalidKeyException {
        System.out.println("testInvalidMasterKey");
        try {
            KeyChain.from(userKeychainFile, new CipherTool("dummy"));
        } catch (IOException ex) {
            throw new InvalidKeyException(ex.toString());

        }

    }

    /**
     *
     */
    @Test
    public void testFind() {
        System.out.println("testFind");
        KeyChain keyChain = null;
        try {
            keyChain = KeyChain.from(userKeychainFile, new CipherTool(KEYCHAIN_MASTER_KEY));
        } catch (IOException ex) {
            fail("cannot create keyChain.");
        }

        keyChain.put(entryA);

        entryB = new KeyEntry();
        entryB.setApplicationName("appx2");
        //entryB.setUsername("xx2");
        //entryB.setPassword("secret@@@2");

        assertEquals("failed to find keyEntry on keyChain", entryA, keyChain.find(entryA.key()));
        assertNotEquals("keyEntry find equals null", entryB, keyChain.find(entryB.key()));
    }

    /**
     * Test of save method, of class KeyChain.
     */
    @Test
    public void testSave() {
        System.out.println("testSave");
        KeyChain keyChain = null;
        try {
            keyChain = KeyChain.from(userKeychainFile, new CipherTool(KEYCHAIN_MASTER_KEY));
        } catch (IOException ex) {
            fail("cannot create keyChain.");
        }
        keyChain.put(entryA);

        //keyChain.save();

        KeyChain keyChain2 = null;
        try {
            keyChain2 = KeyChain.from(userKeychainFile, new CipherTool(KEYCHAIN_MASTER_KEY));
        } catch (IOException ex) {
            fail("cannot create keyChain.");
        }

        assertEquals("failed to put keyEntry on keyChain", entryA.key(), keyChain2.find(entryA.key()).key());

    }

    @Test
    public void testAllEntries() {
        System.out.println("testAllEntries");
        KeyChain keyChain = null;
        try {
            keyChain = KeyChain.from(userKeychainFile, new CipherTool(KEYCHAIN_MASTER_KEY));
        } catch (IOException ex) {
            fail("cannot create keyChain.");
        }
        keyChain.put(entryA);

        entryB = new KeyEntry();
        entryB.setApplicationName("next");
        //entryB.setUsername("xxx");
        //entryB.setPassword("secret@@@xx");

        keyChain.put(entryB);
        keyChain.save();

        KeyChain keyChain2 = null;
        try {
            keyChain2 = KeyChain.from(userKeychainFile, new CipherTool(KEYCHAIN_MASTER_KEY));
        } catch (IOException ex) {
            fail("cannot create keyChain.");
        }
        int found = 0;
        for (KeyEntry entry : keyChain2.allEntries()) {
            if (entry.key().equals(entryA.key()) || entry.key().equals(entryB.key())) {
                found++;
            }
        }

        assertEquals("Cannot find entry's inserted", 2, found);

    }

}
