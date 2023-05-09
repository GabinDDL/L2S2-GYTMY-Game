wasCompile=false
wasRun=false
wasTestCompile=false
wasTestRun=false

compile() {
    echo "Compiling..."
    rm -rf bin
    find src/main/java/com/gytmy -name "*.java" >sources.txt
    javac -cp lib/*:src -d bin @sources.txt && wasCompile=true
    rm sources.txt
    echo "Done!"
}

run() {
    echo "Launching..."
    java -cp lib/*:bin com.gytmy.Main && wasRun=true
}

compileTests() {
    echo "Compiling tests..."
    rm -rf bin
    find -name "*.java" >sources.txt
    javac --class-path="src:lib/*" -d bin @sources.txt && wasTestCompile=true
    rm sources.txt
    echo "Done!"
}

runTests() {
    echo "Running tests..."
    java -cp classes:bin:lib/* org.junit.platform.console.ConsoleLauncher --scan-classpath && wasTestRun=true
}

case "$1" in
    "compile" |"--compile")
        compile
        $wasCompile && exit 0 || exit 1
    ;;
    "compileTest" | "compileTests"| "--compileTest" | "--compileTests")
        compileTests
        $wasTestCompile && exit 0 || exit 1
    ;;
    "runTest" | "runTests" | "test" | "tests"| "--runTest" | "--runTests" | "--test" | "--tests")
        compileTests
        runTests
        $wasTestRun && exit 0 || exit 1
    ;;
    "run" | "--run")
        compile
        run
        $wasRun && exit 0 || exit 1
    ;;
    *)
        run
        $wasRun && exit 0 || exit 1
    ;;
esac
