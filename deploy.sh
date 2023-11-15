#!/bin/bash

# Переменные
APP_NAME="miracle-image-server"  # фактическое имя приложения
JAR_FILE="miracle-image-server-0.0.1-SNAPSHOT.jar"          # фактическое имя JAR-файла
LOG_FILE="miracle-image-server.log"       # желаемое имя файла журнала

# Функция для проверки, запущено ли приложение
is_application_running() {
  if [[ $(ps aux | grep "$JAR_FILE" | grep -v "grep" | wc -l) -gt 0 ]]; then
    return 0  # Приложение запущено
  else
    return 1  # Приложение не запущено
  fi
}

# Функция для остановки приложения
stop_application() {
  if is_application_running; then
    echo "Stopping $APP_NAME..."
    kill $(ps aux | grep "$JAR_FILE" | grep -v "grep" | awk '{print $2}')
  else
    echo "$APP_NAME is not running."
  fi
}

# Функция для запуска приложения
start_application() {
  if is_application_running; then
    echo "$APP_NAME is already running."
  else
    echo "Starting $APP_NAME..."
    nohup java -jar $JAR_FILE > $LOG_FILE 2>&1 &
    echo "$APP_NAME started. Check the log in $LOG_FILE."
  fi
}

# Опции командной строки
case "$1" in
  "start")
    start_application
    ;;
  "stop")
    stop_application
    ;;
  "restart")
    stop_application
    start_application
    ;;
  *)
    echo "Usage: $0 {start|stop|restart}"
    exit 1
    ;;
esac

exit 0