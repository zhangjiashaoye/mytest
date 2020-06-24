pipeline {
    agent {
        // jenkins需要挂载/root/.docker/config.json，或者jenkins的docker有docker私有仓库的登录认证
        docker {
            image '192.168.26.116:8082/base/builder:1.0.3'
            args '-v /root/.m2:/root/.m2 -v /var/run/docker.sock:/var/run/docker.sock -v /root/.ssh:/root/.ssh'
        }
    }

    environment {
        DOCKER_REGISTRY = '192.168.26.116:8082'
    }

    parameters {
        string(name: 'SONAR_HOST_URL', defaultValue: 'http://192.168.26.116:9000', description: 'sonar url')
        //sonar中的令牌（jenkinsfile）
        string(name: 'SONAR_TOKEN', defaultValue: '5df808aa098dab6e7d5b48a473b4b370dd9e7340', description: 'sonar token')
        string(name: 'MAVEN_PROFILE', defaultValue: 'dev', description: 'maven profile激活')

        //string(name: 'LICENSING_DEPLOY_HOST', defaultValue: '192.168.200.129', description: 'licensing-service模块部署的地址')
        string(name: 'PROFILE_ACTIVE', defaultValue: 'dev', description: 'licensing-service profile激活环境')
        string(name: 'SERVER_PORT', defaultValue: '18806', description: 'licensing-service模块启动端口')
        string(name: 'LOG_DIR', defaultValue: '/home/topband/bluetooth-power-api/log', description: 'licensing-service日志挂载目录')

        choice(name: 'DEPLOY_LIST', description: '部署机器列表',
              choices: ['192.168.200.129']
            )
        string(name: 'Xms', defaultValue: '100m', description: 'JVM初始启动内存')
        string(name: 'Xmx', defaultValue: '1000m', description: 'JVM最大内存')

        string(name: 'SUCCESS_RECIPIENT_LIST', defaultValue: 'zhougs@topband.com.cn', description: '构建成功邮件通知列表')
        string(name: 'FAILURE_RECIPIENT_LIST', defaultValue: 'zhougs@topband.com.cn', description: '构建失败邮件通知列表')
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package -P${MAVEN_PROFILE} -pl bluetooth-power-api -am'
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }
        stage('Sonar') {
             steps {
                 echo "starting codeAnalyze with SonarQube......"
                 withSonarQubeEnv('Sonar') {
                     //注意这里withSonarQubeEnv()中的参数要与之前SonarQube servers中Name的配置相同
                     sh 'echo -n $(xmllint --xpath \'/*[local-name()="project"]/*[local-name()="version"]/text()\' bluetooth-power-api/pom.xml) >> version'
                     sh 'echo -n $(xmllint --xpath \'/*[local-name()="project"]/*[local-name()="version"]/text()\' bluetooth-power-api/pom.xml) >> artifactId'

                     sh "mvn install -P${MAVEN_PROFILE} -pl bluetooth-power-api -am -Dmaven.test.skip=true sonar:sonar -Dsonar.projectKey=bluetooth-power-api "+
                     "-Dsonar.projectName=bluetooth-power-api "+
                     "-Dsonar.projectVersion=1.0 "+
                     "-Dsonar.sourceEncoding=UTF-8 -Dsonar.exclusions=src/test/** "+
                     "-Dsonar.sources=src/ -Dsonar.java.binaries=target/classes "+
                     "-Dsonar.host.url=${params.SONAR_HOST_URL} "+
                     "-Dsonar.login=${params.SONAR_TOKEN}"
                 }
               //script {
               //    timeout(1) {
               //        //这里设置超时时间1分钟，不会出现一直卡在检查状态
               //        //利用sonar webhook功能通知pipeline代码检测结果，未通过质量阈，pipeline将会fail
               //        def qg = waitForQualityGate('sonar')
               //        //注意：这里waitForQualityGate()中的参数也要与之前SonarQube servers中Name的配置相同
               //        if (qg.status != 'OK') {
               //            error "未通过Sonarqube的代码质量阈检查，请及时修改！failure: ${qg.status}"
               //        }
               //    }
               //}
             }
         }
        stage('Deploy') {
            steps {
                sh 'mvn -B -DskipTests deploy -P${MAVEN_PROFILE}  -pl bluetooth-power-api -am'
            }
        }
        stage('Deliver-dev') {
            steps {
                echo "---------Deliver for licensing-service, start----------"
                // 获取模块的artifactId、version
                sh 'echo -n topband_back/$(xmllint --xpath \'/*[local-name()="project"]/*[local-name()="artifactId"]/text()\' bluetooth-power-api/pom.xml): > docker_tag'
                sh 'echo -n $(xmllint --xpath \'/*[local-name()="project"]/*[local-name()="version"]/text()\' bluetooth-power-api/pom.xml) >> docker_tag'
                // 启动docker容器
                sh 'docker -H ${DEPLOY_LIST} pull ${DOCKER_REGISTRY}/`cat docker_tag`'
                sh 'docker -H ${DEPLOY_LIST} rm -f $(cut -d: -f1 docker_tag | tr / .) ||  true'
                sh 'docker -H ${DEPLOY_LIST} run -d \
                    -e PROFILE_ACTIVE=${PROFILE_ACTIVE} \
                    -e SERVER_PORT=${SERVER_PORT} \
                    -p ${SERVER_PORT}:${SERVER_PORT} \
                    -v ${LOG_DIR}:/home/topband/bluetooth-power-api/log \
                    --log-opt max-size=1000m --log-opt max-file=2 \
                    --restart always \
                    --name $(cut -d: -f1 docker_tag | tr / .) ${DOCKER_REGISTRY}/`cat docker_tag` \
                    -s ${Xms} -x ${Xmx}'
                 // 休眠18s
                 sh 'date;sleep 18s;date'
                 // 获取容器运行状态
                 sh 'cat /dev/null > status'
                 script{
                    for(int i =0; i < 12; i++) {
                       sh 'sleep 1s'
                       sh 'tmp=`docker -H ${DEPLOY_LIST} inspect --format "{{.State.Status}}" $(cut -d: -f1 docker_tag | tr / .)`; echo "`cat status`$tmp" > status;'
                       //sh 'docker -H ${DEPLOY_LIST} inspect --format "{{.State.Status}}" $(cut -d: -f1 docker_tag | tr / .) >> status || true'
                    }
                 }
                 sh 'if [ `grep -i "restarting" status` ] ; then \
                        echo "$(cut -d: -f1 docker_tag | tr / .) is restarting"; exit 1 ; \
                     fi'
                 echo "---------Deliver for licensing-service, end----------"
            }
            post {
            	success {
            		emailext (
            			subject: "'${env.JOB_NAME} [${env.BUILD_NUMBER}]' 部署正常",
            			body: """
            			详情：
            			SUCCESSFUL: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'
            			状态：${env.JOB_NAME} jenkins 更新运行正常
            			URL ：${env.BUILD_URL}
            			项目名称 ：${env.JOB_NAME}
            			项目更新进度：${env.BUILD_NUMBER}
            			""",
            			to: "${SUCCESS_RECIPIENT_LIST}",
            			recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            			)
            		}
            	failure {
            			emailext (
            				subject: "'${env.JOB_NAME} [${env.BUILD_NUMBER}]' 部署失败",
            				body: """
            				详情：
            				FAILED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'
            				状态：${env.JOB_NAME} jenkins 运行失败
            				URL ：${env.BUILD_URL}
            				项目名称 ：${env.JOB_NAME}
            				项目更新进度：${env.BUILD_NUMBER}
            				""",
            				to: "${FAILURE_RECIPIENT_LIST}",
            				recipientProviders: [[$class: 'DevelopersRecipientProvider']]
            				)
            		}
            }
        }
    }
}