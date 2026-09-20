#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cd "$SCRIPT_DIR"

MAX=100

for (( i = 1; i <= MAX; i++)); do
    NAME="0"

    if [ "$i" -lt 100 ]; then
        NAME+="0"
    fi

    if [ "$i" -lt 10 ]; then
        NAME+="0"
    fi

    NAME+="$i"
    NAME+="0.md"

    touch blank_files/"$NAME"
done