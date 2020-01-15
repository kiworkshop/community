#!/usr/bin/env bash

python3 5-codebuild-cf-template.py > 5-codebuild-cf.template

aws --profile ki cloudformation create-stack \
--stack-name community-mother-api-dev-codebuild \
--capabilities CAPABILITY_IAM \
--template-body file://5-codebuild-cf.template