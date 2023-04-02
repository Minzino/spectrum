<h1 align="middle">🌈 스펙트럼</h1>
<br/>

[📜 한눈에 보는 프로젝트 문서 Notion](https://meenzino.notion.site/Spectrum-7d3b3be6f54247809a3ba7a4325afe39)
<br/>
[📑 API 문서](https://minzino.github.io/spectrum/src/main/resources/static/docs/index.html)

<details>
<summary>프로젝트 인프라 구성도</summary>
<div markdown="1">

![](https://meenzino.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F85387fcd-4cea-47e9-bfa6-661795c5f727%2FDraw.jpeg?table=block&id=bd624f0d-863e-44d4-9f8b-4060c945aa8b&spaceId=9d32437f-ad77-480c-94b0-229d3642279b&width=2000&userId=&cache=v2)
</div>
</details>

<details>
<summary>DB ERD</summary>
<div markdown="1">

![](https://meenzino.notion.site/image/https%3A%2F%2Fs3-us-west-2.amazonaws.com%2Fsecure.notion-static.com%2F20c9487e-d688-40a7-87e3-8d1c5781be3a%2FUntitled.png?table=block&id=500a019b-ea77-477b-bfcc-9ef63d787edb&spaceId=9d32437f-ad77-480c-94b0-229d3642279b&width=1490&userId=&cache=v2)
</div>
</details>

# Intro
- Spectrum은 다양한 주제와 다양한 시각으로 다가가는 블로그 서비스입니다.
- IT 기술, 문화, 취미 등 다양한 주제를 다루며, 저의 생각과 경험을 바탕으로 각종 회고와 이야기를 공유합니다.
- 함께 성장하는 즐거움을 느낄 수 있는 Spectrum에서 다양한 소통의 창구가 되어보세요.

<details>
<summary>실행 방법</summary>
<div markdown="1">

Spring 애플리케이션과 함께 Prometheus와 Grafana를 사용하여 모니터링을 설정하는 방법docker-compose.yml 파일을 사용하여 서비스를 간단하게 시작할 수 있습니다.

## 사전 요구 사항
Docker와 Docker Compose가 설치되어 있는지 확인하세요. 설치되어 있지 않다면, Docker 및 Docker Compose의 공식 문서를 참조하여 설치하세요.

## 실행 방법
프로젝트를 로컬 시스템에 복제합니다.

``` bash
git clone https://github.com/Minzino/spectrum.git
```
```bash
cd spectrum/platform
```

## Docker Compose를 사용하여 서비스를 시작합니다.

```bash
docker-compose up -d
```
이 명령은 Docker Compose로 정의된 모든 서비스를 데몬 모드에서 시작합니다.

## 서비스가 실행되는지 확인합니다.
Spring 애플리케이션: http://localhost:8080
<br>
Prometheus: http://localhost:9090
<br>
Grafana: http://localhost:3000

## 모니터링을 할 경우
Grafana에 로그인하고 대시보드를 설정합니다.

1. 기본 사용자 이름과 암호로 로그인합니다. (기본값: admin / admin)
2. 메뉴에서 Configuratino -> Data Sources로 들어갑니다.
3. Add new data source 버튼을 클릭 
4. Prometheus 데이터 소스를 추가합니다. URL로 http://prometheus:9090를 사용합니다.
5. 새 대시보드를 만들거나 기존 대시보드를 가져와서 데이터를 시각화합니다.<br>
6. 대시보드는 Dashboards -> Import에 Import via grafana.com에 `6756`id값을 적어줍니다.
7. load하고 직접 추가한 prometheus를 선택 후 import 하면 대시보드 구성이 끝납니다.
8. 작업이 완료되면 Docker Compose를 사용하여 서비스를 중지 및 제거합니다.
```bash
docker-compose down
```
</div>
</details>
