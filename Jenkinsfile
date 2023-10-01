pipeline {
    agent any
    tools {
        maven 'mvn'
        jdk 'jdk17'
    }
    environment {
        DATE = new Date().format('yy.M')
        TAG = "${DATE}.${BUILD_NUMBER}"
        DOCKERHUB_CREDENTIALS = credentials('docker_cred_id')
    }
    stages {
        stage('Checkout') {
            steps {
                sh 'echo passed'
                git branch: 'main', credentialsId: 'expense-id', url: 'https://github.com/oms-cs/Expense-Tracker.git'
            }
        }
        stage('Build and Test') {
            steps {
                sh 'ls -ltr'
                sh 'mvn clean package'
            }
        }
        stage("Build Docker image") {
            steps {
                sh 'sudo docker build . -t parthkarad/expense-tracker:${TAG}'
            }
        }
        stage("Push image to Dockerhub") {
            steps {
                sh 'sudo echo $DOCKERHUB_CREDENTIALS_PSW | sudo docker login -u $DOCKERHUB_CREDENTIALS_USR --password-stdin'
                sh 'sudo docker push parthkarad/expense-tracker:${TAG}'
            }
        }
        stage('Write') {
            steps {
                script {
                    writeFile file: '.env', text: 'TAG=' + env.TAG
                }
            }
        }
        stage("Bring application up") {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    sh "sudo docker network rm expense"
                }
                sh "sudo docker network create expense"
                sh "sudo docker compose --env-file ./.env up --force-recreate --detach"
            }
        }
    }
}
