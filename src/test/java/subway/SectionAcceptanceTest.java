package subway;

import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import subway.station.Station;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SectionAcceptanceTest extends CommonAcceptanceTest{

    Long extractResponseId(ExtractableResponse<Response> response) {
        return response.body().jsonPath().getLong("id");
    }

    List<String> extractResponseNames(ExtractableResponse<Response> response) {
        return response.jsonPath().getList("name", String.class);
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 지하철 노선의 하행역과 새로운 구간의 상행역이 일치하면
     * Then 새로운 구간을 추가할 수 있다.
     */
    @Test
    void addSection() {

        //given

        //지하철 노선 생성
        Long 강남역Id = extractResponseId(StationRestAssuredCRUD.createStation("강남역"));
        Long 선릉역Id = extractResponseId(StationRestAssuredCRUD.createStation("선릉역"));
        ExtractableResponse<Response>  lineResponse = LineRestAssuredCRUD.createLine("2호선", "bg-red-600", 강남역Id, 선릉역Id, 7);
        Long lineNum = lineResponse.jsonPath().getLong("id");

        //구간 생성
        Long 삼성역Id = extractResponseId(StationRestAssuredCRUD.createStation("삼성역"));

        //when
        Map<String, Object> paraMap = new HashMap<>();
        paraMap.put("upStationId", 선릉역Id);
        paraMap.put("downStationId", 삼성역Id);
        paraMap.put("distance", 10);

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .pathParam("id", lineNum)
                    .body(paraMap)
                .when()
                    .post("/lines/{id}/sections")
                .then().log().all()
                .extract();

        //then
        ExtractableResponse<Response> line = LineRestAssuredCRUD.showLine(lineNum);

        List<Station> stations = line.jsonPath().getList("stations", Station.class);

        Long maxStationId = stations.stream().mapToLong(v -> v.getId()).max().orElse(0);

        Assertions.assertThat(maxStationId).isEqualTo(삼성역Id);
    }

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
