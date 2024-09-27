pipeline {
    agent any

    tools {
            sonarQube 'sonarquebe1'
        }

    stages {
        stage('Checkout')
        {
            steps {
                git branch: 'Develop', url: 'https://github.com/sergiosocha/SaludArqui_Backend.git'
            }
        }

        stage('Build') {
            steps {
                     sh 'chmod +x ./gradlew'
                    sh './gradlew clean build'
            }
        }

        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }

        stage('SonarQube Analysis')
        {
            steps {
                withSonarQubeEnv('sonarquebe1')
                {
                    sh './gradlew sonarqube'
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t sergioss21/spring-api .'
                }
            }
        }

        stage('Push Docker Image') {
            steps {
                script {
                    sh 'docker push sergioss21/spring-api'
                }
            }
        }
    }
}