## 가상 환경 설정
- (가상 환경 시작) python -m venv java-analyzer-venv
- (가상 환경 활성화 ) .\java-analyzer-venv\Scripts\activate
- (가상 환경 pip 업그레이드) pip install --upgrade pip

## requirements.txt - 패키지 나열
- 현재 설치된 패키지를 requirements.txt 파일로 저장
  - pip freeze > requirements.txt
  - (cf.) 위에 저장 후 모두 제거하려면 pip uninstall -r requirements.txt -y
  - (cf.) @ file:/// 등이 표출되면 pip list --format=freeze > requirements.txt 사용
- (requirements.txt에 있는 모든 패키지 설치) pip install -r requirements.txt

## pyinstaller - .exe 파일로 배포
- 명령어 방식으로 .exe 파일 만들기
  - \(폴더로 배포\) pyinstaller -D\(혹은 --onedir\) \[배포할 파일 경로\]
  - \(하나의 파일로 배포\) pyinstaller -F\(혹은 --onefile\) \[배포할 파일 경로\]
- 옵션 파일 방식으로 .exe 파일 만들기
  - .spec 파일 활용 → 나중에 살펴볼 것
- 참고
  - [기타 블로그 - PyInstaller : 파이썬(Python) 배포를 위한 exe 실행 파일을 만드는 방법](https://jiwift.tistory.com/entry/PyInstaller-%ED%8C%8C%EC%9D%B4%EC%8D%ACPython-%EB%B0%B0%ED%8F%AC%EB%A5%BC-%EC%9C%84%ED%95%9C-exe-%EC%8B%A4%ED%96%89-%ED%8C%8C%EC%9D%BC%EC%9D%84-%EB%A7%8C%EB%93%9C%EB%8A%94-%EB%B0%A9%EB%B2%95)
