# Artifact for OOPSLA 2019 Submission "Program Synthesis with Algebraic Library Specifications"

## Getting Started: Requirements

* [Vagrant](https://www.vagrantup.com/).

## Getting Started: Setup

To use Vagrant to produce a VM, simply clone and navigate into this repo, and call `vagrant up`:
```
git clone https://github.com/bmarwritescode/oopsla-19-artifact.git
cd oopsla-19-artifact
vagrant up              # Build the VM
```

This build should take approximately 30-40 minutes to complete. After this, you can call `vagrant ssh` from within the same directory to access the VM.

## Getting Started: Navigating the VM

The VM includes both JSketch (with JLibSketch add-on) and Sketch.

In the home directory, there is one file and two folders:

* `mysql-apt-config_0.7.2-1_all.deb`: contains setup materials for the VM (ignore this file)
* `sketch/`: houses the Sketch source code (ignore this folder)
* `java-sketch/`: houses JSketch and the JLibSketch add-on

Within `java-sketch/` there are a number of directories and files associated with the tool, however, we will only need the following:

* `artifact_examples/`: contains some JSketch and JLibSketch examples for this evaluation
* `benchmarks/`: contains all benchmark programs from the paper
* `artifact_scripts/`: contains scripts for running the benchmarks and examples
* `jsk.sh`: shell script for running JSketch with JLibSketch
* `result/`: contains output Sketch code produced by JSketch with JLibSketch

The rest of the repo contains the source code of JSketch and JLibSketch, as well as a number of tests of both of these tools.

## Getting Started: Basic Testing

### Running JLibSketch and Interpretting Output

We will first run a simple test to ensure the JSketch built correctly. Run the following from `java-sketch/`:
```
./jsk.sh artifact_examples/ArtifactExample.java --no-lib model/lang/Object.java
```
On running the command, one should see many lines of output, like the following (`...` abreviates all lines except the first and last two lines):
```
12:29:15 [INFO] /home/vagrant/java-sketch/java_sk/main.py:39 => parsing ['artifact_examples/ArtifactExample.java']
...
12:29:19 [DEBUG] java_sk/sketch.py:35 => sketch result/sk_ArtifactExample/main.sk --fe-tempdir result --fe-keep-tmp --fe-output sk_ArtifactExample --fe-inc result/sk_ArtifactExample
12:29:22 [INFO] java_sk/sketch.py:39 => sketch done: result/output/ArtifactExample.txt
```
Note that `XX:XX:XX` will be replaced with the current time when run.

You just ran your first JSketch problem! Let's take a look at what we just ran to learn how to interpret JSketch output. We just ran the following JSketch program (also can be viewed directly from the file in `artifact_examples/ArtifactExample.java`):
```
@rewriteClass
class Stack<E> {
    @alg
    E push(E e);
    @alg
    E pop();

    // pop(push!(s, x)) = x
    rewrite E pop(Stack push!(Stack s, E e)) {
    	return e;
    }
}

class ArtifactExample {
    public harness static void mn(int x) {
	Stack<Object> s = new Stack<Object>();
	Object o1 = new Object();
	Object o2 = new Object();

	# Push either o1 or o2 onto the stack
	Object toPush = {| o1, o2 |};
	s.push(toPush);

	# Assert that the top element on the stack is o2
	Object popped = s.pop();
	assert popped.equals(o2);
    }
}
```

The first class `Stack` is an `@rewriteClass` used to define one axiom for the stack data structure: `pop(push!(s, x)) = x`. This syntax is explained in Section 2 of the paper.

The second class `ArtifactExample` has one method `mn` which gives the synthesis problem and its harness. It first creates a stack `s` and two objects `o1` and `o2`. It then pushes either `o1` or `o2` onto the stack. Finally, it pops the first element of the stack and asserts that the result is `o2`. JLibSketch must successfully determin that `o2` should be the element pushed onto `s`.

The generated Sketch code for this problem is output to `result/sk_ArtifactExample`. In this folder, you will see the following 6 Sketch files:

* `main.sk`: contains Sketch configuration options
* `meta.sk`: contains class IDs (`__cid` explained in Section 4)
* `array.sk`: contains a special Sketch struct for arrays
* `Object.sk`: contains `Object` struct (described in Section 4) as well as the Sketch translation of the `.equals` method from `Object.java`
* `Stack.sk`: contains `Stack` Java class Sketch translation
* `ArtifactExample.sk`: contains `ArtifactExample` Java class Sketch translation

The following contains snippets of the Sketch output in `Stack.sk` to highlight small differences from the paper. To see the full output, view the file `result/sk_ArtifactExample/Stack.sk`.
```
Object Stack_Stack() {
    return new Object(__cid=Stack(), _stack=new Stack_empty());
}

Object push_Object_E(Object self, Object e) {
    return new Object(__cid=Stack(), _stack=new Push_e(self=self._stack, e=e));
}

Object pushb_Object_E(Object self, Object e) {
    self._stack=new Pushb_e(self=self._stack, e=e);
    return self;
}

Object popb_Object(Object self) {
    self._stack=new Popb(self=self._stack);
    return self;
}

Object pop_Object(Object self) {
    return xform_pop_Stack(self._stack);
}

Object xform_pop_Stack(Stack selff) {
  Stack self = selff;
  switch(self) {
  case Stack_empty: { assert false; }
  case Push_e: { assert false; }
  case Pushb_e: {
    return self.e;
  }
  case Popb: { assert false; }
  case Pop: { assert false; }
  }

  return null;
}
```
This Sketch translation is described in Section 4. One difference to note is that instead of the `rewrite_` notation from the paper, we use `xform_` instead. Additionally, Java methods are translated to Sketch methods containing the argument types to handle method overloading, which were elided from the paper examples for readability. Finally, note that Sketch requires a return value from the function outside of the `switch` statement, hence the `return null` for `xform_pop_Stack`.

The following contains a snippet of the Sketch output in `ArtifactExample.sk` to highlight one small difference from the paper. To see the full output, view the file `result/sk_ArtifactExample/ArtifactExample.sk`.
```
assert (popped.__cid == Stack() ? equals_Object@Object(popped, o2) : popped.__cid == ArtifactExample2() ? equals_Object@Object(popped, o2) : popped.__cid == Object() ? equals_Object@Object(popped, o2) : 0);
```
The above snippet contains the Sketch translation of the call to `assert popped.equals(o2);`. Note that this call is translated into a conditional which checks the class ID of the receiver. This is used to encode dynamic dispatch, which was elided from the paper for readability.



- Let's look at the C++ output. The output is written to `result/output/ArtifactExample2.txt`. Open that file as before.
- Similar to `ArtifactExample1.txt` there is some Sketch stuff at the top that can be ignored. Skip to line 8 where the C++ translation of `mn_int` from the `ArtifactExample2.sk` file is given. We can see we create the stack `s` which has been renmaed to `s_s6` (line 15). Similarly, we create `o1` and `o2` as `o1_s10` and `o2_s14` respectively (lines 19 and 23). The temporary variable `toPush` has been optimized away. However, we can see the calls to `push_Object_E` and `pushb_Object_E` both have `o2_s14` as the second argument, indicating Sketch correctly assigned `toPush` to `o2`.

Step By Step Instructions: Program Synthesis with Algebraic Library Specifications
-----------------------------------------------------------------------------------

Benchmarks
-----------

- You can find the benchmarks (described in Table 1) in	`test/axioms/benchmarks`.
- In this directory, each folder contains a different benchmark. For each benchmark, the synthesis problem is contained in the file denoted "NAME_syn.java" while the solution is simply "NAME.java". All shared libraries (that is libraries used with both specs and mocks) are contained in the `shared/` folder. All specs are contained in the `rewrite/` folder, and all mocks are contained in the `mocks/` folder.
- For the cryptography examples, please note the "_mock" and "_rewrite" naming scheme which differentiates the versions used for mocks and rewrites. This was necessary due to annotations, as discussed in Section 4 of the paper.

LOC Comparison (Section 5.1)
-----------------------------

- One can manually compare the algebraic specifications versus the mocks for various libraries by looking in the `rewrite/` and `mock/` folders for the benchmarks. One should see, as described in section 5.1, that both implement the same API.
- To compare the lines of code used for each approach, we have preinstalled the SLOCCount tool. To run the comparison, simply run the `sloccounter.sh` script by running `./sloccounter.sh`. If for some reason it doesn't have execute permissions, give it them by running first `chmod +x sloccounter.sh`.
- The output of this will be stuck in the `sloccounts/` folder. Each benchmark will have its own file. We will pay attemtion to the output in the SLOC-by-Language column about halfway through the output. Here, copare the lines where the directory (second column) equals `mock` and `rewrite`. The number of lines of code for each of these are given in the third column by `java=XXX` where XXX is the number of reported LOC in the paper.

Synthesis Problems (Section 5.2)
---------------------------------

- In Table 2, we see a brief description of the synthesis problems, including the number of calls to the `stmts` and `guards` generators. This information can be verified by visiting the various `*_syn.java` files for each benchmark.

Performance Comparison (Section 5.2)
-------------------------------------

- In Table 2, we also give a performance comparison for mocks versus algebraic specifications.
- The script that runs this performance comparison is given in the file `test/run_jlibsketch_benchmarks.py`. Note that there are two methods per benchmark, one for the mocks and one for the specifications (denoted `_mock` and `_rewrite` respectively). The Sketch arguments for each are passed as arguments to the `run` method, which runs JLibSketch on the specified problem.
- While one could just run this script to verify the results, I would not suggest it. Note that 31 trials of each experiment was run, meaning a total of 551 experiments were run. Given that multiple tests take over an hour (and a few even timeout), this would take a very long time. One could reduce the number of trials, but even with just 1 trial, it would likely take many hours to run the experiments. Also note that inter-quartile-range (IQR) for many of the experiments is quite large, meaning performance for an individual test might vary significantly.
- Note also that we ran the experiments on a fairly powerful machine: 10 cores and 128 GB of RAM, as described in Section 5.2. As a result, while we would not expect the relative performance of mocks versus specifications to change, a significant (though likely proportional) slowing in both approaches may be observed on other machines.
- To run the benchmarks, one can navigate to `java-sketch/` and run the following command: `python -m test.jlibsketch_benchmarks`
- Similar to the examples from the Getting Started Guide, this script will output all of the intermediate sketch files in `/result/sk_BENCHMARK` where `BENCHMARK` is the name of the benchmark being run.
- Additionally, like the Getting Started Guide, the final output from Sketch with the completed synthesis solution can be found in `/result/output/BENCHMARK.txt`.
- In addition to these files, one can also observe the timing information summarized in two files: `rewrite_results.csv` and `mock_results.csv` which give the runtimes for experiments using rewrites as well as mocks. Runtime for an individual run can also be found at the bottom of `result/output/BENCHMARK.txt`.
- As running all of the benchmarks is not feasible, we have set up a similar running script, `test/short_jlibsketch_benchmarks.py`. This runs a few of the shorter benchmarks and finished in under 30 minutes on our machine. Run this script with: `python -m test.short_jlibsketch_benchmarks` from `java-sketch/`. Outputs of this result should be accessed in the same way as outputs from the full benchmark testing script.
