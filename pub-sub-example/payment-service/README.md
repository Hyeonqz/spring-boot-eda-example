# Payment Service (Publisher)

결제 상태 변경 이벤트를 발행하는 서비스

## 기능
- 결제 상태 변경 API (POST /payments/{id}/status)
- 이벤트 발행: PENDING → COMPLETED → FAILED
- 포트: 8080

## API 예시
```bash
# 결제 상태 변경
POST http://localhost:8080/payments/1/status
Content-Type: application/json

{
  "status": "COMPLETED",
  "userId": "user123"
}

# 결제 상태 조회
GET http://localhost:8080/payments/1/status
```

## 실행 방법
```bash
./gradlew :pub-sub-example:payment-service:bootRun
```
