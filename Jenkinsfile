#!groovy
build('binbase', 'java-maven') {
    checkoutRepo()
    loadBuildUtils()

    runStage('Init submodules') {
        withGithubPrivkey {
            def privKey = sh (
                script: 'echo ${GITHUB_PRIVKEY} | sed -e \'s|%|%%|g\'',
                returnStdout: true
            ).trim()

            withEnv(["GIT_SSH_COMMAND=ssh -o \"IdentityFile=${privKey}\" -o StrictHostKeyChecking=no -o User=git"]) {
                sh 'git submodule update --init --recursive'
            }
        }
    }

    def javaServicePipeline
    runStage('load JavaService pipeline') {
        javaServicePipeline = load("build_utils/jenkins_lib/pipeJavaService.groovy")
    }

    def serviceName = env.REPO_NAME
    def mvnArgs = '-DjvmArgs="-Xmx256m"'
    def useJava11 = true
    def registry = 'dr2.rbkmoney.com'
    def registryCredsId = 'jenkins_harbor'

    javaServicePipeline(serviceName, useJava11, mvnArgs, registry, registryCredsId)
}