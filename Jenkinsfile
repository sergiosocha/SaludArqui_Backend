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
   feature/jenkins
                withSonarQubeEnv('SonarQube') {
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
        feature/jenkins
                    // Iniciar sesión en Docker Hub usando las variables de entorno
                    sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'

                    // Hacer push de la imagen
                    sh 'docker push sergioss21/spring-api'

                    // Cerrar sesión de Docker
                    sh 'docker logout'

                    withCredentials([usernamePassword(credentialsId: 'DockerHub', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "docker login -u $DOCKER_USER -p $DOCKER_PASSWORD"
                        sh 'docker push sergioss21/spring-api'
                    }
           Develop
                }
            }
        }
    }
}
