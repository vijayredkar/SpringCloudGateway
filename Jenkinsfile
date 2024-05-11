
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
                }
            }
        }
        stage('Execute Integration Test') {
            steps {
                script {
                    // Assume these are your curl commands and you capture the output
                    def response = sh(script: "curl --silent --location 'http://localhost:8081/payments/aggregator' --header 'Content-Type: application/json' --header 'Cookie: JSESSIONID=5A5EE3A133ACFBB487A1512988C4A119'", returnStdout: true).trim()
                    // Use jq to check if the response is as expected
                    def isValid = sh(script: "echo '${response}' | jq -e '.firstName == \"Sam\" and .lastName == \"Markson\"'", returnStatus: true) == 0

                    // Write TAP results to a file
                    writeFile file: 'tap-results.tap', text: "1..1\n"
                    if (isValid) {
                        writeFile file: 'tap-results.tap', text: "ok 1 - Payment aggregator response is valid\n", append: true
                    } else {
                        writeFile file: 'tap-results.tap', text: "not ok 1 - Payment aggregator response is invalid\n", append: true
                    }
                }
            }
        }
    }
    post {
        always {
            script {
                echo "***** Cleaning up Docker containers *****"
                sh 'docker-compose down'
                echo "***** Publishing TAP Results *****"
                step([$class: 'TapPublisher', testResults: 'tap-results.tap', failIfNoResults: true, failedTestsMarkBuildAsFailure: true])
            }
        }
    }
}