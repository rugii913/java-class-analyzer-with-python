from service.class_analyzer_connector import ClassAnalyzerConnector
from service.hwp_connector import HwpConnector
from service.table_writer import ClassDefinitionHwpTableWriter


def main():
    classAnalyzerConnector = ClassAnalyzerConnector()
    analyzed_model = classAnalyzerConnector.analyze("jars\\java-class-analyzer-with-spoon-1.0-SNAPSHOT.jar")

    table_writer = ClassDefinitionHwpTableWriter(HwpConnector(), analyzed_model)
    table_writer.writeClassDefinitionTables()

    classAnalyzerConnector.cleanDecompiled()
    classAnalyzerConnector.shutdown()


if __name__ == "__main__":
    main()
