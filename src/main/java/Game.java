import javafx.animation.ParallelTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.Random;

class Game {

	private static int row = 4;
	private static int col = 4;
	private TilePane backgroundTilePane;
	private Score scoreManager = new Score();
	private TilePane tilePane;
	private Cell[][] data = new Cell[row][col];
	private long currentScore;

	Game() {
		tilePane = new TilePane();
		tilePane.setPrefColumns(col);
		tilePane.setPrefRows(row);
		backgroundTilePane = new TilePane();
		backgroundTilePane.setPrefColumns(col);
		backgroundTilePane.setPrefRows(row);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				data[i][j] = new Cell(false);
				tilePane.getChildren().add(data[i][j]);
				data[i][j].refresh();
				backgroundTilePane.getChildren().add(new Cell(true));
			}
		}
		newGame();
	}

	private void newGame() {
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				data[i][j].setNum(0);
				data[i][j].refresh();
			}
		}
		newNum();
		newNum();
		currentScore = 0;
		scoreManager.updateScore(currentScore);
	}

	private void reload() {
		tilePane.getChildren().clear();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				Cell cell = data[i][j];
				tilePane.getChildren().add(cell);
				cell.refresh();
			}
		}
	}

	private Node initGrid() {
		StackPane stackPane = new StackPane();
		stackPane.getChildren().addAll(backgroundTilePane, tilePane);

		StackPane parent = new StackPane();

		BackgroundFill fill = new BackgroundFill(GraphicsEngine.getGridColor(), new CornerRadii(10), null);
		Background background = new Background(fill);
		parent.setBackground(background);

		parent.setPadding(new Insets(8));
		parent.getChildren().add(stackPane);

		return parent;
	}

	private Node setGameView() {
		BackgroundFill bg = new BackgroundFill(GraphicsEngine.getNewGameColor(), new CornerRadii(10), null);
		StackPane newGame = new StackPane();
		Label label = new Label("New Game");
		label.setFont(Font.font(16));
		label.setTextFill(Color.WHITE);
		newGame.setBackground(new Background(bg));
		newGame.setPadding(new Insets(8));
		newGame.getChildren().add(label);
		newGame.setOnMouseClicked(event -> newGame());
		HBox hBox = new HBox(newGame);
		hBox.setAlignment(Pos.CENTER_RIGHT);
		return hBox;
	}

	private Parent content() {
		Node newGame = setGameView();
		Node grid = initGrid();
		VBox root = new VBox();
		root.setSpacing(10);
		root.getChildren().addAll(scoreManager.getScoreView(), newGame, grid);

		root.setPadding(new Insets(10));
		return root;
	}

	private boolean tryMerging(Cell target, Cell source) {
		if (target.getNum() != source.getNum() || target.isMerge() || source.isMerge()) {
			return false;
		}
		int num = target.getNum() << 1;
		target.setNum(num);
		source.setNum(0);
		currentScore += num;
		scoreManager.updateScore(currentScore);
		target.setMerge(true);
		source.setMerge(true);
		return true;
	}

	private boolean isGameOver() {
		if (!isFull()) {
			return false;
		}

		for (int i = 0; i < row; i++) {
			Cell left = data[i][0];
			for (int j = 1; j < col; j++) {
				Cell current = data[i][j];
				if (current.getNum() == left.getNum()) {
					return false;
				}
				left = current;
			}
		}

		for (int j = 0; j < col; j++) {
			Cell up = data[0][j];
			for (int i = 1; i < row; i++) {
				Cell current = data[i][j];
				if (current.getNum() == up.getNum()) {
					return false;
				}
				up = current;
			}
		}

		return true;
	}

	private void showGameOverDialog() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION, "Game Over");
		alert.getDialogPane().setHeaderText(null);
		alert.show();
	}

	private boolean isFull() {
		for (int r = 0; r < row; r++) {
			for (int c = 0; c < col; c++) {
				if (data[r][c].getNum() == 0) {
					return false;
				}
			}
		}
		return true;
	}

	private void newNum() {
		if (isFull()) {
			return;
		}
		Random random = new Random(System.currentTimeMillis());
		int count = 0;
		while (true) {
			int rand = random.nextInt(row * col);
			int i = rand / row;
			int j = rand % col;
			Cell cell = data[i][j];
			if (cell.getNum() == 0) {
				if (Math.random() > 0.8) {
					cell.setNum(4);
				} else {
					cell.setNum(2);
				}
				cell.refresh();
				GraphicsEngine.buildScaleTransition(cell).play();
				if (isFull() || (++count) > 0) {
					break;
				}
			}
		}
	}

	private void onKeyLeft() {
		ParallelTransition parallelTransition = new ParallelTransition();
		for (int i = 0; i < row; i++) {
			for (int j = 1; j < col; j++) {
				Cell key = data[i][j];
				int step = 0;
				if (key.getNum() != 0) {
					for (int k = j; k > 0; k--) {
						Cell current = data[i][k];
						Cell left = data[i][k - 1];
						if (left.getNum() == 0) {
							swap(i, k - 1, i, k);
							step++;
						} else if (tryMerging(left, current)) {
							step++;
						}
					}
					parallelTransition.getChildren()
							.add(GraphicsEngine.buildTranslateTransition(key, -Cell.getBoardHeight() * step, 0));
				}
			}
		}
		parallelTransition.play();
		parallelTransition.setOnFinished(event -> {
			reload();
			newNum();
		});
	}

	private void onKeyRight() {
		ParallelTransition parallelTransition = new ParallelTransition();
		for (int i = 0; i < row; i++) {
			for (int j = col - 2; j >= 0; j--) {
				Cell key = data[i][j];
				int step = 0;
				if (key.getNum() != 0) {
					for (int k = j; k < col - 1; k++) {
						Cell current = data[i][k];
						Cell right = data[i][k + 1];
						if (right.getNum() == 0) {
							swap(i, k, i, k + 1);
							step++;
						} else if (tryMerging(right, current)) {
							step++;
						}
					}
					parallelTransition.getChildren()
							.add(GraphicsEngine.buildTranslateTransition(key, Cell.getBoardHeight() * step, 0));
				}

			}
		}
		parallelTransition.play();
		parallelTransition.setOnFinished(event -> {
			reload();
			newNum();
		});
	}

	private void onKeyUp() {
		ParallelTransition parallelTransition = new ParallelTransition();
		for (int i = 1; i < row; i++) {
			for (int j = 0; j < col; j++) {
				Cell key = data[i][j];
				int step = 0;
				if (key.getNum() != 0) {
					for (int k = i; k > 0; k--) {
						Cell current = data[k][j];
						Cell above = data[k - 1][j];
						if (above.getNum() == 0) {
							swap(k - 1, j, k, j);
							step++;
						} else if (tryMerging(above, current)) {
							step++;
						}
					}
					parallelTransition.getChildren()
							.addAll(GraphicsEngine.buildTranslateTransition(key, 0, -Cell.getBoardHeight() * step));
				}
			}
		}
		parallelTransition.play();
		parallelTransition.setOnFinished(event -> {
			reload();
			newNum();
		});
	}

	private void onKeyDown() {
		ParallelTransition parallelTransition = new ParallelTransition();
		for (int i = 2; i >= 0; i--) {
			for (int j = 0; j < col; j++) {
				Cell key = data[i][j];
				int step = 0;
				if (key.getNum() != 0) {
					for (int k = i; k < row - 1; k++) {
						Cell current = data[k][j];
						Cell below = data[k + 1][j];
						if (below.getNum() == 0) {
							swap(k, j, k + 1, j);
							step++;
						} else if (tryMerging(below, current)) {
							step++;
						}
					}
					parallelTransition.getChildren()
							.addAll(GraphicsEngine.buildTranslateTransition(key, 0, Cell.getBoardHeight() * step));
				}
			}
		}
		parallelTransition.play();
		parallelTransition.setOnFinished(event -> {
			reload();
			newNum();
		});
	}

	private void swap(int r1, int c1, int r2, int c2) {
		Cell temp = data[r1][c1];
		data[r1][c1] = data[r2][c2];
		data[r2][c2] = temp;
	}

	Scene buildScene() {
		Scene scene = new Scene(content());
		scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (isGameOver()) {
				showGameOverDialog();
			}
			switch (event.getCode()) {
				case UP:
					onKeyUp();
					break;
				case DOWN:
					onKeyDown();
					break;
				case LEFT:
					onKeyLeft();
					break;
				case RIGHT:
					onKeyRight();
					break;
				default:
					break;
			}
		});
		return scene;
	}
}