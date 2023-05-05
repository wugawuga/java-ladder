package ladder;

import java.util.Scanner;
import ladder.controller.LadderGameRunner;
import ladder.domain.RandomLineGenerator;
import ladder.view.InputView;
import ladder.view.OutputView;

public class Application {

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            final LadderGameRunner ladderGameRunner = new LadderGameRunner(
                    new RandomLineGenerator(),
                    new InputView(scanner),
                    new OutputView()
            );
            ladderGameRunner.run();
        }
    }
}
