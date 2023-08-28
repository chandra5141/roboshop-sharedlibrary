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
                    steps {
                        echo "Quality_control"
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

