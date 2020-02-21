FROM @dockerfile.registry@/rbkmoney/service-java:@dockerfile.base.service.tag@ as service-java
FROM @dockerfile.registry@/rbkmoney/build:@dockerfile.build.tag@ as build

COPY --from=service-java / /tmp/portage-root/

ENV PGDATA "/etc/postgresql-@postgresql.version@/"
ENV DATA_DIR "/var/lib/postgresql/@postgresql.version@/data"
ENV PG_INITDB_OPTS "--username=postgres --auth-host=trust --auth-local=trust --locale=en_US.UTF-8"

ENV ROOT=/tmp/portage-root
RUN mkdir /etc/postgresql-@postgresql.version@/ && echo dev-db/postgresql server > /etc/portage/package.use/postgresql \
    && git clone git://git.bakka.su/gentoo-mirror --depth 1 /usr/portage \
    && emerge --usepkgonly sys-libs/ncurses && emerge --usepkgonly dev-db/postgresql:@postgresql.version@ sys-libs/readline \
    && emerge --config dev-db/postgresql:@postgresql.version@

FROM @dockerfile.registry@/rbkmoney/service-java:@dockerfile.base.service.tag@
ENV LOG_PATH /tmp/
COPY --from=build /tmp/portage-root/ /
COPY --from=build /var/lib/postgresql/@postgresql.version@/data /var/lib/postgresql/@postgresql.version@/data
COPY --from=build /etc/postgresql-@postgresql.version@ /etc/postgresql-@postgresql.version@
COPY entrypoint.sh /opt/@artifactId@/entrypoint.sh
COPY data /opt/@artifactId@/data

RUN echo postgres:x:70: >> /etc/group \
    && echo postgres:x:70:70::/var/lib/postgresql:/bin/sh >> /etc/passwd \
    && echo postgres:!:17725:::::: >> /etc/shadow \
    && mkdir /run /run/postgresql

RUN chmod 0700 -R /var/lib/postgresql/@postgresql.version@/data \
    && chown postgres:postgres -R \
        /var/lib/postgresql/@postgresql.version@/data \
        /etc/postgresql-@postgresql.version@ \
        /run/postgresql \
        /opt/@artifactId@ \
    && chmod +x /opt/@artifactId@/entrypoint.sh

USER postgres
COPY @artifactId@-@version@.jar /opt/@artifactId@/@artifactId@.jar
RUN pg_ctl -D /var/lib/postgresql/@postgresql.version@/data start -w \
    && psql --command "ALTER USER postgres WITH SUPERUSER PASSWORD '@db.password@';" \
    && createdb -O postgres @db.name@ "@description@" \
    && java -jar /opt/@artifactId@/@artifactId@.jar com.rbkmoney.binbase.config.BatchConfig binBaseJob --logging.level.com.rbkmoney.binbase=ERROR --logging.level.com.rbkmoney.binbase.batch.listener.DefaultChunkListener=INFO --batch.file_path=file:/opt/@artifactId@/data --batch.shutdown_after_execute=true \
    && pg_ctl -D /var/lib/postgresql/@postgresql.version@/data stop -w \
    && rm -rf /opt/@artifactId@/data/*

WORKDIR /opt/@artifactId@
ENTRYPOINT ["/opt/@artifactId@/entrypoint.sh"]
CMD ["java", "-Xmx256m", "-jar","/opt/@artifactId@/@artifactId@.jar", "--spring.batch.job.enabled=false"]

EXPOSE @exposed.ports@

MAINTAINER @project.maintainer@
LABEL com.rbkmoney.@artifactId@.parent=service-java \
    com.rbkmoney.@artifactId@.parent_tag=@dockerfile.base.service.tag@ \
    com.rbkmoney.@artifactId@.commit_id=@git.commit.id@ \
    com.rbkmoney.@artifactId@.branch=@git.branch@
