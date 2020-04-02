plugins {
    java
    idea
}

repositories {
    jcenter()
}

dependencies {
    val slf4jVersion: String by project

    implementation("org.slf4j:slf4j-api:${slf4jVersion}")
}

