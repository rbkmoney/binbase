#!/bin/bash
function onExit {
    pg_ctl -D /var/lib/postgresql/@postgresql.version@/data stop -w
}
trap onExit EXIT

pg_ctl -D /var/lib/postgresql/@postgresql.version@/data start -w
$@
exit $?