from service.hwp_connector import HwpConnector

class ClassDefinitionHwpTableWriter:

    def __init__(self, hwp_connector: HwpConnector, analyzed_model):
        self.__hwp = hwp_connector.hwp
        self.__analyzed_model = analyzed_model

    def writeClassDefinitionTables(self):
        self.__writeEachTable()
        self.__addNewLineToDocumentEnd()

    def __writeEachTable(self):
        self.__createFormTable()
        self.__addNewLineToDocumentEnd()

    def __addNewLineToDocumentEnd(self):
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언

        hwp.HAction.Run("MoveTopLevelEnd")
        hwp.HAction.Run("BreakPara")

    def __createFormTable(self):
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언

        hwp.HAction.GetDefault("TableCreate", hwp.HParameterSet.HTableCreation.HSet)
        hwpTableCreation = (
            hwp.HParameterSet.HTableCreation
        )  # 편의를 위한 변수 할당 (cf.) 이 라인이 hwp.HAction.GetDefault("TableCreate", hwp.HParameterSet.HTableCreation.HSet)보다 위에 있으면 동작하지 않음

        hwpTableCreation.Rows = 7
        hwpTableCreation.HeightType = 1  # 높이 지정(0:자동, 1:임의값)
        hwpTableCreation.CreateItemArray("RowHeight", 7)  # 행 높이 지정을 위한 배열
        hwpTableCreation.RowHeight.SetItem(0, hwp.MiliToHwpUnit(5.6))
        hwpTableCreation.RowHeight.SetItem(1, hwp.MiliToHwpUnit(5.6))
        hwpTableCreation.RowHeight.SetItem(2, hwp.MiliToHwpUnit(5.6))
        hwpTableCreation.RowHeight.SetItem(3, hwp.MiliToHwpUnit(5.6))
        hwpTableCreation.RowHeight.SetItem(4, hwp.MiliToHwpUnit(5.6))
        hwpTableCreation.RowHeight.SetItem(5, hwp.MiliToHwpUnit(5.6))
        hwpTableCreation.RowHeight.SetItem(6, hwp.MiliToHwpUnit(5.6))
        hwpTableCreation.Cols = 5
        hwpTableCreation.WidthType = 2  # 너비 지정(0:단에맞춤, 1:문단에맞춤, 2:임의값)
        hwpTableCreation.CreateItemArray("ColWidth", 5)  # 열 너비 지정을 위한 배열
        hwpTableCreation.ColWidth.SetItem(0, hwp.MiliToHwpUnit(33.2))
        hwpTableCreation.ColWidth.SetItem(1, hwp.MiliToHwpUnit(23.2))
        hwpTableCreation.ColWidth.SetItem(2, hwp.MiliToHwpUnit(28.2))
        hwpTableCreation.ColWidth.SetItem(3, hwp.MiliToHwpUnit(36.2))
        hwpTableCreation.ColWidth.SetItem(4, hwp.MiliToHwpUnit(30.2))
        hwpTableCreation.TableProperties.TreatAsChar = 1  # 글자처럼 취급

        hwp.HAction.Execute("TableCreate", hwp.HParameterSet.HTableCreation.HSet)
