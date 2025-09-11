# SpringBoot Event Driven Architecture Example

## 개요
springboot 에서 eda 아키텍쳐에 대하여 실습을 진행한다 <br>

총 2가지 예시가 있으며 pub/sub, message-queue 가 있다 <br>

### 1. pub-sub
#### 1-1) 장점 
확장성:
- 새로운 Consumer 추가 시 기존 시스템 무변경
- 느슨한 결합 (Loose Coupling)
- 시스템 간 독립적 배포

실시간성:
- 즉시 이벤트 전파
- 다중 처리 가능
- 병렬 처리 최적화

비즈니스 유연성:
- 새로운 비즈니스 요구사항 쉽게 반영
- 도메인 이벤트 기반 설계
- 마이크로서비스 간 통신 최적화

#### 1-2) 단점
복잡성:
- 메시지 순서 보장 어려움
- 중복 처리 가능성
- 트랜잭션 관리 복잡
- 초기 러닝커브

모니터링:
- 분산 추적 복잡
- 에러 처리 어려움
- 디버깅 난이도 증가

리소스 오버헤드:
- 모든 Subscriber가 메시지 수신
- 불필요한 처리 발생 가능


### 2. message-queue
#### 2-1) 장점 
안정성:
- 메시지 순서 보장
- 정확히 한 번 처리 (Exactly-once)
- 트랜잭션 지원 용이

성능:
- 작업 분산 최적화
- 백프레셔(Backpressure) 제어
- 리소스 효율적 사용

운영:
- 모니터링 단순
- 에러 추적 용이
- 재처리 로직 명확

#### 2-2) 단점
확장성 제약:
- Consumer 추가 시 로드밸런싱 복잡
- 처리 로직 변경 시 전체 영향

결합도:
- Producer-Consumer 간 강결합
- 새로운 처리 로직 추가 어려움

단일 장애점:
- Consumer 장애 시 전체 처리 중단
- Queue 자체의 병목 가능성


### 1. Pub/Sub 모델 vs Message Queue 모델 비교
```text
Pub/Sub (Publisher/Subscriber):
Publisher → Topic → Multiple Subscribers
- 1:N 관계 (브로드캐스팅)
- Event 중심 사고

Message Queue:
Producer → Queue → Single Consumer  
- 1:1 관계 (작업 분산)
- Task 중심 사고
```


## 1. pub-sub-example
### 1-1) 예시 상황
> 실시간 결제 상태 변경 이벤트 처리
> > 1. 결제 상태 변경 시 여러 서비스가 즉시 반응
> > 2. 각 서비스는 독립적으로 동작 (하나 실패해도 다른 서비스는 계속)
> > 3. 실시간성이 중요 (푸시 알림, 대시보드 업데이트)
> > 4. 새로운 기능 추가 시 기존 시스템 변경 없이 확장


## 2. message-queue-example
### 2-1) 예시 상황
> 대량 정산 처리 시스템
> > 1. 매일 자정에 10만+ 가맹점의 정산 작업 실행
> > 2. 순차적 처리 필요 (계좌 잔액 정합성)
> > 3. 실패 시 재시도 로직 필수
> > 4. 처리 순서와 정확성이 성능보다 중요

