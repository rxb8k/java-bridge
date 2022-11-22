package bridge.controller;

import bridge.domain.Bridge;
import bridge.domain.BridgeGame;
import bridge.domain.GameStatus;
import bridge.ui.InputView;
import bridge.ui.OutputView;
import bridge.validator.InputValidator;

public class BridgeGameController {
    InputView input;
    OutputView output;
    Bridge bridge;
    BridgeGame game;


    public BridgeGameController() {
        this.input = new InputView();
        this.output = new OutputView();
    }

    public void startNewGame() {
        output.printStartMessage();
        int size;
        while (true) {
            try {
                System.out.println("다리의 길이를 입력해주세요.");
                size = input.readBridgeSize();
                this.bridge = new Bridge(size);
                this.game = new BridgeGame(bridge);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void playGame() {
        while (game.currentStatus.equals(GameStatus.CONTINUE)) {
            movePosition();
            if (game.currentStatus.equals(GameStatus.FAIL)) {
                retryOrEndGame();
            }
            if (game.currentStatus.equals(GameStatus.SUCCESS)) {
                output.printResult(game);
            }
        }
    }

    public void movePosition() {
        System.out.println("이동할 칸을 선택해주세요. (위: U, 아래: D)");
        String direction = input.readMoving();
        game.move(direction);
        output.printMap(bridge);
    }

    public void retryOrEndGame() {
        System.out.println("게임을 다시 시도할지 여부를 입력해주세요. (재시도: R, 종료: Q)");
        String gameCommand = input.readGameCommand();
        if (gameCommand.equals("R")) {
            game.retry();
        }
        if (gameCommand.equals("Q")) {
            output.printResult(game);
        }
    }
}
