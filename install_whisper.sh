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
    echo_color "" "" "\u2705 "
    echo_color "32" "underline" "${message}" "b"
}

function error() {
    local message="$1"
    echo_color "" "" "\u274C "
    echo_color "31" "" "${message}" "b"
}

function warning() {
    local message="$1"
    echo_color "33" "underline" "Warning"
    echo_color "33" "" " : ${message}" "b"
}

function info() {
    local message="$1"
    echo_color "34" "underline" "INFO"
    echo_color "34" "" " : ${message}" "b"
}

#------------------------------------------------------------#
#------------------------- INSTALL.SH -----------------------#
#------------------------------------------------------------#

# Turn off the xtrace option, which enables the printing
# of each command before its execution.
set +x

# Define the name of the virtual environment
ENV_NAME=amaze

# Check if the user has administrative privileges
if [[ $EUID -ne 0 ]]; then
    SUDO="sudo"
else
    SUDO=""
fi

#------------------------------------------------------------#
#------------------------- FUNCTIONS ------------------------#
#------------------------------------------------------------#

function make_python_ready() {

    VERSION=$(python3 --version);
    # Check if Python 3.8, 3.9 or 3.10 is installed
    for VERSION in 3.8 3.9 3.10; do
        if (which "python"$VERSION) &>/dev/null; then
            PYTHON3=$(which "python"$VERSION)
            break
        fi
    done

    # Check if PYTHON3 is set
    if [ -z "$PYTHON3" ]; then
        error "Python 3.8, 3.9 or 3.10 is required."
        info "Follow install.md for installing Python 3.8, 3.9 or 3.10."
        exit 1
    else
        info "Python 3.8, 3.9 or 3.10 is installed."
    fi

    
    success "Virtual environment $ENV_NAME with Python$VERSION activated successfully."
    
    # check if virtualenv is installed
    if ! command -v virtualenv &>/dev/null; then
        warning "virtualenv is not installed."
        
        # Install virtualenv
        pip3 install virtualenv &>/dev/null
        
        # Check if virtualenv is installed
        if command -v virtualenv &>/dev/null; then
            success "virtualenv installed successfully."
        else
            error "Failed to install virtualenv."
            exit 1
        fi
    else
        info "virtualenv is already installed."
    fi
    
    # Check if virtual environment exists
    if [ -d $ENV_NAME ]; then
        warning "Virtual environment $ENV_NAME already exists."
    else
        
        # Create a virtual environment using $PYTHON3
        virtualenv -p "$PYTHON3" $ENV_NAME &>/dev/null
    fi
    
    source $ENV_NAME/bin/activate &>/dev/null
    
    # Check if virtual environment is activated ($VIRTUAL_ENV is set by virtualenv/bin/activate)
    if [ "$VIRTUAL_ENV" = "$PWD/$ENV_NAME" ]; then
        info "Virtual environment $ENV_NAME is activated."
    else
        warning "Virtual environment $ENV_NAME is not activated."
        exit 1
    fi
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
            success "PyTorch installed successfully."
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
            $SUDO apt-get install ffmpeg -y
            
            elif [ -f /etc/fedora-release ]; then
            # Fedora system
            $SUDO dnf -y install "https://download1.rpmfusion.org/free/fedora/rpmfusion-free-release-$(rpm -E %fedora).noarch.rpm"
            $SUDO dnf -y install "https://download1.rpmfusion.org/nonfree/fedora/rpmfusion-nonfree-release-$(rpm -E %fedora).noarch.rpm"
            $SUDO dnf install ffmpeg -y

            elif [ -f /etc/arch-release ]; then
            # Arch-based system
            $SUDO pacman -S ffmpeg -y
            
        else
            # Unsupported system
            echo "Unsupported operating system."
            exit 1
        fi
        
        # Check if ffmpeg is installed
        if command -v ffmpeg &>/dev/null; then
            success "ffmpeg installed successfully."
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
    if ! command -v whisper-ctranslate2 &>/dev/null; then
        warning "Whisper is not installed."
        
        # Install whisper
        pip3 install -U whisper-ctranslate2
        
        # Check if whisper is installed
        if command -v whisper-ctranslate2 &>/dev/null; then
            success "Whisper installed successfully."
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

success "Installation completed successfully."
