#!/bin/sh
rm -rf .next && npm run build && rm -rf node_modules && npm i --production && cp -a '../../node_modules/@community' 'node_modules/@community' && cp -a '../../packages' .
