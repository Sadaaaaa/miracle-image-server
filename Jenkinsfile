pipeline {
  agent any
  tools {
      maven 'Maven 3.9.5'
  }
  environment {
    JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64/'
  }

  stages {
      stage('Build') {
          steps {
              script {
                  sh 'mvn -B -DskipTests clean package'
              }
          }
      }

      stage('Deploy') {
//           when {
//             branch 'master'
//             branch 'develop'
//           }
          steps {
              script {
                  sh 'echo poik123 | sudo -S ./mvnw spring-boot:run'
              }
          }
      }
  }
}