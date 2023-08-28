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
        sh 'npm test '

    }

    if (app_lang == "maven") {
        sh 'mvn test'
    }

    if (app_lang == "python") {
        sh 'python3 -m unittest'
    }
}

def email(email_note) {
    mail bcc: '', body: 'JOB Failed ${JOB_BASE_NAME}\n JENKINS_URL - ${JOB_URL}', cc: '', from: 'pcs04031999@gmail.com', replyTo: 'cp7524420@gmail.com', subject: 'failure in contineous integration', to: 'pcs04031999@gmail.com'
}
