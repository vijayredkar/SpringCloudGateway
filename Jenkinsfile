String customer-payments-client-directory = 'customer-payments-client'
String customer-payments-gateway-directory = 'customer-payments-scg-gtwy'
String customers-processor-directory = 'customers-processor'
String payments-processor-directory = 'payments-processor'
pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                script {
                    echo 'Start Building Client View Module ...'
                    dir(customer-payments-client-directory) {
                        sh 'mvn -B clean package'
                    }
                }
            }
        }
        stage('Test') {
            steps {
                script {
                    sh 'mvn test'
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