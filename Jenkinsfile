pipeline{
    agent any
    stages{
        stage('Enviroment Setup'){
            environment{
                DB_HOST = "group1-rds.cqqmj66dxtlw.us-east-1.rds.amazonaws.com"
                DB_USER = "postgres"
                DB_PASSWORD = "postgres"
            }
            steps{
                sh 'echo "DB_HOST: ${DB_HOST}"'
                sh 'echo "DB_USER: ${DB_USER}"'
                sh 'echo "DB_PASSWORD: ${DB_PASSWORD}"'
            }
        }
        stage('Test'){
            agent{
                docker "maven"
            }
            steps{
                sh 'echo "DB_HOST: ${DB_HOST}"'
                sh 'mvn clean compile -DDATABASE_HOST=${DB_HOST} -DDATABASE_USER=${DB_USER} -DDATABASE_PASSWORD=${DB_PASSWORD}'
                sh 'mvn clean test -DDATABASE_HOST=${DB_HOST} -DDATABASE_USER=${DB_USER} -DDATABASE_PASSWORD=${DB_PASSWORD}'
            }
        }

        stage('Build backend'){
            agent any
            steps{
                sh 'docker build --build-arg DB_H=${DB_HOST} --build-arg DB_U=${DB_USER} --build-arg DB_P=${DB_PASSWORD} -t jloperad/praxis-gildedrose_backend:latest .'
            }
        }   
        stage('Docker Push') {
            agent any
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerHub', passwordVariable: 'dockerHubPassword', usernameVariable: 'dockerHubUser')]) {
                sh "docker login -u ${env.dockerHubUser} -p ${env.dockerHubPassword}"
                sh 'docker push jloperad/praxis-gildedrose_backend:latest'
                sh 'docker logout'
                }
            }
        }   
    }    
}