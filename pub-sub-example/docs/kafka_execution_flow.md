# Kafka Pub/Sub 실행 흐름 가이드

## 📋 목차
1. [사전 준비](#1-사전-준비)
2. [Kafka 실행](#2-kafka-실행)
3. [서비스 실행](#3-서비스-실행)
4. [실행 흐름 테스트](#4-실행-흐름-테스트)
5. [로그 확인](#5-로그-확인)

---

## 1. 사전 준비

### 1-1) 프로젝트 빌드
```bash
cd /Users/jinhyeongyu/Dev/springboot-eda-examples
./gradlew clean build
```

### 1-2) Kafka 설치 확인
- Docker 사용 시: Docker Desktop 실행 확인
- 로컬 Kafka 사용 시: Kafka 설치 경로 확인

---

## 2. Kafka 실행

### 방법 1: Docker Compose (권장)
```bash
# docker-compose.yml이 있는 경로에서
docker-compose up -d

# Kafka 브로커 상태 확인
docker ps | grep kafka
```

## 3. 서비스 실행

### 3-1) Payment Service 실행 (Publisher)
```bash
# 터미널 1
cd /Users/jinhyeongyu/Dev/springboot-eda-examples
./gradlew :pub-sub-example:payment-service:bootRun
```

**실행 확인:**
- 포트: `8081`
- 로그: `Started PaymentServiceApplication`

---

### 3-2) Notification Service 실행 (Subscriber)
```bash
# 터미널 2
cd /Users/jinhyeongyu/Dev/springboot-eda-examples
./gradlew :pub-sub-example:notification-service:bootRun
```

**실행 확인:**
- 포트: `8082`
- 로그: `Started NotificationServiceApplication`
- Kafka Consumer 연결 로그 확인

---

### 3-3) Audit Service 실행 (Subscriber)
```bash
# 터미널 3
cd /Users/jinhyeongyu/Dev/springboot-eda-examples
./gradlew :pub-sub-example:audit-service:bootRun
```

**실행 확인:**
- 포트: `8083`
- 로그: `Started AuditServiceApplication`

---

### 3-4) Monitor Service 실행 (Subscriber)
```bash
# 터미널 4
cd /Users/jinhyeongyu/Dev/springboot-eda-examples
./gradlew :pub-sub-example:monitor-service:bootRun
```

**실행 확인:**
- 포트: `8084`
- 로그: `Started MonitorServiceApplication`

---

## 4. 실행 흐름 테스트

### 4-1) 결제 상태 변경 API 호출

#### 테스트 1: 결제 완료
```bash
curl -X POST http://localhost:8081/api/payments/1/status \
  -H "Content-Type: application/json" \
  -d '{
    "status": "COMPLETED",
    "userId": "user123"
  }'
```

**예상 응답:**
```json
{
  "message": "Payment status updated successfully",
  "paymentId": 1,
  "newStatus": "COMPLETED"
}
```

---

#### 테스트 2: 결제 실패
```bash
curl -X POST http://localhost:8081/api/payments/2/status \
  -H "Content-Type: application/json" \
  -d '{
    "status": "FAILED",
    "userId": "user456"
  }'
```

---

#### 테스트 3: 결제 처리중
```bash
curl -X POST http://localhost:8081/api/payments/1/status \
  -H "Content-Type: application/json" \
  -d '{
    "status": "PROCESSING",
    "userId": "user789"
  }'
```

---

## 5. 로그 확인

### 5-1) Payment Service (Publisher) 로그
```
[터미널 1에서 확인]

예상 로그:
- Kafka Producer 메시지 전송 로그
- Topic: payment-status-changed
```

---

### 5-2) Notification Service (Subscriber) 로그
```
[터미널 2에서 확인]

예상 로그:
INFO - Notification sent to user user123: 결제가 완료되었습니다. (결제 ID: 1)
INFO - Sending notification to user user123: 결제가 완료되었습니다. (결제 ID: 1)
```

---

### 5-3) Audit Service (Subscriber) 로그
```
[터미널 3에서 확인]

예상 로그:
INFO - Audit log created - Payment ID: 1, Status: PENDING -> COMPLETED, User: user123
```

---

### 5-4) Monitor Service (Subscriber) 로그
```
[터미널 4에서 확인]

예상 로그:
INFO - Monitor updated - Payment ID: 1, Status: COMPLETED, Success Rate: 100.00%
```

---

## 6. 실행 흐름 다이어그램

```
┌─────────────────┐
│  API 요청       │
│  POST /status   │
└────────┬────────┘
         │
         ▼
┌─────────────────────────┐
│  Payment Service        │
│  (Publisher)            │
│  - 상태 변경            │
│  - Kafka 메시지 발행    │
└────────┬────────────────┘
         │
         │ Kafka Topic: payment-status-changed
         │
         ├──────────────┬──────────────┬──────────────┐
         ▼              ▼              ▼              ▼
┌────────────────┐ ┌────────────┐ ┌──────────┐ ┌──────────┐
│ Notification   │ │  Audit     │ │ Monitor  │ │  ...     │
│ Service        │ │  Service   │ │ Service  │ │ (확장)   │
│ (Consumer)     │ │ (Consumer) │ │(Consumer)│ │          │
│                │ │            │ │          │ │          │
│ - 알림 발송    │ │ - 로그저장 │ │ - 통계   │ │          │
└────────────────┘ └────────────┘ └──────────┘ └──────────┘
```

---

## 7. Kafka 메시지 직접 확인 (선택사항)

### 7-1) Consumer로 메시지 확인
```bash
# Docker 사용 시
docker exec -it <kafka-container-id> kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic payment-status-changed \
  --from-beginning


**예상 출력:**
```json
{
  "paymentId": 1,
  "previousStatus": "PENDING",
  "currentStatus": "COMPLETED",
  "userId": "user123"
}
```

---

## 8. 트러블슈팅

### 8-1) Kafka 연결 실패
```
Error: Connection to node -1 could not be established
```
**해결:** Kafka 브로커가 실행 중인지 확인
```bash
docker ps | grep kafka
# 또는
netstat -an | grep 9092
```

---

### 8-2) Consumer가 메시지를 받지 못함
**확인 사항:**
1. Consumer group-id가 올바른지 확인
2. Topic 이름이 일치하는지 확인 (`payment-status-changed`)
3. Kafka 로그에서 Consumer 연결 확인

---

### 8-3) 직렬화/역직렬화 오류
```
Error: Deserialization exception
```
**해결:** `application-pub-sub.yml`에서 trusted packages 설정 확인
```yaml
spring.json.trusted.packages: "*"
```

---

## 9. 정리

### 서비스 종료
```bash
# 각 터미널에서 Ctrl + C

# Kafka 종료 (Docker)
docker-compose down

# Kafka 종료 (로컬)
bin/kafka-server-stop.sh
bin/zookeeper-server-stop.sh
```

---

## 10. 핵심 포인트

### ✅ Pub/Sub 모델의 장점 확인
1. **독립적 실행**: 각 서비스가 독립적으로 시작/종료 가능
2. **확장성**: 새로운 Consumer 추가 시 기존 코드 변경 없음
3. **장애 격리**: Notification Service 장애 시에도 Audit/Monitor는 정상 동작
4. **메시지 영속성**: Kafka에 메시지 저장 (재처리 가능)

### 🔍 확인할 사항
- [ ] 4개 서비스 모두 정상 실행
- [ ] API 호출 시 모든 Consumer가 메시지 수신
- [ ] 각 서비스의 로그에서 처리 확인
- [ ] 한 서비스 종료 시 다른 서비스는 정상 동작

---

## 11. 다음 단계

1. Consumer 그룹 스케일링 테스트
2. 메시지 재처리 로직 추가
3. Dead Letter Queue (DLQ) 구현
4. 모니터링 대시보드 연동
