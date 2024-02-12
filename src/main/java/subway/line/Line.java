package subway.line;

import subway.section.Section;
import subway.section.Sections;
import subway.station.Station;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
public class Line {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String color;
    private int distance;

    @Embedded
    private Sections sections = new Sections();

    public Line() {}

    public Line(String name, String color, int distance) {
        this.name = name;
        this.color = color;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public int getDistance() {
        return distance;
    }

    public List<Section> getSections() {
        return sections.getSections();
    }

    public void changeLineInfo(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void addSection(Section newSection) {
        sections.addSection(newSection);
        this.distance += newSection.getDistance();
        newSection.registerLine(this);
    }

    public Long deleteSection(Station deleteStation) {
        return sections.deleteStation(deleteStation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return getDistance() == line.getDistance() && Objects.equals(getId(), line.getId()) && Objects.equals(getName(), line.getName()) && Objects.equals(getColor(), line.getColor()) && Objects.equals(getSections(), line.getSections());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getColor(), getDistance(), getSections());
    }
}
