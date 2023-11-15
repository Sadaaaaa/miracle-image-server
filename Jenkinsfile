// pipeline {
//   agent any
//   tools {
//       maven 'Maven 3.9.5'
//   }
//   environment {
//     JAVA_HOME = '/usr/lib/jvm/java-21-openjdk-amd64/'
//   }
//
//   stages {
//       stage('Build') {
//           steps {
//               script {
//                   sh 'mvn -B -DskipTests clean package'
//               }
//           }
//       }
//
//       stage('Deploy') {
//           when {
//                anyOf {
//                     branch 'develop'
//                     branch 'master'
// //                     expression { params.deployPresident }
// //                     expression { params.sandbox }
// //                     expression { params.baltiyskiyBereg }
//                }
//           }
//           steps {
//               script {
//                   sh 'echo poik123 | sudo -S java -jar target/miracle-image-server-0.0.1-SNAPSHOT.jar'
//               }
//           }
//       }
//   }
// }
//

pipeline {
    agent any

    environment {
        APP_NAME = 'miracle-image-server'
        JAR_FILE = 'miracle-image-server-0.0.1-SNAPSHOT.jar'
        LOG_FILE = 'miracle-image-server.log'
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    // Выполнить любые дополнительные шаги, необходимые для проверки кода из репозитория
                    checkout scm
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    // Ваш код для сборки проекта, если необходимо
                    // Например, если вы используете Maven:
                    sh 'mvn -B -DskipTests clean package'
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    // Копирование JAR-файла в рабочий каталог
                    sh "cp target/${JAR_FILE} ."

                    // Копирование скрипта в рабочий каталог
                    sh "cp ./start_spring_app.sh ."

                    // Вызов скрипта для управления приложением
                    sh "./start_spring_app.sh restart"
                }
            }
        }
    }

    post {
        success {
            echo 'Deployment successful!'
        }
        failure {
            echo 'Deployment failed!'
        }
    }
}