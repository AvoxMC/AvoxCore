pipeline {
  agent {
    any 'maven:3.8.1-adoptopenjdk-11'
  }
  stages {
    stage('Build') {
      steps {
        sh 'mvn clean install'
      }
    }

    stage('Archive') {
      steps {
        archiveArtifacts '**/*.jar'
      }
    }

    stage('Clean') {
      steps {
        sh 'mvn clean'
      }
    }

  }
}