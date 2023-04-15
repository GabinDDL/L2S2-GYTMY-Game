#!/bin/bash

#------------------------------------------------------------#
#--------------------- OUTPUT FORMATTING --------------------#
#------------------------------------------------------------#

function echo_color() {
    local color="$1"
    local text_type="$2"
    local message="$3"
    local line_break="$4"

    local start=""
    local end="\e[0m"

    case "$text_type" in
    bold) start="\e[1m" ;;
    underline) start="\e[4m" ;;
    *) start="" ;;
    esac

    if [ "$line_break" = "b" ]; then
        echo -e "${start}\e[${color}m${message}${end}"
    else
        echo -e -n "${start}\e[${color}m${message}${end}"
    fi
}

function success() {
    local message="$1"
    echo_color "32" "underline" "${message}" "b"
}

function error() {
    local message="$1"
    echo_color "31" "bold" "ERROR: "
    echo_color "31" "" "${message}" "b"
}

function warning() {
    local message="$1"
    echo_color "33" "bold" "WARNING: "
    echo_color "33" "" "${message}" "b"
}

function info() {
    local message="$1"
    echo_color "" "" "${message}" "b"
}

function confirm() {
    local message="$1"
    echo_color "36" "bold" "${message}" "b"
}

function confirmed() {
    local message="$1"
    echo_color "32" "bold" "${message}" "b"
}

#------------------------------------------------------------#
#------------------------- INSTALL.SH -----------------------#
#------------------------------------------------------------#

# Turn off the xtrace option, which enables the printing
# of each command before its execution.
set +x

# Define the name of the virtual environment
ENV_NAME=amaze

# Define the window of the needed python version
MIN_PYTHON_VERSION=3.8
MAX_PYTHON_VERSION=3.10

#------------------------------------------------------------#
#------------------------- FUNCTIONS ------------------------#
#------------------------------------------------------------#

# Installs Python 3.10 based on Linux (only Debian or Fedora)
function install_python() {

    confirm "Are you okay with installing Python $MAX_PYTHON_VERSION? (y/n)"
    read -r choice

    # Check user's response
    case "$choice" in
    y | Y)
        confirmed "Okay, proceeding with installation of Python $MAX_PYTHON_VERSION."
        ;;
    n | N)
        confirmed "Python $MAX_PYTHON_VERSION will not be installed."
        info "Packages may behave in unintended ways."
        return
        ;;
    *)
        error "Invalid choice, please enter 'y' or 'n'."
        return
        ;;
    esac

    # Install Python 3.10
    if [ -f /etc/debian_version ]; then
        # Debian-based system
        info "Detected Debian-based system."
        sudo apt-get update
        sudo apt-get install python3 -y
        sudo apt-get install python3-pip

    elif [ -f /etc/fedora-release ]; then
        # Fedora system
        info "Detected Fedora system."
        sudo dnf update
        sudo dnf install python3 -y
        sudo dnf install python3-pip

    elif [ -f /etc/arch-release ]; then
        # Arch-based system
        info "Detected Arch-based system."
        sudo pacman -Syu
        sudo pacman -S python3 -y
        sudo pacman -S python-pip
    else
        # Unsupported system
        error "Unsupported operating system."
        exit 1
    fi

    success "Python 3.10 installed successfully."

    return
}

function make_python_ready() {
    # Install Python 3.10
    install_python

    # check if virtualenv is installed
    if ! command -v virtualenv &>/dev/null; then
        warning "virtualenv is not installed."

        # Install virtualenv
        pip3 install virtualenv &>/dev/null

        # Check if virtualenv is installed
        if command -v virtualenv &>/dev/null; then
            confirmed "virtualenv installed successfully."
        else
            error "Failed to install virtualenv."
            exit 1
        fi
    else
        info "virtualenv is already installed."

        # Check if virtual environment exists
        if [ -d $ENV_NAME ]; then
            warning "Virtual environment $ENV_NAME already exists."

            # Check if virtual environment is activated
            if [ "$VIRTUAL_ENV" = "$PWD/$ENV_NAME" ]; then
                info "Virtual environment $ENV_NAME is already activated."
            else
                # Activate the virtual environment
                source $ENV_NAME/bin/activate &>/dev/null
            fi
        else
            # Create a virtual environment using Python 3.10
            virtualenv -p "/usr/bin/python3.10" $ENV_NAME &>/dev/null

            # Activate the virtual environment
            source $ENV_NAME/bin/activate &>/dev/null
        fi
    fi

    # Check if Python version in virtual environment is within the specified range
    if python3 -c "import sys; exit(not (sys.version_info >= (3, 8) and sys.version_info < (3, 11)))"; then
        info "Python version in the virtual environment is between $MIN_PYTHON_VERSION and $MAX_PYTHON_VERSION."
    else
        error "Failed to install Python 3.10 in virtual environment."
        exit 1
    fi

    confirmed "Virtual environment $ENV_NAME with Python 3.10 activated successfully."
}

function make_pytorch_ready() {

    # Activate the virtual environment
    source $ENV_NAME/bin/activate &>/dev/null

    # Check if PyTorch is installed
    if ! python3 -c "import torch" &>/dev/null; then
        warning "PyTorch is not installed."

        # Install PyTorch
        pip uninstall torch
        pip cache purge
        pip3 install torch torchvision torchaudio --extra-index-url https://download.pytorch.org/whl/cu116I

        # Check if PyTorch is installed
        if python3 -c "import torch" &>/dev/null; then
            confirmed "PyTorch installed successfully."
        else
            error "Failed to install PyTorch."
            exit 1
        fi
    else
        info "PyTorch is already installed."
    fi
}

function make_ffmpeg_ready() {
    # Check if ffmpeg is installed
    if ! command -v ffmpeg &>/dev/null; then
        warning "ffmpeg is not installed."

        # Install ffmpeg
        if [ -f /etc/debian_version ]; then
            # Debian-based system
            sudo apt-get install ffmpeg -y

        elif [ -f /etc/fedora-release ]; then
            # Fedora system
            sudo dnf -y install "https://download1.rpmfusion.org/free/fedora/rpmfusion-free-release-$(rpm -E %fedora).noarch.rpm"
            sudo dnf -y install "https://download1.rpmfusion.org/nonfree/fedora/rpmfusion-nonfree-release-$(rpm -E %fedora).noarch.rpm"
            sudo dnf install ffmpeg -y

        else
            # Unsupported system
            echo "Unsupported operating system."
            exit 1
        fi

        # Check if ffmpeg is installed
        if command -v ffmpeg &>/dev/null; then
            confirmed "ffmpeg installed successfully."
        else
            error "Failed to install ffmpeg."
            exit 1
        fi
    else
        info "ffmpeg is already installed."
    fi
}

function make_whisper_ready() {
    # Activate the virtual environment
    source $ENV_NAME/bin/activate &>/dev/null

    # Check if whisper is installed
    if ! command -v whisper &>/dev/null; then
        warning "Whisper is not installed."

        # Install whisper
        pip3 install -U openai-whisper

        # Check if whisper is installed
        if command -v whisper &>/dev/null; then
            confirmed "Whisper installed successfully."
        else
            error "Failed to install whisper."
            exit 1
        fi
    else
        info "Whisper is already installed."
    fi
}

#------------------------------------------------------------#
#--------------------------- MAIN ---------------------------#
#------------------------------------------------------------#

# Make sure the script is being executed with root privileges.
if [[ $EUID -ne 0 ]]; then
    error "This script must be run as root."
    exit 1
fi

make_python_ready
make_pytorch_ready
make_ffmpeg_ready
make_whisper_ready
