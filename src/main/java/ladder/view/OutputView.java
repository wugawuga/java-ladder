package ladder.view;

import static java.util.stream.Collectors.joining;

import java.util.List;
import java.util.Map;
import ladder.domain.Bottoms;
import ladder.domain.LadderGame;
import ladder.domain.Line;
import ladder.domain.LineStatus;
import ladder.domain.Players;
import ladder.domain.Result;

public class OutputView {
    private static final String GAME_RESULT_MESSAGE = System.lineSeparator() + "실행결과";
    private static final int INITIAL_PLAYER_INDEX = 0;
    private static final int EMPTY_NAME_LENGTH = 0;
    private static final String INIT_NAME_FORMAT = "%s ";
    private static final long SKIP_INITIAL_PLAYER = 1L;
    private static final String CONNECTED_SYMBOL = "-";
    private static final String DISCONNECTED_SYMBOL = " ";
    private static final String EMPTY_SYMBOL = " ";
    private static final String NAME_MESSAGE_FORMAT = " %%%ds";
    private static final String LINE_STATUS_MESSAGE_FORMAT = "%s|";
    private static final String NEXT_LINE = System.lineSeparator();
    private static final String ERROR_MESSAGE = "[ERROR] ";
    private static final int ONE_NAME_SEARCH_CONDITION = 1;

    public void printResult(final LadderGame ladderGame) {
        System.out.println(GAME_RESULT_MESSAGE);

        final int maxNameLength = getMaxNameLength(ladderGame.getPlayerNames());
        System.out.println(generateNameMessages(ladderGame.getPlayerNames(), maxNameLength));

        final String initialPlayerName = findInitialPlayerName(ladderGame.getPlayerNames());
        System.out.println(generateLadderMessage(initialPlayerName.length(), maxNameLength, ladderGame.getLadder()));
        System.out.println(generateResultMessages(ladderGame.getBottoms(), maxNameLength));
    }

    private String generateResultMessages(final Bottoms bottom, final int maxNameLength) {
        String resultMessage = bottom.toList().stream()
                .map(result -> generateResultMessage(result, maxNameLength))
                .collect(joining());
        return resultMessage + NEXT_LINE;
    }

    private String generateResultMessage(final String result, final int maxNameLength) {
        String maxNameLengthFormat = String.format(NAME_MESSAGE_FORMAT, maxNameLength);
        return String.format(maxNameLengthFormat, result);
    }

    private int getMaxNameLength(final List<String> playerNames) {
        return playerNames.stream()
                .map(String::length)
                .max(Integer::compareTo)
                .orElse(EMPTY_NAME_LENGTH);
    }

    private String generateNameMessages(final List<String> playerNames, final int maxNameLength) {
        final String initialPlayerName = String.format(INIT_NAME_FORMAT, findInitialPlayerName(playerNames));
        final String nameMessage = playerNames.stream()
                .skip(SKIP_INITIAL_PLAYER)
                .map(name -> generateNameMessage(name, maxNameLength))
                .collect(joining());
        return NEXT_LINE + initialPlayerName + nameMessage;
    }

    private String generateNameMessage(final String name, int maxNameLength) {
        String maxNameLengthFormat = String.format(NAME_MESSAGE_FORMAT, maxNameLength);
        return String.format(maxNameLengthFormat, name);
    }

    private String findInitialPlayerName(final List<String> playerNames) {
        return playerNames.get(INITIAL_PLAYER_INDEX);
    }

    private String generateLadderMessage(final int initialNameLength, final int maxNameLength, final List<Line> lines) {
        return lines.stream()
                .map(line -> generateLineMessage(initialNameLength, maxNameLength, line))
                .collect(joining(NEXT_LINE));
    }

    private String generateLineMessage(final int initialNameLength, final int maxNameLength, final Line line) {
        final String initialMessage = generateRepeatedLineStatusMessage(EMPTY_SYMBOL, initialNameLength);
        final String ladderMessage = line.getLine().stream()
                .map(lineStatus -> generateLineStatusMessage(maxNameLength, lineStatus))
                .collect(joining());
        return initialMessage + ladderMessage;
    }

    private String generateRepeatedLineStatusMessage(final String symbol, final int count) {
        return String.format(LINE_STATUS_MESSAGE_FORMAT, symbol.repeat(count));
    }

    private String generateLineStatusMessage(final int maxNameLength, final LineStatus lineStatus) {
        if (lineStatus.isConnected()) {
            return generateRepeatedLineStatusMessage(CONNECTED_SYMBOL, maxNameLength);
        }
        return generateRepeatedLineStatusMessage(DISCONNECTED_SYMBOL, maxNameLength);
    }

    public void printError(final String message) {
        System.out.println(ERROR_MESSAGE + message);
    }

    public void printSearchResult(final Result result,
                                  final Players earlyPlayers) {
        System.out.println(GAME_RESULT_MESSAGE);
        Map<String, String> overallResult = result.getOverallResult();
        if (overallResult.size() == ONE_NAME_SEARCH_CONDITION) {
            overallResult.forEach((key, value) -> System.out.println(value + NEXT_LINE));
            return;
        }

        printAllResult(overallResult, earlyPlayers);
    }

    private void printAllResult(final Map<String, String> searchResultPlayerName, final Players earlyPlayers) {
        for (String initializedName : earlyPlayers.getNames()) {
            System.out.println(initializedName + " : " + searchResultPlayerName.get(initializedName));
        }
    }
}
