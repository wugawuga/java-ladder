package ladder.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Line {

    private final List<LineStatus> statuses;

    public Line(final LadderGameCreateLineGenerator generator, final int width) {
        this.statuses = generateLine(generator, width);
    }

    private List<LineStatus> generateLine(final LadderGameCreateLineGenerator generator,
                                          final int width) {
        final Deque<LineStatus> statuses = new ArrayDeque<>();
        for (int i = 0; i < width; i++) {
            statuses.add(generateLineStatus(generator, statuses));
        }
        return new ArrayList<>(statuses);
    }

    private LineStatus generateLineStatus(final LadderGameCreateLineGenerator generator,
                                          final Deque<LineStatus> statuses) {
        final boolean status = generator.generate();
        if (statuses.isEmpty() || statuses.getLast().isDisconnected()) {
            return LineStatus.from(status);
        }
        return LineStatus.DISCONNECTED;
    }

    public List<LineStatus> getLine() {
        return Collections.unmodifiableList(statuses);
    }

    public boolean isConnected(final int index) {
        return statuses.get(index).isConnected();
    }
}
