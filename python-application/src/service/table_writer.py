from service.hwp_connector import HwpConnector
from service.table_initializer import HwpTableInitializer


class ClassDefinitionHwpTableWriter:

    def __init__(self, hwp_connector: HwpConnector, type_simple_info_list):
        self.__hwp = hwp_connector.hwp
        self.__table_initializer = HwpTableInitializer(hwp_connector)
        self.__type_simple_info_list = type_simple_info_list

    def writeClassDefinitionTables(self):
        for type_simple_info in self.__type_simple_info_list:
            self.__writeEachTable(type_simple_info)
            self.__addNewLineToDocumentEnd()

    def __writeEachTable(self, type_simple_info):
        self.__table_initializer.InitializeFormTable()

        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언
        # fields 텍스트 입력할 위치로(네번째 행 첫 열) # TODO HwpTableInitializer의 __moveCaretToFirstCell()과 중복 제거 필요할 수도 있음
        hwp.Run("TableCellBlock")
        hwp.Run("TableColBegin")
        hwp.Run("TableColPageUp")
        hwp.Run("TableLowerCell")
        hwp.Run("TableLowerCell")
        hwp.Run("TableLowerCell")
        hwp.Run("Cancel")

        fields = type_simple_info.getFields()
        if fields.size() > 0:
            # fields 사이즈 - 1만큼 행 추가
            hwp.HAction.GetDefault("TableInsertRowColumn", hwp.HParameterSet.HTableInsertLine.HSet)
            hwp.HParameterSet.HTableInsertLine.Side = 3
            hwp.HParameterSet.HTableInsertLine.Count = (fields.size() - 1)
            hwp.HAction.Execute("TableInsertRowColumn", hwp.HParameterSet.HTableInsertLine.HSet)

            # fields 순회하며 속성명, 가시성, 타입, 기본값(N/A) 순으로 작성
            for index, field in enumerate(fields):
                self.__writeCellText(str(field.getSimpleName()))
                hwp.Run("MoveRight")
                self.__writeCellText(str(field.getVisibility()))
                hwp.Run("MoveRight")
                self.__writeCellText(str(field.getType()))
                hwp.Run("MoveRight")
                self.__writeCellText("N/A")
                if index != (fields.size() - 1):
                    hwp.Run("TableCellBlock")
                    hwp.Run("TableColBegin")
                    hwp.Run("TableLowerCell")
                    hwp.Run("Cancel")

        # methods 텍스트 입력할 위치로(마지막 행 첫 열)
        hwp.Run("TableCellBlock")
        hwp.Run("TableColBegin")
        hwp.Run("TableColPageDown")
        hwp.Run("Cancel")

        methods = type_simple_info.getMethods()
        if methods.size() > 0:
            # methods 사이즈 - 1만큼 행 추가
            hwp.HAction.GetDefault("TableInsertRowColumn", hwp.HParameterSet.HTableInsertLine.HSet)
            hwp.HParameterSet.HTableInsertLine.Side = 3
            hwp.HParameterSet.HTableInsertLine.Count = (methods.size() - 1)
            hwp.HAction.Execute("TableInsertRowColumn", hwp.HParameterSet.HTableInsertLine.HSet)

            # methods 순회하며 오퍼레이션명, 가시성, 파라미터, 반환타입 순으로 작성
            for index, method in enumerate(methods):
                self.__writeCellText(str(method.getSimpleName()))
                hwp.Run("MoveRight")
                self.__writeCellText(str(method.getVisibility()))
                hwp.Run("MoveRight")
                self.__writeMethodParameters(method.getParameterTypeList())
                hwp.Run("MoveRight")
                self.__writeCellText(str(method.getReturnType()))
                if index != (methods.size() - 1):
                    hwp.Run("TableCellBlock")
                    hwp.Run("TableColBegin")
                    hwp.Run("TableLowerCell")
                    hwp.Run("Cancel")

        # 설계 클래스명 기입
        hwp.Run("TableCellBlock")
        hwp.Run("TableColEnd")
        hwp.Run("TableColPageUp")
        hwp.Run("Cancel")
        self.__writeCellText(str(type_simple_info.getSimpleName()))

    def __addNewLineToDocumentEnd(self):
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언

        hwp.HAction.Run("MoveTopLevelEnd")
        hwp.HAction.Run("BreakPara")

    def __writeCellText(self, text: str): # TODO HwpTableInitializer의 __writeCellText()와 중복 제거 필요
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언
        hwp.HAction.GetDefault("InsertText", hwp.HParameterSet.HInsertText.HSet)
        hwp.HParameterSet.HInsertText.Text = text
        hwp.HAction.Execute("InsertText", hwp.HParameterSet.HInsertText.HSet)

    def __writeMethodParameters(self, parameter_type_list):
        if parameter_type_list.size() == 0:
            self.__writeCellText("N/A")

        for index, parameter_type in enumerate(parameter_type_list):
            self.__writeCellText(str(parameter_type))
            if index != (parameter_type_list.size() - 1):
                self.__writeCellText(",\r\n") # 한글에서는 \n으로는 \r\n으로 줄바꿈
