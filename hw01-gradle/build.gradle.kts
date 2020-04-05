plugins {
    java
    id("com.github.johnrengelman.shadow")
}

version = "0.1"

tasks {
    shadowJar {
        archiveBaseName.set("fat-${project.name}")
        archiveVersion.set("${project.version}")
        archiveClassifier.set("")
        manifest {
            attributes["Main-Class"] = "ru.otus.vsh.hw01.HelloOtus"
        }
    }
    build {
        dependsOn("shadowJar")
    }
}
