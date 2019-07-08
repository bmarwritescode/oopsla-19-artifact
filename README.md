# Artifact for OOPSLA 2019 Submission "Program Synthesis with Algebraic Library Specifications"

## Getting Started: Requirements

* [VirtualBox](https://www.virtualbox.org/).
* [Vagrant](https://www.vagrantup.com/).

## Getting Started: Setup

To use Vagrant to produce a VM, first clone and navigate into this repo.:
```
git clone https://github.com/bmarwritescode/oopsla-19-artifact.git
cd oopsla-19-artifact
```

Now, fetch the tar file for [Sketch](https://drive.google.com/file/d/1Cq3M0jFFZ-dAWtNfb_OAl-BN_iI5VB69/view?usp=sharing) and copy it into the `oopsla-19-artifact` folder. Make sure the file is named `sketch.tar.gz`.

Now, simply call the following to build the machine
```
vagrant up
```

This build should take approximately 30-40 minutes to complete. After this, you can call `vagrant ssh` from within the same directory to access the VM.

**NOTE**: By default, we build the VM with 7 GB of RAM. This may be a lot for some computers, but without this much, many of the benchmarks fail. If you need to change this, edit `Vagrantfile` changing the following section with the desired memory in KB prior to running `vagrant up`. If you have already run `vagrant up`, you can exit the VM, make the change, run `vagrant halt`, followed by `vagrant up` and the memory change should take place.
```
  config.vm.provider "virtualbox" do |vb|
    # Customize the amount of memory on the VM:
    # vb.memory = "2048"
    # vb.memory = "4096"
    vb.memory = "7168"
  end
```

## Getting Started: Navigating the VM

The VM includes both JSketch (with JLibSketch add-on) and Sketch.

In the home directory, there aret two files and two folders:

* `mysql-apt-config_0.7.2-1_all.deb`: contains setup materials for the VM (ignore this file)
* `sketch.tar.gz`: tar file for Sketch source code (ignore this file)
* `sketch/`: houses the Sketch source code (ignore this folder)
* `java-sketch/`: houses JSketch and the JLibSketch add-on

Within `java-sketch/` there are a number of directories and files associated with the tool, however, we will only focus on the following:

* `artifact_examples/`: contains some JSketch and JLibSketch examples for this evaluation
* `artifact_results/`: folder for results of running artifact
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

	// Push either o1 or o2 onto the stack
	Object toPush = {| o1, o2 |};
	s.push(toPush);

	// Assert that the top element on the stack is o2
	Object popped = s.pop();
	assert popped.equals(o2);
    }
}
```

The first class `Stack` is an `@rewriteClass` used to define one axiom for the stack data structure: `pop(push!(s, x)) = x`. This syntax is explained in Section 2 of the paper.

The second class `ArtifactExample` has one method `mn` which gives the synthesis problem and its harness. It first creates a stack `s` and two objects `o1` and `o2`. It then pushes either `o1` or `o2` onto the stack. Finally, it pops the first element off the stack and asserts that the result is `o2`. JLibSketch must successfully determine that `o2` should be the element pushed onto `s`.

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

Below, we give Sketch's C++ output for the `mn` function from `ArtifactExample.java`. To view the full Sketch output, look at `result/output/ArtifactExample.txt`.
```
void mn_int (int x)/*Artifac..ample2.sk:7*/
  {
    int self_s2 = 0;
    Object@meta(self_s2);
    Object@Object self_s4 = null;
    Object_Object@Object(new Object@Object(__cid=self_s2), self_s4)//{};
    Object@Object s_s6 = null;
    Stack_Stack@Stack(s_s6);
    int o1_s8 = 0;
    Object@meta(o1_s8);
    Object@Object o1_s10 = null;
    Object_Object@Object(new Object@Object(__cid=o1_s8), o1_s10)//{};
    int o2_s12 = 0;
    Object@meta(o2_s12);
    Object@Object o2_s14 = null;
    Object_Object@Object(new Object@Object(__cid=o2_s12), o2_s14)//{};
    int _out_s16 = 0;
    Stack@meta(_out_s16);
    if((s_s6.__cid) == _out_s16)/*Artifac..ample2.sk:13*/
    {
      Object@Object _out_s19 = null;
      push_Object_E@Stack(s_s6, o2_s14, _out_s19);
    }
    int s_s21 = 0;
    Stack@meta(s_s21);
    Object@Object _pac_sc_s22 = null;
    if((s_s6.__cid) == s_s21)/*Artifac..ample2.sk:13*/
    {
      Object@Object s_s24 = null;
      pushb_Object_E@Stack(s_s6, o2_s14, s_s24);
      _pac_sc_s22 = s_s24;
    }
    else
    {
      _pac_sc_s22 = null;
    }
    int popped_s26 = 0;
    Stack@meta(popped_s26);
    Object@Object _pac_sc_s27 = null;
    if((_pac_sc_s22.__cid) == popped_s26)/*Artifac..ample2.sk:14*/
    {
      Object@Object popped_s29 = null;
      pop_Object@Stack(_pac_sc_s22, popped_s29);
      _pac_sc_s27 = popped_s29;
    }
    else
    {
      _pac_sc_s27 = null;
    }
    int s_s31 = 0;
    Stack@meta(s_s31);
    if((_pac_sc_s22.__cid) == s_s31)/*Artifac..ample2.sk:14*/
    {
      Object@Object s_s34 = null;
      popb_Object@Stack(_pac_sc_s22, s_s34);
    }
    int _out_s36 = 0;
    Stack@meta(_out_s36);
    bit _pac_sc_s37 = 0;
    if((_pac_sc_s27.__cid) == _out_s36)/*Artifac..ample2.sk:15*/
    {
      bit _out_s39 = 0;
      equals_Object@Object(_pac_sc_s27, o2_s14, _out_s39);
      _pac_sc_s37 = _out_s39;
    }
    else
    {
      int _out_s41 = 0;
      ArtifactExample2@meta(_out_s41);
      bit _pac_sc_s42 = 0;
      if((_pac_sc_s27.__cid) == _out_s41)/*Artifac..ample2.sk:15*/
      {
        bit _out_s44 = 0;
        equals_Object@Object(_pac_sc_s27, o2_s14, _out_s44);
        _pac_sc_s42 = _out_s44;
      }
      else
      {
        int _out_s46 = 0;
        Object@meta(_out_s46);
        bit _pac_sc_s47 = 0;
        if((_pac_sc_s27.__cid) == _out_s46)/*Artifac..ample2.sk:15*/
        {
          bit _out_s49 = 0;
          equals_Object@Object(_pac_sc_s27, o2_s14, _out_s49);
          _pac_sc_s47 = _out_s49;
        }
        else
        {
          _pac_sc_s47 = 0;
        }
        _pac_sc_s42 = _pac_sc_s47;
      }
      _pac_sc_s37 = _pac_sc_s42;
    }
    assert (_pac_sc_s37); //Assert at Artifac..ample2.sk:15 (0)
  }
  /*Artifac..ample2.sk:7*/
```

In general, parsing the final Sketch output can be a little challenging. In this case, we can see we create the stack `s` (renamed to `s_s6`) and `o1` and `o2` (renamed to `o1_s10` and `o2_s14`). The temporary variable `toPush` was optimized away by Sketch, however, we can see the calls to `push_Object_E` and `pushb_Object_E` both have `o2_s14` as the second argument, indicating Sketch correctly assigned `toPush` to `o2`.

## Step by Step: Reviewing the Benchmarks

As mentioned before, the benchmarks from the paper (described in Table 1) can be found in `benchmarks/`.

In this directory, each folder contains a different benchmark. For each benchmark, the synthesis problem is contained in the file denoted "NAME_syn.java" while the solution is simply "NAME.java". All shared libraries (that is libraries used with both specs and mocks) are contained in the `shared/` folder. All specs are contained in the `rewrite/` folder, and all mocks are contained in the `mocks/` folder.

For the cryptography examples, please note the "_mock" and "_rewrite" naming scheme which differentiates the versions used for mocks and rewrites. This was necessary due to boxing annotations, as discussed in Section 4 of the paper.

## Step by Step: LOC Comparison (Section 5.1)

To compare the lines of code used for each approach, we have preinstalled the SLOCCount tool. To run the comparison, navigate to `artifact_scripts/` and run the following:
```
chmod +x sloccounter.sh
./sloccounter.sh
```
The output of this will be stuck in the `artifact_results/sloccounts/` folder. Each benchmark will have its own file, named `$BENCHMARK_NAME$.txt`. For example, here is the output from `SuffixArray.txt`.
```
Have a non-directory at the top, so creating directory top_dir
Adding /Users/grumpy/Research/tmp/benchmarks/SuffixArray//SuffixArray.java to top_dir
Adding /Users/grumpy/Research/tmp/benchmarks/SuffixArray//SuffixArrayTest.java to top_dir
Adding /Users/grumpy/Research/tmp/benchmarks/SuffixArray//SuffixArray_syn.java to top_dir
Adding /Users/grumpy/Research/tmp/benchmarks/SuffixArray//SuffixArray_syn.java~ to top_dir
Creating filelist for mock
Creating filelist for rewrite
Creating filelist for shared
Categorizing files.
Finding a working MD5 command....
Found a working MD5 command.
Computing results.


SLOC	Directory	SLOC-by-Language (Sorted)
399     mock            java=399
385     shared          java=385
310     top_dir         java=310
225     rewrite         java=225


Totals grouped by language (dominant language first):
java:          1319 (100.00%)




Total Physical Source Lines of Code (SLOC)                = 1,319
Development Effort Estimate, Person-Years (Person-Months) = 0.27 (3.21)
 (Basic COCOMO model, Person-Months = 2.4 * (KSLOC**1.05))
Schedule Estimate, Years (Months)                         = 0.32 (3.89)
 (Basic COCOMO model, Months = 2.5 * (person-months**0.38))
Estimated Average Number of Developers (Effort/Schedule)  = 0.82
Total Estimated Cost to Develop                           = $ 36,133
 (average salary = $56,286/year, overhead = 2.40).
SLOCCount, Copyright (C) 2001-2004 David A. Wheeler
SLOCCount is Open Source Software/Free Software, licensed under the GNU GPL.
SLOCCount comes with ABSOLUTELY NO WARRANTY, and you are welcome to
redistribute it under certain conditions as specified by the GNU GPL license;
see the documentation for details.
Please credit this data as "generated using David A. Wheeler's 'SLOCCount'."
```

The LOC information is given in the table about halfway through the output. the first row gives the LOC, the second the directory, and the third the LOC by language (which is the same as the LOC as there is only Java). We are interested in the first and last lines, which report that `SuffixArray` has `399` LOC for mocks and `225` LOC for rewrites (algebraic specifications).

## Step by Step: Synthesis Problems (Section 5.2)

In Table 2, we record the number of calls to `stmts` and `guards` for each benchmark. This is not calculated automatically, but can instead be verified manually by inspecting the `*_syn.java` program for each benchmark.

## Step by Step: Performance Comparison (Section 5.2)

As reported in Table 2, many of our tests take quite a while to run (and this is with 128 GB of RAM and 10 CPUs!). As a result, we provide three tests to run: the full benchmarks from the paper, an abridged version, and a short version. We expect that running the full benchmark suite could take many weeks, running the abridged version shouldn't take longer than 36 hours, and the short version should take about an hour. More information is given for each of these in the subsections below.

Performance on the benchmarks may vary significantly depending on the power of the machine used. However, we expect that the comparative difference between mocks and algebraic specifications should remain roughly the same.

However, we found that the memory limitations of the VM had impact on some of the benchmarks. In particular, we noticed that three mock benchmarks (`SuffixArray`, `PasswordManager`, and `Kafka`) all died earlier than the Sketch time out we expected. We suspect the cause of this is the VM running out of memory. Additionally, lowering the RAM below 7 GB affected other tests, including `RomList` as well as `PasswordManager`, `Kafka`, and `HashMap1` for rewrite.

One should also note that there is significant variance for some of the benchmarks (see IQRs from Table 2 in the paper), which means that running the "Abridged" or "Short" tests could result in performance that varies somewhat significantly from the median reported in the paper (bolded in Table 2).

Note that times reported in output files are in millesconds and not in seconds, as reported in the paper. Also note the names reported for each benchmark are slightly different than the names. Most should be straightforward, but for clarity, the following is the mapping from benchmark to name reported in the output files:

* `SuffixArray`: SuffixArrayTest
* `HashMap1`: HashTableTest
* `HashMap2`: BucketingTest
* `PasswordManager`: PasswordManagerTest
* `CipherFactory`: CipherFactoryTests
* `Kafka`: Kafka_Tester
* `EasyCSV`: CSVTester
* `RomList`: RomListTester
* `Comparator`: Comparator

### Running the Full Benchmark Tests

NOTE: This will take a very long time to run. 31 trials of each experiment was run, meaning this will run a total of 551 experiments, each of which can take up to 4 hours. We would expect running the full benchmark suite will take at least three weeks with a very powerful machine.

To run the full performance comparison from the paper, navigate to `java-sketch` and run the following:
```
python -m artifact_scripts.jlibsketch_benchmarks_full
```
This will create 4 output files in the `artifact_results/full/` folder:

* `out_mock.txt`: contains rolling timing output for each experiment using mocks
* `out_rewrite.txt`: contains rolling timing output for each experiment using specs
* `results_mock.csv`: contains final timing output for each experiment using mocks
* `results_rewrite.csv`: contains final timing output for each experiment using mocks

Each benchmark is run 31 times. Currently there is no automatic script to read in this information and calculate the statistics from Table 2; this can be done manually if desired.

### Running the Abridged Benchmark Tests

NOTE: We expect this will take at least a day to run, depending on the strength of your machine and the RAM given to the VM.

Here, instead of running 31 trials of each experiment, we run each experiment once. To run, navigate to `java-sketch` and run the following:
```
python -m artifact_scripts.jlibsketch_benchmarks_abridged
```
Again, this creates 4 output files in `artifact_results/abridged/` folder, the same as described for the full experiments.

One can observe the Sketch output of JLibSketch in `result/` as with the example problem from the Getting Started guide.

Similarly, one can observe the final C++ ouput in `result/output/`. 

### Running the Short Benchmark Tests

NOTE: We expect this should take less than an hour.

Here, we only run the benchmarks that finish in under 10 minutes. To run, navigate to `java-sketch` and run the following:
```
python -m artifact_scripts.jlibsketch_benchmarks_short
```
Similarly, this creates 4 output files in `artifact_results/short/` and Sketch input and ouput can be viewed in the `result/` folder.

