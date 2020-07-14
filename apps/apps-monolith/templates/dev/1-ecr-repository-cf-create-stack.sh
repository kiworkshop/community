#!/usr/bin/env bash

python3 1-ecr-repository-cf-template.py > 1-ecr-repository-cf.template

aws --profile ki cloudformation create-stack \
--stack-name community-mother-api-dev-ecr \
--capabilities CAPABILITY_IAM \
--template-body file://1-ecr-repository-cf.template \
--parameters \
ParameterKey=RepoName,ParameterValue=community-mother-api-dev
