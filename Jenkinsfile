pipeline {
  agent any
  tools {
      maven 'Maven'
  }
  environment {
    JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64/'
  }

  stages {
//       stage('Checkout') {
//           steps {
//               script {
//                   git 'https://github.com/ваш-репозиторий.git'
//               }
//           }
//       }

      stage('Build') {
          steps {
              script {
                  sh 'mvn -B -DskipTests clean package'
              }
          }
      }

      stage('Deploy') {
          steps {
              script {
                  sh 'java -jar target/miracle-image-server.jar'
              }
          }
      }
  }
}