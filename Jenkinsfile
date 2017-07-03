pipeline {
    agent any
    tools { 
        maven 'Maven_3_5_0' 
        jdk 'jdk1.8.0_131.jdk' 
    }
    stages {
        stage('initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                    'mvn --version'
                '''
            }
        }
        
        stage('build') {
            steps {
                sh 'mvn -B install'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'       
                }
            }
        }
    }
}
