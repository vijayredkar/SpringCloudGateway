
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
        stage('Run Docker Container') {
            steps {
                script {
                    echo "***** Deploying application using Docker Compose *****"
                    sh 'docker-compose up -build -d'
                }
            }
        }
    }
}