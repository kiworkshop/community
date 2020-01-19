#!/usr/bin/env bash

python3 3-ecs-alb-cf-template.py > 3-ecs-alb-cf.template

aws --profile ki cloudformation update-stack \
--stack-name community-mother-api-dev-alb \
--capabilities CAPABILITY_IAM \
--template-body file://3-ecs-alb-cf.template
