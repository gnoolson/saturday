#!/bin/bash

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$SCRIPT_DIR"

set -e
source .env

if [[ "$(docker images -q saturday:$REVISION 2> /dev/null)" == "" ]]; then
  docker load -i "saturday-$REVISION.tar"
fi

docker compose up -d