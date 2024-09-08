from service.class_analyzer_connector import ClassAnalyzerConnector
from service.hwp_connector import HwpConnector
from service.table_writer import ClassDefinitionHwpTableWriter


class AnalyzerAdapter:
    def __init__(self) -> None:
        self.__class_analyzer_connector = ClassAnalyzerConnector()

    def analyze(self, target_jar_path: str):
        class_analyzer_connector = self.__class_analyzer_connector

        type_simple_info_list = class_analyzer_connector.analyze(target_jar_path)

        table_writer = ClassDefinitionHwpTableWriter(HwpConnector(), type_simple_info_list)
        table_writer.writeClassDefinitionTables()

        class_analyzer_connector.cleanDecompiled()
        class_analyzer_connector.shutdown()
