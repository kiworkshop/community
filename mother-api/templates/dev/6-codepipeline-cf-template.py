"""Generating CloudFormation template."""
from awacs.aws import (
    Allow,
    Policy,
    Principal,
    Statement,
)
from awacs.sts import AssumeRole
from troposphere import (
    Ref,
    GetAtt,
    Template,
    Join,
    Select,
    Split,
)
from troposphere.codepipeline import (
    Actions,
    ActionTypeId,
    ArtifactStore,
    InputArtifacts,
    OutputArtifacts,
    Pipeline,
    Stages
)
from troposphere.iam import Role
from troposphere.iam import Policy as IAMPolicy
from troposphere.s3 import Bucket, VersioningConfiguration

t = Template()
t.set_description("community-mother-api-dev: community-mother-api-dev Pipeline")

t.add_resource(Bucket(
    "S3Bucket",
    VersioningConfiguration=VersioningConfiguration(
        Status="Enabled",
    )
))

t.add_resource(Role(
    "PipelineRole",
    AssumeRolePolicyDocument=Policy(
        Statement=[
            Statement(
                Effect=Allow,
                Action=[AssumeRole],
                Principal=Principal("Service",
                                    ["codepipeline.amazonaws.com"])
            )
        ]
    ),
    Path="/",
    Policies=[
        IAMPolicy(
            PolicyName="communityMotherApiDevCodePipeline",
            PolicyDocument={
                "Statement": [
                    {"Effect": "Allow", "Action": "cloudformation:*", "Resource": "*"},
                    {"Effect": "Allow", "Action": "codebuild:*", "Resource": "*"},
                    {"Effect": "Allow", "Action": "codepipeline:*", "Resource": "*"},
                    {"Effect": "Allow", "Action": "ecr:*", "Resource": "*"},
                    {"Effect": "Allow", "Action": "ecs:*", "Resource": "*"},
                    {"Effect": "Allow", "Action": "iam:*", "Resource": "*"},
                    {"Effect": "Allow", "Action": "s3:*", "Resource": "*"},
                ]
            }
        )
    ]
))

t.add_resource(Role(
    "CloudFormationCommunityMotherApiDevRole",
    RoleName="CloudFormationCommunityMotherApiDevRole",
    Path="/",
    AssumeRolePolicyDocument=Policy(
        Statement=[
            Statement(
                Effect=Allow,
                Action=[AssumeRole],
                Principal=Principal(
                    "Service",
                  ["cloudformation.amazonaws.com"]
                ),
            )
        ]
    ),
    Policies=[
        IAMPolicy(
            PolicyName="CommunityMotherApiDevCloudFormation",
            PolicyDocument={
                "Statement": [
                    {"Effect": "Allow", "Action": "cloudformation:*", "Resource": "*"},
                    {"Effect": "Allow", "Action": "ecr:*", "Resource": "*"},
                    {"Effect": "Allow", "Action": "ecs:*", "Resource": "*"},
                    {"Effect": "Allow", "Action": "iam:*", "Resource": "*"},
                    {"Effect": "Allow", "Action": "s3:*", "Resource": "*"},
                ]
            }
        )
    ]
))

t.add_resource(Pipeline(
    "CommunityMotherApiDevPipeline",
    RoleArn=GetAtt("PipelineRole", "Arn"),
    ArtifactStore=ArtifactStore(
        Type="S3",
        Location=Ref("S3Bucket")
    ),
    Stages=[
        Stages(
            Name="Source",
            Actions=[
                Actions(
                    Name="Source",
                    ActionTypeId=ActionTypeId(
                        Category="Source",
                        Owner="ThirdParty",
                        Version="1",
                        Provider="GitHub"
                    ),
                    Configuration={
                        "Owner": "ToBeConfiguredLater",
                        "Repo": "ToBeConfiguredLater",
                        "Branch": "ToBeConfiguredLater",
                        "OAuthToken": "ToBeConfiguredLater",
                    },
                    OutputArtifacts=[
                        OutputArtifacts(
                            Name="App"
                        )
                    ]
                )
            ]
        ),
        Stages(
            Name="Build",
            Actions=[
                Actions(
                    Name="Container",
                    ActionTypeId=ActionTypeId(
                        Category="Build",
                        Owner="AWS",
                        Version="1",
                        Provider="CodeBuild"
                    ),
                    Configuration={
                        "ProjectName": "CommunityMotherApiDevContainer",
                    },
                    InputArtifacts=[
                        InputArtifacts(
                            Name="App"
                        )
                    ],
                    OutputArtifacts=[
                        OutputArtifacts(
                            Name="BuildOutput"
                        )
                    ]
                )
            ]
        ),
        Stages(
            Name="Run",
            Actions=[
                Actions(
                    Name="Deploy",
                    ActionTypeId=ActionTypeId(
                        Category="Deploy",
                        Owner="AWS",
                        Version="1",
                        Provider="CloudFormation"
                    ),
                    Configuration={
                        "ChangeSetName": "Deploy",
                        "ActionMode": "CREATE_UPDATE",
                        "StackName": Join(
                                         "-",
                                         [Select(0, Split("-", Ref("AWS::StackName"))),
                                          Select(1, Split("-", Ref("AWS::StackName"))),
                                          Select(2, Split("-", Ref("AWS::StackName"))),
                                          Select(3, Split("-", Ref("AWS::StackName"))),
                                          "service"]
                                     ),
                        "Capabilities": "CAPABILITY_NAMED_IAM",
                        "TemplatePath": "App::mother-api/templates/dev/4-ecs-service-cf.template",
                        "RoleArn": GetAtt("CloudFormationCommunityMotherApiDevRole", "Arn"),
                        "ParameterOverrides": """{
                            "Tag": {
                                "Fn::GetParam" : [ "BuildOutput", "build.json", "tag" ]
                            },
                            "ApplicationEnv": {
                                "Fn::GetParam" : [ "BuildOutput", "build.json", "application_env" ]
                            }
                        }"""
                    },
                    InputArtifacts=[
                        InputArtifacts(
                            Name="App",
                        ),
                        InputArtifacts(
                            Name="BuildOutput"
                        )
                    ],
                )
            ]
        ),
    ],
))


print(t.to_json())
