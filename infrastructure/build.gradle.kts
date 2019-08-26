
dependencies {

    compile(project(":application"))

   compile("javax:javaee-api:8.0")
    compile("org.jboss.resteasy:resteasy-core:4.2.0.Final")
    compile("org.jboss.resteasy:resteasy-servlet-initializer:4.2.0.Final")
    compile("org.jboss.resteasy:resteasy-undertow:4.2.0.Final")
    compile("org.jboss.resteasy:resteasy-validator-provider:4.2.0.Final")
    compile("org.jboss.resteasy:resteasy-jackson2-provider:4.2.0.Final")
    compile("org.jboss.resteasy:resteasy-rxjava2:4.2.0.Final")
    compile("org.jboss.resteasy:resteasy-cdi:4.2.0.Final")

    compile("javax.servlet:javax.servlet-api:4.0.1")

    compile("io.undertow:undertow-core:2.0.13.Final")
    compile("io.undertow:undertow-servlet:2.0.13.Final")

    compile("org.jboss.weld.servlet:weld-servlet-core:2.4.7.Final")

    compile("io.swagger:swagger-jaxrs:1.5.9")
    compile("org.hibernate:hibernate-validator:6.0.17.Final")
    compile("org.glassfish:javax.el:3.0.0")

    compile("com.google.code.gson:gson:2.8.5")

    compile("org.slf4j:slf4j-api:1.7.28")
    compile("ch.qos.logback:logback-classic:1.2.3")
    compile("ch.qos.logback.contrib:logback-json-classic:0.1.5")
    compile("ch.qos.logback.contrib:logback-jackson:0.1.5")

    //database
    compile("org.mybatis:mybatis:3.5.2")
    compile("org.mybatis:mybatis-cdi:1.1.0")

    compile("com.h2database:h2:1.4.199")
    compile("org.liquibase:liquibase-core:3.5.0")
    compile("org.liquibase:liquibase-groovy-dsl:2.0.3")
}