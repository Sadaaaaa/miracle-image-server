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

//         stage('Deploy') {
//             steps {
//                 script {
//                     // Копирование JAR-файла в рабочий каталог
//                     sh "cp target/${JAR_FILE} ."
//
//                     // Предоставление прав на выполнение deploy.sh
//                     sh "chmod +x deploy.sh"
//
//                     // Вызов скрипта для управления приложением
//                     sh "./deploy.sh restart"
//                 }
//             }
//         }
//     }

        stage('Deploy') {
            steps {
                // Шаг для деплоя на удаленный сервер по SSH
                script {
                    def remoteServer = [
                        $class: 'BapSshHostConfiguration',
                        hostname: '192.168.88.77',
                        username: 'serg',
                        remoteRoot: '/home/serg/miracle-image-server'
                    ]

//                    sshPublisher(publishers: [
//                        sshPublisherDesc(configName: 'Miracle image server', transfers: [
//                            sshTransfer(execCommand: 'sudo service your-app restart',
//                                        execTimeout: 120000,
//                                        flatten: false,
//                                        makeEmptyDirs: false,
//                                        noDefaultExcludes: false,
//                                        patternSeparator: '[, ]+',
//                                        remoteDirectory: '.',
//                                        remoteDirectorySDF: false,
//                                        removePrefix: 'target',
//                                        sourceFiles: 'target/*.jar')
//                        ])
//                    ])

                    // Предоставление прав на выполнение deploy.sh
                    sh "chmod +x deploy.sh"

                    // Вызов скрипта для управления приложением
                    sh "./deploy.sh restart"

                    // Выполнение команды с сохранением вывода
                    def result = sh(script: './deploy.sh', returnStatus: true)
                    if (result != 0) {
                        error "Deploy failed. Exit code: ${result}"
                    }
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