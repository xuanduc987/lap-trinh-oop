package com.btl.GameElements.mapstate;

import java.awt.Point;
import java.awt.image.BufferedImage;

import com.btl.Model.AuxiliaryFunction;

// TODO: Auto-generated Javadoc
/**
 * The Class DeleteAllButton.
 */
public class DeleteAllButton extends ButtonForHandle {
	/**
	 * Hàm khởi tạo của button.
	 * 
	 * @param p
	 *            - tọa độ đặt Button
	 * @param normalImage
	 *            - ảnh hiển thị của Button ở trạng thái bình thường
	 * @param activeImage
	 *            - ảnh hiển thị của Button ở trạng thái kích hoạt (khi ấn vào)
	 * @param controlCode
	 *            - mã điều khiển của Button
	 */

	public DeleteAllButton(Point p, BufferedImage normalImage,
			BufferedImage activeImage, int controlCode) {
		super(p, normalImage, activeImage, controlCode);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.btl.GameElements.mapstate.ButtonForHandle#handle(com.btl.GameElements.mapstate.MapCreation)
	 */
	@Override
	public void handle(MapCreation map) {
		AuxiliaryFunction.handleMenuDeleteAll(map);
		this.normalRender();
		map.setInitialMenuState();
	}

}
