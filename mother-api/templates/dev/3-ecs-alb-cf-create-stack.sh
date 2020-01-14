#!/usr/bin/env bash

python3 3-ecs-alb-cf-template.py > 3-ecs-alb-cf.template

awsecr cloudformation create-stack \
--stack-name community_mother_api-staging-alb \
--capabilities CAPABILITY_IAM \
--template-body file://3-ecs-alb-cf.template
