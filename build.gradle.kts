import org.gradle.plugins.ide.idea.model.IdeaModel

plugins {
    id("io.spring.dependency-management")
    id("com.github.johnrengelman.shadow")
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "io.spring.dependency-management")
    apply(plugin = "com.github.johnrengelman.shadow")

    repositories {
        jcenter()
    }

    val junitVersion: String by project
    val guavaVersion: String by project
    val slf4jVersion: String by project

    configure<JavaPluginConvention> {
        sourceCompatibility = JavaVersion.VERSION_13
        targetCompatibility = JavaVersion.VERSION_13
        dependencies {
            add("implementation", "org.slf4j:slf4j-api:${slf4jVersion}")
            add("implementation", "com.google.guava:guava:$guavaVersion")
            add("testImplementation", "org.junit.jupiter:junit-jupiter")
        }
    }

    configure<IdeaModel> {
        module {
            isDownloadJavadoc = true
            isDownloadSources = true
        }
    }

    dependencyManagement {
        dependencies {
            imports {
                mavenBom("org.junit:junit-bom:$junitVersion")
            }
        }
    }

}
