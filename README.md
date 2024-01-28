1.# SpringCloudGateway to accomplish GDPR compliance challenges.
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

   #### core services
   - entity-mgt launch      
     - cd <project_dir>\event-driven-platform\entity-management >  mvn spring-boot:run


cd C:\Vijay\Java\projects\spring-cloud-gateway\payments-processor
mvn clean install
java -jar target/scg-payments-processor.jar

cd C:\Vijay\Java\projects\spring-cloud-gateway\customers-processor
mvn clean install
java -jar target/scg-customers-processor.jar

cd C:\Vijay\Java\projects\spring-cloud-gateway\customer-payments-client
mvn clean install
java -jar target/scg-customer-payments-client.jar

cd C:\Vijay\Java\projects\spring-cloud-gateway\customer-payments-scg-gtwy
mvn clean install
java -jar target/cloud-gateway-service-0.0.1-SNAPSHOT.jar


![SpringCloudGateway_GDPR_Arch](https://github.com/vijayredkar/SpringCloudGateway-to-solve-GDPR-challenge/assets/25388646/218cb309-e6f2-4f6e-a437-945567aa4bf6)
