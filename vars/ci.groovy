def call () {
    pipeline {

        agent {
            label 'workstation'
        }

        stages {
            stage ('Compile/Build') {
                steps {
                    echo "compile"
                }
            }

            stage ('Unit_tests') {
                steps {
                    echo "Unit_tests"
                }
            }

            stage ('Quality_control') {
                steps {
                    echo "Quality_control"
                }
            }

            stage ('Upload the artifact to central repo') {
                steps {
                    echo "artifact upload"
                }
            }

        }

    }
}