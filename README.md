# Full Stack Toronto 2015
Empowering MicroServices With Apache Karaf.

This repository contains a demonstration of how Apache Karaf empowers Full Stack developers to design, develop, deploy micro service based architectues.

#Architecture:

Conceptually, there are four main services deployed in our container: a RESTful webservice handing incoming requests, a common library of objects, back end business processing logic, and a JMS broker. We mix and match these services together to build our stack.

<pre><code>
  
  +==================+
  |                  |
  |  (FE)            |
  |         (Domain) |
  |  (BE)            |
  |                  |
  |      (AMQ)       |
  |                  |
  +=====[Karaf]======+

</code></pre>

#Prerequisites:

<pre><code>
JDK 8

Maven 3.2+

Apache Karaf 4.0.2

Alternatively, try Aetos 3.0+ for prepurposed Integration Container 
</code></pre>


#Installation:

Start a Karaf 4.0.2 instance, and execute the following commands:

<pre><code>
feature:repo-add mvn:com.savoirtech/feature/1.0.0-SNAPSHOT/xml/features

feature:install fsto-deps

feature:install fsto-application 

</code></pre>


#Runtime:

Congratulations! You are likely reading this because you attended my session "Empowering MicroServices with Apache Karaf".  The presentation does an overview of building modular code on a use case for utilizing Camel to process an order with an online restful servive API that takes an order with multiple line items.  The end goal is to take this order and break down each line item to a specific manufacturer and send that order to each manufacturers processing locations. The business logic is broken down in several modules, such that we can swap out implementations as required. We then take advantage of Apache Karaf to simplify provisioning & deployment, add in custom interactions, and test integration of the stack!


The goal is to demonstrate building up on a use case that uses a restful service (JAXRS) that takes a JSON order, repsonds to the caller with an order accepted, and sending to via persistent messaging to EIPs using a splitter, messaging, and the file component.  The code demonstrates transofrmation from the original order into multiple individual orders based on the manufacturer of the widget (which is the beginning of the product name).


This project requires a tool that allows submission of Restful "POST" JSON payloads (IntelliJ, SOAPUI, Javascript). 

To build this project, run the following at the root of the project:
 
<pre><code>mvn clean install</code> </pre>

Follow the installation instructions above to deploy our built project into Apache Karaf.

In the Apache Karaf logs you should see something similar to this:

<pre>
<code>
2015-11-12 13:50:25,204 | INFO  | rint Extender: 3 | BlueprintCamelContext            | 73 - org.apache.camel.camel-core - 2.15.2 | Route: route1 started and consuming from: Endpoint[jmsConsumer://queue:orders?disableReplyTo=true]
2015-11-12 13:50:25,210 | INFO  | rint Extender: 3 | BlueprintCamelContext            | 73 - org.apache.camel.camel-core - 2.15.2 | Route: route2 started and consuming from: Endpoint[jmsConsumer://queue:abc_company?disableReplyTo=true]
2015-11-12 13:50:25,216 | INFO  | rint Extender: 3 | BlueprintCamelContext            | 73 - org.apache.camel.camel-core - 2.15.2 | Route: route3 started and consuming from: Endpoint[jmsConsumer://queue:xyz_company?disableReplyTo=true]
2015-11-12 13:50:25,223 | INFO  | rint Extender: 3 | BlueprintCamelContext            | 73 - org.apache.camel.camel-core - 2.15.2 | Total 3 routes, of which 3 is started.
2015-11-12 13:50:25,225 | INFO  | rint Extender: 3 | BlueprintCamelContext            | 73 - org.apache.camel.camel-core - 2.15.2 | Apache Camel 2.15.2 (CamelContext: backEnd-camelContext) started in 0.617 seconds
2015-11-12 13:50:25,239 | INFO  | rint Extender: 2 | AbstractConnector                | 171 - org.eclipse.jetty.aggregate.jetty-all-server - 8.1.14.v20131031 | Started SelectChannelConnector@localhost:9090
2015-11-12 13:50:25,268 | INFO  | rint Extender: 2 | BlueprintCamelContext            | 73 - org.apache.camel.camel-core - 2.15.2 | Route: route4 started and consuming from: Endpoint[cxfrs://bean:orderService]
2015-11-12 13:50:25,268 | INFO  | rint Extender: 2 | BlueprintCamelContext            | 73 - org.apache.camel.camel-core - 2.15.2 | Total 1 routes, of which 1 is started.
2015-11-12 13:50:25,269 | INFO  | rint Extender: 2 | BlueprintCamelContext            | 73 - org.apache.camel.camel-core - 2.15.2 | Apache Camel 2.15.2 (CamelContext: frontEnd-camelContext) started in 0.569 seconds
</code>
</pre>

On the console you'll see the follow list output:

<pre><code>
karaf@root()> list
START LEVEL 100 , List Threshold: 50
 ID | State  | Lvl | Version                            | Name
---------------------------------------------------------------------------------------------------
 52 | Active |  80 | 5.12.1                             | activemq-karaf
 53 | Active |  80 | 1.0.0.SNAPSHOT                     | FSTO :: Back End Routes
 54 | Active |  80 | 2.4.3                              | Jackson-annotations
 55 | Active |  80 | 2.4.3                              | Jackson-core
 56 | Active |  80 | 2.4.3                              | jackson-databind
 57 | Active |  80 | 2.4.3                              | Jackson-module-JAXB-annotations
 58 | Active |  80 | 1.0.0.SNAPSHOT                     | FSTO :: Common Domain Library
 59 | Active |  80 | 1.0.0.SNAPSHOT                     | FSTO :: Front End Routes
 66 | Active |  80 | 3.1.4                              | activeio-core
 67 | Active |  80 | 5.12.1                             | activemq-camel
 68 | Active |  80 | 5.12.1                             | activemq-osgi
 69 | Active |  80 | 1.1.1                              | Apache Aries Transaction Manager
 70 | Active |  80 | 2.15.2                             | camel-blueprint
 71 | Active |  80 | 2.15.2                             | camel-catalog
 72 | Active |  80 | 2.15.2                             | camel-commands-core
 73 | Active |  80 | 2.15.2                             | camel-core
 74 | Active |  80 | 2.15.2                             | camel-cxf
 75 | Active |  80 | 2.15.2                             | camel-cxf-transport
 76 | Active |  80 | 2.15.2                             | camel-jackson
 77 | Active |  80 | 2.15.2                             | camel-jms
 78 | Active |  80 | 2.15.2                             | camel-spring
 79 | Active |  80 | 2.15.2                             | camel-karaf-commands
 81 | Active |  80 | 3.2.1                              | Commons Collections
 83 | Active |  80 | 3.3.0                              | Commons Net
 84 | Active |  80 | 1.6.0                              | Commons Pool
 85 | Active |  80 | 2.4.2                              | Apache Commons Pool
120 | Active |  80 | 2.0.0                              | geronimo-j2ee-connector_1.5_spec
121 | Active |  80 | 1.0.1                              | geronimo-j2ee-management_1.1_spec
127 | Active |  80 | 3.4.6                              | ZooKeeper Bundle
131 | Active |  80 | 2.0.9                              | Apache MINA Core
137 | Active |  80 | 1.9.2.1                            | Apache ServiceMix :: Bundles :: jasypt
138 | Active |  80 | 2.2.6.1                            | Apache ServiceMix :: Bundles :: jaxb-impl
152 | Active |  80 | 1.7.0.6                            | Apache ServiceMix :: Bundles :: velocity
156 | Active |  80 | 1.1.0.4c_5                         | Apache ServiceMix :: Bundles :: xpp3
157 | Active |  80 | 1.4.7.1                            | Apache ServiceMix :: Bundles :: xstream
168 | Active |  80 | 3.18.0                             | Apache XBean :: Spring
197 | Active |  80 | 0.6.4                              | JAXB2 Basics - Runtime
205 | Active |  80 | 2.11.0.v20140415-163722-cac6383e66 | Scala Standard Library
karaf@root()> 
</code></pre>

If you see these messages, then your ActiveMQ instance is not running or its not listening on port 61616 (or its not set up properly):

<pre><code>
2015-05-10T21:39:11.676 INFO  [org.apache.camel.component.jms.DefaultJmsMessageListenerContainer] - JMS message listener invoker needs to establish shared Connection
2015-05-10T21:39:11.676 INFO  [org.apache.camel.component.jms.DefaultJmsMessageListenerContainer] - JMS message listener invoker needs to establish shared Connection
2015-05-10T21:39:11.678 INFO  [org.apache.camel.component.jms.DefaultJmsMessageListenerContainer] - JMS message listener invoker needs to establish shared Connection
2015-05-10T21:39:11.681 ERROR [org.apache.camel.component.jms.DefaultJmsMessageListenerContainer] - Could not refresh JMS Connection for destination 'abc_company' - retrying using FixedBackOff{interval=5000, currentAttempts=0, maxAttempts=unlimited}. Cause: Could not connect to broker URL: tcp://localhost:61616. Reason: java.net.ConnectException: Connection refused
2015-05-10T21:39:11.681 ERROR [org.apache.camel.component.jms.DefaultJmsMessageListenerContainer] - Could not refresh JMS Connection for destination 'xyz_company' - retrying using FixedBackOff{interval=5000, currentAttempts=0, maxAttempts=unlimited}. Cause: Could not connect to broker URL: tcp://localhost:61616. Reason: java.net.ConnectException: Connection refused
2015-05-10T21:39:11.681 ERROR [org.apache.camel.component.jms.DefaultJmsMessageListenerContainer] - Could not refresh JMS Connection for destination 'orders' - retrying using FixedBackOff{interval=5000, currentAttempts=0, maxAttempts=unlimited}. Cause: Could not connect to broker URL: tcp://localhost:61616. Reason: java.net.ConnectException: Connection refused
</code></pre>

Fixing ActiveMQ is beyond the scope of this, so if you can't get it you should chat with the folks over at ActiveMQ or Camel dev lists.

To execute the call to the restful service, besure that you set your tool/code/whatever to use the POST verb and point it to:

http://localhost:9090/rest/order/add/

Your payload should look something like this:

<pre><code>
{
  "order": {
    "customer": {
      "lastName": "Hessla",
      "firstName": "Heaf",
      "address": "1234 Main St",
      "city": "Jackson Hole",
      "state": "WY",
      "zip": "83001"
    },
    "items": [
      {
        "product": "abc widget",
        "quantity": 2
      },
      {
        "product": "xyz widget",
        "quantity": 1
      }
    ]
  }
}
</code></pre>

You can copy and paste it from above or use the example.json in the root directory of this project.

To see the results, the target directoy should have a manufacturers subdirectory in it.  That directory should have 2 additional sub direcorties in it, abc and xyz.  In those directories you will have file names in the format "order-yyyyMMddHHmmss.json".  These files and directories will appear after you successfully submit a properly formed json payload like the one shown above. The contents should look similar ot the following:

<pre><code>
{
  "customer" : {
    "lastName" : "Hessla",
    "firstName" : "Heaf",
    "address" : "1234 Miain St",
    "city" : "Jackson Hole",
    "state" : "WY",
    "zip" : "83001"
  },
  "product" : "abc widget",
  "quanitity" : 2
}
</code></pre>

The difference will mainly be in the product which should declare the xyz or abc widget depending on the manufacturer.
