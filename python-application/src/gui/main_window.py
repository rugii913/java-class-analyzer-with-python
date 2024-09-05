from PyQt6.QtCore import QSize
from PyQt6.QtWidgets import QFileDialog, QLineEdit, QPushButton, QWidget, QHBoxLayout


class MainWindow(QWidget):
    def __init__(self):
        super().__init__()
        self.initialize_main_window()

        self.import_button = QPushButton("file select", self)
        self.import_button.clicked.connect(self.selectFile)
        self.line_edit = QLineEdit(self)
        self.line_edit.setFixedWidth(480)
        self.line_edit.setDisabled(True)

        hbox = QHBoxLayout()
        hbox.addWidget(self.line_edit)
        hbox.addWidget(self.import_button)
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

    # def setup_main_window(self):
    #     hello_lable = QLabel(self)
    #     hello_lable.setText("Hello world and Qt!")
    #     hello_lable.move(150, 90)
