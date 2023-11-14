pipeline {
  agent any
  tools {
      maven 'Maven 3.9.5'
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
                  sh 'echo poik123 | sudo -S java -jar target/miracle-image-server-0.0.1-SNAPSHOT.jar'
              }
          }
      }
  }
}