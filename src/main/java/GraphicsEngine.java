import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

class GraphicsEngine {
	private static Map<Integer, Color> blockColorMap = new HashMap<>();
	@Getter
	private static Color emptyColor = Color.web("rgba(238, 228, 218, 0.35)");
	@Getter
	private static Color gridColor = Color.web("#bbada0");
	@Getter
	private static Color newGameColor = Color.web("#8f7a66");
	@Getter
	private static Color scoreColor = Color.web("#bbada0");

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

	private GraphicsEngine() {
		throw new IllegalStateException("Utility class");
	}

	static ScaleTransition buildScaleTransition(Node node) {
		final ScaleTransition scale = new ScaleTransition(Duration.millis(100), node);
		scale.setFromX(0.618);
		scale.setFromY(0.618);
		scale.setToX(1);
		scale.setToY(1);
		return scale;
	}

	static ScaleTransition buildMergedScaleTransition(Node node) {
		final ScaleTransition scale = new ScaleTransition(Duration.millis(200), node);
		scale.setFromX(0.618);
		scale.setFromY(0.618);
		scale.setToX(1.2);
		scale.setToY(1.2);
		scale.setOnFinished(event -> {
			node.setScaleX(1);
			node.setScaleY(1);
		});
		return scale;
	}

	static TranslateTransition buildTranslateTransition(Node node, int x, int y) {
		final TranslateTransition tr = new TranslateTransition(Duration.millis(100), node);
		tr.setByX(x);
		tr.setByY(y);
		tr.setOnFinished(event -> {
			node.setTranslateX(0);
			node.setTranslateY(0);
		});
		return tr;
	}

	static Color getColor(int num) {
		if (blockColorMap.containsKey(num)) {
			return blockColorMap.get(num);
		}
		return Color.LIGHTGRAY;
	}
}