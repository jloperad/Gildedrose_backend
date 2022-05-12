pipeline{
    agent any
    stages{
        stage('Build Database'){
            steps{
                sh 'docker run --name my-postgres -e POSTGRES_PASSWORD=secret -p 5432:5432 -d postgres'
                
            }
        }
        stage('Enviroment Setup'){
            enviroment{
                DATABASEIP=docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' my-postgres
            }
            steps{
                echo "DATABASEIP: ${DATABASEIP}"
            }
        }
    }    
}