@echo off
SETLOCAL ENABLEDELAYEDEXPANSION

REM Check if the script is running on macOS or Windows
IF "%OS%"=="Windows_NT" (
    REM Windows commands
    echo **********************************************************
    echo ATTENTION! This script will COMPLETELY uninstall Teamify, removing also all the images and the database data. Are you sure to undeploy?
    echo **********************************************************
    echo.
    pause
    docker-compose down
    docker image rm teamify-frontend
    docker image rm teamify-backend
    docker image rm teamify-backend-backup
    docker image rm teamify-db
    docker image rm teamify-db-backup
    docker volume rm teamify_db-data
    echo.
    echo Teamify successfully undeployed.
    echo.
    pause
) ELSE (
    # macOS commands
    echo "**********************************************************"
    echo "ATTENTION! This script will COMPLETELY uninstall Teamify, removing also all the images and the database data. Are you sure to undeploy?"
    echo "**********************************************************"
    echo
    read -p "Press [Enter] to continue or [Ctrl+C] to cancel..."
    docker-compose down
    docker image rm teamify-frontend
    docker image rm teamify-backend
    docker image rm teamify-backend-backup
    docker image rm teamify-db
    docker image rm teamify-db-backup
    docker volume rm teamify_db-data
    echo
    echo "Teamify successfully undeployed."
    echo
    read -p "Press [Enter] to exit..."
)
