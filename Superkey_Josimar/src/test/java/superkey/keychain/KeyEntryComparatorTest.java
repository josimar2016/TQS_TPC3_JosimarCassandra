/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package superkey.keychain;

import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Josimar Cassandra - 66109
 */
public class KeyEntryComparatorTest {

    private KeyEntryComparator comparator = new KeyEntryComparator();
    private KeyEntry entryA, entryB;

    public KeyEntryComparatorTest() {
    }

    @Before
    public void setUp() {
        System.out.println("Start test{");
        entryA = new KeyEntry();
        entryA.setApplicationName("appx");
        entryA.setUsername("xx");
        entryB = new KeyEntry();
        entryB.setApplicationName("appy");
        entryB.setUsername("yy");

    }

    @After
    public void tearDown() {
        entryA = null;
        assertNull(entryA);
        System.out.println("}End test\n");
    }

    @Test
    public void testCompare() {
        System.out.println("testCompare");
        assertEquals("Comparing equal objects fails", 0, comparator.compare(entryA, entryA));
        assertEquals("Comparing equal objects fails", 0, comparator.compare(entryB, entryB));

        assertThat("faile to identify less than case", comparator.compare(entryA, entryB), Matchers.lessThan(0));
        assertThat("faile to identify greater than case", comparator.compare(entryB, entryA), Matchers.greaterThan(0));

    }

}
