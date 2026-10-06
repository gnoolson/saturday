#!/bin/sh

mkdir -p /opt/saturday/config
mkdir -p /opt/saturday/dashboard_static
mkdir -p /opt/saturday/data
mkdir -p /opt/saturday/plugins
mkdir -p /opt/saturday/log


REQUIRED_FILES=("application.properties" "log4j2.xml")

for FILE in "${REQUIRED_FILES[@]}"; do
    if [ ! -f "/opt/saturday/config/$FILE" ]; then
        cp "/opt/saturday/defaults/config/$FILE" "/opt/saturday/config/$FILE"
    fi
done


if [ -z "$(ls -A /opt/saturday/dashboard_static 2>/dev/null)" ]; then
    cp -r /opt/saturday/defaults/dashboard_static/. /opt/saturday/dashboard_static/
fi

if [ -z "$(ls -A /opt/saturday/plugins 2>/dev/null)" ]; then
    cp -r /opt/saturday/defaults/plugins/. /opt/saturday/plugins/
fi


exec java -jar app.jar