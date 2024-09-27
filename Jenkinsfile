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
                script {

                    withSonarQubeEnv('sonarquebe1') {
                        sh './gradlew sonar'
                    }
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

    post {
        always {
            // Opcional: Limpiar el workspace tras la ejecución
            cleanWs()
        }

        failure {
            echo 'Build failed!'
        }

        success {
            echo 'Build succeeded!'
        }
    }
}
