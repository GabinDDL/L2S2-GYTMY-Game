wasCompile=false
wasRun=false
wasTestCompile=false
wasTestRun=false

install() {
    echo "Installing dependencies..."
    ./install.sh && echo "Done!"
}

compile() {
    echo "Compiling..."
    install
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
    install
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
"compile")
    compile
    $wasCompile && exit 0 || exit 1
    ;;
"compileTest" | "compileTests")
    compileTests
    $wasTestCompile && exit 0 || exit 1
    ;;
"runTest" | "runTests" | "test" | "tests")
    compileTests
    runTests
    $wasTestRun && exit 0 || exit 1
    ;;
*)
    compile && run
    $wasRun && exit 0 || exit 1
    ;;
esac
