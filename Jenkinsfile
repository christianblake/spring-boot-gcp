pipeline {
  agent any
  stages {
    stage('checkout project') {
      steps {
        checkout scm
      }
    }
    stage ('Clean') {
    	steps {
        	sh 'mvn clean' 
       }
    }
    stage ('Test') {
    	steps {
        	sh 'mvn test' 
       }
    }
  }
}