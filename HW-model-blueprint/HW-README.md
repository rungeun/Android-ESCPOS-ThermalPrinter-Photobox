# 🪐H/W 설계

---
안드로이드 열전사 인쇄기(영수증 인쇄기) 포토박스

- [1. H/W 구성도](#-hw-구성도)
- [2. 부품 리스트](#-부품-리스트)
- [3. 조립 결과물](#-조립-결과물)
- [4. 부품 파일](#-부품-파일)

---

## 🌿 H/W 구성도

![HW 구성도](/image/HW구성도.png)

## 🌿 부품 리스트

### 제작한 3D 프린터 부품 리스트

![3DPrinterList](/image/3DPrinterList.bmp)

### 제작한 아크릴 레이저 커팅 부품 리스트

![LaserAblationList](/image/LaserAblationList.bmp)
(*검정 선만 커팅 하여야 함)

## 하드웨어 및 사용환경

|     하드웨어     |         목적         |        사용환경        |       비고       | 
|:------------:|:------------------:|:------------------:|:--------------:|
| 열전사(영수증) 인쇄기 |       사진 인쇄        | Android 16 (Local) |    CPP-3000    |
|     카메라      |         촬영         | Android 16 (Local) | SONY hdr-cx450 |
|     캡쳐보드     |   HDMI to USB 변환   | Android 16 (Local) |    GV-HUVC     |
|   터치 디스플레이   |      사용자 상호작용      | Android 16 (Local) |                |
|   USB 저장장치   |     Android OS     | Android 16 (Local) |                |
|    QR스캐너     |      QR 코드 스캔      | Android 16 (Local) |                |
|     멀티탭      |       전원 공급        | Android 16 (Local) |       4구       |
|     릴레이      |     QR 스캐너 작동용     | Android 16 (Local) |  jqc-3ff-s-z   |
|      부저      |      사용자 상호작용      | Android 16 (Local) |                |
|      쿨러      |     라즈베리파이 냉각      | Android 16 (Local) |  고장난 PC에서 추출   |
|      PC      | DB 서버, 다운로드용 웹 호스팅 | Rocky 9.6 (Server) |                |

## 🌿 조립 결과물

![조립후](/image/조립후.jpg)

우측면은 투명 아크릴을 사용하여 내부를 볼 수 있게 하였습니다.

## 🌿 부품 파일

- **/HW-model-blueprint/3dPrinter**
    - **/*.ipt :** 인벤터 설계 파일
    - **/*.stl :** 변환된 파일 | 슬라이스 프로그램에서 인식 가능한 포멧
- **/HW-model-blueprint/LaserAblation**
    - **/*.ipt :** 인벤터 설계 파일
    - **/*.dxf :** 변환된 파일 | 커팅 프로그램에서 인식 가능한 포멧