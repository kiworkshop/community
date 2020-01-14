#!/usr/bin/env bash

python3 5-codebuild-cf-template.py > 5-codebuild-cf.template

awsecr cloudformation create-stack \
--stack-name community_mother_api-codebuild \
--capabilities CAPABILITY_IAM \
--template-body file://5-codebuild-cf.template