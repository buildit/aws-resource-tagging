Automatically tags aws resources on create with user arn. 
 
Uses the Serverless framework to build necessary resources. 

Currently supports to the following events:

    - CreateBucket
    - CreateFunction20150331
    - RunInstances
    - CreateNetworkInterface
    - CreateInternetGateway
    - CreateRouteTable
    - CreateSubnet
    - CreateVpc


*Events flow from CloudWatch -> SNS -> Lambda.

To add more taggers:

    1. Extend the AbstractTagger and implement the Tag method
    2. Add an instance of your tagger to the map in TaggerFactory
    3. Add the necessary permissions to iamStatements in serverless.yml 
    4. Update the CloudWatch EventPattern with source & eventName in serverless.yml 
  
  