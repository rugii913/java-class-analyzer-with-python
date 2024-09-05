from service.class_analyzer_connector import ClassAnalyzerConnector
from service.hwp_connector import HwpConnector
from service.table_writer import ClassDefinitionHwpTableWriter


def main():
    classAnalyzerConnector = ClassAnalyzerConnector()
    print("분석할 jar 파일의 경로를 입력하세요. >>> ", end = "") # (ex.) C:\toy-project\java-class-analyzer-with-python\python-application\jars\class-analyzer-ex.jar
    target_jar_path = input()
    analyzed_model = classAnalyzerConnector.analyze(target_jar_path)

    table_writer = ClassDefinitionHwpTableWriter(HwpConnector(), analyzed_model)
    table_writer.writeClassDefinitionTables()

    classAnalyzerConnector.cleanDecompiled()
    classAnalyzerConnector.shutdown()


if __name__ == "__main__":
    main()
