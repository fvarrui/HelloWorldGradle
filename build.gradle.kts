import io.github.fvarrui.javapackager.gradle.PackageTask
import io.github.fvarrui.javapackager.gradle.PackagePluginExtension
import io.github.fvarrui.javapackager.model.Registry
import io.github.fvarrui.javapackager.model.RegistryEntry
import io.github.fvarrui.javapackager.model.Platform
import io.github.fvarrui.javapackager.model.Scripts
import io.github.fvarrui.javapackager.model.WindowsConfig
import io.github.fvarrui.javapackager.model.HeaderType
import io.github.fvarrui.javapackager.model.ValueType
import groovy.lang.Closure

buildscript {
	repositories {
        mavenCentral()
	}
	dependencies {
		classpath("io.github.fvarrui:javapackager:1.6.0-SNAPSHOT")
	}
}

plugins {
    java
    id("maven-publish")
}

apply(plugin = "io.github.fvarrui.javapackager.plugin")

dependencies {
    implementation("commons-io:commons-io:2.6")
}

group = "io.github.fvarrui"
version = "1.0.0"
description = "HelloWorld for Gradle"

java {                                      
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

configure<PackagePluginExtension> {
	mainClass("io.github.fvarrui.helloworld.Main")
	additionalResources(listOf(File("src/main/resources/info.txt")))
	bundleJre(true)
	generateInstaller(true)
}

tasks.register<PackageTask>("packageForWindows") {
	setDescription("Packages the application as a native Windows executable and bundles it in a zipball")
	setPlatform(Platform.windows)
	setCreateZipball(false)
	setAdditionalModulePaths(listOf(File("src")))
	setBundleJre(true)
	winConfig(closureOf<WindowsConfig>({
		setHeaderType(HeaderType.console)
		setGenerateSetup(true)
		setGenerateMsm(true)
		setGenerateMsi(true)
		setRegistry(
			Registry(
				listOf(
					RegistryEntry("HKCU:MyGradleApp", "greeting", ValueType.REG_SZ, "hello")
				)
			)
		)
	}) as Closure<WindowsConfig>)
	scripts(closureOf<Scripts>({
		setBootstrap(File("assets/windows/script.bat"))
	}) as Closure<Scripts>)
	dependsOn(tasks.build)
}

tasks.register<PackageTask>("packageForLinux") {
	setDescription("Packages the application as a native GNU/Linux executable and bundles it in a tarball")
	setPlatform(Platform.linux)
	setBundleJre(true)
	setCreateTarball(true)
	setGenerateInstaller(true)
	scripts(closureOf<Scripts>({
		setBootstrap(File("assets/bootstrap.sh"))
	}) as Closure<Scripts>)
	dependsOn(tasks.build)
}

tasks.register<PackageTask>("packageForMac") {
	setDescription("Packages the application as a native Mac OS app and bundles it in a tarball")
	setPlatform(Platform.mac)
	setCreateTarball(true)
	scripts(closureOf<Scripts>({
		setBootstrap(File("assets/bootstrap.sh"))
	}) as Closure<Scripts>)
	dependsOn(tasks.build)
}

tasks.register("packageForAllPlatforms") {
	setDescription("Packages the application for all platforms")
	setGroup("JavaPackager")
	dependsOn(listOf("packageForWindows", "packageForLinux", "packageForMac"))
}
