pipeline{
    agent any
    stages{
        stage('Build Database'){
            steps{
                
                sh 'docker run --name my-postgres -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres'
                def db = sh '''docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' my-postgres'''
            }
        }
        stage('Enviroment Setup'){
            environment{
                DATABASE_HOST=db
            }
            steps{
                sh 'echo "DATABASE_HOST: ${DATABASE_HOST}"'
            }
        }
        stage('remove container'){
            steps{
                sh 'docker rm -f my-postgres'
            }
        }
    }    
}