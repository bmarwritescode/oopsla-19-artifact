package java.lang;

public class Character {
    char value;

    public Character(char value) {
	this.value = value;
    }

    public char charValue() {
	return value;
    }

    public boolean equals(Object obj) {
    	if (obj instanceof Character) {
    	    return value == ((Character)obj).charValue();
    	}
    	return false;
    }
}

