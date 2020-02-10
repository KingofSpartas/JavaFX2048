import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

class Cell extends StackPane {
	private static int boardHeight = 80;
	private static int boardWidth = 80;
	private boolean merge;
	private Label label = new Label();
	private Rectangle rectangle;
	private int num;

	Cell(boolean isBackground) {
		setPadding(new Insets(boardWidth >> 4));
		setAlignment(Pos.CENTER);

		rectangle = new Rectangle(boardWidth, boardHeight);
		rectangle.setArcWidth(10);
		rectangle.setArcHeight(10);
		if (isBackground) {
			rectangle.setFill(GraphicsEngine.getEmptyColor());
		}
		getChildren().add(rectangle);

		DropShadow shadow = new DropShadow();
		shadow.setColor(Color.BLACK);
		shadow.setRadius(1);
		shadow.setOffsetX(1);
		shadow.setOffsetY(1);
		label.setFont(Font.font(24));
		label.setEffect(shadow);
		label.setTextFill(Color.WHITE);

		getChildren().add(label);
	}

	public static int getBoardHeight() {
		return Cell.boardHeight;
	}

	public static int getBoardWidth() {
		return Cell.boardWidth;
	}

	void refresh() {
		if (this.num == 0) {
			setOpacity(0);
		} else {
			label.setText(String.valueOf(this.num));
			setOpacity(1);
		}
		if (merge) {
			GraphicsEngine.buildMergedScaleTransition(this).play();
		}
		merge = false;
		rectangle.setFill(GraphicsEngine.getColor(this.num));
	}

	public boolean isMerge() {
		return this.merge;
	}

	public void setMerge(boolean merge) {
		this.merge = merge;
	}

	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}