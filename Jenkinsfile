pipeline {
    agent any

    stages {
        stage('Checkout') {
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

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('sonarquebe1') {
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

       stage('Scan Docker Image with Trivy') {
            steps {
                script {
                    sh 'trivy image --exit-code 1 --severity CRITICAL --scanners vuln --cache-dir /var/jenkins_home/trivy-cache sergioss21/spring-api'
                }
            }
        }
    
        stage('Push Docker Image') {
            steps {
                script {

                    withCredentials([usernamePassword(credentialsId: 'DockerHub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "docker login -u $DOCKER_USER -p $DOCKER_PASSWORD"
                        sh 'docker push sergioss21/spring-api'
                    }
                }
            }
        }
    }
}
