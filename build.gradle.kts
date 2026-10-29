
import org.gradle.kotlin.dsl.modApi
import org.gradle.kotlin.dsl.modLocalRuntime
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
	id("fabric-loom")
	`maven-publish`
	id("org.jetbrains.kotlin.jvm") version "2.3.20"
	id("at.petra-k.pkpcpbp.PKJson5Plugin") version "0.2.0-pre-95"
}

pkJson5 {
	autoProcessJson5 = true
	autoProcessJson5Flattening = true
}

version = providers.gradleProperty("mod_version").get() + "+" + providers.gradleProperty("minecraft_version").get()
group = providers.gradleProperty("maven_group").get()

repositories {
	// Add repositories to retrieve artifacts from in here.
	// You should only use this when depending on other mods because
	// Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
	// See https://docs.gradle.org/current/userguide/declaring_repositories.html
	// for more information about repositories.
	mavenCentral()
	maven("https://maven.blamejared.com")
	maven("https://maven.shedaniel.me/")
	maven("https://maven.terraformersmc.com/releases/")
	maven("https://maven.ladysnake.org/releases")
	maven("https://maven.hexxy.media/")
}

loom {
	splitEnvironmentSourceSets()

	mods {
		register("hextrapats") {
			sourceSet(sourceSets.main.get())
			sourceSet(sourceSets.getByName("client"))
		}
	}
}

dependencies {
	// To change the versions see the gradle.properties file
	minecraft("com.mojang:minecraft:${properties["minecraft_version"]}")
	mappings("net.fabricmc:yarn:${properties["yarn_mappings"]}:v2")
	modImplementation("net.fabricmc:fabric-loader:${properties["loader_version"]}")

	// Fabric API. This is technically optional, but you probably want it anyway.
	modImplementation("net.fabricmc.fabric-api:fabric-api:${properties["fabric_api_version"]}")
	modImplementation("net.fabricmc:fabric-language-kotlin:${properties["fabric_kotlin_version"]}")

	// Hex casting
	modImplementation ("at.petra-k.hexcasting:hexcasting-fabric-${properties["minecraft_version"]}:${properties["hexcasting_version"]}") {
		exclude( "phosphor")
		exclude("lithium")
		exclude("emi")
	}
	modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-base:${properties["cca_version"]}")
	modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-entity:${properties["cca_version"]}")
	modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-item:${properties["cca_version"]}")
	modImplementation("dev.onyxstudios.cardinal-components-api:cardinal-components-block:${properties["cca_version"]}")
	modImplementation("at.petra-k.paucal:paucal-fabric-${properties["minecraft_version"]}:${properties["paucal_version"]}")
	modImplementation("vazkii.patchouli:Patchouli:1.20.1-84.1-FABRIC")
	modImplementation("com.samsthenerd.inline:inline-fabric:${properties["minecraft_version"]}-${properties["inline_version"]}")
	modApi("me.shedaniel.cloth:cloth-config-fabric:11.1.136")
	modLocalRuntime("io.github.tropheusj:serialization-hooks:0.4.99999")
}

tasks.processResources {
	inputs.property("version", version)

	filesMatching("fabric.mod.json") {
		expand("version" to version)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release = 17
}

kotlin {
	compilerOptions {
		jvmTarget = JvmTarget.JVM_17
	}
}

java {
	// Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
	// if it is present.
	// If you remove this line, sources will not be generated.
	withSourcesJar()

	sourceCompatibility = JavaVersion.VERSION_17
	targetCompatibility = JavaVersion.VERSION_17
}

tasks.jar {
	inputs.property("projectName", project.name)

	from("LICENSE") {
		rename { "${it}_${project.name}" }
	}
}

// configure the maven publication
publishing {
	publications {
		register<MavenPublication>("mavenJava") {
			from(components["java"])
		}
	}

	// See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
	repositories {
		// Add repositories to publish to here.
		// Notice: This block does NOT have the same function as the block in the top level.
		// The repositories here will be used for publishing your artifact, not for
		// retrieving dependencies.
	}
}
