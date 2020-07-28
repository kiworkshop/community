#!/bin/sh
rm -rf .next && npm run build && rm -rf node_modules && npm i --production