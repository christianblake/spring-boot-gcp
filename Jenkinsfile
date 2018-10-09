pipeline {
  agent any
  tools {
        maven 'maven'
  }
  environment {
        GOOGLE_APPLICATION_CREDENTIALS = '/Users/Shared/Jenkins/google/christian-blake-ffa1ebe7990c.json'
  }
  stages {
    stage('checkout project') {
      steps {
        checkout scm
      }
    }
 	stage ('Test') {
    	steps {
        	sh 'mvn clean test' 
       }
    }
    stage ('Package and Deploy to GC') {
    	steps {
        	sh 'mvn -DskipTests clean package' 
       }
    }
  }
}