package com.oop.mapcreation;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.oop.data.ItemImage;
import com.oop.gamepanel.Drawable;
import com.oop.mapcreation.objects.FactoryMap;
import com.oop.mapcreation.objects.ItemMap;
import com.oop.mapcreation.objects.SwitchMap;
import com.oop.mapcreation.objects.TerminalMap;
import com.oop.model.AuxiliaryFunction;
import com.oop.model.Helper;

/**
 * class này là class của các đối tượng hiển thị ảnh của factory trên lưới ô
 * vuông vẽ map Thực hiện các tính toán liên quan đến vẽ ảnh và tính toán sự hợp
 * lệ trong việc vẽ ảnh ra lưới ô vuông.
 * 
 * @author mai tien khai
 */
public class FactoryIcon extends ItemMap {

	/**
	 * Hàm khởi tạo đối tượng.
	 * 
	 * @param position
	 *            - điểm đầu vào(click chuột)
	 * @param side
	 *            - cạnh lưới ô vuông của map đang vẽ
	 * @param image
	 *            - ảnh hiển thị
	 */
	public FactoryIcon(Point position, int side, BufferedImage image) {
		super(position, side, image);
		calculateTopLeft();
	}

	/*
	 * (non - Javadoc)
	 * 
	 * @see
	 * com.btl.GameElement.ItemMap#calculateValidation(com.btl.GameBoard.MapCreation
	 * )
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.oop.mapcreation.objects.ItemMap#calculateValidation(com.oop.mapcreation
	 * .MapCreation)
	 */
	@Override
	public void calculateValidation(MapCreation map) {
		boolean check = false;// xem co o phu nao trong switchLayer khong
		if (!AuxiliaryFunction.checkIdenticalPostition(map, position))
			this.setValidation(false);
		else {
			ArrayList<Point> pointCovered = ItemImage.getSquareCovered(image,
					position, side);
			/* kiem tra tron switchList */
			for (Drawable i : map.getSwitchLayer().getListDrawable()) {
				SwitchMap s = (SwitchMap) i;
				if (pointInList(s.getPosition(), pointCovered)) {
					check = true;
					break;
				}

			}
			if (!check)
				/* kiem tra trong terminal */
				for (Drawable i : map.getTerminallayer().getListDrawable()) {
					TerminalMap s = (TerminalMap) i;
					if (pointInList(s.getPosition(), pointCovered)) {
						check = true;
						break;
					}

				}
			if (!check)
				/* kiem tra trong factory */
				for (Drawable i : map.getFactorylayer().getListDrawable()) {
					FactoryMap s = (FactoryMap) i;
					if (pointInList(s.getPosition(), pointCovered)) {
						check = true;
						break;
					}

				}
			if (!check)
				for (Point i : map.getSquareCovedList())
					if (pointInList(i, pointCovered)) {
						check = true;
						break;
					}

			if (check)
				this.setValidation(false);
		}
	}

	/*
	 * (non - Javadoc)
	 * 
	 * @see com.btl.GameElement.ItemMap#contains(java.awt.Point)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.oop.mapcreation.objects.ItemMap#contains(java.awt.Point)
	 */
	@Override
	public boolean contains(Point p) {

		return false;
	}

	/*
	 * (non - Javadoc)
	 * 
	 * @see com.btl.GameElement.ItemMap#identifyImageType()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.oop.mapcreation.objects.ItemMap#identifyImageType()
	 */
	@Override
	public void identifyImageType() {
		type = ItemImage.VEHICLE_TYPE;
	}

	/*
	 * (non - Javadoc)
	 * 
	 * @see com.btl.GameElement.ItemMap#paint(java.awt.Graphics)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.oop.mapcreation.objects.ItemMap#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		if (isValid)
			g.drawImage(image, topLeftPoint.x, topLeftPoint.y, width, height,
					null);

		else {

			g.setColor(INVALID_COLOR);
			g.fillPolygon(Helper.polygon(position, side, side));
			ArrayList<Point> pointCovered = ItemImage.getSquareCovered(image,
					position, side);
			for (Point i : pointCovered) {
				g.fillPolygon(Helper.polygon(i, side, side));
			}
			g.drawImage(image, topLeftPoint.x, topLeftPoint.y, width, height,
					null);
		}

	}

	/**
	 * kiem tra mot diem trong mot list hay khong.
	 * 
	 * @param p
	 *            diem can kiem tra
	 * @param list
	 *            ca kiem tra
	 * @return true neu diem p nam trong list
	 */
	private boolean pointInList(Point p, ArrayList<Point> list) {
		for (Point i : list)
			if (AuxiliaryFunction.checkPoint(i, p))
				return true;
		return false;
	}

	/*
	 * (non - Javadoc)
	 * 
	 * @see com.btl.GameElement.ItemMap#calculateNearestPoint()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.oop.mapcreation.objects.ItemMap#calculateNearestPoint()
	 */
	@Override
	protected void calculateNearestPoint() {
		Point temp = null;
		if (this.image == ItemImage.TRUCK)
			temp = new Point(position.x, position.y - 4 * side);
		else if (this.image == ItemImage.TRUCK_DOWN)
			temp = new Point(position.x + 4 * side, position.y);
		else if (this.image == ItemImage.AIRPLANE)
			temp = new Point(position.x - side, position.y);
		else if (this.image == ItemImage.AIRPLANE_RIGHT)
			temp = new Point(position.x, position.y + side);
		else if (this.image == ItemImage.SHIP)
			temp = new Point(position.x - side, position.y);
		else if (this.image == ItemImage.SHIP_RIGHT)
			temp = new Point(position.x, position.y + side);
		nearestPoint = temp;

	}

	/*
	 * (non - Javadoc)
	 * 
	 * @see com.btl.GameElement.ItemMap#calculateTopLeft()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.oop.mapcreation.objects.ItemMap#calculateTopLeft()
	 */
	@Override
	protected void calculateTopLeft() {
		Point temp = null;
		if (this.image == ItemImage.TRUCK)
			temp = new Point(position.x - 3 * side, position.y - 2 * side);
		else if (this.image == ItemImage.TRUCK_DOWN)
			temp = new Point(position.x, position.y + side);
		else if (this.image == ItemImage.AIRPLANE)
			temp = new Point(position.x - 4 * side, position.y);
		else if (this.image == ItemImage.AIRPLANE_RIGHT)
			temp = new Point(position.x - 2 * side, position.y + 2 * side);
		else if (this.image == ItemImage.SHIP)
			temp = new Point(position.x - 6 * side, position.y + 2 * side);
		else if (this.image == ItemImage.SHIP_RIGHT)
			temp = new Point(position.x - 4 * side, position.y + 4 * side);
		topLeftPoint = Helper.logicToReal(temp);

	}
}
