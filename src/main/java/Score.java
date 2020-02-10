import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import lombok.Getter;

class Score {
	@Getter
	private StackPane scoreView;
	private Label label;

	Score() {
		Background bg = new Background(new BackgroundFill(GraphicsEngine.getScoreColor(),
				new CornerRadii(10), null));
		VBox scoreContainer = new VBox();
		scoreContainer.setAlignment(Pos.CENTER);
		scoreContainer.setBackground(bg);
		scoreContainer.setPadding(new Insets(4, 20, 4, 20));
		label = new Label("0");
		label.setFont(Font.font(20));
		label.setTextFill(Color.WHITE);
		Label score = new Label("SCORE");
		score.setFont(Font.font(16));
		score.setTextFill(Color.WHITE);
		scoreContainer.getChildren().addAll(score, this.label);
		scoreView = new StackPane(scoreContainer);
	}

	void updateScore(long score) {
		label.setText(String.valueOf(score));
	}
}