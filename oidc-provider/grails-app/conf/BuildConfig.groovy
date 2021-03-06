grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.fork = [
    // configure settings for compilation JVM, note that if you alter the Groovy version forked compilation is required
    //  compile: [maxMemory: 256, minMemory: 64, debug: false, maxPerm: 256, daemon:true],

    // configure settings for the test-app JVM, uses the daemon by default
    test: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, daemon:true],
    // configure settings for the run-app JVM
    run: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the run-war JVM
    war: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256, forkReserve:false],
    // configure settings for the Console UI JVM
    console: [maxMemory: 768, minMemory: 64, debug: false, maxPerm: 256]
]

grails.project.dependency.resolver = "maven" // or ivy
grails.project.dependency.resolution = {
    // inherit Grails' default dependencies
    inherits("global") {
        // uncomment to disable ehcache
        // excludes 'ehcache'
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
        // uncomment the below to enable remote dependency resolution
        // from public Maven repositories
        //mavenRepo "http://repository.codehaus.org"
        //mavenRepo "http://download.java.net/maven/2/"
        //mavenRepo "http://repository.jboss.com/maven2/"
    }
    dependencies {
        //TODO: Can we do 'org.mitre:openid-connect-server-webapp:1.1.8@war' ?
        compile 'org.mitre:openid-connect-server:1.1.8'

        // TODO: eliminate HSQL & ECLIPSELINK dependencies
        // JPA + dataSource from MITREid
        runtime 'org.eclipse.persistence:org.eclipse.persistence.jpa:2.5.1'
        runtime 'org.hsqldb:hsqldb:2.2.9'
        runtime 'commons-dbcp:commons-dbcp:1.4'

        runtime 'org.springframework:spring-webmvc:3.2.8.RELEASE'
        runtime 'org.springframework:spring-orm:3.2.8.RELEASE'
        // for JSP templates
        runtime ('org.springframework.security:spring-security-taglibs:3.1.4.RELEASE') {
            excludes group: 'org.springframework'
        }
        runtime 'javax.servlet:jstl:1.2'

    }

    plugins {
        build(":release:3.0.1",
              ":rest-client-builder:1.0.3") {
            export = false
        }
    }
}
