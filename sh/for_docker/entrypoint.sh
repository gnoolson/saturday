#!/bin/sh

mkdir -p /opt/saturday/config
mkdir -p /opt/saturday/dashboard_static
mkdir -p /opt/saturday/data
mkdir -p /opt/saturday/plugins
mkdir -p /opt/saturday/log


if [ -z "$(ls -A /opt/saturday/config 2>/dev/null)" ]; then
    cp -r /opt/saturday/defaults/config/. /opt/saturday/config/
fi

if [ -z "$(ls -A /opt/saturday/dashboard_static 2>/dev/null)" ]; then
    cp -r /opt/saturday/defaults/dashboard_static/. /opt/saturday/dashboard_static/
fi

if [ -z "$(ls -A /opt/saturday/plugins 2>/dev/null)" ]; then
    cp -r /opt/saturday/defaults/plugins/. /opt/saturday/plugins/
fi


exec java -jar app.jar