# Notification Service (Subscriber)

결제 상태 변경 시 사용자 알림을 처리하는 서비스

## 기능
- 결제 완료/실패 알림 발송
- 실시간 푸시 알림 처리
- 포트: 8081

## 처리 이벤트
- COMPLETED: "결제가 완료되었습니다"
- FAILED: "결제가 실패했습니다"

## 실행 방법
```bash
./gradlew :pub-sub-example:notification-service:bootRun
```
