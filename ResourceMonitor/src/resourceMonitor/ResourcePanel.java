package resourceMonitor;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import fftManager.Complex;
import PamUtils.PamCalendar;
import PamView.dialog.PamGridBagContraints;
import PamView.dialog.PamLabel;
import PamView.dialog.PamTextDisplay;
import PamView.panel.PamPanel;

/**
 * Panel that can be included in anything else. 
 * @author Doug
 *
 */
public class ResourcePanel {

	private ResourceMonitor resourceMonitor;
	
	private PamPanel mainPanel;
	
	private PamTextDisplay freeMemory, maxMemory, totalMemory, comCons;
	
	static private final int textLength = 10;

	public ResourcePanel(ResourceMonitor resourceMonitor) {
		super();
		this.resourceMonitor = resourceMonitor;
		
		mainPanel = new PamPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		
		PamPanel memPanel = new PamPanel(new GridBagLayout());
		GridBagConstraints c = new PamGridBagContraints();
		memPanel.setBorder(new TitledBorder("Memory"));
		memPanel.add(new PamLabel("Max: ", JLabel.RIGHT), c);
		c.gridx++;
		memPanel.add(maxMemory = new PamTextDisplay(textLength), c);
		c.gridx = 0;
		c.gridy++;
		memPanel.add(new PamLabel("Total: ", JLabel.RIGHT), c);
		c.gridx++;
		memPanel.add(totalMemory = new PamTextDisplay(textLength), c);
		c.gridx = 0;
		c.gridy++;
		memPanel.add(new PamLabel("Free: ", JLabel.RIGHT), c);
		c.gridx++;
		memPanel.add(freeMemory = new PamTextDisplay(textLength), c);
		c.gridx = 0;
		c.gridy++;
		memPanel.add(new PamLabel("Com Cons: ", JLabel.RIGHT), c);
		c.gridx++;
		memPanel.add(comCons = new PamTextDisplay(textLength), c);
		
		maxMemory.setToolTipText("Maximum memory");
		totalMemory.setToolTipText("Current used memory");
		freeMemory.setToolTipText("Current free memory");
		comCons.setToolTipText("Complex constructor calls");
		
		mainPanel.add(memPanel);
		
	}
	
	public JComponent getComponent() {
		return mainPanel;
	}

	private long lastComCons = 0;
	/**
	 * update timer action every second or so. 
	 */
	public void update() {
		Runtime r = Runtime.getRuntime();
//		System.out.println(String.format("System memory at %s Max %d, Free %d", 
//				PamCalendar.formatDateTime(System.currentTimeMillis()), 
//				r.maxMemory(), r.freeMemory()));
		freeMemory.setText(formatMemory(r.freeMemory()));
		totalMemory.setText(formatMemory(r.totalMemory()));
		maxMemory.setText(formatMemory(r.maxMemory()));
		long cons = Complex.getConstructorCalls();
		comCons.setText(String.format("%d", cons-lastComCons));
		lastComCons = cons;
//		r.gc();
	}
	
	static final private long[] scales = {1, 1024, 1024*1024};
	static final private String[] scaleUnits = {"B", "K", "M"};
	
	
	String formatMemory(long membytes) {
		int scaleInd = getScaleIndex(membytes);
		if (scaleInd == 0) {
			return String.format("%d%s", membytes, scaleUnits[0]);
		}
		else {
			return String.format("%3.1f%s", (double) membytes / (double) scales[scaleInd], scaleUnits[scaleInd]);
		}
	}

	private int getScaleIndex(long membytes) {
		for (int i = 0; i < scales.length; i++) {
			if (membytes < scales[i]) {
				return i;
			}
		}
		return scales.length-1;
	}
}
