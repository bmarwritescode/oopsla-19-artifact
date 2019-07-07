# Artifact for OOPSLA 2019 Submission "Program Synthesis with Algebraic Library Specifications"


Getting Started Guide: Program Synthesis with Algebraic Library Specifications
-------------------------------------------------------------------------------

Setup
------

- (TODO: Include Docker setup...)

Basic Testing
--------------

Basic Running of JSketch and Interpretting Output:

- Once you have the docker image running, navigate to the `java-sketch/` folder in the home directory
- In this folder, you will find all relevant materials for the artifact
- JSketch should already be built. We will run jsketch on the simple test given in the file `test/ArtifactExample1.java`
- Open that file, and observe the contents; this is a JSketch file
- There are two classes, `ArtifactExample1` and `Foo`. Class `Foo` has a constructor, which sets its field `x`, and another method `mulBy2`, which returns the value of its multiplied by a constant, which JSketch will find. The `harness` for this method is given in `ArtifactExample1`, in the `mn` method. The `mn` method takes one (unbound) argument `x`, and creates a `Foo` object with this `x`. It then `assert`s that calling the `mulBy2` method from this new `Foo` object `f` will result in a value which is equal to `x+x`. 
- To run JSketch on this example, run the following command from the `java-sketch/` folder: `./jsk.sh test/ArtifactExample1.java --no-lib model/lang/Object.java`
- On running, you will see output of the form `XX:XX:XX [DEBUG] ... => ...`
- The lines might also have `[INFO]` instead of `[DEBUG]`
- The last line should end with `sketch done: result/output/ArtifactExample1.txt`
- Before looking at the result, let's first look at the generated Sketch code, output to the folder `result/sk_ArtifactExample1`.
- In this folder, you will see 6 different Sketch files. `main.sk`, `meta.sk`, and `array.sk` contain Sketch configuration options, class IDs (`__cid` explained in Section 4), and a special Sketch struct for arrays respectively. `Object.sk` includes the `Object` struct described in Section 4, as well as the sketch translation of the `Object` class `.equals` method. The final two files, `ArtifactExample1.sk` and `Foo.sk` contain the Sketch translation of the two classes from `ArtifactExample1.java`.
- Let's start by looking at `Foo.sk`. There are three functions in this file, `Foo_Foo`, `Foo_Foo_int`, and `mulBy2`. The first function is the default constructor (i.e. no arguments) that was implicity present in the `Foo` class. For the Sketch translation, an additional argument `self` has been added which represents the caller. In this case of the constructor, this `self` Object is instantiated as a new `Object` struct with the appropriate class ID (`__cid`). The second function, `Foo_Foo_int` is the Sketch translation of the single argument constructor. Notice that the name of the function has been changed to include the parameter types to handle method overloading in Java. The body of this function sets the `x_Foo` field (renamed with class as described in Section 4) to `x`. The last function `mulBy2` is almost identical to the Java function with the exception of the caller argument `self`.
- Now let's look at `ArtifactExample1.sk`. You can see that again we have a default constructor. We also have the `harness` function `mn_int`. The translation here is mostly what you would expect (and as is described in Section 4). Notably, the call to `mulBy2` has been translated to a conditional check on the object type for dynamic dispatch, but as no class extends it, `Foo`'s `mulBy2` method is the only option.
- Now that we've seen what the Sketch input looks like, let's look at the Sketch output. Open the output file: `emacs result/output/ArtifactExample1.txt` (replace `emacs` with whichever text editor you prefer)
- Here you will see the resulting C++ code produced. At the top you will see some ouput about the sketch internals, which you can ignore for now. Navigate to the line where it says `/* BEGIN PACKAGE ArtifactExample*/`. This should be on line 6.
- First, you will see the generated code for `mn_int`. This code is a fairly straightforward (though ugly looking) translation of the Sketch code. Navigate now to `/* BEGIN PACKAGE Foo*/` on line 93. Again, most of this translation is fairly straightforward (and described in more detail in the Sketch manual: TODO: ADD LINK). However, notice on line 107 that the `??` in the `mulBy2` function has been correctly replaced by `2`. Sketch indeed found the right answer. 

Basic Running of JLibSketch and Interpretting Output:

- Now we will run JLibSketch. Consider the example in `test/ArtifactExample2.java`
- Again, there are two classes. We will start with the bottom one, `Stack`. Notice first that `Stack` has the `@rewriteClass` annotation, meaning it uses algebraic rewriting rules to define its behavior. In this case, it has two methods, `push` and `pop`, both of which are given method declarations with no body and the annotation `@alg`. Descriptions of these annotations are given in Section 2 of the paper (under "Algebraic Specifications using Rewrite Rules"). Below those, we give the rewrite rule, which uses JLibSketch syntax (also explained in Section 2) to give the rewrite rule pop(push!(s, x)) = x. This rule describes the stack behavior that calling `pop()` after `push(x)` results in the value `x`.
- The top class, `ArtifactExample2` has the `harness` method `mn`, which in this case contains both the synthesis problem and its specification. In this method, a new stack `s` is created as well as two `Object`s `o1` and `o2`. Next, we create another `Object` named `toPush` which we assign to a generator that will choose between `o1` and `o2`. Next, we push `toPush` onto the stack. Finally, we `pop` the top value of the stack and assert that it is equal to `o2`. JLibSketch will choose the appropriate instantiation of `toPush` such that this assertion holds according to the behavior of stack, which in this case will set `toPush` equal to `o2`.
- To run this example, run the following from `java-sketch`: `./jsk.sh test/ArtifactExample2.java --no-lib model/lang/Object.java`. Note, the `--no-lib` indicates to not include the standard libraries we have implemented by default (to avoid conflict with our Stack implementation) and the `model/lang/Object.java` tells JLibSketch to include our `Object` class implementation.
- Let's look at the Sketch code generated in `result/sk_ArtifactExample2/`. Just as last time, we have `main.sk`, `meta.sk`, `array.sk`, and `Object.sk` which serve the same purpose. Let's first look at `Stack.sk` which contains the Sketch translation of the `Stack` class. Note that a detailed description of this translation is given in Section 4.
- Notice first that on lines 3-9 we have our `Stack` adt, which has added a term for method as well as a `!` term for each impure method, denoted by adding a `b` after the name (note that both `push` and `pop` are both impure). This convention is elided for the constructor, for which there is only one term `Stack_empty`.
- Below this, we have 5 functions, each of which is used as an outside handle for stack functions. Again, each impure function has two handles, one representing return values and one (the `b` functions) representing side-effects on the stack. Note that return value functions (i.e. `push_Object_E`) return a new `Object` struct, while side-effect functions (i.e. `pushb_Object_E` and `popb_Object`) simply update the `__stack` field of the caller with the new stack term. This translation is to preserve aliasing. Notice that `pop_Object` instead calls the `xform_pop_Stack` function, passing it the `Stack` adt argument `self._stack`. Note that we used `rewrite` in the paper for clarity, but here the `xform_pop_Stack` function performs the same rewriting operations as described by the `rewrite` Sketch function in Section 4 (e.g. in Figure 7).
- The implementation of `xform_pop_Stack` is given on line 57. It does pattern matching on its argument (i.e. the caller of the method in Java). As shown in line 63, if the argument is of type `Pushb_e` (i.e. the call to `pop` was preceded by a call to `push`) we return the value `e`. Notice that any other type in the `switch` statement leads to an `assert false`. This ensures that any behavior not explicity stated by the rewrite rules (e.g. pop(pop!(...))) cannot be used for successful synthesis.
- Now let's look at `ArtifactExample2.sk`. This translation is mostly straightforward, similiar to `ArtifactExample1.sk`. However, there is one notable difference. On lines 13 and 15, the calls to s.push(toPush) and s.pop() have been translated into two statements. On line 13, he first calls the return value function (which for `push` is not assigned to anything). The second assigns to our stack `s` the result of calling the side-effect function (which as you recall will update `s`'s `_stack` field). The same translation occurs for `pop` on line 15. Note that these calls use the handle functions (`push_Object_E`, `pushb_Object_E`, `pop_Object`, and `popB_Object`) from `Stack.sk`.
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
