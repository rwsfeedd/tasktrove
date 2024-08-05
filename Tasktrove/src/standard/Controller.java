package standard;

import java.awt.event.TextEvent;
import java.awt.event.TextListener;

public class Controller implements TextListener{
	public void textValueChanged(TextEvent e) {
		System.out.println(e);
	}
}
