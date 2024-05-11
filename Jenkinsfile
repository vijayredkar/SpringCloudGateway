
pipeline {
    agent any
    /*environment {
        // Define global variables
    }*/
    tools {
        maven '3.9.6'
    }
    stages {
        stage('Build Standalone Modules') {
            steps {
                script {
                    def clientViewModule = 'customer-payments-client'
                    def customerPaymentsGatewayModule = 'customer-payments-scg-gtwy'
                    def customersProcessorModule= 'customers-processor'
                    def paymentsProcessorModule = 'payments-processor'
                    dir(clientViewModule) {
                        echo '***** Start Building Client View Module *****'
                        sh 'mvn -B clean package'
                        echo '***** Client View Module Built Successfully *****'
                    }
                    dir(customerPaymentsGatewayModule) {
                        echo '***** Start Building Customer Payment Gateway Module *****'
                        sh 'mvn -B clean package'
                        echo '***** Customer Payment Gateway Module Built Successfully *****'
                    }
                    dir(customersProcessorModule) {
                        echo '***** Start Building Customers Processor Backend Module *****'
                        sh 'mvn -B clean package'
                        echo '***** Customers Processor Backend Module Built Successfully *****'
                    }
                    dir(paymentsProcessorModule) {
                        echo '***** Start Building Payments Processor Backend Module *****'
                        sh 'mvn -B clean package'
                        echo '***** Payments Processor Backend Module Built Successfully *****'
                    }
                }
            }
        }
        stage('Run Docker Containers') {
            steps {
                script {
                    echo "***** Deploying application using Docker Compose *****"
                    sh 'docker-compose up --build -d'
                    // Pause the pipeline for a predetermined time
                    sleep(15) // Sleep for 15 seconds
                }
            }
        }
        stage('Execute Integration Test') {
            steps {
                script {
                    echo '***** Start Executing Integration Test *****'
                    echo '***** Creating TAP Results File :: tap-results.tap *****'
                    writeFile file: 'tap-results.tap', text: '4..4\n'

                    echo '***** Executing Payments Aggregator Scenario *****'
                    // Assume these are your curl commands and you capture the output
                    def response = sh(script: "curl --location --silent 'http://host.docker.internal:8081/payments/aggregator' --header 'Content-Type: application/json' --header 'Cookie: JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119'", returnStdout: true).trim()
                    // Use jq to check if the response is as expected
                    def isValid = sh(script: """
                        echo '${response}' | jq -e '
                        .source == "Payments Aggregator response : has SENSITIVE PCI" and
                        .firstName == "Sam" and
                        .lastName == "Markson" and
                        .maritalStatus == "M" and
                        .citizenship == "USA" and
                        .currentResidenceCountry == "GB" and
                        .creditcardnumber == "5242677622358871"
                        '
                    """, returnStatus: true) == 0
                    def currentReportContent = readFile 'tap-results.tap'
                    if (isValid) {
                        echo '***** Payments Aggregator Response is Valid *****'
                        writeFile file: 'tap-results.tap', text: currentReportContent + 'ok 1 - Payments Aggregator Response is Valid\n'
                    } else {
                        echo '***** Payments Aggregator Response is Invalid *****'
                        writeFile file: 'tap-results.tap', text: currentReportContent + 'not ok 1 - Payments Aggregator Response is Invalid\n'
                    }

                    echo '***** Executing Customers Aggregator Scenario *****'
                    response = sh(script: "curl --location --silent 'http://host.docker.internal:8082/customers/aggregator' --header 'Content-Type: application/json' --header 'Cookie: JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119'", returnStdout: true).trim()
                    // Use jq to check if the response is as expected
                    isValid = sh(script: """
                        echo '${response}' | jq -e '
                        .source == "Customer Aggregator response : has SENSITIVE PII" and
                        .firstName == "Peter" and
                        .lastName == "Markel" and
                        .citizenship == "FRA" and
                        .telephoneNumber == "+826785438752" and
                        .emailAddress == "peter_m@gmail.com"
                        '
                    """, returnStatus: true) == 0
                    currentReportContent = readFile 'tap-results.tap'
                    if (isValid) {
                        echo '***** Customers Aggregator Response is Valid *****'
                        writeFile file: 'tap-results.tap', text: currentReportContent + 'ok 1 - Customers Aggregator Response is Valid\n'
                    } else {
                        echo '***** Customers Aggregator Response is Invalid *****'
                        writeFile file: 'tap-results.tap', text: currentReportContent + 'not ok 1 - Customers Aggregator Response is Invalid\n'
                    }

                    echo '***** Executing Payments Report Scenario *****'
                    response = sh(script: "curl --location --silent 'http://host.docker.internal:8079/view/payments/report' --header 'Content-Type: application/json' --header 'Cookie: JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119'", returnStdout: true).trim()
                    // Use jq to check if the response is as expected
                    isValid = sh(script: """
                        echo '${response}' | jq -e '
                        .source == "Payment Aggregator response : has SENSITIVE PCI" and
                        (.firstName | test("^\\*+$")) and    // Check if firstName is anonymized correctly
                        (.lastName | test("^\\*+$")) and     // Check if lastName is anonymized correctly
                        .maritalStatus == "M" and
                        .citizenship == "USA" and
                        .currentResidenceCountry == "GB" and
                        (.creditcardnumber | test("^\\*+$")) // Check if creditcardnumber is anonymized correctly
                        '
                    """, returnStatus: true) == 0
                    currentReportContent = readFile 'tap-results.tap'
                    if (isValid) {
                        echo '***** Payments Report Response is Valid *****'
                        writeFile file: 'tap-results.tap', text: currentReportContent + 'ok 1 - Payments Report Response is Valid\n'
                    } else {
                        echo '***** Payments Report Response is Invalid *****'
                        writeFile file: 'tap-results.tap', text: currentReportContent + 'not ok 1 - Payments Report Response is Invalid\n'
                    }

                    echo '***** Executing Customers Report Scenario *****'
                    response = sh(script: "curl --location --silent 'http://host.docker.internal:8079/view/payments/report' --header 'Content-Type: application/json' --header 'Cookie: JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119'", returnStdout: true).trim()
                    // Use jq to check if the response is as expected
                    isValid = sh(script: """
                        echo '${response}' | jq -e '
                        .source == "Customer Aggregator response : has SENSITIVE PII" and
                        (.firstName | test("^\\*+$")) and    // Verify firstName is correctly anonymized
                        (.lastName | test("^\\*+$")) and     // Verify lastName is correctly anonymized
                        .citizenship == "FRA" and
                        (.telephoneNumber | test("^\\*+$")) and // Verify telephoneNumber is anonymized
                        (.emailAddress | test("^\\*+$"))        // Verify emailAddress is anonymized
                        '
                    """, returnStatus: true) == 0
                    currentReportContent = readFile 'tap-results.tap'
                    if (isValid) {
                        echo '***** Customers Report Response is Valid *****'
                        writeFile file: 'tap-results.tap', text: currentReportContent + 'ok 1 - Customers Report Response is Valid\n'
                    } else {
                        echo '***** Customers Report Response is Invalid *****'
                        writeFile file: 'tap-results.tap', text: currentReportContent + 'not ok 1 - Customers Report Response is Invalid\n'
                    }

                    def content = readFile 'tap-results.tap'
                    echo '***** Content of tap-results.tap ::' + content + ' *****'
                }
            }
        }
    }
    post {
        always {
            script {
                echo '***** Cleaning up Docker containers *****'
                sh 'docker-compose down'
                echo '***** Publishing TAP Results *****'
                step([$class: 'TapPublisher', testResults: 'tap-results.tap', failIfNoResults: true, failedTestsMarkBuildAsFailure: true])
            }
        }
    }
}