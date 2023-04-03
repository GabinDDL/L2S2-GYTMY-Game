# Install ALIZE and Spro

If this is an installation on Windows, there is the possibility to convert into tools (not tested).

Prerequisites (on linux ubuntu, it can be installed with the apt-get) :

- `aclocal` (install autotools-dev if it doesn't works)
- `autoconf`
- `automake`
- `libtool`

**Install the libraries in order!** and if possible in the same repertory.

## Install Spro

- [Install SPro 4.0.1](http://www.irisa.fr/metiss/guig/spro/spro-4.0.1/spro-4.0.1.tar.gz)
- Extract the `tar` file and run the terminal **in the resulting folder**.
- Type `./configure` to configure the package for your system
- Type `make` to compile the package

> There may be errors during compilation such as **missing libraries are possible**. The most efficient way to deal with them is to **include them yourself in the files that require them** (`#include <string.h>` for example).

- Optionally, type `make check` to run tests that come with the package
- Type `make install` to install the programs
- The file we are interested in is the `sfbcep` executable which is now in the current folder

## Install alize-core

- [Intall alize-core](https://github.com/ALIZE-Speaker-Recognition/alize-core)
- Extract `alize-core` and launch the terminal \*\*in the resulting folder
- Type `aclocal`
- Type `automake` (if the ./compile is missing, do `automake --add-missing`)
- Type `autoconf`
- Type `./configure`
- Type `make`

## Intall LIA_RAL

[Intall LIA_RAL](https://github.com/ALIZE-Speaker-Recognition/LIA_RAL)

Warning: `alize-core` and `Spro` must be in the same directory that `LIA_RAL` (you must install them first).

You can also change the installation path before doing `./configure`.

- Extract `LIA_RAL` and launch the terminal \*\*in the resulting folder
- Type `aclocal`.
- Type `automake` (if the ./compile is missing, do automake --add-missing)
- Type `autoconf`.
- Type `./configure --with-spro`
- Type `make`

**The files we are interested in are the executables `EnergyDetector`, `NormFeat`, `TrainWorld`, (not yet implemented) `TrainTarget` and (not yet implemented) `ComputeScore`** which are in the bin folder of LIA_RAL

## How to implement ALIZE + Spro (temporary)

To implement the features of `Spro` and `Alize` in the game, you will have to remove the executables (not the .sh's) that are in the src/main/exe/model directory of the game:

- EnergyDetector (`LIA_RAL`)
- NormFeat (`LIA_RAL`)
- TrainWorld (`LIA_RAL`)
- (Not yet implemented) TrainTarget (`LIA_RAL`)
- (Not yet implemented) ComputeScore (`LIA_RAL`)
- sfbcep (`Spro`)

And replace them by copying the executables of the same name, located respectively in the `LIA_RAL/bin` and `Spro` folders.

(You can also replace the paths in the sh files with their positions in your folders: to do this, replace the `EXE_DIR` variables in all .sh files in src/main/exe/model to specify the path.)

`Spro` and `ALIZE` are now implemented !
