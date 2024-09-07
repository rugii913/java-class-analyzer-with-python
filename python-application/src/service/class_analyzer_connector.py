import jpype
import jpype.imports
from jpype.types import *


# ClassAnalyzerConnector 싱글톤을 보장하기 위한 메타 클래스
class ClassAnalyzerConectorMeta(type):
    _instances = {}

    def __call__(class_, *args, **kwargs):
        if class_ not in class_._instances:
            class_._instances[class_] = super(
                ClassAnalyzerConectorMeta, class_
            ).__call__(*args, **kwargs)

        return class_._instances[class_]


# Java 라이브러리를 사용하기 위한 클래스
class ClassAnalyzerConnector(metaclass=ClassAnalyzerConectorMeta):
    def __init__(self) -> None:
        jpype.startJVM(
            jvmpath=jpype.getDefaultJVMPath(),
            classpath="analyzer/class-analyzer-0.1-SNAPSHOT.jar",
        )
        ClassAnalyzerController = jpype.JClass(
            "io.classanalyzer.ClassAnalyzerController"
        )
        self.__classAnalyzerController = ClassAnalyzerController()

    # 대상 jar 파일 분석 후 CtModel 반환
    def analyze(self, target_jar_path):
        return self.__classAnalyzerController.analyze(target_jar_path)

    # 디컴파일된 코드 삭제 작업
    def cleanDecompiled(self):
        self.__classAnalyzerController.cleanDecompiled()

    ## jpype 종료 작업
    def shutdown(self):
        jpype.shutdownJVM()
