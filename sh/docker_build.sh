#!/usr/bin/env bash

set -euo pipefail


# -----------------------------------------------------------------------------

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
PROJECT_DIR="$(cd "$SCRIPT_DIR/.." && pwd)"

cd "$PROJECT_DIR"

JRE_FOLDER="/data/application/linux/JDK/JRE/jdk-21.0.12+8-jre"

DIST_FOLDER="_dist"
DOCKER_FOLDER="$DIST_FOLDER/docker"
TEMP_FOLDER="$DOCKER_FOLDER/temp"
TEMP_FOLDER_SATURDAY="$TEMP_FOLDER/saturday"
TEMP_FOLDER_JRE="$TEMP_FOLDER/jre"

FOLDER_WITH_FILES_FOR_DOCKER="sh/for_docker"
RELEASE_FOLDER="$DOCKER_FOLDER/saturday"


# -----------------------------------------------------------------------------

REVISION="$(
    mvn help:evaluate \
        -Dexpression=project.version \
        -q \
        -DforceStdout
)"

echo "Building Saturday version: $REVISION"


# -----------------------------------------------------------------------------

rm -rf "$DOCKER_FOLDER"

mkdir -p \
    "$TEMP_FOLDER_SATURDAY" \
    "$TEMP_FOLDER_JRE" \
    "$RELEASE_FOLDER"


# -----------------------------------------------------------------------------

if [[ ! -d "$JRE_FOLDER" ]]; then
    echo "ERROR: JRE directory does not exist: $JRE_FOLDER" >&2
    exit 1
fi

echo "Copying JRE..."
cp -a "$JRE_FOLDER"/. "$TEMP_FOLDER_JRE"/


# -----------------------------------------------------------------------------

echo "Building application..."
mvn clean install

echo "Copying application..."
cp "app/target/app.jar" "$TEMP_FOLDER_SATURDAY/app.jar"


# -----------------------------------------------------------------------------

mkdir -p \
    "$TEMP_FOLDER_SATURDAY/config" \
    "$TEMP_FOLDER_SATURDAY/defaults/config" \
    "$RELEASE_FOLDER/config"

cp -a "config/for_build/." "$TEMP_FOLDER_SATURDAY/defaults/config/"


# -----------------------------------------------------------------------------

mkdir -p \
    "$TEMP_FOLDER_SATURDAY/dashboard_static" \
    "$TEMP_FOLDER_SATURDAY/defaults/dashboard_static" \
    "$RELEASE_FOLDER/dashboard_static"

cp -a "dashboard_static/." "$TEMP_FOLDER_SATURDAY/defaults/dashboard_static/"


# -----------------------------------------------------------------------------

mkdir -p \
    "$TEMP_FOLDER_SATURDAY/data" \
    "$RELEASE_FOLDER/data"


# -----------------------------------------------------------------------------

mkdir -p "$TEMP_FOLDER_SATURDAY/demo"

cp "demo/demo.zip" "$TEMP_FOLDER_SATURDAY/demo/"


# -----------------------------------------------------------------------------

mkdir -p \
    "$TEMP_FOLDER_SATURDAY/log/scripts" \
    "$RELEASE_FOLDER/log/scripts"


# -----------------------------------------------------------------------------

mkdir -p "$TEMP_FOLDER_SATURDAY/lib"

cp -a "lib/stylua" "$TEMP_FOLDER_SATURDAY/lib/"


# -----------------------------------------------------------------------------

mkdir -p \
    "$TEMP_FOLDER_SATURDAY/plugins" \
    "$TEMP_FOLDER_SATURDAY/defaults/plugins" \
    "$RELEASE_FOLDER/plugins"

copy_plugin() {
    local plugin="$1"
    local plugin_source="../saturday-plugins/$plugin"
    local plugin_target="$TEMP_FOLDER_SATURDAY/defaults/plugins/$plugin"

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

mkdir -p "$TEMP_FOLDER_SATURDAY/template"

cp -a "template/." "$TEMP_FOLDER_SATURDAY/template/"


# -----------------------------------------------------------------------------

mkdir -p "$TEMP_FOLDER_SATURDAY/web"

cp -a "web/messages" "$TEMP_FOLDER_SATURDAY/web/"

cp -a "web/static" "$TEMP_FOLDER_SATURDAY/web/"

cp -a "web/templates" "$TEMP_FOLDER_SATURDAY/web/"


# -----------------------------------------------------------------------------

cp "$FOLDER_WITH_FILES_FOR_DOCKER/Dockerfile" "$TEMP_FOLDER/Dockerfile"

cp "$FOLDER_WITH_FILES_FOR_DOCKER/entrypoint.sh" "$TEMP_FOLDER_SATURDAY/entrypoint.sh"

cp "$FOLDER_WITH_FILES_FOR_DOCKER/compose.yaml" "$RELEASE_FOLDER/compose.yaml"

cp "$FOLDER_WITH_FILES_FOR_DOCKER/start.sh" "$RELEASE_FOLDER/start.sh"

cp "$FOLDER_WITH_FILES_FOR_DOCKER/stop.sh" "$RELEASE_FOLDER/stop.sh"

cp "$FOLDER_WITH_FILES_FOR_DOCKER/start.bat" "$RELEASE_FOLDER/start.bat"

cp "$FOLDER_WITH_FILES_FOR_DOCKER/stop.bat" "$RELEASE_FOLDER/stop.bat"

chmod +x \
    "$RELEASE_FOLDER/start.sh" \
    "$RELEASE_FOLDER/stop.sh"


# -----------------------------------------------------------------------------

echo "Building Docker image: saturday:$REVISION"

docker build -t "saturday:$REVISION" "$TEMP_FOLDER"


echo "Saving Docker image..."

docker save -o "$RELEASE_FOLDER/saturday-$REVISION.tar" "saturday:$REVISION"



# -----------------------------------------------------------------------------
rm -rf $TEMP_FOLDER



# -----------------------------------------------------------------------------

printf 'REVISION=%s\n' "$REVISION" > "$RELEASE_FOLDER/.env"


# -----------------------------------------------------------------------------
echo
echo "Build completed successfully."
echo "Version: $REVISION"
echo "Release: $RELEASE_FOLDER"