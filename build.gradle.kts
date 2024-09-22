plugins {
    // This applies the Java plugin, which adds tasks for compiling Java code, running tests, and creating JAR files.
    id("java")
    // Apply the application plugin for building an executable application
    // application: This applies the Application plugin, which provides support for running the application from the command line.
    application
    kotlin("jvm")
}

// Defines the group ID for the project, typically representing the organization or company
group = "org.example"
// Set the version of the project
version = "1.0-SNAPSHOT"

application {
    // Specifies the fully qualified name of the main class to be executed when running the application. This is the entry point for the program.
    mainClass.set("org.example.GreetingServerApplication")
}

// This section defines where to find the project dependencies. mavenCentral() specifies that dependencies will be pulled from the Maven Central Repository.
repositories {
    mavenCentral()
}

// Declare project dependencies
// only things in the dependencies that look like implementation(“”) will be put in the fat jar
dependencies {
    // JUnit testing framework
    testImplementation(platform("org.junit:junit-bom:5.10.0")) // Import the JUnit BOM for consistent versioning
    testImplementation("org.junit.jupiter:junit-jupiter") // Add JUnit Jupiter for testing

    // Lombok for reducing boilerplate code
    compileOnly("org.projectlombok:lombok:1.18.34") // Lombok available at compile time, but not included in the JAR
    annotationProcessor("org.projectlombok:lombok:1.18.34") // Process Lombok annotations at compile time
    testCompileOnly("org.projectlombok:lombok:1.18.34") // Lombok available for testing only
    testAnnotationProcessor("org.projectlombok:lombok:1.18.34") // Process Lombok annotations for tests

    // SLF4J for logging
    implementation("org.slf4j:slf4j-api:2.0.9") // SLF4J API for logging
    implementation("org.slf4j:slf4j-simple:2.0.9") // Simple implementation of SLF4J

    // JCommander for parsing command line arguments
    implementation("org.jcommander:jcommander:2.0") // Add JCommander library for argument parsing

    // Mockito for creating mock objects in tests
    testImplementation("org.mockito:mockito-core:5.13.0") // Add Mockito for testing with mocks
    implementation(kotlin("stdlib-jdk8"))
}

// Configure the JAR task
tasks.jar {

//    The manifest block is used to define metadata for the JAR file.
//    By specifying attributes["main-class"], you indicate which class contains the main method that will be executed when the JAR is run.
//    This makes it executable from the command line using java -jar yourapp.jar.
    manifest {
        attributes["main-class"] = "org.example.GreetingServerApplication"
    }

    // Exclude duplicate files in the JAR, remove implicit dependencies
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    //The from block specifies how to include runtime dependencies in the JAR.
    //configurations.runtimeClasspath.get() retrieves all the dependencies required at runtime.
    //The filter { it.name.endsWith("jar") } ensures that only JAR files are considered.
    //map { zipTree(it) } extracts the contents of each JAR file, adding them to the final JAR.
    // This means that your application will be self-contained, allowing it to run without needing external dependencies to be installed separately.
    // Include runtime dependencies in the JAR
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}

// Configure the test task
tasks.test {
    // Use JUnit platform for running tests
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}