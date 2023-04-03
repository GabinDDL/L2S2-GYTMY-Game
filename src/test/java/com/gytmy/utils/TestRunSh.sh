#!/bin/bash

if [ "$1" == "exit0" ]; then
	exit 0
fi
if [ "$1" == "exit1" ]; then
	exit 1
fi
if [ "$2" == "exit2" ]; then
	exit 2
fi
exit 3
