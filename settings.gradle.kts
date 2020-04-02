rootProject.name = "2020-04-otus-java-shirunova"
include("hw01-gradle")
pluginManagement {
    val shadowVersion: String by settings
    plugins {
        java
        idea
        id ("com.github.johnrengelman.shadow") version shadowVersion apply false
    }
}

