## api 시나리오

### 서버 구동시 schema.sql에 있는 DDL과 DML이 실행됩니다.

* 휴가 신청 : 로그인 API 호출 -> 휴가 신청 API 호출
  

* 휴가 취소 : 로그인 API 호출 -> 목록 API 호출 -> 휴가 보기 API 호출 -> 휴가 취소 API 호출

## 로그인 api 호출 방법
* header에 ["Authorization" : "Token {accessToken}"] 형식으로 추가합니다.
* Token 뒤에 한 자리 스페이스(" ")를 둡니다.

