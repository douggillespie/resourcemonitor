package resourceMonitor;

import javax.swing.JComponent;

import PamView.PamSidePanel;

public class ResourceSidePanel implements PamSidePanel {

	private ResourceMonitor resourceMonitor;
	private ResourcePanel resourcePanel;
	
	public ResourceSidePanel(ResourceMonitor resourceMonitor) {
		super();
		this.resourceMonitor = resourceMonitor;
		resourcePanel = new ResourcePanel(resourceMonitor);
	}

	@Override
	public JComponent getPanel() {
		return resourcePanel.getComponent();
	}

	@Override
	public void rename(String newName) {
		// TODO Auto-generated method stub
		
	}

	public void timerAction() {
		resourcePanel.update();
	}

}
