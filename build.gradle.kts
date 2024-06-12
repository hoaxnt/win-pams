plugins {
    id("java")
    id("io.freefair.aspectj.post-compile-weaving") version "8.6"
}

group = "com.winpams"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.javatuples:javatuples:1.2")
    implementation("io.github.cdimascio:dotenv-java:2.2.0")
    implementation("com.mysql:mysql-connector-j:8.4.0")
    implementation("org.mariadb.jdbc:mariadb-java-client:2.1.2")
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}


tasks.withType<Test> {
    useJUnitPlatform()
}
