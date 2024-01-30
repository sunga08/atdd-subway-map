package subway.line;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class LineController {
    private LineService lineService;

    public LineController(LineService lineService) {this.lineService = lineService;}

    @PostMapping("/lines")
    public ResponseEntity<LineResponse> createLine(@RequestBody LineRequest lineRequest) {
        LineResponse line = lineService.saveLine(lineRequest);
        return ResponseEntity.created(URI.create("/lines/" + line.getId())).body(line);
    }

    @GetMapping("/lines")
    public ResponseEntity<List<LineResponse>> showLines() {
        List<LineResponse> lines = lineService.findAllLines();
        return ResponseEntity.ok().body(lines);
    }

    @GetMapping("/lines/{id}")
    public ResponseEntity<LineResponse> showOneLine(@PathVariable Long id) {
        LineResponse line = lineService.findLine(id);
        return ResponseEntity.ok().body(line);
    }

    @PutMapping("/lines/{id}")
    public ResponseEntity<LineResponse> modifyLine(@PathVariable Long id,
                                                   @RequestBody LineRequest lineRequest) {
        lineService.modifyLine(id, lineRequest);
        return ResponseEntity.noContent().build();
    }
}
