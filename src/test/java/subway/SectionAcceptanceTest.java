package subway;

import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import subway.common.CommonAcceptanceTest;
import subway.common.LineRestAssuredCRUD;
import subway.common.SectionRestAssuredCRUD;
import subway.common.StationRestAssuredCRUD;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("지하철 구간 관련 기능")
public class SectionAcceptanceTest extends CommonAcceptanceTest {
    private static Long 강남역Id;
    private static Long 선릉역Id;
    private static Long 이호선Id;

    @BeforeEach
    void createLine() {
        강남역Id = extractResponseId(StationRestAssuredCRUD.createStation("강남역"));
        선릉역Id = extractResponseId(StationRestAssuredCRUD.createStation("선릉역"));
        ExtractableResponse<Response> lineResponse = LineRestAssuredCRUD.createLine("2호선", "bg-red-600", 강남역Id, 선릉역Id, 7);
        이호선Id = lineResponse.jsonPath().getLong("id");
    }

    Long extractResponseId(ExtractableResponse<Response> response) {
        return response.body().jsonPath().getLong("id");
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 새로운 구간을 등록하면
     * Then 지하철 노선 조회 시 노선의 하행역은 추가한 구간의 하행역이다.
     */
    @DisplayName("지하철 노선에 새로운 구간을 추가한다.")
    @Test
    void addSection() {
        //given
         createLine();

        //when
        Long 삼성역Id = extractResponseId(StationRestAssuredCRUD.createStation("삼성역"));
        ExtractableResponse<Response> addResponse = SectionRestAssuredCRUD.addSection(선릉역Id, 삼성역Id, 10, 이호선Id);
        assertThat(addResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        //then
        ExtractableResponse<Response> line = LineRestAssuredCRUD.showLine(이호선Id);
        List<Long> ids = line.jsonPath().getList("stations.id", Long.class);

        assertThat(ids).contains(삼성역Id);
    }


    /**
     * Given 지하철 노선을 생성하고
     * When 노선에 등록된 역이 하행역인 구간을 등록하면
     * Then 400에러가 발생한다.
     */
    @Test
    @DisplayName("하행역이 지하철 노선에 등록된 역인 구간을 추가하면 서버오류가 발생한다.")
    void addSectionExistStationException() {
        //given
        createLine();

        //when
        ExtractableResponse<Response> addResponse = SectionRestAssuredCRUD.addSection(선릉역Id, 강남역Id, 10, 이호선Id);

        //then
        assertThat(addResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Given 지하철 노선을 생성하고
     * When 지하철 노선의 하행역과 새로운 구간의 상행역이 일치하지 않은 구간을 추가하면
     * Then 400에러가 발생한다.
     */
    @Test
    @DisplayName("지하철 노선의 하행역과 새로운 구간의 생행역이 일치하지 않는 구간을 등록하면 서버오류가 발생한다.")
    void addSectionNotMatchException() {
        //given
        createLine();

        //when
        Long 잠실역Id = extractResponseId(StationRestAssuredCRUD.createStation("잠실역"));
        Long 삼성역Id = extractResponseId(StationRestAssuredCRUD.createStation("삼성역"));
        ExtractableResponse<Response> addResponse = SectionRestAssuredCRUD.addSection(잠실역Id, 삼성역Id, 10, 이호선Id);

        //then
        assertThat(addResponse.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    /**
     * Given 지하철 노선에 새로운 구간을 등록하고
     * When 마지막 구간을 제거하면
     * Then 지하철 노선에서 마지막 구간이 삭제된다.
     */
    @Test
    @DisplayName("지하철 노선에서 구간을 제거한다.")
    void deleteSection() {
        //given
        createLine();

        Long 삼성역Id = extractResponseId(StationRestAssuredCRUD.createStation("삼성역"));
        SectionRestAssuredCRUD.addSection(선릉역Id, 삼성역Id, 10, 이호선Id);

        //when
        ExtractableResponse<Response> deleteResponse = SectionRestAssuredCRUD.deleteSection(이호선Id, 삼성역Id);

        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.OK.value());

        //then
        ExtractableResponse<Response> lineResponse = LineRestAssuredCRUD.showLine(이호선Id);
        List<Long> stationIds = lineResponse.jsonPath().getList("stations.id", Long.class);

        assertThat(stationIds).doesNotContain(삼성역Id);
    }

    /**
     * Given 지하철 노선에 새로운 구간 2개를 등록하고
     * When 마지막 구간이 아닌 역을 제거하면
     * Then 500에러가 발생한다.
     */
    @Test
    @DisplayName("지하철 노선에서 마지막 구간이 아닌 역을 제거하면 서버오류가 발생한다.")
    void deleteMiddleSectionException() {
        //given
        createLine();

        Long 삼성역Id = extractResponseId(StationRestAssuredCRUD.createStation("삼성역"));
        SectionRestAssuredCRUD.addSection(선릉역Id, 삼성역Id, 10, 이호선Id);

        Long 잠실역Id = extractResponseId(StationRestAssuredCRUD.createStation("잠실역"));
        SectionRestAssuredCRUD.addSection(삼성역Id, 잠실역Id, 13, 이호선Id);

        //when
        ExtractableResponse<Response> deleteResponse = SectionRestAssuredCRUD.deleteSection(이호선Id, 삼성역Id);

        //then
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    /**
     * Given 지하철 노선을 등록하고
     * When 마지막 구간을 제거하면
     * Then 500에러가 발생한다.
     */
    @Test
    @DisplayName("노선의 마지막 구간을 제거하면 서버오류가 발생한다.")
    void deleteLastSectionException() {
        //given
        createLine();

        //when
        ExtractableResponse<Response> deleteResponse = SectionRestAssuredCRUD.deleteSection(이호선Id, 선릉역Id);

        //then
        assertThat(deleteResponse.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
