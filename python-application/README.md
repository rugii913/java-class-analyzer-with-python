## 가상 환경 설정
- (가상 환경 시작) python -m venv java-analyzer-venv
- (가상 환경 활성화 ) .\java-analyzer-venv\Scripts\activate
- (가상 환경 pip 업그레이드) pip install --upgrade pip

## requirements.txt
- 현재 설치된 패키지를 requirements.txt 파일로 저장
  - pip freeze > requirements.txt
  - (cf.) 위에 저장 후 모두 제거하려면 pip uninstall -r requirements.txt -y
  - (cf.) @ file:/// 등이 표출되면 pip list --format=freeze > requirements.txt 사용
- (requirements.txt에 있는 모든 패키지 설치) pip install -r requirements.txt

