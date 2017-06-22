package pu.test;

import javax.swing.SwingUtilities;

import pu.reactor.ReactorMainFrame;

public class TestGUIReactor {

	public static void main(String[] args) 
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() 
			{				
				ReactorMainFrame reactor;
				try {
					reactor = new ReactorMainFrame("/reactor-test-preferences.json");
					reactor.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				
			}
		});

	}

}
