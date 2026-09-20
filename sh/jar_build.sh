#!/usr/bin/env bash

set -euo pipefail


# -----------------------------------------------------------------------------

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

cd "$PROJECT_DIR"


DIST_FOLDER="_dist"
JAR_FOLDER="$DIST_FOLDER/jar"
FOLDER_WITH_FILES_FOR_JAR="sh/for_jar"
RELEASE_FOLDER="$JAR_FOLDER/saturday"

# -----------------------------------------------------------------------------

REVISION="$(
    mvn help:evaluate \
        -Dexpression=project.version \
        -q \
        -DforceStdout
)"

echo "Building Saturday version: $REVISION"

rm -rf "$JAR_FOLDER"

mkdir -p "$RELEASE_FOLDER"

# -----------------------------------------------------------------------------

echo "Building application..."
mvn clean install

echo "Copying application..."
cp "app/target/app.jar" "$RELEASE_FOLDER/app.jar"


# -----------------------------------------------------------------------------

mkdir -p "$RELEASE_FOLDER/config"

cp -a "config/for_build/." "$RELEASE_FOLDER/config/"


# -----------------------------------------------------------------------------

mkdir -p "$RELEASE_FOLDER/dashboard_static"

cp -a "dashboard_static/." "$RELEASE_FOLDER/dashboard_static/"


# -----------------------------------------------------------------------------

mkdir -p  "$RELEASE_FOLDER/data"

# -----------------------------------------------------------------------------

mkdir -p "$RELEASE_FOLDER/demo"

cp "demo/demo.zip" "$RELEASE_FOLDER/demo/"

# -----------------------------------------------------------------------------

mkdir -p "$RELEASE_FOLDER/log/scripts"

# -----------------------------------------------------------------------------

mkdir -p "$RELEASE_FOLDER/lib"

cp -a "lib/stylua" "$RELEASE_FOLDER/lib/"


# -----------------------------------------------------------------------------

mkdir -p "$RELEASE_FOLDER/plugins"

copy_plugin() {
    local plugin="$1"
    local plugin_source="../saturday-plugins/$plugin"
    local plugin_target="$RELEASE_FOLDER/plugins/$plugin"

    mkdir -p "$plugin_target"

    if [[ -f "$plugin_source/target/$plugin-plugin.jar" ]]; then
        cp "$plugin_source/target/$plugin-plugin.jar" \
            "$plugin_target/"
    fi

    if [[ -d "$plugin_source/for_build" ]]; then
        cp -a "$plugin_source/for_build/." \
            "$plugin_target/"
    fi
}

for plugin in \
    cache \
    db \
    http-client \
    json \
    locker \
    now \
    telegram-bot \
    timer \
    serial
do
    copy_plugin "$plugin"
done


# -----------------------------------------------------------------------------

mkdir -p "$RELEASE_FOLDER/template"

cp -a "template/." "$RELEASE_FOLDER/template/"


# -----------------------------------------------------------------------------

mkdir -p "$RELEASE_FOLDER/web"

cp -a "web/messages" "$RELEASE_FOLDER/web/"

cp -a "web/static" "$RELEASE_FOLDER/web/"

cp -a "web/templates" "$RELEASE_FOLDER/web/"


# -----------------------------------------------------------------------------


cp "$FOLDER_WITH_FILES_FOR_JAR/start.sh" "$RELEASE_FOLDER/start.sh"
cp "$FOLDER_WITH_FILES_FOR_JAR/start.bat" "$RELEASE_FOLDER/start.bat"
