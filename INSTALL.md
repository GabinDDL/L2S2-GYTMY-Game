# How to install the game's dependencies

## Install ALIZE and SPro

The recommended operating systems are **Linux** and **MacOS**. There is a possibility to install on Windows, but it is not recommended and we do not provide support for it.

Prerequisites (on Linux Ubuntu, it can be installed via `apt-get`) :

- `aclocal`

> This tool is not always available. If the installation of `aclocal` fails, you can try to install `autotools-dev`.

- `autoconf`
- `automake`
- `libtool`

**Install the libraries following the order given below!** and, if possible, in the same folder.

### Install SPro

- [Download SPro 4.0.1](http://www.irisa.fr/metiss/guig/spro/spro-4.0.1/spro-4.0.1.tar.gz)
- Extract the `tar` archive and open a terminal **in the resulting folder**.
- Type `./configure` to configure the package for your system
- Type `make` to compile the package

> There may be errors during compilation such as **missing libraries**. The most efficient way to deal with them is to **include them yourself in the files that require them** (`#include <string.h>` for example).
>
> One known example is that, in Debian based systems, the Makefile is not generated properly with the `./configure` command. To fix it, you simply have to add the `-lm` option to the `LIBS` macro in the Makefile.

- Optionally, type `make check` to run tests that come with the package
- Type `make install` to install the programs

The file we are interested in is the `sfbcep` executable which is now in the current folder

### Install alize-core

- [Download alize-core](https://github.com/ALIZE-Speaker-Recognition/alize-core)
- Extract `alize-core` and launch a terminal **in the resulting folder**
- Run the following commands in the terminal:

```bash
aclocal
automake
```

> The `compile` file may be missing. If so, run `automake --add-missing`

```bash
autoconf
./configure
make
```

### Install LIA_RAL

[Download LIA_RAL](https://github.com/ALIZE-Speaker-Recognition/LIA_RAL)

Warning: `alize-core` and `SPro` must be in the **same directory** as `LIA_RAL` (you must install them **first**).

You can also change the installation path doing `./configure`.

- Extract `LIA_RAL` and launch a terminal **in the resulting folder**
- Run the following commands in the terminal:

```bash
aclocal
automake
```

> Like for `alize-core`, the `compile` file may be missing. If so, run `automake --add-missing`

```bash
autoconf
./configure --with-spro
make
```

**The files we are interested in are the executables `EnergyDetector`, `NormFeat`, `TrainWorld`, `TrainTarget` and `ComputeTest`** which are in the bin folder of LIA_RAL

### How to implement ALIZE + SPro

To implement the features of `SPro` and `Alize` in the game, you will have to get the following executables :

- EnergyDetector (`LIA_RAL`)
- NormFeat (`LIA_RAL`)
- TrainWorld (`LIA_RAL`)
- TrainTarget (`LIA_RAL`)
- ComputeTest (`LIA_RAL`)
- sfbcep (`SPro`)

Which are located inside the respective folders `LIA_RAL/bin` and `SPro` of the programs you just installed.
Then copy `EnergyDetector`, `NormFeat`, `TrainWorld`, `TrainTarget` and `sfbcep` inside the directory of your game: `src/main/exe/model`, and then copy `ComputeTest` inside the directory also in your game: `src/main/exe/comparison`.

We provide a script to do this automatically, `copy_executables.sh`, in the root directory of the project. You can run it with `./copyExecutables.sh`. If you wish you can pass the path of `LIA_RAL` and of `SPro` as arguments to the script, for example:

```bash
./copyExecutables.sh ../LIA_RAL ../spro-4.0
```

The order matters, so you have to pass the path of `LIA_RAL` first and then the path of `SPro`. If you do not pass any argument the script will prompt you to enter the path of `LIA_RAL` and then the path of `SPro`.

(You can also replace the paths in the sh files with their positions in your folders: to do this, replace the `EXE_DIR` variables in all .sh files in src/main/exe/model to specify the path. This allows you to not have to copy the files in the game directory)

**`SPro` and `ALIZE` are now implemented !**

## Install Whisper and dependencies

### How to install `python3` (3.8 < 3.x < 3.11)

- [Download a python stable source release (between 3.8 and 3.11 strictly)](https://www.python.org/downloads/source/) (The downloaded folder would be erased later so wherever you want !)
- Decompress the installed file
- Open `Terminal` in the decompressed folder and execute the given commands one at a time in the order :
```bash
./configure
make
sudo make install
```
- **Make sure to note down the version of python you installed** : if python 3.10.11 then note python 3.10 for later usage
- You can erase the `folder` and the `tar` file after the installation

### Time to run the script `install_whisper.sh`

Now, in order to install `Whisper`, you will only need to run the `install_whisper.sh` script in the root directory of the project.
If the script does not have the right permissions, you can run `chmod +x install.sh` to give it the right permissions.
To uninstall `Whisper`, you can run the `uninstall_whisper.sh` script.
