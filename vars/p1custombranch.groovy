@NonCPS
def selectBranch() {
    try {
        timeout(time:60, unit:'SECONDS') {
/*                def selectBranch = input message: 'Please select branch', ok: 'Next',
                parameters: [
                    [
                    $class: 'ChoiceParameterDefinition',
                    name: 'BRANCH', 
                    choices: BranchList,
                    description: 'Project branches'
                    ]
                ]
        }*/

        BRANCH_TO_BUILD_REQUESTED = input(
                message: 'Input branch to build', 
                parameters: [
                    [$class: 'TextParameterDefinition', 
                        //defaultValue: BRANCH_TO_BUILD_DEFAULT, 
                        defaultValue: 'develop', 
                        description: 'Branch name', name: 'Enter branch name (or leave default) and press [Proceed]:']
                ])
                echo ("User has entered the branch name: " + BRANCH_TO_BUILD_REQUESTED)
        }
    } catch(err) {
        echo err.getMessage()
        echo "Input aborted"
    }
}

def call() {
    script {
        def repositoryUrl = scm.userRemoteConfigs[0].url

        def getBranches = ("git ls-remote -t -h ${repositoryUrl}").execute()
        
        def BranchList = getBranches.text.readLines().collect {
                it.split()[1].replaceAll('refs/heads/', '').replaceAll('refs/tags/', '').replaceAll("\\^\\{\\}", '')
        }
        echo "BranchList: ${BranchList}"
        selectBranch();
/*        def selectBranch = input message: 'Please select branch', ok: 'Next',
            parameters: [
                [
                $class: 'ChoiceParameterDefinition',
                name: 'BRANCH', 
                choices: BranchList,
                description: 'Project branches'
                ]
            ]*/
        
        //echo "You selected branch with name: ${selectBranch}"

        //def selectedBranch = sh (returnStdout: true, script: "echo ${selectBranch}")
        //echo "You selected branch with name: ${selectedBranch}"
        //env.BRANCH_NAME=selectedBranch
    }
}
