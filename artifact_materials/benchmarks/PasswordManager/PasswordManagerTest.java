public class PasswordManagerTest {

    harness public static void main(int u, int w, int x, int y, int z) {
    	int limit = 100;
    	assume u > 0 & u < limit;
    	assume w > 0 & w < limit;
    	assume x > 0 & x < limit;
    	assume y > 0 & y < limit;
    	assume z > 0 & z < limit;	
    	assume u != w && w != y && x != z;
	
    	String p1 = Integer.toString(u);
    	String p2 = Integer.toString(w);
    	String p3 = Integer.toString(x);
    	String p4 = Integer.toString(y);
    	String p5 = Integer.toString(z);

    	PasswordManager pasman = new PasswordManager(p1);

    	pasman.addPassword(p2, p3);
    	pasman.addPassword(p4, p5);
    	String pass1 = pasman.getPass(p2);	
    	assert pass1.equals(p3);	
    	String pass2 = pasman.getPass(p4);	
    	assert pass2.equals(p5);
    	pasman.modifyPassword(p2, p4);
    	String pass3 = pasman.getPass(p2);	
    	assert pass3.equals(p4);	
    }
    
}
