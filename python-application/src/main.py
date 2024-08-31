import jpype
import jpype.imports
from jpype.types import *


def main():
    jpype.startJVM(
        jvmpath=jpype.getDefaultJVMPath(),
        classpath="jars/class-analyzer-0.1-SNAPSHOT.jar",
    )
    print(jpype.isJVMStarted())

    ClassAnalyzerController = jpype.JClass("io.classanalyzer.ClassAnalyzerController")

    analyzer = ClassAnalyzerController()

    analyzer.analyze("jars/java-class-analyzer-with-spoon-1.0-SNAPSHOT.jar")

    # 디컴파일된 코드 삭제 작업
    analyzer.cleanDecompiled()
    jpype.shutdownJVM()


if __name__ == "__main__":
    main()
