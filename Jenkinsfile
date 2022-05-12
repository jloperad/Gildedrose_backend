pipeline{
    agent any
    stages{
        stage('Build Database'){
            steps{
                
                sh 'docker run --name my-postgres -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres'
            }
        }
        stage('Enviroment Setup'){
            environment{
                DB_HOST = """${sh(
                returnStdout: true,
                script: 'docker inspect -f "{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}" my-postgres'
                )}"""
            }
            steps{
                sh 'echo "DB_HOST: ${DB_HOST}"'
            }
        }
        stage('Build backend'){
            agent any
            steps{
                sh 'docker build --build-arg DB_H=${DB_HOST} -t jloperad/praxis-gildedrose_backend:latest .'
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
        stage('remove container'){
            steps{
                sh 'docker rm -f my-postgres'
            }
        }
    }    
}