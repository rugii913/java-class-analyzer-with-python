plugins {
    id("java")
}

group = "com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // spoon-core 종속성 추가
    implementation("fr.inria.gforge.spoon:spoon-core:10.4.1")

    // slf4j-simple 종속성 추가
    /*
    * SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder". 임시 조치
    * - 참고 자료
    *   - https://www.slf4j.org/codes.html#StaticLoggerBinder, https://www.slf4j.org/manual.html, https://github.com/qos-ch/slf4j/blob/master/README.md
    *   - https://stackoverflow.com/questions/14544991/how-to-configure-slf4j-simple
    * */
    // (cf.) implementation("org.slf4j:slf4j-api:2.0.16")는 → spoon-core에 포함됨
    implementation("org.slf4j:slf4j-simple:2.0.16")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}