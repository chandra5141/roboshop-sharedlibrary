def compile() {
    if (app_lang == "nodejs") {
        sh 'npm install'
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
    mail bcc: '', body: 'check in jenkins dashboard http://jenkins.chandupcs.online:8080', cc: '', from: 'pcs04031999@gmail.com', replyTo: 'cp7524420@gmail.com', subject: 'failure in contineous integration', to: 'pcs04031999@gmail.com'
}
