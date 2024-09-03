from service.hwp_connector import HwpConnector


class HwpTableInitializer:

    __table_title = ["설계 클래스 ID", "", "설계 클래스명"]
    __property_title = ["속성"]
    __property_row_texts = ["속성명", "가시성", "타입", "기본값", "설명"]
    __operation_title = ["오퍼레이션"]
    __opertation_row_text = ["오퍼레이션명", "가시성", "파라미터", "반환타입", "설명"]

    def __init__(self, hwp_connector: HwpConnector):
        self.__hwp = hwp_connector.hwp

    def InitializeFormTable(self):
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

        self.__applyFormStyle()
        self.__writeFormText()

    def __applyFormStyle(self):
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언

        # 표 전체 글꼴 설정
        self.__applyEntireTableCharShape()

        # - 첫번째 row 작업
        self.__moveCaretToFirstCell()
        self.__applyCellColor()
        self.__applyCellCharShapeBold()

        hwp.Run("TableCellBlock")
        hwp.Run("TableColEnd")
        hwp.Run("TableCellBlockExtend")
        hwp.Run("TableLeftCell")
        hwp.Run("TableMergeCell")

        hwp.Run("TableLeftCell")
        self.__applyCellColor()
        self.__applyCellCharShapeBold()

        # - 두번째 row 작업
        hwp.Run("TableCellBlock")
        hwp.Run("TableLowerCell")
        hwp.Run("TableColBegin")
        hwp.Run("TableCellBlockExtend")
        hwp.Run("TableColEnd")
        hwp.Run("TableMergeCell")
        self.__applyCellColor()
        self.__applyCellCharShapeBold()

        # - 세번째 row 작업
        hwp.Run("TableCellBlock")
        hwp.Run("TableLowerCell")
        hwp.Run("TableColBegin")
        hwp.Run("TableCellBlockExtend")
        hwp.Run("TableColEnd")
        self.__applyCellColor()
        hwp.Run("TableCellBlock")
        hwp.Run("TableCellBlockExtend")
        hwp.Run("TableColBegin")
        self.__applyCellCharShapeBold()

        # - 다섯번째 row 작업
        hwp.Run("TableCellBlock")
        hwp.Run("TableLowerCell")
        hwp.Run("TableLowerCell")
        hwp.Run("TableColBegin")
        hwp.Run("TableCellBlockExtend")
        hwp.Run("TableColEnd")
        hwp.Run("TableMergeCell")
        self.__applyCellColor()
        self.__applyCellCharShapeBold()

        # - 여섯번째 row 작업
        hwp.Run("TableCellBlock")
        hwp.Run("TableLowerCell")
        hwp.Run("TableColBegin")
        hwp.Run("TableCellBlockExtend")
        hwp.Run("TableColEnd")
        self.__applyCellColor()
        hwp.Run("TableCellBlock")
        hwp.Run("TableCellBlockExtend")
        hwp.Run("TableColBegin")
        self.__applyCellCharShapeBold()

        hwp.Run("Cancel")

    def __moveCaretToFirstCell(self):
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언
        hwp.Run("TableCellBlock")
        hwp.Run("TableColBegin")
        hwp.Run("TableColPageUp")
        hwp.Run("Cancel")

    def __applyEntireTableCharShape(self):  # - 표 전체 글자 모양 변경
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언

        hwp.Run("TableCellBlock")
        hwp.Run("TableCellBlockExtend")
        hwp.Run("TableCellBlockExtend")
        hwp.HAction.GetDefault("CharShape", hwp.HParameterSet.HCharShape.HSet)
        h_char_shape = hwp.HParameterSet.HCharShape
        h_char_shape.FaceNameUser = "맑은 고딕"
        h_char_shape.FontTypeUser = hwp.FontType("TTF")
        h_char_shape.FontTypeUser = hwp.FontType("TTF")
        h_char_shape.FaceNameSymbol = "맑은 고딕"
        h_char_shape.FontTypeSymbol = hwp.FontType("TTF")
        h_char_shape.FaceNameOther = "맑은 고딕"
        h_char_shape.FontTypeOther = hwp.FontType("TTF")
        h_char_shape.FaceNameJapanese = "맑은 고딕"
        h_char_shape.FontTypeJapanese = hwp.FontType("TTF")
        h_char_shape.FaceNameHanja = "맑은 고딕"
        h_char_shape.FontTypeHanja = hwp.FontType("TTF")
        h_char_shape.FaceNameLatin = "맑은 고딕"
        h_char_shape.FontTypeLatin = hwp.FontType("TTF")
        h_char_shape.FaceNameHangul = "맑은 고딕"
        h_char_shape.FontTypeHangul = hwp.FontType("TTF")
        hwp.HAction.Execute("CharShape", hwp.HParameterSet.HCharShape.HSet)
        hwp.Run("Cancel")

    def __applyCellColor(self):
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언
        hwp.Run("TableCellBlock")
        hwp.HAction.GetDefault("CellBorder", hwp.HParameterSet.HCellBorderFill.HSet)

        fill_attr = hwp.HParameterSet.HCellBorderFill.FillAttr
        fill_attr.type = hwp.BrushType("NullBrush|WinBrush")
        fill_attr.WinBrushFaceColor = hwp.RGBColor(214, 214, 214)
        fill_attr.WinBrushHatchColor = hwp.RGBColor(255, 255, 255)
        fill_attr.WinBrushFaceStyle = hwp.HatchStyle("None")
        fill_attr.WindowsBrush = 1

        hwp.HAction.Execute("CellBorder", hwp.HParameterSet.HCellBorderFill.HSet)
        hwp.Run("Cancel")

    def __applyCellCharShapeBold(self):
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언

        hwp.Run("TableCellBlock")
        hwp.Run("CharShapeBold")
        hwp.Run("Cancel")

    def __writeFormText(self):
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언
        writeCellText = self.__writeCellText  # 사용 편의를 위한 지역변수 선언

        self.__moveCaretToFirstCell()
        for index, text in enumerate(self.__table_title):
            writeCellText(text)
            if index != (self.__table_title.__len__() - 1):
                hwp.Run("MoveRight")

        hwp.Run("TableCellBlock")
        hwp.Run("TableLowerCell")
        hwp.Run("TableColBegin")
        hwp.Run("Cancel")

        for index, text in enumerate(self.__property_title):
            writeCellText(text)
            if index != (self.__property_title.__len__() - 1):
                hwp.Run("MoveRight")

        hwp.Run("TableCellBlock")
        hwp.Run("TableLowerCell")
        hwp.Run("TableColBegin")
        hwp.Run("Cancel")

        for index, text in enumerate(self.__property_row_texts):
            writeCellText(text)
            if index != (self.__property_row_texts.__len__() - 1):
                hwp.Run("MoveRight")

        hwp.Run("TableCellBlock")
        hwp.Run("TableLowerCell")
        hwp.Run("TableLowerCell")
        hwp.Run("TableColBegin")
        hwp.Run("Cancel")

        for index, text in enumerate(self.__operation_title):
            writeCellText(text)
            if index != (self.__operation_title.__len__() - 1):
                hwp.Run("MoveRight")

        hwp.Run("TableCellBlock")
        hwp.Run("TableLowerCell")
        hwp.Run("TableColBegin")
        hwp.Run("Cancel")

        for index, text in enumerate(self.__opertation_row_text):
            writeCellText(text)
            if index != (self.__opertation_row_text.__len__() - 1):
                hwp.Run("MoveRight")

    def __writeCellText(self, text: str):
        hwp = self.__hwp  # 사용 편의를 위한 지역변수 선언
        hwp.HAction.GetDefault("InsertText", hwp.HParameterSet.HInsertText.HSet)
        hwp.HParameterSet.HInsertText.Text = text
        hwp.HAction.Execute("InsertText", hwp.HParameterSet.HInsertText.HSet)
