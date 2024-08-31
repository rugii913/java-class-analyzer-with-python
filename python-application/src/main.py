from service.class_analyzer_connector import ClassAnalyzerConnector


def main():
    classAnalyzerConnector = ClassAnalyzerConnector()
    analyzed_model = classAnalyzerConnector.analyze("jars\java-class-analyzer-with-spoon-1.0-SNAPSHOT.jar")
    classAnalyzerConnector.cleanDecompiled()
    classAnalyzerConnector.shutdown()


if __name__ == "__main__":
    main()
