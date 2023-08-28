def call () {
    try {
        pipeline {

            agent {
                label 'workstation'
            }

            stages {
                stage('Compile/Build') {
                    steps {
                        script {
                            common.compile()
                        }
                    }
                }

                stage('Unit_tests') {
                    steps {
                        script {
                            common.unit_test()
                        }
                    }
                }

                stage('Quality_control') {
                    environment {
                        SONAR_USER = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                        SONAR_PASS = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                    }
                    steps {
                        sh  "sonar-scanner -Dsonar.host.url=http://18.206.38.189:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${JOB_BASE_NAME}"
                    }
                }

                stage('Upload the artifact to central repo') {
                    steps {
                        echo "artifact upload"
                    }
                }
            }


        }
    } catch (Exception e) {
        common.email("failed")
    }

}

