package pu.reactor;

import javax.swing.SwingUtilities;

public class ReactorGUIApp 
{
	public static void main(String[] args) throws Exception
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() 
			{				
				ReactorMainFrame reactor;
				try {
					reactor = new ReactorMainFrame( "./preferencesFile.json");
					reactor.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				
			}
		});
	}
}
