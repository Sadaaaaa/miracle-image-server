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
        REMOTE_SERVER = '192.168.88.77'
        REMOTE_USERNAME = 'serg'
        REMOTE_PASSWORD = 'poik123'
        REMOTE_DESTINATION = '/home/serg/miracle-image-server'
        ABSOLUTE_PATH_TO_DEPLOY_SH = '/home/serg/miracle-image-server/deploy.sh'
    }

    stages {
        stage('Checkout') {
            steps {
                // Может потребоваться настроить ваш репозиторий и ветку
                checkout scm
            }
        }

        stage('Build') {
            steps {
                script {
                    sh 'mvn -B -DskipTests clean package'
                }
            }
        }

        stage('Copy Files to Remote Server') {
            steps {
                script {
                    sshagent(['/home/serg/.ssh/id_rsa']) {
                        // Ваш код SSH-команд здесь

                        // Set execute permissions on deploy.sh using SSH key
//                    sh "ssh -i /home/serg/.ssh/id_rsa ${REMOTE_USERNAME}@${REMOTE_SERVER} 'chmod +x ${REMOTE_DESTINATION}/${ABSOLUTE_PATH_TO_DEPLOY_SH}'"

                        sh "ssh -o StrictHostKeyChecking=no -o UserKnownHostsFile=/home/serg/.ssh/known_hosts -i /home/serg/.ssh/id_rsa serg@192.168.88.77 'chmod +x /home/serg/miracle-image-server/'"
                        // Copy deploy.sh and JAR file to the remote server
                        sh "scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -r deploy.sh ${REMOTE_USERNAME}@${REMOTE_SERVER}:${REMOTE_DESTINATION}/"
                        sh "scp -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null -r miracle-image-server-0.0.1-SNAPSHOT.jar ${REMOTE_USERNAME}@${REMOTE_SERVER}:${REMOTE_DESTINATION}/"
                    }
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
                                    sshTransfer(execCommand: '/home/serg/miracle-image-server/deploy.sh restart',
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
                }
            }
        }
    }

//        stage('Deploy to Remote Server') {
//            steps {
//                script {
//                    // Копирование файлов на удаленный сервер
//                    sshPublisher(
//                            publishers: [
//                                    sshPublisherDesc(
//                                            configName: 'Miracle image server',
//                                            transfers: [
//                                                    sshTransfer(
//                                                            sourceFiles: '/home/serg/miracle-image-server/miracle-image-server-0.0.1-SNAPSHOT.jar',  // Путь к вашему JAR-файлу
//                                                            execCommand: './deploy.sh start',      // Команда для запуска deploy.sh
//                                                            execTimeout: 120000,                     // Таймаут выполнения команды
//                                                            remoteDirectory: REMOTE_DESTINATION
//                                                    )
//                                            ]
//                                    )
//                            ]
//                    )
//                }
//            }
//        }
//    }
}