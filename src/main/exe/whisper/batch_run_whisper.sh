#!/bin/bash

# Help message
if [ "$1" == "-h" ] || [ "$1" == "--help" ]; then
    echo "Usage: batch_run_whisper.sh [options]"
    echo "Options:"
    echo "  -c, --cuda        Use CUDA for inference. Default: False"
    echo "  -m, --model       Model to use for inference. Default: tiny.en"
    exit 0
fi

# Default values
cuda=false
model=tiny.en

# Store start time
start=$(date +%s)

# Parse command line arguments
while [[ $# -gt 0 ]]; do
    key="$1"
    case $key in
    -c | --cuda)
        cuda=true
        shift # pass argument
        ;;
    -m | --model)
        model="$2"
        shift # pass argument
        shift # pass value
        ;;
    *) # unknown option
        echo "Unknown option: $1"
        exit 1
        ;;
    esac
done

# Store paths in variables
audio_dir="$(pwd)"/src/resources/audioFiles
results_dir="$(pwd)"/src/resources/results_whisper

if [ ! -d "$results_dir" ]; then
    echo "Creating results directory..."
    mkdir "$results_dir"
else
    # Remove all files in results directory
    echo "Removing all files in results directory..."
    rm -rf "${results_dir:?}/"*
fi

# Loop through .wav files in audio directory
for audio in "$audio_dir"/*/*/*.wav; do
    # Echo paths to .wav file and results folder
    echo "$audio" "$results_dir"
    if [ "$cuda" = true ]; then
        "$(pwd)"/whisper.sh -d cuda -m "$model" -a "$audio" -o "$results_dir"
    else
        "$(pwd)"/whisper.sh -m "$model" -a "$audio" -o "$results_dir"
    fi
done

# Store end time and calculate duration
end=$(date +%s)
duration=$((end - start))
echo "Script duration: $duration seconds."
