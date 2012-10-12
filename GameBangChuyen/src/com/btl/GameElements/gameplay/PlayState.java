package com.btl.GameElements.gameplay;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.btl.GameBoard.GamePanel;
import com.btl.GameBoard.GameState;
import com.btl.GameEngine.Layer;
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

	private Image buffer;

	private Layer squareLayer, switchLayer, factoryLayer, terminalLayer,
			boxLayer, menuLayer, hiddenMenuLayer;
	private ArrayList<Layer> listLayer;

	/**
	 * Instantiates a new play state.
	 * 
	 * @param parent
	 *            the parent
	 */
	public PlayState(GamePanel parent, final ModelMap map) {
		super(parent);
		initialize();
		initFromModelMap(map);
	}

	private void initFromModelMap(final ModelMap map) {
		/* them factory tu map vao factoryLayer */
		for (ModelFactory factory : map.getListFactory()) {
			this.factoryLayer.addDrawable(new PlayFactory(factory));
		}

		/* them terminal tu map vao terminalLayer */
		for (ModelTerminal terminal : map.getListTerminal()) {
			this.terminalLayer.addDrawable(new PlayTerminal(terminal));
		}

		/* them switch vao switchLayer */
		for (ModelSwitch mSwitch : map.getListSwitch()) {
			this.switchLayer.addDrawable(new PlaySwitch(mSwitch));
		}

	}

	/**
	 * Initialize.
	 */
	private void initialize() {
		buffer = new BufferedImage(PlayState.WIDTH, PlayState.HEIGHT,
				BufferedImage.TYPE_INT_ARGB);

		//
		// SquareLayer
		//
		this.squareLayer = new Layer(PlayState.WIDTH, PlayState.HEIGHT);
		//
		// SwitchLayer
		//
		this.switchLayer = new Layer(PlayState.WIDTH, PlayState.HEIGHT);
		//
		// factoryLayer
		//
		this.factoryLayer = new Layer(PlayState.WIDTH, PlayState.HEIGHT);
		//
		// terminalLayer
		//
		this.terminalLayer = new Layer(PlayState.WIDTH, PlayState.HEIGHT);
		//
		// boxLayer
		//
		this.boxLayer = new Layer(PlayState.WIDTH, PlayState.HEIGHT);
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
		this.listLayer = new ArrayList<Layer>();
		this.listLayer.add(squareLayer);
		this.listLayer.add(switchLayer);
		this.listLayer.add(factoryLayer);
		this.listLayer.add(terminalLayer);
		this.listLayer.add(boxLayer);
		this.listLayer.add(menuLayer);
		this.listLayer.add(hiddenMenuLayer);

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
		// TODO Auto-generated method stub

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
		Graphics g1 = buffer.getGraphics();
		for (Layer l : this.listLayer) {
			l.render();
			g1.drawImage(l.getLayer(), 0, 0, null);

		}

		g.drawImage(this.buffer, 0, 0, null);

	}
}
