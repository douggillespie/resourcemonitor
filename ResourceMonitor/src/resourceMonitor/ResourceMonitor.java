package resourceMonitor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import PamController.PamControlledUnit;
import PamView.PamSidePanel;

public class ResourceMonitor extends PamControlledUnit {

	private ResourceSidePanel resourceSidePanel;
	
	public ResourceMonitor(String unitName) {
		super("Resource Monitor", unitName);
		resourceSidePanel = new ResourceSidePanel(this);
		Timer updateTimer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				timerAction();
			}
		});
		updateTimer.start();
	}

	protected void timerAction() {
		resourceSidePanel.timerAction();
	}

	@Override
	public PamSidePanel getSidePanel() {
		return resourceSidePanel;
	}

}
