def compile() {
    if (app_lang == "nodejs") {
        sh 'npm install'
        sh 'env'
    }

    if (app_lang == "maven") {
        sh 'mvn clean package'
    }
}

def unit_test() {
    if (app_lang == "nodejs") {
        sh 'npm test || true'

    }

    if (app_lang == "maven") {
        sh 'mvn test'
    }

    if (app_lang == "python") {
        sh 'python3 -m unittest'
    }
}

def email(email_note) {
    mail bcc: '', body: "JOB Failed ${JOB_BASE_NAME}\nJENKINS_URL - ${JOB_URL}", cc: '', from: 'pcs04031999@gmail.com', replyTo: 'cp7524420@gmail.com', subject: "FAILURE IN JENKINS INTEGRATION ${JOB_BASE_NAME} ", to: 'pcs04031999@gmail.com'
}


def artifactpush (){

    sh "echo ${TAG_NAME} >VERSION"

    sh 'env'

    if (app_lang == "nodejs") {
        sh "zip -r ${component}-${TAG_NAME}.zip node_modules server.js VERSION ${extra_files}"
    }

// for nginx and python all files required except jenkinsfile so using -x we can avoid unzipping
    if (app_lang == "nginx" || app_lang == "python") {
        sh "zip -r ${component}-${TAG_NAME}.zip * -x Jenkinsfile ${extra_files}"
    }

    if (app_lang == "maven")  {
        sh "zip -r ${component}-${TAG_NAME}.zip  ${component}.jar VERSION ${extra_files}"
    }


    NEXUS_PASS = sh ( script: 'sudo aws ssm get-parameters --region us-east-1 --names nexus.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
    NEXUS_USER = sh ( script: 'sudo aws ssm get-parameters --region us-east-1 --names nexus.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
    wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${NEXUS_PASS}", var: 'SECRET']]]) {
        sh "curl -v -u ${NEXUS_USER}:${NEXUS_PASS} --upload-file ${component}-${TAG_NAME}.zip http://172.31.93.220:8081/repository/${component}/${component}-${TAG_NAME}.zip"
    }

}