# Monitor Service (Subscriber)

결제 상태를 실시간으로 모니터링하는 서비스

## 기능
- 실시간 결제 통계 업데이트
- 대시보드 데이터 제공 API
- 포트: 8083

## 제공 데이터
- 실시간 결제 건수, 성공률, 실패율
- 시간대별 결제 통계

## API 예시
```bash
# 결제 통계 조회
GET http://localhost:8083/monitor/stats
```

## 실행 방법
```bash
./gradlew :pub-sub-example:monitor-service:bootRun
```
