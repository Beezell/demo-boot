pipeline {
    agent any
    
        environment {
        registry = "beezell/demo-boot"
        registryCredential = 'docker-hub'
        containerName = 'demo-app' //nom du conteneur
        appPort = '8080' 
        testPort = '8380'
        prodIp = '13.38.227.42' // ip public de l instance de production sur aws
        prodPort = '80'
    }

    stages {
        
        stage('Build Artifact') {
            steps {
              sh "mvn clean package -DskipTests=true"
              archive 'target/*.jar' //so that they can be downloaded later
            }
        }
        
        stage('Unit Tests - JUnit and Jacoco') {
          steps {
            sh "mvn test"
          }
          post {
            always {
              junit 'target/surefire-reports/*.xml'
              jacoco execPattern: 'target/jacoco.exec'
            }
          }
        }
        
        stage('Docker Build and Push') {
          steps {
            withDockerRegistry([credentialsId: "docker-hub", url: ""]) {
              sh 'printenv'
              sh 'docker build -t $registry:$BUILD_NUMBER .'
              sh 'docker push $registry:$BUILD_NUMBER'

            }
          }
        }
        
        stage('Remove Unused docker image') {
          steps{
            sh "docker rmi $registry:$BUILD_NUMBER"
          }
        }
        
        stage('Deploy to test env') {
          steps{
            sh "docker stop $containerName || true"
            sh "docker rm $containerName || true"
            sh "docker run -d -p $testPort:$appPort --name $containerName $registry:$BUILD_NUMBER"
          }
        }
        
        stage('Production env'){
        	steps {
        		input 'Do you approve deployment ?'
        		echo 'Going into production'
        	}
        }
        
        stage('Deploy to prod env'){
        	steps {
        		sh "docker -H $prodIp stop $containerName || true"
        		sh "docker -H $prodIp rm $containerName || true"
        		sh "docker -H $prodIp run -d -p $prodPort:$appPort --name $containerName $registry:$BUILD_NUMBER"
        	}
        }
        
        stage('Clean test env'){
        	steps {
            sh "docker stop $containerName || true"
            sh "docker rm $containerName || true"
        	sh "docker rmi $registry:$BUILD_NUMBER"
        	sh "docker rmi $(docker images -aq) -f"
        	}
        }
    }
}