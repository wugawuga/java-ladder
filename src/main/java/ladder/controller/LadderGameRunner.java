package ladder.controller;

import java.util.List;
import java.util.Map;
import ladder.domain.BooleanGenerator;
import ladder.domain.Bottoms;
import ladder.domain.Height;
import ladder.domain.LadderGame;
import ladder.domain.Players;
import ladder.domain.Result;
import ladder.view.InputView;
import ladder.view.OutputView;

public class LadderGameRunner {

    private static final String QUIT_COMMAND = "all";
    private final BooleanGenerator booleanGenerator;
    private final InputView inputView;
    private final OutputView outputView;

    public LadderGameRunner(
            final BooleanGenerator booleanGenerator,
            final InputView inputView,
            final OutputView outputView
    ) {
        this.booleanGenerator = booleanGenerator;
        this.inputView = inputView;
        this.outputView = outputView;
    }

    public void run() {
        final Players players = readPlayers();
        final Bottoms bottoms = readResults(players);
        final Height height = readHeight();

        final LadderGame ladderGame = new LadderGame(booleanGenerator, players, height);
        outputView.printResult(ladderGame, bottoms);

        search(ladderGame, bottoms, height);
    }

    private void search(final LadderGame ladderGame, final Bottoms bottoms, final Height height) {
        List<String> initializedNames = ladderGame.getPlayerNames();
        Players players = ladderGame.makeResult(height);
        Result result = new Result(players, bottoms);

        searchResult(result, initializedNames);
    }

    private void searchResult(final Result result, final List<String> initializedNames) {
        String searchPlayerName = "";
        while (!searchPlayerName.equals(QUIT_COMMAND)) {
            searchPlayerName = getSearchResult(result);
            Map<String, String> searchResult = result.resultByName(searchPlayerName);
            outputView.printSearchResult(searchResult, initializedNames);
        }
    }

    private String getSearchResult(final Result result) {
        try {
            String input = inputView.readSearchName();
            result.isExistPlayerName(input);
            return input;
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return getSearchResult(result);
        }
    }

    private Bottoms readResults(final Players players) {
        try {
            final List<String> bottomsInput = inputView.readBottoms();
            return new Bottoms(bottomsInput, players);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return readResults(players);
        }
    }

    private Players readPlayers() {
        try {
            final List<String> names = inputView.readPlayerNames();
            return new Players(names);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return readPlayers();
        }
    }

    private Height readHeight() {
        try {
            int input = inputView.readLadderHeight();
            return new Height(input);
        } catch (IllegalArgumentException e) {
            outputView.printError(e.getMessage());
            return readHeight();
        }
    }
}