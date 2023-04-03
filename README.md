# 2022-SB1-GA-GYTMY

- [2022-SB1-GA-GYTMY](#2022-sb1-ga-gytmy)
  - [Project: `Voice-controlled movement of characters`](#project-voice-controlled-movement-of-characters)
  - [Assigned professor](#assigned-professor)
  - [Students](#students)
  - [What is this project about ?](#what-is-this-project-about-)
  - [Game modes](#game-modes)
    - [`"Classic Mode"`](#classic-mode)
    - [`"One-Dimensional Mode"`](#one-dimensional-mode)
    - [`"Blackout Mode"`](#blackout-mode)
  - [What is ALIZE](#what-is-alize)
  - [What is Spro](#what-is-spro)
  - [How to install ALIZE and Spro](#how-to-install-alize-and-spro)
  - [Intall Spro](#intall-spro)
  - [Install alize-core](#install-alize-core)
  - [Intall LIA_RAL](#intall-lia_ral)
  - [How to implement ALIZE + Spro (temporary)](#how-to-implement-alize--spro-temporary)

This project was realized by GYTMY for the unit `Projet de programmation (PI4)` during Year 2 Second Semester of `Double Licence Mathématiques-Informatique` (2022-2023).

## Project: `Voice-controlled movement of characters`

## Assigned professor

Sami Boutamine (sami.boutamine@utc.fr)

## Students

| Student ID # |    Last Name     | First Name | Group |                Email                 |
| :----------: | :--------------: | :--------: | :---: | :----------------------------------: |
|   22103458   |    DUDILLIEU     |   Gabin    |  MI2  |         gdudillieu@gmail.com         |
|   22101699   | IGLESIAS VAZQUEZ |    Yago    |  MI2  | yago.iglesias-vazquez@etu.u-paris.fr |
|   22107803   |       SOAN       |  Tony Ly   |  MI2  |     tony-ly.soan@etu.u-paris.fr      |
|   22112498   |    SELVAKUMAR    |  Mathusan  |  MI2  |    mathusan.selvakumar@gmail.com     |
|   22103173   |     LACENNE      |   Yanis    |  MI2  |     yanis.lacenne@etu.u-paris.fr     |

## What is this project about ?

The goal of this project is to be able to control the movements of characters in a Maze game using real-time voice commands such as saying out loud `Up`, `Down`, `Left` or `Right`.

## Game modes

### `"Classic Mode"`

![Image Classic Mode](images/ClassicMode.png)

A simple single or multi-player mode where you have to reach the end of the maze.

You can also choose the `width` and the `height` of the labyrinth.

### `"One-Dimensional Mode"`

![Image One-Dimensional Mode](images/OneDimensionalMode.png)

An extremely simple single or multi-player mode where you have to reach the end of the corridor.

You can also choose the `length` of the labyrinth.

### `"Blackout Mode"`

|                         Lights on                          |                        Lights out                        |
| :--------------------------------------------------------: | :------------------------------------------------------: |
| ![Image Blackout Mode Light](images/BlackoutModeLight.png) | ![Image Blackout Mode Dark](images/BlackoutModeDark.png) |

A fun single-player mode where your memory skills will comme in handy to get yourself through the darkness.

There are 3 difficulties available : `EASY`, `NORMAL` and `HARD`.

## What is ALIZE

ALIZE est une librairie, qui est de base codé en c, et qui sert à la reconnaissance d'un individu.

Dans notre programme, on l'utilise afin de reconnaitre une personne ou un mot en particulier à partir d'un modèle qu'on crée aussi avec Alize (et Spro)

Pour cela, on utilise les exectubles qui sont à disposition dans le document bin du LIA_RAL de d'ALIZE

Les executable qu'on utilise (est amené à évoluer) sont :

- _EnergyDetector_ qui prend des paramètres et qui donnent des label donnant comme information les passages ou le client parle.
- _NormFeat_ qui avec les label et les paramètres, normalise ces paramètres.
- _TrainWorld_ qui sert à créer le modèle en lui donnant les paramètres normalisé ainsi que les labels.
- (Pas encore implémenté) _TrainTarget_ qui permet d'entrainer un paramètre en particulier (donc un audio quand on enregistre le client).
- (Pas encore implémenté) _ComputeScore_ qui donne le score de reconnaissance du modèle train par TrainTarger, par rapport aux autres modèles formés.

## What is Spro

Spro est une librairie qui est aussi en c, qui nous sert ici à extraire les paramètres d'un audio pour pouvoir les fournir à ALIZE.

Pour cela, on utilise l'executable _sfbcep_ qui donne chaque paramètre de chaque audios.

## How to install ALIZE and Spro

Si t'es sur windows, tu peux le convertir en tools sur windows (not tested)

Prerequis :

- aclocal
- autoconf
- automake
- libtool

**Installer les librairies dans l'ordre !**

## Intall Spro

- [Install SPro 4.0.1](http://www.irisa.fr/metiss/guig/spro/spro-4.0.1/spro-4.0.1.tar.gz)

- Désarchiver le fichier `tar` et lancer le terminal **dans le dossier résultant**
- Taper `./configure` pour configurer le paquet pour votre système
- Taper `make` pour compiler le paquet

> Des erreurs lors de la compilation telles que **les bibliothèques manquantes sont possibles**. Le moyen le plus efficace pour les traiter est de **les inclure soi-même dans les fichiers qui les nécessitent** (`#include <string.h>` par exemple)

- Optionnellement, taper `make check` pour exécuter des tests fournis avec le paquet
- Taper `make install` pour installer les programmes
- **Le fichier qui nous intéresse est l’exécutable `sfbcep`** qui se trouve à présent dans le dossier courant

## Install alize-core

- [Intall alize-core](https://github.com/ALIZE-Speaker-Recognition/alize-core)
- Désarchiver alize-core et lancer le terminal dans le dossier résultant.
- Taper `aclocal`
- Taper `automake` (si le ./compile est manquant, faire automake --add-missing)
- Taper `autoconf`
- Taper `./configure`
- Taper `make`

## Intall LIA_RAL

[Intall LIA_RAL](https://github.com/ALIZE-Speaker-Recognition/LIA_RAL)

Vous avez besoin pour l'installer des mêmes éléments que pour alize-core + spro pour le configure, qui doivent se situer dans le même répertoire (vous devez les installer avant)

- Désarchiver LIA_RAL et lancer le terminal dans le dossier résultant.
- Taper `aclocal`
- Taper `automake` (si le ./compile est manquant, faire automake --add-missing)
- Taper `autoconf`
- Taper `./configure`
- Taper `make`

**Les fichiers qui nous intéresse sont les exécutables `EnergyDetector`, `NormFeat`, `TrainWorld`, (pas encore implémenté) `TrainTarger` et (pas encore implèmenté)`ComputeScore`** qui sont dans le dossier bin de LIA_RAL

## How to implement ALIZE + Spro (temporary)

Pour implémenter les fonctionnalités de Spro et Alize dans le jeu, il va falloir supprimer les executables (pas les .sh):

- EnergyDetector
- NormFeat
- TrainWorld
- (Pas encore implémenté) TrainTarget
- (Pas encore implémenté) ComputeScore
- sfbcep

Et les remplacer en copiant les executable du même noms, situés respectivement dans les dossiers LIA_RAL/bin et Spro.

(Vous pouvez aussi remplacer les chemins dans les sh par leurs positions dans vos dossier).

Voila, Spro et ALIZE sont implémentés.
