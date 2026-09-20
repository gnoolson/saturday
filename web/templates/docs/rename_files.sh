#!/usr/bin/env bash

set -euo pipefail

DIR="${1:-.}"

if [ ! -d "$DIR" ]; then
    echo "Directory does not exist"
    exit 0
fi

cd "$DIR"

mapfile -t files < <(ls -1)

for file in "${files[@]}"; do
    mv "$file" ___"$file"
done

COUNTER=1

for file in "${files[@]}"; do
    NEW_FILE_NAME=""

    if [ "$COUNTER" -lt 100 ]; then
        NEW_FILE_NAME+="0"
    fi

    if [ "$COUNTER" -lt 10 ]; then
        NEW_FILE_NAME+="0"
    fi

    NEW_FILE_NAME+="$COUNTER"
    NEW_FILE_NAME+="0.md"

    COUNTER=$((COUNTER + 1))

    mv  ___"$file" "$NEW_FILE_NAME"
done