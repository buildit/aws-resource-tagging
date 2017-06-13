Automatically tags aws resources on create with user arn. 
 
Uses the serverless framework to build necessary resources. 

Currently supports to the following events:

    - CreateBucket
    - CreateFunction20150331
    - RunInstances
    - CreateNetworkInterface
    - CreateInternetGateway
    - CreateRouteTable
    - CreateSubnet
    - CreateVpc


Events flow from Cloudwatch -> SNS -> Lambda.

TODO:

    - Assumes the SNS topic is already created. Should create the topic on the fly
    - Add more taggable resources.  