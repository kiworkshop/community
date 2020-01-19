#!/usr/bin/env bash

python3 6-codepipeline-cf-template.py > 6-codepipeline-cf.template

aws --profile ki cloudformation create-stack \
--stack-name community-mother-api-dev-codepipeline \
--capabilities CAPABILITY_NAMED_IAM \
--template-body file://6-codepipeline-cf.template