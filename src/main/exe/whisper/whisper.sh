#!/bin/bash

ENV_NAME=amaze

source $ENV_NAME/bin/activate &>/dev/null

# Help message
if [ "$1" == "-h" ] || [ "$1" == "--help" ]; then
    echo "Usage: whisper.sh [options]"
    echo "Options:"
    echo "  -d, --device      Device to use for inference. Default: cpu"
    echo "  -m, --model       Model to use for inference. Default: tiny.en"
    echo "  -f, --fp16        Use FP16 for inference. Default: False"
    echo "  -a, --audio       Path to audio file to transcribe."
    echo "  -o, --output      Path to output directory."
    exit 0
fi

# set default values for flags and options
DEVICE="cpu"
MODEL="tiny.en"
FP16="False"

# parse command line arguments
while [[ $# -gt 0 ]]; do
    key="$1"

    case $key in
    -d | --device)
        DEVICE="$2"
        shift
        shift
        ;;
    -m | --model)
        MODEL="$2"
        shift
        shift
        ;;
    -f | --fp16)
        FP16="$2"
        shift
        shift
        ;;
    -a | --audio)
        AUDIO_PATH="$2"
        shift
        shift
        ;;
    -o | --output)
        OUTPUT_PATH="$2"
        shift
        shift
        ;;
    *) # unknown option
        shift
        ;;
    esac
done

# set fp16 false if device is cpu
if [ "$DEVICE" == "cpu" ]; then
    echo "Device is cpu, setting fp16 to false."
    FP16="False"
else # set fp16 true if device is gpu
    echo "Device is gpu, setting fp16 to true."
    FP16="True"
fi

# execute the command
whisper --fp16 "$FP16" --language en --device "$DEVICE" --output_format json --model "$MODEL" "$AUDIO_PATH" -o "$OUTPUT_PATH"
