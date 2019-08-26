import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "5.1.0"
}

group = "de.gefa.hr-requisition.service"
version = "0.0.4"

dependencies {
    compile(project(":infrastructure"))
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "bank.revolut.fund.transfer.FundsTransferApplication"
    }
}


//Fix for issue in resolving MessageBodyReader for REST request body
tasks.withType<ShadowJar> {
    mergeServiceFiles()
}



