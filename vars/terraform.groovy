def call(){
    pipeline {

        agent {
            node {
                label 'workstation'
            }

        }

        parameters{
//          string(name:'INFRA_ENV', defaultValue: '', description: 'enter env like dev or prod')
            choice(name: 'INFRA_ENV', choices: ['dev', 'prod'], description: 'enter env like dev or prod')
            choice(name: 'ACTION', choices: ['apply', 'destroy', 'plan'], description: 'Pick something')
        }

        stages{
            stage('terraform init'){

                steps{

                    sh "sudo terraform init -backend-config=env-${INFRA_ENV}/state.tfvars"
                }


            }

            stage('terraform plan'){

                steps{

                    sh "sudo terraform ${ACTION}  -var-file=env-${INFRA_ENV}/main.tfvars"

                }


            }

            stage('terraform action - ${ACTION}'){
                if ( ACTION == "apply" ) {
                    sh "sudo terraform ${ACTION} -auto-approve -var-file=env-${INFRA_ENV}/main.tfvars"
                }
            }


        }

        post{
            always {
                cleanWs()
            }
        }
    }
}