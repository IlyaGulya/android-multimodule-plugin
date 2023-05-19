import org.gradle.kotlin.dsl.configure
import org.jetbrains.intellij.IntelliJPluginExtension
import org.jetbrains.intellij.tasks.InstrumentCodeTask
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import ru.hh.plugins.ExternalLibrariesExtension

plugins {
    id("convention.kotlin-jvm")
    id("org.jetbrains.intellij")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}

configure<IntelliJPluginExtension> {
    type.set("IC")

    val currentVersion = Libs.chosenIdeaVersion
    when (currentVersion) {
        is ExternalLibrariesExtension.Product.ICBasedIde -> {
            version.set(currentVersion.ideVersion)
        }

        is ExternalLibrariesExtension.Product.LocalIde -> {
            localPath.set(currentVersion.pathToIde)
        }
    }
    plugins.set(currentVersion.pluginsNames)
}

tasks.getByName<InstrumentCodeTask>("instrumentCode") {
    val currentVersion = Libs.chosenIdeaVersion
    if (currentVersion is ExternalLibrariesExtension.Product.LocalIde) {
        compilerVersion.set(currentVersion.compilerVersion)
    }
}
