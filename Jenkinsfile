#!groovy
build('binbase', 'java-maven') {
    checkoutRepo()

    def serviceName = env.REPO_NAME
    def mvnArgs = '-DjvmArgs="-Xmx256m"'

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

    // Run mvn and generate docker file
    runStage('Maven package') {
        withCredentials([[$class: 'FileBinding', credentialsId: 'java-maven-settings.xml', variable: 'SETTINGS_XML']]) {
            def mvn_command_arguments = ' --batch-mode --settings  $SETTINGS_XML -P ci ' +
                    " -Dgit.branch=${env.BRANCH_NAME} " +
                    " ${mvnArgs}"
            if (env.BRANCH_NAME == 'master') {
                sh 'mvn deploy' + mvn_command_arguments
            } else {
                sh 'mvn package' + mvn_command_arguments
            }
        }
    }

    def serviceImage;
    def imgShortName = 'rbkmoney/' + "${serviceName}" + ':' + '$COMMIT_ID';
    getCommitId()
    runStage('Build Service image') {
        serviceImage = docker.build(imgShortName, '-f ./target/Dockerfile ./target')
    }

    try {
        if (env.BRANCH_NAME == 'master' || env.BRANCH_NAME.startsWith('epic')) {
            runStage('Push Service image') {
                docker.withRegistry('https://dr.rbkmoney.com/v2/', 'dockerhub-rbkmoneycibot') {
                    serviceImage.push();
                }
                // Push under 'withRegistry' generates 2d record with 'long filename' in local docker registry.
                // Untag the long-filename
                sh "docker rmi dr.rbkmoney.com/${imgShortName}"
            }
        }
    }
    finally {
        runStage('Remove local image') {
            // Remove the image to keep Jenkins runner clean.
            sh "docker rmi ${imgShortName}"
        }
    }
}

