package subway.section;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import subway.line.Line;
import subway.line.LineRepository;

import java.util.Optional;

@Service
public class SectionService {

    private LineRepository lineRepository;

    public SectionService(LineRepository lineRepository) {
        this.lineRepository = lineRepository;
    }

    @Transactional
    public void addSection(Long id, SectionRequest sectionRequest) {
        Line line = lineRepository.findById(id).orElseThrow();

        if(sectionRequest.getUpStationId() == line.getDownStationId()) {
            line.changeDownStationId(sectionRequest.getDownStationId());
        }
    }
}
