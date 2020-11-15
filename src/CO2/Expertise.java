package CO2;

import javafx.scene.paint.Color;

public class Expertise {
    private GreenEnergyTypes type;
    private Color color;
    private int max;

    public Expertise(GreenEnergyTypes type, int max, Color color) {
        this.type = type;
        this.max = max;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public GreenEnergyTypes getType() {
        return type;
    }

    public void setType(GreenEnergyTypes type) {
        this.type = type;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
