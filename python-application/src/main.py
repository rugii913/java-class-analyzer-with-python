import sys

from PyQt6.QtWidgets import QApplication

from gui.main_window import MainWindow
from service.class_analyzer_connector import ClassAnalyzerConnector
from service.hwp_connector import HwpConnector
from service.table_writer import ClassDefinitionHwpTableWriter


def main():

    app = QApplication(sys.argv)
    window = MainWindow()
    window.show()
    sys.exit(app.exec())

    # 일부러 코드에 접근할 수 없는 위치로 옮김
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
