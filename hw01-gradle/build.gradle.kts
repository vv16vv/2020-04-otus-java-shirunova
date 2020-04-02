plugins {
    java
    idea
    id ("com.github.johnrengelman.shadow")
}

version = "0.1"

val junitVersion: String by project
val guavaVersion: String by project

java{
    sourceCompatibility = JavaVersion.VERSION_13
    targetCompatibility = JavaVersion.VERSION_13
}

repositories {
    jcenter()
}

dependencies {
    implementation("com.google.guava:guava:$guavaVersion")

    testImplementation("junit:junit:$junitVersion")
}

idea {
    module {
        isDownloadJavadoc = true
        isDownloadSources = true
    }
}

tasks {
    shadowJar {
        archiveBaseName.set("hw01-gradle-vshirunova")
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
