package ladder.domain;

import static java.util.stream.Collectors.toList;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

class Ladder {
    private final List<Line> lines;

    public Ladder(final LadderGameCreateLineGenerator generator, final Height height, final int width) {
        this.lines = generateLines(generator, height, width);
    }

    private List<Line> generateLines(final LadderGameCreateLineGenerator generator, final Height height,
                                     final int width) {
        return Stream.generate(() -> new Line(generator, width))
                .limit(height.getHeight())
                .collect(toList());
    }

    public List<Line> getLines() {
        return Collections.unmodifiableList(lines);
    }
}
