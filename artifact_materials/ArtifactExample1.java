class ArtifactExample1 {

    public harness static void mn(int x) {
	Foo f = new Foo(x);

	assert f.mulBy2() == x + x;
    }
}

class Foo {
    int x;
    public Foo(int x) { this.x = x; }
    public int mulBy2() { return this.x*??; }
}

