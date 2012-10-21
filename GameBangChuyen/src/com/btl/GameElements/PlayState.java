package com.btl.GameElements;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import com.btl.GameBoard.GamePanel;
import com.btl.GameBoard.GameState;
import com.btl.GameEngine.Drawable;
import com.btl.GameEngine.Layer;
import com.btl.Model.ConversionFunction;
import com.btl.Model.Direction;
import com.btl.Model.GraphNode;
import com.btl.Model.ModelFactory;
import com.btl.Model.ModelMap;
import com.btl.Model.ModelSwitch;
import com.btl.Model.ModelTerminal;

// TODO: Auto-generated Javadoc
/**
 * The Class PlayState.
 */
public class PlayState extends GameState {

	private static final int WIDTH = 700;
	private static final int HEIGHT = 700;

	private Image background = null;

	private Image buffer;

	private ArrayList<PlaySquare> listSquares = new ArrayList<PlaySquare>();
	private ArrayList<PlayFactory> listFactorys = new ArrayList<PlayFactory>();
	private ArrayList<PlayTerminal> listTerminals = new ArrayList<PlayTerminal>();
	private ArrayList<PlaySwitch> listSwitchs = new ArrayList<PlaySwitch>();

	private Layer menuLayer, hiddenMenuLayer;
	private DrawLayer bgLayer, objLayer, switchLayer;
	private ArrayList<Layer> listLayers;
	private ArrayList<PlayBox> listBoxs = new ArrayList<PlayBox>();

	/**
	 * Instantiates a new play state.
	 * 
	 * @param panel
	 *            the parent
	 */
	public PlayState(final GamePanel panel, final ModelMap map) {
		super(panel);
		initialize();
		initFromModelMap(map);

		Timer timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {

				updateGame();

				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						parent.repaint();
					}
				});

			}
		}, 0, 20);

	}

	int count = 0;

	private void updateGame() {

		// TODO tesst
		if (count == 0) {
			PlayBox test = new PlayBox(ConversionFunction.positionToLocation(
					new Point(7, -4), PlaySwitch.SIZE), null);

			objLayer.addDrawable(test);
			listBoxs.add(test);
		}
		count = (count + 1) % 64;

		for (PlayBox i : listBoxs) {
			if (!(i.isMoving())) {

				Drawable drawable = this.bgLayer.getClickedObj(i.getLocation());
				if (drawable == null)
					drawable = this.switchLayer.getClickedObj(i.getLocation());
				if (drawable != null) {
					if (drawable.getClass().equals(PlaySwitch.class)) {
						PlaySwitch pSwitch = (PlaySwitch) drawable;
						if (i.getLocation()
								.equals(ConversionFunction.positionToLocation(
										pSwitch.getPosition(), PlaySwitch.SIZE)))
							i.setDestination(pSwitch.getNeighbor(
									pSwitch.getDirection()).getPosition());
					} else if (drawable.getClass().equals(PlayFactory.class)) {
						PlayFactory factory = (PlayFactory) drawable;
						if (i.getLocation()
								.equals(ConversionFunction.positionToLocation(
										factory.getPosition(), PlaySwitch.SIZE)))
							i.setDestination(factory.getNeighbor(
									factory.getDirection()).getPosition());
					}
				}

			}
		}
	}
	private void initSquare() {
		for (PlaySwitch pSwitch : this.listSwitchs) {
			pSwitch.setFlag(0);
		}

		for (PlayFactory factory : this.listFactorys) {
			initFromFactory(factory);
		}

	}

	private void initFromFactory(final PlayFactory factory) {
		GraphNode neighbor = getNeighbor(factory.getPosition(),
				factory.getDirection());
		factory.addNeighbor(neighbor);
		if (neighbor.getClass().equals(PlaySwitch.class)) {
			((PlaySwitch) neighbor).addInput(factory.getDirection());
			DFS((PlaySwitch) neighbor);
		}
	}

	private GraphNode getNeighbor(final Point position, final Direction d) {
		Point newPoint = position;
		GraphNode node;
		do {
			newPoint = goDirection(newPoint, d);
			node = getNode(newPoint);
			if (node == null) { /* Tao square */
				this.listSquares.add(new PlaySquare(d, newPoint));

			} else
				return node;

		} while (true);
	}

	private GraphNode getNode(final Point p) {
		for (PlaySwitch i : this.listSwitchs) {
			if (i.getPosition().equals(p))
				return i;
		}
		for (PlayTerminal i : this.listTerminals) {
			if (i.getPosition().equals(p))
				return i;
		}

		return null;

	}
	private Point goDirection(final Point oldPosition, Direction d) {
		switch (d) {
			case RIGHT :
				return new Point(oldPosition.x, oldPosition.y + 1);
			case LEFT :
				return new Point(oldPosition.x, oldPosition.y - 1);
			case DOWN :
				return new Point(oldPosition.x + 1, oldPosition.y);
			case UP :
				return new Point(oldPosition.x - 1, oldPosition.y);
			default :
				return null;
		}
	}
	private void DFS(PlaySwitch pSwitch) {
		if (pSwitch.getFlag() == 1)
			return;
		pSwitch.setFlag(1);
		for (Direction d : pSwitch.getListDirection()) {
			GraphNode temp = getNeighbor(pSwitch.getPosition(), d);
			pSwitch.addNeighbor(temp);
			if (temp.getClass().equals(PlaySwitch.class)) {
				((PlaySwitch) temp).addInput(d);
				DFS((PlaySwitch) temp);
			}
		}
	}
	private void initFromModelMap(final ModelMap map) {
		/* them factory tu map vao listFactory */
		for (ModelFactory factory : map.getListFactory()) {
			this.listFactorys.add(new PlayFactory(factory));
		}

		/* them terminal tu map vao listTerminal */
		for (ModelTerminal terminal : map.getListTerminal()) {
			this.listTerminals.add(new PlayTerminal(terminal));
		}

		/* them switch vao listSwitch */
		for (ModelSwitch mSwitch : map.getListSwitch()) {
			this.listSwitchs.add(new PlaySwitch(mSwitch));
		}

		/* init duong trung gian va hinh thanh graph */
		initSquare();

		/* dua cac doi tuong vao bgLayer */
		for (PlayFactory factory : this.listFactorys) {
			this.bgLayer.addDrawable(factory);
		}
		for (PlaySquare square : this.listSquares) {
			this.bgLayer.addDrawable(square);
		}
		for (PlayTerminal terminal : this.listTerminals) {
			this.bgLayer.addDrawable(terminal);
		}
		/* Dua switch vao switchLayer */

		for (PlaySwitch mSwitch : this.listSwitchs) {
			this.switchLayer.addDrawable(mSwitch);
		}

		/* sap xep lai cac doi tuong trong bgLayer, objLayer */
		this.bgLayer.sort();
		this.objLayer.sort();

	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		background = ConversionFunction
				.loadImage("E:\\Working project\\OOP\\res\\BG.bmp");
		buffer = new BufferedImage(PlayState.WIDTH, PlayState.HEIGHT,
				BufferedImage.TYPE_INT_ARGB);

		//
		// bgLayer
		//
		this.bgLayer = new DrawLayer(PlayState.WIDTH, PlayState.HEIGHT);
		//
		// objLayer
		//
		this.objLayer = new DrawLayer(PlayState.WIDTH, PlayState.HEIGHT);
		//
		// switchLayer
		//
		this.switchLayer = new DrawLayer(PlayState.WIDTH, PlayState.HEIGHT);
		//
		// menuLayer
		//
		this.menuLayer = new Layer(PlayState.WIDTH, PlayState.HEIGHT);
		//
		// hiddenMenuLayer
		//
		this.hiddenMenuLayer = new Layer(PlayState.WIDTH, PlayState.HEIGHT);
		this.hiddenMenuLayer.hide();
		//
		// listLayer
		//
		this.listLayers = new ArrayList<Layer>();
		this.listLayers.add(bgLayer);
		this.listLayers.add(switchLayer);
		this.listLayers.add(objLayer);
		this.listLayers.add(menuLayer);
		this.listLayers.add(hiddenMenuLayer);

	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}
	private void switchClickedHandle(final PlaySwitch pSwitch) {
		pSwitch.changeDirection();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		Drawable clicked = null;
		Point p = new Point(arg0.getX(), arg0.getY());

		for (Layer i : this.listLayers) {
			clicked = i.getClickedObj(p);
			if (clicked != null)
				break;
		}
		if (clicked != null) {
			if (clicked.getClass().equals(PlaySwitch.class))
				switchClickedHandle((PlaySwitch) clicked);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.btl.GameBoard.GameState#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.btl.GameBoard.GameState#gameRender(java.awt.Graphics)
	 */
	@Override
	public void gameRender(Graphics g) {
		this.objLayer.sort();

		Graphics g1 = buffer.getGraphics();
		g1.drawImage(this.background, 0, 0, null);
		for (Layer l : this.listLayers) {
			l.render();
			g1.drawImage(l.getLayer(), 0, 0, null);

		}

		g.drawImage(this.buffer, 0, 0, null);

	}
}
