compile (){
    echo "Compiling..."
    find src/main/java/com/gytmy -name "*.java"  > sources.txt
    javac -cp src:lib -d bin @sources.txt
    echo "Done!"
}


run (){
    echo "Launching..."
    java -cp bin/ com.gytmy.Main
}

compileTests (){
    echo "Compiling tests..."
    find -name "*.java" > sources.txt
    javac --class-path="src:lib/junit-platform-console-standalone-1.9.2.jar" -d bin @sources.txt
    rm sources.txt
    echo "Done!"
}

runTests (){
    echo "Running tests..."
    java -jar lib/junit-platform-console-standalone-1.9.2.jar --classpath=bin --scan-classpath --include-classname='.*';
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
