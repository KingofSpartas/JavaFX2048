import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

class Score {
	private StackPane scoreView;
	private Label label;

	Score() {
		Background bg = new Background(new BackgroundFill(GraphicsEngine.getScoreColor(), new CornerRadii(10.0D), null));
		VBox scoreContainer = new VBox();
		scoreContainer.setAlignment(Pos.CENTER);
		scoreContainer.setBackground(bg);
		scoreContainer.setPadding(new Insets(4.0D, 20.0D, 4.0D, 20.0D));
		this.label = new Label("0");
		this.label.setFont(Font.font(20.0D));
		this.label.setTextFill(Color.WHITE);
		Label score = new Label("SCORE");
		score.setFont(Font.font(16.0D));
		score.setTextFill(Color.WHITE);
		scoreContainer.getChildren().addAll(score, this.label);
		this.scoreView = new StackPane(scoreContainer);
	}

	void updateScore(long score) {
		this.label.setText(String.valueOf(score));
	}

	public StackPane getScoreView() {
		return this.scoreView;
	}
}