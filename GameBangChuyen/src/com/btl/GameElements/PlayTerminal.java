package com.btl.GameElements;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import com.btl.GameEngine.Drawable;
import com.btl.Model.ConversionFunction;
import com.btl.Model.ModelTerminal;

public class PlayTerminal extends ModelTerminal implements Drawable {
	private Color color;
	public static final int SIZE = PlaySquare.SIZE;
	private static Image picture;
	private static final String resDir = "E:\\Working project\\OOP\\res\\SQUARE.png";

	public PlayTerminal(Point p) {
		super(p);

		if (PlayTerminal.picture == null) {
			PlayTerminal.picture = ConversionFunction.loadImage(resDir);
		}
	}

	public PlayTerminal(ModelTerminal terminal) {
		super(terminal);
		if (PlayTerminal.picture == null) {
			PlayTerminal.picture = ConversionFunction.loadImage(resDir);
		}
	}

	@Override
	public void paint(Graphics g) {
		Point coordinate = ConversionFunction.positionToLocation(getPosition(),
				SIZE);

		g.drawImage(PlayTerminal.picture, coordinate.x - 8, coordinate.y - SIZE
				/ 2 - 7, null);

	}

	@Override
	public boolean contains(Point point) {
		Point logicCoordinate = ConversionFunction.locationToPosition(point,
				SIZE);

		if (logicCoordinate.equals(getPosition()))
			return true;

		return false;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
