compile (){
    echo "Compiling..."
    find src/main/java/com/gytmy -name "*.java"  > sources.txt
    javac -cp lib/*:src  -d bin @sources.txt
    echo "Done!"
}


run (){
    echo "Launching..."
    java -cp lib/*:bin com.gytmy.Main
}

compileTests (){
    echo "Compiling tests..."
    find -name "*.java" > sources.txt
    javac --class-path="src:lib/*" -d bin @sources.txt
    rm sources.txt
    echo "Done!"
}

runTests (){
    echo "Running tests..."
    java  -cp classes:bin:lib/*  org.junit.platform.console.ConsoleLauncher --scan-classpath;
}


case "$1" in
    "compile")
        compile
        exit
    ;;
    "compileTests")
        compileTests
        exit
    ;;
    "runTests"| "test")
        compileTests
        runTests
        exit
    ;;
    *)
        compile && run
esac
