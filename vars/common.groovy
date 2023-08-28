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
//        sh 'npm test'
           echo "unit test for nodejs"
    }

    if (app_lang == "maven") {
        sh 'mvn test'
    }
}
