// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath (ClassPaths.ANDROID_GRADELS)
        classpath (ClassPaths.KOTLIN_GRADLES)
        classpath(ClassPaths.HILT_DAGGER)

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }
}

tasks.register(name = "type", type = Delete::class) {
    delete(rootProject.buildDir)
}