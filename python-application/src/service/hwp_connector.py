import win32com.client as win32

class HwpConnector():

    def __init__(self):
        self.hwp = win32.gencache.EnsureDispatch("HWPFrame.HwpObject")
        hwp = self.hwp  # 사용 편의를 위한 지역변수 선언
        hwp.XHwpWindows.Item(0).Visible = True

        # 편집 용지 설정
        hwp.HAction.GetDefault("PageSetup", hwp.HParameterSet.HSecDef.HSet)
        page_def = hwp.HParameterSet.HSecDef.PageDef
        page_def.TopMargin = hwp.MiliToHwpUnit(20)
        page_def.HeaderLen = hwp.MiliToHwpUnit(30)
        page_def.LeftMargin = hwp.MiliToHwpUnit(15)
        page_def.RightMargin = hwp.MiliToHwpUnit(15)
        page_def.FooterLen = hwp.MiliToHwpUnit(15)
        page_def.BottomMargin = hwp.MiliToHwpUnit(15)
        
        h_set = hwp.HParameterSet.HSecDef.HSet
        h_set.SetItem("ApplyClass", 24)
        h_set.SetItem("ApplyTo", 3)

        hwp.HAction.Execute("PageSetup", hwp.HParameterSet.HSecDef.HSet)
