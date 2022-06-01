pipeline{
    agent any
    stages{
        stage('Test'){
            agent{
                docker "maven"
            }
            environment{
                DB_HOST = "group1-rds.cqqmj66dxtlw.us-east-1.rds.amazonaws.com"
                DB_USER = "postgres"
                DB_PASSWORD = "postgres"
            }
            steps{
                sh 'mvn clean compile -DDATABASE_HOST=${DB_HOST} -DDATABASE_USER=${DB_USER} -DDATABASE_PASSWORD=${DB_PASSWORD}'
                sh 'mvn clean test -DDATABASE_HOST=${DB_HOST} -DDATABASE_USER=${DB_USER} -DDATABASE_PASSWORD=${DB_PASSWORD}'
                sh 'mvn surefire-report:report -DDATABASE_HOST=${DB_HOST} -DDATABASE_USER=${DB_USER} -DDATABASE_PASSWORD=${DB_PASSWORD}'
                sh 'cp -R target/site/surefire-report.html */surefire-report.html'
            }
        }

        stage('Build backend'){
            agent any
            environment{
                DB_HOST = "group1-rds.cqqmj66dxtlw.us-east-1.rds.amazonaws.com"
                DB_USER = "postgres"
                DB_PASSWORD = "postgres"
            }
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
    post{
      always{
        publishHTML (
                    target : [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: '**',
                        reportFiles: 'surefire-report.html',
                        reportName: 'Unit test Report'
                    ]
                )
      }
    }    
}