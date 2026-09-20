FROM redhat/ubi9-minimal:9.4

COPY ./jre /opt/jre
COPY ./saturday /opt/saturday

ENV JAVA_HOME=/opt/jre
ENV PATH="${JAVA_HOME}/bin:${PATH}"

EXPOSE 5050
EXPOSE 1883

RUN chmod +x /opt/saturday/entrypoint.sh

WORKDIR /opt/saturday

ENTRYPOINT ["/opt/saturday/entrypoint.sh"]