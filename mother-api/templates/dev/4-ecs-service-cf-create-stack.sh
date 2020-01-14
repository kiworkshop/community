#!/usr/bin/env bash

python3 4-ecs-service-cf-template.py > 4-ecs-service-cf.template

# services are automatically created by pipeline.
awsecr cloudformation update-stack \
--stack-name community_mother_api-staging-service \
--capabilities CAPABILITY_IAM \
--template-body file://4-ecs-service-cf.template \
--parameters \
ParameterKey=Tag,ParameterValue=latest