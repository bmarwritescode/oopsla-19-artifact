// This code is from https://github.com/anthonynsimon/java-ds-algorithms

import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

import org.junit.Assert;


public class HashTableTest {
    public static final int INITIAL_SIZE = 4;

    private HashTable<Object, Object> classUnderTest;

    harness void mn(int x, int y, int z) {
    	assume x != y && x != z && y != z;
    	assume x > 0 && x < 10;
    	assume y > 0 && y < 10;
    	assume z > 0 && z < 10;	

	Integer xx = new Integer(x);
	Integer yy = new Integer(y);
	Integer zz = new Integer(z);
	setUp();
	testGetEmpty();
	testPutAndGet(xx, yy, zz);
    }

    public Integer[] makeInts(Integer i1, Integer i2, Integer i3) {
    	Integer[] i = {new Integer(i1.intValue()), new Integer(i2.intValue()), new Integer(i3.intValue())};
    	return i;
    }

    public void setUp() {
        classUnderTest = new HashTable<>(INITIAL_SIZE);
    }
    
    public void testGetEmpty() {
        classUnderTest.clear();
        Assert.assertNull(classUnderTest.get(null));
    }

    public void testPutAndGet(Integer x, Integer y, Integer z) {
	Integer[3] is = makeInts(x, y, z);
        classUnderTest.clear();

        classUnderTest.put(x, y);
	classUnderTest.put(y, x);
        classUnderTest.put(z, x);
        Assert.assertEquals(classUnderTest.get(x), y);
        Assert.assertEquals(classUnderTest.get(y), x);
        Assert.assertEquals(classUnderTest.get(z), x);
    }
}
