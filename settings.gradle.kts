pluginManagement {
	repositories {
		maven {
			name = "Fabric"
			url = uri("https://maven.fabricmc.net/")
		}
		mavenCentral()
		gradlePluginPortal()
		maven { url = uri("https://maven.blamejared.com/") }
	}

	plugins {
		id("fabric-loom") version providers.gradleProperty("loom_version")
	}
}

// Should match your modid
rootProject.name = "hextrapats"
