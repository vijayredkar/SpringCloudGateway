
pipeline {
    agent any
    environment {
        // Define global variables
        CLIENT_VIEW_MODULE = 'customer-payments-client'
        CUSTOMER_PAYMENTS_GATEWAY_MODULE = 'customer-payments-scg-gtwy'
        CUSTOMERS_PROCESSOR_MODULE= 'customers-processor'
        PAYMENTS_PROCESSOR_MODULE = 'payments-processor'
    }
    stages {
        stage('Build') {
            steps {
                script {
                    echo 'Start Building Client View Module ...'
                    dir(${CLIENT_VIEW_MODULE}) {
                        sh 'mvn -B clean package'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    dir(${CLIENT_VIEW_MODULE}) {
                        sh 'mvn test'
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                script {
                    echo "Deploying application..."
                    // Add deployment scripts here
                }
            }
        }
    }
}