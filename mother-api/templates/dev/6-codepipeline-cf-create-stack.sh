#!/usr/bin/env bash

python3 6-codepipeline-cf-template.py > 6-codepipeline-cf.template

awsecr cloudformation create-stack \
--stack-name community_mother_api-codepipeline \
--capabilities CAPABILITY_NAMED_IAM \
--template-body file://6-codepipeline-cf.template