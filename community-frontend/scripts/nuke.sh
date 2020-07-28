#!/bin/sh

for d in app/*/node_modules; do echo $d; rm -rf $d; done
# for d in core/*/node_modules; do echo $d; rm -rf $d; done

rm -rf node_modules