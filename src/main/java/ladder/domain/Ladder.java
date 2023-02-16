package ladder.domain;

import static java.util.stream.Collectors.toUnmodifiableList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class Ladder {

    private final List<Line> lines;

    public Ladder(final BooleanGenerator booleanGenerator, final int height, final int width) {
        this.lines = generateLines(booleanGenerator, height, width);
    }

    private List<Line> generateLines(final BooleanGenerator booleanGenerator, final int height, final int width) {
        return Stream.generate(() -> new Line(booleanGenerator, width))
                .limit(height)
                .collect(toUnmodifiableList());
    }

    public List<Line> getLines() {
        return Collections.unmodifiableList(lines);
    }
}
