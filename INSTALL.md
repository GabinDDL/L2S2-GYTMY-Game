# Install ALIZE and Spro

The recommended operating systems are **Linux** and **MacOS**. There is a possibility to install on Windows, but it is not recommended and we do not provide support for it.

Prerequisites (on Linux Ubuntu, it can be installed via `apt-get`) :

- `aclocal`

> This tool is not always available. If the installation of `aclocal` fails, you can try to install `autotools-dev`.

- `autoconf`
- `automake`
- `libtool`

**Install the libraries following the order given below!** and, if possible, in the same folder.

## Install Spro

- [Install SPro 4.0.1](http://www.irisa.fr/metiss/guig/spro/spro-4.0.1/spro-4.0.1.tar.gz)
- Extract the `tar` archive and open a terminal **in the resulting folder**.
- Type `./configure` to configure the package for your system
- Type `make` to compile the package

> There may be errors during compilation such as **missing libraries**. The most efficient way to deal with them is to **include them yourself in the files that require them** (`#include <string.h>` for example). One known example is that, in Debian based systems, the Makefile is not generated properly with the `./configure` command, so you have to add the `-lm` option to the `LIBS` macro in the Makefile.`

- Optionally, type `make check` to run tests that come with the package
- Type `make install` to install the programs

The file we are interested in is the `sfbcep` executable which is now in the current folder

## Install alize-core

- [Install alize-core](https://github.com/ALIZE-Speaker-Recognition/alize-core)
- Extract `alize-core` and launch a terminal **in the resulting folder**
- Type `aclocal`
- Type `automake`

> The `compile` file may be missing. If so, run `automake --add-missing`

- Type `autoconf`
- Type `./configure`
- Type `make`

## Install LIA_RAL

[Install LIA_RAL](https://github.com/ALIZE-Speaker-Recognition/LIA_RAL)

Warning: `alize-core` and `SPro` must be in the **same directory** than `LIA_RAL` (you must install them **first**).

You can also change the installation path doing `./configure`.

- Extract `LIA_RAL` and launch a terminal **in the resulting folder**
- Type `aclocal`.
- Type `automake`

> Like for `alize-core`, the `compile` file may be missing. If so, run `automake --add-missing`

- Type `autoconf`.
- Type `./configure --with-spro`
- Type `make`

**The files we are interested in are the executables `EnergyDetector`, `NormFeat`, `TrainWorld`, (not yet implemented) `TrainTarget` and `ComputeTest`** which are in the bin folder of LIA_RAL

## How to implement ALIZE + Spro (temporary)

To implement the features of `Spro` and `Alize` in the game, you will have to remove the executables (not the .sh's) that are in the src/main/exe/model directory of the game:

- EnergyDetector (`LIA_RAL`)
- NormFeat (`LIA_RAL`)
- TrainWorld (`LIA_RAL`)
- (Not yet implemented) TrainTarget (`LIA_RAL`)
- ComputeTest (`LIA_RAL`)
- sfbcep (`Spro`)

And replace them by copying the executables of the same name, located respectively in the `LIA_RAL/bin` and `Spro` folders.

(You can also replace the paths in the sh files with their positions in your folders: to do this, replace the `EXE_DIR` variables in all .sh files in src/main/exe/model to specify the path.)

`Spro` and `ALIZE` are now implemented !
