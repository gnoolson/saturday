#!/usr/bin/env bash

set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"

cd "$SCRIPT_DIR"

merge_parts() {
  rm -f "$1"/index.md

  touch "$1"/index.md

  for file in "$1"/parts/*.md; do
      cat "$file" >> "$1"/index.md
      printf "\r\n\r\n\r\n\r\n" >> "$1"/index.md
  done
}

merge_parts uk/client
merge_parts uk/dashboard
merge_parts uk/lua
merge_parts uk/lua_modules
merge_parts uk/plugin
merge_parts uk/project
merge_parts uk/schedule
merge_parts uk/script
merge_parts uk/subscription

merge_parts en/client
merge_parts en/dashboard
merge_parts en/lua
merge_parts en/lua_modules
merge_parts en/plugin
merge_parts en/project
merge_parts en/schedule
merge_parts en/script
merge_parts en/subscription