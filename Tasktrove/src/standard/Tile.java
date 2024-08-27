package standard;

import java.util.LinkedList;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Tile {
	
	private AnchorPane pane;
	
	public Tile(int number, LinkedList<CalendarDate> dates) {
		this.pane = new AnchorPane();
		pane.setBorder(new Border(new BorderStroke(null, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
		pane.setMinSize(100, 100);
		Label label = new Label(Integer.toString(number));
		pane.getChildren().add(label);
		AnchorPane.setBottomAnchor(label, 0.0);
		AnchorPane.setRightAnchor(label, 0.0);

		VBox panelDates = new VBox();
		AnchorPane.setTopAnchor(panelDates, 0.0);
		Label arrayLabel[] = new Label[dates.size()];
		for(int i = 0; i < dates.size(); i++) {
			arrayLabel[i] = new Label(dates.get(i).getName());
			panelDates.getChildren().add(arrayLabel[i]);
		}
		pane.getChildren().add(panelDates);
	}
	
	public Node getNode() {
		return pane;
	}
}
