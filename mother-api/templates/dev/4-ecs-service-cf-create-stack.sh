#!/usr/bin/env bash

python3 4-ecs-service-cf-template.py > 4-ecs-service-cf.template

# services are automatically created by pipeline.
aws --profile ki cloudformation update-stack \
--stack-name community-mother-api-dev-service \
--capabilities CAPABILITY_IAM \
--template-body file://4-ecs-service-cf.template \
--parameters \
ParameterKey=Tag,ParameterValue=latest
