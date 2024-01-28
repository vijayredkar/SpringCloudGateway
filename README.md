1. SpringCloudGateway to accomplish GDPR compliance challenges.  
2. Problem statement & solution approach - [https://vijayredkar.medium.com/](https://vijayredkar.medium.com/banknext-case-study-gdpr-challenges-solved-with-springcloudgateway-e8688050e7c8)
3. Architecture diagram - ![SpringCloudGateway_GDPR_Arch](https://github.com/vijayredkar/SpringCloudGateway-to-solve-GDPR-challenge/assets/25388646/218cb309-e6f2-4f6e-a437-945567aa4bf6)
4. Local application setup
    - Demo consists of below 4 microservices -
    - Payments     - core service whose response contains PCI sensitive data   
    - Customers    - core service whose response contains PII sensitive data
    - Viewer       - consumer viewing these responses but should not see sensitive data in plain text s aper GDPR guidelines.
    - SpringCloudGateway - Our solution service that will intercept and mutate/mask sensitive fields in req/response.
5. Launch microservices
   - git clone in to your local & then launch with below commands
   - Launch payments microservice
     - cd <application-path-in-your-local-machine>\payments-processor
     - mvn clean install
     - java -jar target/scg-payments-processor.jar
   - Launch customers microservice
     - cd <application-path-in-your-local-machine>\customers-processor
     - mvn clean install
     - java -jar target/scg-customers-processor.jar    
   - Launch viewer microservice
     - cd <application-path-in-your-local-machine>\customer-payments-client
     - mvn clean install
     - java -jar target/scg-customer-payments-client.jar
   - Launch viewer microservice
     - cd <application-path-in-your-local-machine>\customer-payments-scg-gtwy
     - mvn clean install
     - java -jar target/cloud-gateway-service-0.0.1-SNAPSHOT.jar
6. Test
   - Invoke the core payments and customers microservices directly with the below cURLs
     - curl --request GET \
  --url http://localhost:8081/payments/aggregator \
  --header 'Content-Type: application/json' \
  --header 'Cookie: JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119' \
  --cookie JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119
     - curl --request GET \
  --url http://localhost:8082/customers/aggregator \
  --header 'Content-Type: application/json' \
  --header 'Cookie: JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119' \
  --cookie JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119
  - Observe that the response shows sensitive data in plain text. This violates GDPR.
   - Invoke the core payments and customers microservices via the SpringCloudGateway with the below cURLs
     - curl --request GET \
  --url http://localhost:8079/view/payments/report \
  --header 'Content-Type: application/json' \
  --header 'Cookie: JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119' \
  --cookie JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119
     - curl --request GET \
  --url http://localhost:8079/view/customers/report \
  --header 'Content-Type: application/json' \
  --header 'Cookie: JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119' \
  --cookie JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119
  - Observe that the response now shows sensitive data masked. Thus ahering to the GDPR compliance.
