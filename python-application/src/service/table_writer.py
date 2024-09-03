from service.hwp_connector import HwpConnector
from service.table_initializer import HwpTableInitializer


class ClassDefinitionHwpTableWriter:

    def __init__(self, hwp_connector: HwpConnector, analyzed_model):
        self.__hwp = hwp_connector.hwp
        self.__table_initializer = HwpTableInitializer(hwp_connector)
        self.__analyzed_model = analyzed_model

    def writeClassDefinitionTables(self):
        self.__table_initializer.InitializeFormTable()
        self.__addNewLineToDocumentEnd()

    def __writeEachTable(self):
        # self.__createFormTable()
        # self.__addNewLineToDocumentEnd()
        pass

    def __addNewLineToDocumentEnd(self):
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언

        hwp.HAction.Run("MoveTopLevelEnd")
        hwp.HAction.Run("BreakPara")
