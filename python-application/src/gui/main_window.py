from PyQt6.QtCore import QSize
from PyQt6.QtWidgets import QFileDialog, QLineEdit, QPushButton, QWidget, QHBoxLayout

from service.analyzer_adapter import AnalyzerAdapter


class MainWindow(QWidget):
    def __init__(self):
        super().__init__()

        self.__analyzer_adapter = AnalyzerAdapter()
        self.initialize_main_window()

        self.line_edit = QLineEdit(self)
        self.line_edit.setFixedWidth(480)
        self.line_edit.setDisabled(True)
        self.file_select_button = QPushButton("file select", self)
        self.file_select_button.clicked.connect(self.selectFile)
        self.analyze_button = QPushButton("analyze", self)
        self.analyze_button.clicked.connect(lambda: self.analyze(self.line_edit.text())) # arg를 넘겨 호출하기 위해 lambda 사용

        hbox = QHBoxLayout()
        hbox.addWidget(self.line_edit)
        hbox.addWidget(self.file_select_button)
        hbox.addStretch(1)

        self.setLayout(hbox)

    def initialize_main_window(self):
        self.setGeometry(400, 200, 500, 300)
        self.setFixedSize(QSize(600, 120))
        self.setWindowTitle("JVM class analyzer")

    def selectFile(self):
        file_path, is_selected = QFileDialog.getOpenFileName(
            self, filter="*.jar;; *.war"
        )  # 기본 디렉토리 지정할 경우 directory=""설정
        if is_selected:
            self.line_edit.setText(file_path)

    def analyze(self, target_jar_path):
        self.__analyzer_adapter.analyze(target_jar_path)

    # def setup_main_window(self):
    #     hello_lable = QLabel(self)
    #     hello_lable.setText("Hello world and Qt!")
    #     hello_lable.move(150, 90)
