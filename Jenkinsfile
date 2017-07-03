pipeline {
    agent any
    tools { 
        maven 'Maven 3.5.0' 
        jdk 'jdk8' 
    }
    stages {
        stage('initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}"
                '''
            }
        }
        
        stage('build') {
            steps {
                sh 'mvn --version'
            }
        }
    }
}
