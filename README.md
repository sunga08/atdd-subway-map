# 지하철 노선도 미션
[ATDD 강의](https://edu.nextstep.camp/c/R89PYi5H) 실습을 위한 지하철 노선도 애플리케이션

## 지하철 노선 관리
### 요구사항
1) 인수 조건을 검증하는 인수 테스트 작성
2) 인수 테스트를 충족하는 기능 구현
* [ ] 지하철 노선 생성  
* [ ] 지하철 노선 목록 조회  
* [ ] 지하철 노선 조회  
* [ ] 지하철 노선 수정  
* [ ] 지하철 노선 삭제


## 지하철 구간 추가
### 요구사항
1. 구간 등록 기능
  - 지하철 노선에 구간을 등록하는 기능을 구현  
  - 새로운 구간의 상행역은 해당 노선에 등록되어있는 하행 종점역이어야 한다.  
  - 이미 해당 노선에 등록되어있는 역은 새로운 구간의 하행역이 될 수 없다.  
  - 새로운 구간 등록시 위 조건에 부합하지 않는 경우 에러 처리한다.  

2. 구간 삭제 기능  
  - 지하철 노선에 구간을 제거하는 기능 구현  
  - 지하철 노선에 등록된 역(하행 종점역)만 제거할 수 있다. 즉, 마지막 구간만 제거할 수 있다.  
  - 지하철 노선에 상행 종점역과 하행 종점역만 있는 경우(구간이 1개인 경우) 역을 삭제할 수 없다.  
  - 새로운 구간 제거시 위 조건에 부합하지 않는 경우 에러 처리한다.

### 인수 테스트 시나리오
- 지하철 노선의 하행역과 새로운 구간의 상행역이 일치하면 구간을 추가할 수 있다.
- 지하철 노선에 새로운 구간을 추가하면 해당 노선의 하행역은 새로운 구간의 하행역이 된다.
- 지하철 노선의 하행역과 새로운 구간의 상행역이 일치하지 않으면 예외가 발생한다.
- 지하철 노선에서 마지막 구간을 제거할 수 있다.
- 지하철 노선에서 마지막 구간이 아닌 역을 삭제하면 예외가 발생한다.
- 지하철 노선에 상행 종점역과 하행 종점역만 존재하면 구간 삭제시 예외가 발생한다.