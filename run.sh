wasRun=false

compile() {
    echo "Compiling..."
    rm -rf bin
    find src/main/java/com/gytmy -name "*.java" >sources.txt
    javac -cp lib/*:src -d bin @sources.txt && wasRun=true
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
    javac --class-path="src:lib/*" -d bin @sources.txt && wasRun=true
    rm sources.txt
    echo "Done!"
}

runTests() {
    echo "Running tests..."
    java -cp classes:bin:lib/* org.junit.platform.console.ConsoleLauncher --scan-classpath && wasRun=true
}

case "$1" in
    "compile")
        compile
        $wasRun && exit 0 || exit 1
    ;;
    "compileTests")
        compileTests
        $wasRun && exit 0 || exit 1
    ;;
    "runTests" | "test")
        compileTests
        runTests
        $wasRun && exit 0 || exit 1
    ;;
    *)
        compile && run
        $wasRun && exit 0 || exit 1
    ;;
esac
