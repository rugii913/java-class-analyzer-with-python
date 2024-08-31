plugins {
    kotlin("jvm")
}

group = "io"
version = "0.1-SNAPSHOT"

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
    implementation(kotlin("stdlib-jdk8"))
}

tasks {
    test {
        useJUnitPlatform()
    }

    register<Jar>("buildFatJar") {
        duplicatesStrategy = DuplicatesStrategy.WARN // 파일명 중복 시 경고
        from(configurations.runtimeClasspath.get().map { if (it.isDirectory) it else zipTree(it) }) {
            exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.RSA")
            /*
            * exclude 관련 참고 - Invalid signature file digest for Manifest main attributes 런타임 오류 관련
            * - 기타 블로그 - [Exception] java.lang.SecurityException: Invalid signature file digest for Manifest main attributes
            *   - https://jojoldu.tistory.com/15
            * - 기타 GitHub 이슈 - Please change build.gradle to avoid "Invalid signature file digest for Manifest main attributes" errors
            *   - https://github.com/edvin/tornadofx-idea-plugin/issues/18
            * */
            // (cf.) 전체적인 구조는 인프런 "스프링 부트 - 핵심 원리와 활용"의 "스프링 부트와 웹 서버 - 빌드와 배포" 부분을 참고하였음
        }
        with(jar.get())
    }
}

kotlin {
    jvmToolchain(17)
}
