/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkey.keychain;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Josimar Cassandra - 66109
 */
public class KeyEntryTest {

    private KeyEntry entryA, entryEmpty;

    public KeyEntryTest() {
    }

    @Before
    public void setUp() {
        System.out.println("Start test{");
        entryA = new KeyEntry();
        entryA.setApplicationName("appx");
        entryA.setUsername("xx");
        entryA.setPassword("secret@@@");

    }

    @After
    public void tearDown() {
//        entryA = null;
        assertNull(entryA);
        System.out.println("}End test\n");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetApplicationNameWithNull() {
        System.out.println("testSetApplicationNameWithNull");
        entryA.setApplicationName(null);
    }

    @Test
    public void testKey() {
        System.out.println("testKey");
        // the key is the application name
        assertEquals("failed to get existing key field", entryA.getApplicationName(), "appx");
    }

    @Test
    public void testFormatAsCsv() {
        System.out.println("testFormatAsCsv");
        String expects = "appx" + KeyEntry.FIELDS_DELIMITER + "xx" + KeyEntry.FIELDS_DELIMITER + "secret@@@";
        assertEquals("failed to format entry to delimited string", entryA.formatAsCsv(), expects);

    }

    @Test
    public void testToString() {
        System.out.println("testToString");
        StringBuilder builder = new StringBuilder();
//        builder.append(entryA.getApplicationName());
        builder.append("\t");
        builder.append(entryA.getUsername());
        builder.append("\t");
//        builder.append(entryA.getPassword());

        assertEquals("failed to format entry to string", entryA.toString(), builder.toString());

    }

    @Test
    public void testParse() {
        System.out.println("testParse");
        String sample = entryA.getApplicationName() + KeyEntry.FIELDS_DELIMITER
                + entryA.getUsername() + KeyEntry.FIELDS_DELIMITER + entryA.getPassword();

        KeyEntry entryB = KeyEntry.parse(sample);

        assertEquals("failed to parse entry to KeyEntry", entryB.toString(), entryA.toString());
    }

}
