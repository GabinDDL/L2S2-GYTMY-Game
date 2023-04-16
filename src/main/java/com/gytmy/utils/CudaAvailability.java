package com.gytmy.utils;

public class CudaAvailability {
    public static boolean isCudaAvailable() {
        String[] commandArgs = {"-c", "find /usr/lib /usr/lib64 -name 'libcuda.so*' -type f -exec echo 'found' \\; | head -n 1"};

        // Run the command using the RunSH utility class
        int exitCode = RunSH.run("/bin/sh", commandArgs);

        // If the exit code is 0, it means the command was successful and at least one libcuda.so file was found
        return exitCode == 0;
    }
}
