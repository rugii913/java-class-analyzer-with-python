plugins {
    id("java")
}

group = "com"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Spoon Core 종속성
    implementation("fr.inria.gforge.spoon:spoon-core:11.0.0")
    // Spoon Decompiler 종속성 - JarLauncher를 사용하기 위함
    implementation("fr.inria.gforge.spoon:spoon-decompiler:0.1.0")

    // Apache Commons IO 종속성 - 파일 처리
    implementation("commons-io:commons-io:2.16.1")

    // SLF4J Simple Provider 종속성 - SLF4J: Failed to load class "org.slf4j.impl.StaticLoggerBinder" 오류에 대한 임시 조치
    /*
    * - 참고 자료
    *   - https://www.slf4j.org/codes.html#StaticLoggerBinder, https://www.slf4j.org/manual.html, https://github.com/qos-ch/slf4j/blob/master/README.md
    *   - https://stackoverflow.com/questions/14544991/how-to-configure-slf4j-simple
    * */
    implementation("org.slf4j:slf4j-simple:2.0.16") // (cf.) implementation("org.slf4j:slf4j-api:2.0.16")는 spoon-core에 포함됨

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}