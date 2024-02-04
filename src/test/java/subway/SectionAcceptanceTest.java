package subway;

public class SectionAcceptanceTest extends CommonAcceptanceTest{

    /**
     * Given 지하철 노선을 생성하고
     * When 지하철 노선의 하행역과 새로운 구간의 상행역이 일치하면
     * Then 새로운 구간을 추가할 수 있다.
     */

    /**
     * Given 지하철 노선을 생성하고
     * When 새로운 구간을 등록하면
     * Then 지하철 노선 조회 시 노선의 하행역은 추가한 구간의 하행역이다.
     */

    /**
     * Given 지하철 노선을 생성하고
     * When 지하철 노선의 하행역과 새로운 구간의 상행역이 일치하지 않으면
     * Then 예외가 발생한다.
     */

    /**
     * Given 지하철 노선에 새로운 구간을 등록하고
     * When 마지막 구간을 제거하면
     * Then 지하철 노선에서 마지막 구간이 삭제된다.
     */

    /**
     * Given 지하철 노선에 새로운 구간 2개를 등록하고
     * When 중간 구간을 제거하면
     * Then 예외가 발생한다.
     */

    /**
     * Given 지하철 노선을 등록하고
     * When 마지막 구간을 제거하면
     * Then 예외가 발생한다.
     */
}
