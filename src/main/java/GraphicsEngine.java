import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.Map;

class GraphicsEngine {
	private static Map<Integer, Color> blockColorMap = new HashMap();
	private static Color emptyColor = Color.web("rgba(238, 228, 218, 0.35)");
	private static Color gridColor = Color.web("#bbada0");
	private static Color newGameColor = Color.web("#8f7a66");
	private static Color scoreColor = Color.web("#bbada0");

	private GraphicsEngine() {
		throw new IllegalStateException("Utility class");
	}

	static ScaleTransition buildScaleTransition(Node node) {
		ScaleTransition scale = new ScaleTransition(Duration.millis(100.0D), node);
		scale.setFromX(0.618D);
		scale.setFromY(0.618D);
		scale.setToX(1.0D);
		scale.setToY(1.0D);
		return scale;
	}

	static ScaleTransition buildMergedScaleTransition(Node node) {
		ScaleTransition scale = new ScaleTransition(Duration.millis(200.0D), node);
		scale.setFromX(0.618D);
		scale.setFromY(0.618D);
		scale.setToX(1.2D);
		scale.setToY(1.2D);
		scale.setOnFinished((event) -> {
			node.setScaleX(1.0D);
			node.setScaleY(1.0D);
		});
		return scale;
	}

	static TranslateTransition buildTranslateTransition(Node node, int x, int y) {
		TranslateTransition tr = new TranslateTransition(Duration.millis(100.0D), node);
		tr.setByX(x);
		tr.setByY(y);
		tr.setOnFinished((event) -> {
			node.setTranslateX(0.0D);
			node.setTranslateY(0.0D);
		});
		return tr;
	}

	static Color getColor(int num) {
		return blockColorMap.containsKey(num) ? blockColorMap.get(num) : Color.LIGHTGRAY;
	}

	public static Color getEmptyColor() {
		return emptyColor;
	}

	public static Color getGridColor() {
		return gridColor;
	}

	public static Color getNewGameColor() {
		return newGameColor;
	}

	public static Color getScoreColor() {
		return scoreColor;
	}

	static {
		String color = "#eee4da";
		blockColorMap.put(2, Color.web(color));
		blockColorMap.put(4, Color.web("#ede0c8"));
		blockColorMap.put(8, Color.web("#f2b179"));
		blockColorMap.put(16, Color.web("#f59563"));
		blockColorMap.put(32, Color.web("#f67c5f"));
		blockColorMap.put(64, Color.web("#f65e3b"));
		blockColorMap.put(128, Color.web("#edcf72"));
		blockColorMap.put(256, Color.web("#edcc61"));
		blockColorMap.put(512, Color.web("#edc850"));
		blockColorMap.put(1024, Color.web("#edc53f"));
		blockColorMap.put(2048, Color.web("#edc22e"));
		blockColorMap.put(4096, Color.web(color));
		blockColorMap.put(8192, Color.web(color));
	}
}
