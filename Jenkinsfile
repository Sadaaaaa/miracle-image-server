//pipeline {
//    agent any
//
//    environment {
//        APP_NAME = 'miracle-image-server'
//        JAR_FILE = 'miracle-image-server-0.0.1-SNAPSHOT.jar'
//        LOG_FILE = 'miracle-image-server.log'
//    }
//
//    stages {
//        stage('Checkout') {
//            steps {
//                script {
//                    // Выполнить любые дополнительные шаги, необходимые для проверки кода из репозитория
//                    checkout scm
//                }
//            }
//        }
//
//        stage('Build') {
//            steps {
//                script {
//                    // Ваш код для сборки проекта, если необходимо
//                    // Например, если вы используете Maven:
//                    sh 'mvn -B -DskipTests clean package'
//                }
//            }
//        }
//
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
//}


pipeline {
    agent any

    environment {
        REMOTE_SERVER = '192.168.88.77'  // Замените на адрес вашего удаленного сервера
        REMOTE_USER = 'serg'      // Замените на вашего пользователя на удаленном сервере
        REMOTE_PATH = '/home/serg/miracle-image-server'
        // Замените на путь к вашему Spring Boot приложению на удаленном сервере
        JAR_FILE = 'miracle-image-server-0.0.1-SNAPSHOT.jar'             // Замените на имя вашего JAR-файла
        LOG_FILE = 'miracle-image-server.log'          // Замените на имя файла журнала
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

        stage('Deploy to Remote Server') {
            steps {

                script {
                    def remoteServer = [
                            $class    : 'BapSshHostConfiguration',
                            hostname  : '192.168.88.77',
                            username  : 'serg',
                            remoteRoot: '/home/serg/miracle-image-server'
                    ]

                    sshPublisher(publishers: [
                            sshPublisherDesc(configName: 'Miracle image server', transfers: [
                                    sshTransfer(execCommand: 'sudo service miracle-image-server restart',
                                            execTimeout: 120000,
                                            flatten: false,
                                            makeEmptyDirs: false,
                                            noDefaultExcludes: false,
                                            patternSeparator: '[, ]+',
                                            remoteDirectory: '.',
                                            remoteDirectorySDF: false,
                                            removePrefix: 'target',
                                            sourceFiles: 'target/*.jar')
                            ])
                    ])


                    // Копирование JAR-файла в удаленный сервер
                    sh "scp target/${JAR_FILE} ${REMOTE_USER}@${REMOTE_SERVER}:${REMOTE_PATH}"

                    // Выполнение скрипта на удаленном сервере для запуска приложения
                    sh "ssh ${REMOTE_USER}@${REMOTE_SERVER} 'cd ${REMOTE_PATH} && ./deploy.sh restart'"
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