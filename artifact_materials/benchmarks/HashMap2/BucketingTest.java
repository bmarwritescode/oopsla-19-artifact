// This code is from https://github.com/anthonynsimon/java-ds-algorithms

import org.junit.Assert;

public class BucketingTest {
    private Bucketing<Object, Object> classUnderTest;

    harness void mn(int x, int y, int z) {      
    	assume x != y && x != z && y != z;
	assume x > -3 && x < 3;
	assume y > -3 && y < 3;
	assume z > -3 && z < 3;	

	Integer xx = new Integer(x);
	Integer yy = new Integer(y);
	Integer zz = new Integer(z);

	setUp();
	testGetEmpty();
	testPutAndGet(xx, yy, zz);
    }
    
    public void setUp() {
        classUnderTest = new Bucketing<>();
    }

    public void testGetEmpty() {
        classUnderTest.clear();
        Assert.assertNull(classUnderTest.get(null));
    }

    public void testPutAndGet(Integer x, Integer y, Integer z) {
        classUnderTest.clear();
	
        classUnderTest.put(x, y);
        classUnderTest.put(y, x);
        classUnderTest.put(z, x);
        Assert.assertEquals(classUnderTest.get(x), y);
        Assert.assertEquals(classUnderTest.get(y), x);
        Assert.assertEquals(classUnderTest.get(z), x);
    }
}
