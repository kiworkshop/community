"""Generating CloudFormation template."""
from awacs.aws import (
    Allow,
    Policy,
    Principal,
    Statement
)

from awacs.sts import AssumeRole
from troposphere import (
    Select,
    Split,
    Join,
    Ref,
    Template
)
from troposphere.codebuild import (
    Artifacts,
    Environment,
    Project,
    Source
)
from troposphere.iam import Role

t = Template()
t.set_description("community-mother-api-dev: CodeBuild - community-mother-api-dev container")

t.add_resource(Role(
    "ServiceRole",
    AssumeRolePolicyDocument=Policy(
        Statement=[
            Statement(
                Effect=Allow,
                Action=[AssumeRole],
                Principal=Principal("Service", ["codebuild.amazonaws.com"])
            )
        ]
    ),
    Path="/",
    ManagedPolicyArns=[
        'arn:aws:iam::aws:policy/AWSCodePipelineReadOnlyAccess',
        'arn:aws:iam::aws:policy/AwsCodeBuildDeveloperAccess',
        'arn:aws:iam::aws:policy/AmazonEC2ContainerRegistryPowerUser',
        'arn:aws:iam::aws:policy/AmazonS3FullAccess',
        'arn:aws:iam::aws:policy/CloudWatchLogsFullAccess',
    ]
))

environment = Environment(
    ComputeType='BUILD_GENERAL1_SMALL',
    Image='aws/codebuild/docker:18.09.0',
    Type='LINUX_CONTAINER',
    EnvironmentVariables=[
        {'Name': 'REPOSITORY_NAME', 'Value': Join(
                                            "-",
                                            [Select(0, Split("-", Ref("AWS::StackName"))),
                                             Select(1, Split("-", Ref("AWS::StackName"))),
                                             Select(2, Split("-", Ref("AWS::StackName"))),
                                             Select(3, Split("-", Ref("AWS::StackName")))]
                                        )},
        {'Name': 'REPOSITORY_URI',
         'Value': Join("", [
             Ref("AWS::AccountId"),
             ".dkr.ecr.",
             Ref("AWS::Region"),
             ".amazonaws.com",
             "/",
             "community-mother-api-dev"])},
        {'Name': 'APPLICATION_ENV', 'Value': 'BAR=bar BAZ=baz'},
    ]
)

buildspec = """version: 0.1
phases:
  pre_build:
    commands:
      - aws codepipeline get-pipeline-state --name "${CODEBUILD_INITIATOR##*/}" --query stageStates[?actionStates[0].latestExecution.externalExecutionId==\`$CODEBUILD_BUILD_ID\`].latestExecution.pipelineExecutionId --output=text > /tmp/execution_id.txt
      - aws codepipeline get-pipeline-execution --pipeline-name "${CODEBUILD_INITIATOR##*/}" --pipeline-execution-id $(cat /tmp/execution_id.txt) --query 'pipelineExecution.artifactRevisions[0].revisionId' --output=text > /tmp/tag.txt
      - printf "%s:%s" "$REPOSITORY_URI" "$(cat /tmp/tag.txt)" > /tmp/build_tag.txt
      - printf '{"tag":"%s", "application_env":"%s"}' "$(cat /tmp/tag.txt)" "$(echo $APPLICATION_ENV)" | tee /tmp/build.json
      - $(aws ecr get-login --no-include-email)
  build:
    commands:
      - docker build -f mother-api.Dockerfile -t "$(cat /tmp/build_tag.txt)" .
  post_build:
    commands:
      - docker push "$(cat /tmp/build_tag.txt)"
      - aws ecr batch-get-image --repository-name $REPOSITORY_NAME --image-ids imageTag="$(cat /tmp/tag.txt)" --query 'images[].imageManifest' --output=text | tee /tmp/latest_manifest.json
      - aws ecr put-image --repository-name $REPOSITORY_NAME --image-tag latest --image-manifest file:///tmp/latest_manifest.json
artifacts:
  files: /tmp/build.json
  discard-paths: yes
"""

t.add_resource(Project(
    "CodeBuild",
    Name='CommunityMotherApiDevContainer',
    Environment=environment,
    ServiceRole=Ref("ServiceRole"),
    Source=Source(
        Type="CODEPIPELINE",
        BuildSpec=buildspec
    ),
    Artifacts=Artifacts(
        Type="CODEPIPELINE",
        Name="output"
    ),
))

print(t.to_json())
