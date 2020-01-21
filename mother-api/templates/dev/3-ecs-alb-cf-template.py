"""Generating CloudFormation template."""

from troposphere import elasticloadbalancingv2 as elb

from troposphere import (
    Export,
    GetAtt,
    ImportValue,
    Join,
    Output,
    Ref,
    Select,
    Split,
    Sub,
    Template,
    ec2
)

t = Template()

t.set_description("community-mother-api: ALB for the ECS Cluster")

t.add_resource(ec2.SecurityGroup(
    "LoadBalancerSecurityGroup",
    GroupDescription="Web load balancer security group.",
    VpcId=ImportValue(
        Join(
            "-",
            [Select(0, Split("-", Ref("AWS::StackName"))),
             Select(1, Split("-", Ref("AWS::StackName"))),
             Select(2, Split("-", Ref("AWS::StackName"))),
             Select(3, Split("-", Ref("AWS::StackName"))),
                "cluster-vpc-id"]
        )
    ),
    SecurityGroupIngress=[
        ec2.SecurityGroupRule(
            IpProtocol="tcp",
            FromPort="80",
            ToPort="80",
            CidrIp="0.0.0.0/0",
        ),
        ec2.SecurityGroupRule(
            IpProtocol="tcp",
            FromPort="443",
            ToPort="443",
            CidrIp="0.0.0.0/0",
        ),
    ],
))

t.add_resource(elb.LoadBalancer(
    "LoadBalancer",
    Scheme="internet-facing",
    Subnets=Split(
        ',',
        ImportValue(
            Join("-",
                 [Select(0, Split("-", Ref("AWS::StackName"))),
                  Select(1, Split("-", Ref("AWS::StackName"))),
                  Select(2, Split("-", Ref("AWS::StackName"))),
                  Select(3, Split("-", Ref("AWS::StackName"))),
                  "cluster-public-subnets"]
                 )
        )
    ),
    SecurityGroups=[Ref("LoadBalancerSecurityGroup")],
))

t.add_resource(elb.TargetGroup(
    "TargetGroup",
    DependsOn='LoadBalancer',
    HealthCheckIntervalSeconds="20",
    HealthCheckProtocol="HTTP",
    HealthCheckTimeoutSeconds="15",
    HealthCheckPath="/actuator/health",
    HealthyThresholdCount="5",
    Matcher=elb.Matcher(
        HttpCode="200"),
    Port=80,
    Protocol="HTTP",
    UnhealthyThresholdCount="3",
    VpcId=ImportValue(
        Join(
            "-",
            [Select(0, Split("-", Ref("AWS::StackName"))),
             Select(1, Split("-", Ref("AWS::StackName"))),
             Select(2, Split("-", Ref("AWS::StackName"))),
             Select(3, Split("-", Ref("AWS::StackName"))),
                "cluster-vpc-id"]
        )
    ),
))

t.add_resource(elb.Listener(
    "HTTPSListener",
    Port="443",
    Protocol="HTTPS",
    LoadBalancerArn=Ref("LoadBalancer"),
    DefaultActions=[elb.Action(
        Type="forward",
        TargetGroupArn=Ref("TargetGroup")
    )],
    # SSL certificate for https.
    Certificates=[elb.Certificate(
        CertificateArn="arn:aws:acm:ap-northeast-2:540379673889:certificate/3202b1b0-7446-420e-99d2-4b175ecd6310")]
))

t.add_resource(elb.Listener(
    "HTTPListener",
    Port="80",
    Protocol="HTTP",
    LoadBalancerArn=Ref("LoadBalancer"),
    DefaultActions=[elb.Action(
        Type="redirect",
        RedirectConfig=elb.RedirectConfig(
            Protocol='HTTPS',
            StatusCode='HTTP_301',
            Port='443',
        )
    )]
))

t.add_output(Output(
    "TargetGroup",
    Description="TargetGroup",
    Value=Ref("TargetGroup"),
    Export=Export(Sub("${AWS::StackName}-target-group")),
))

t.add_output(Output(
    "URL",
    Description="community-mother-api URL",
    Value=Join("", ["https://", GetAtt("LoadBalancer", "DNSName"), ":443"])
))

print(t.to_json())
