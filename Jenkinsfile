pipeline {
  agent any
  tools {
        maven 'maven'
  }
  environment {
        GOOGLE_APPLICATION_CREDENTIALS = '/Users/christianblake/Documents/ChristianBlake/GoogleCloud/christian-blake-ffa1ebe7990c.json'
  }
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