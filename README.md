AWS Resource Tagging
====================
Meta
----
Automatically tags aws resources on create with user ARN.  Resources to tag are identified
by CloudTrail-generated CloudWatch Events, thus you need to have CloudTrail enabled in your 
account.

Events flow as follows:

```
CloudTrail -> CloudWatch Events -> SNS -> Lambda
```

The Serverless framework is used to build and deploy necessary resources. 

Given that you have CloudTrail enabled, the CloudWatch Events filter, SNS topic, and Lambda
function are all created for you by this project.
 

Currently supports the following events:

- EC2
    - RunInstances
    - CreateNetworkInterface
    - CreateInternetGateway
    - CreateRouteTable
    - CreateSubnet
    - CreateVpc
- Etc
    - CreateBucket
    - CreateFunction20150331


Docker (cleanroom) Deployment (and Development, if you like)
-----------------------------
A Dockerfile is provided.  By using Docker we remove the need to install any global dependencies 
on the host. In this case Java 8, NodeJS, the serverless framework, Python and the AWS CLI.

- `docker build --tag sls-tagging .`
- `docker run -itv $(pwd):/src sls-tagging`
- `aws configure`
- `cd /src`
- `./gradlew build`
- `sls deploy --region <deployment region>` (to your development/test region)

(or you can install the stuff mentioned in the Dockerfile in your host environment and work there, if you like)


Development
-----------

The solution was written using Java 8, and uses a simple "by convention" method of constructing instances to handle 
different CloudWatch Event types.

To add tagging for more resource types:

- In Java code
  - Implement an appropriate `ResourceIdExtractor`.  Follow the convention `<Event Name>IdExtractor`.  
    A convenience abstract implementation is provided: `AbstractResourceIdExtractor`, which uses `JsonPath` to pull out the ID element(s).

     Currently, these implementations must be packaged with the other `ResourceIdExtractor` implementations.
  - Implement an appropriate `Tagger`.  Follow the convention `<Event Name>Tagger`.  
    A `Tagger` implementation uses the AWS API to tag resources identified by its associated `IDExtractor`.

     Like `ResourceIdExtractor`s, these implementations must be packaged with the other `Tagger` implementations.

- In serverless.yml
  - Add the necessary permissions to `iamRoleStatements` 
  - Update the CloudWatch `EventPattern` with `source` & `eventName` 
  

Implementation Notes
--------------------
The "by convention" approach to finding implementors is a simplistic reflection-based solution.  There is an 
alternative annotation-based approach checked in, but it is half-baked and not DRY at the moment.  Thus, the 
code currently uses the reflective approach.