# Audit Service (Subscriber)

결제 상태 변경 이력을 기록하는 감사 서비스

## 기능
- 모든 결제 상태 변경 이력 저장
- 감사 로그 조회 API
- 포트: 8082

## 저장 정보
- 결제 ID, 이전 상태, 변경 상태, 변경 시간, 사용자 정보

## API 예시
```bash
# 감사 로그 조회
GET http://localhost:8082/audit/logs
```

## 실행 방법
```bash
./gradlew :pub-sub-example:audit-service:bootRun
```
