subprojects {

    apply(plugin = "java")

    repositories {
        mavenCentral()
    }

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_11
    }

    val test by tasks.getting(Test::class) {
        // Use junit platform for unit tests
        useJUnitPlatform()
    }

    dependencies {
        "compile"("org.javamoney", "moneta", "1.3")
        "compile"("org.jboss.weld.se:weld-se-shaded:3.1.2.Final")
        "compile"("org.jboss.weld.servlet:weld-servlet-core:3.1.2.Final")

        "testImplementation"("org.junit.jupiter:junit-jupiter-api:5.4.2")
        "testRuntimeOnly"("org.junit.jupiter:junit-jupiter-engine:5.4.2")
        "testImplementation"("org.mockito:mockito-core:2.28.2")
        "testImplementation"("org.assertj:assertj-core:3.12.2")

    }
}
