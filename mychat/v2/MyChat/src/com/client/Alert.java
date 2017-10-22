package com.client;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JButton;
//提示框
public class Alert {

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="255,39"
	private JPanel jContentPane = null;
	private JLabel jLabelAlertInfo = null;
	private JButton jButton = null;
	
	public Alert(){
		this.getJFrame();
	}
	//将信息显示到提示框中
	public void showAlert(String info){
		jLabelAlertInfo.setText(info);
		jFrame.setVisible(true);
	}
	
	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(377, 281));
			jFrame.setTitle("信息");
			jFrame.setResizable(false);
			jFrame.setContentPane(getJContentPane());
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jLabelAlertInfo = new JLabel();
			jLabelAlertInfo.setBounds(new Rectangle(15, 16, 333, 142));
			jLabelAlertInfo.setEnabled(false);
			jLabelAlertInfo.setText("");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabelAlertInfo, null);
			jContentPane.add(getJButton(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(106, 172, 159, 49));
			jButton.setText("确认");
			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					jFrame.setVisible(false);
				}
			});
		}
		return jButton;
	}

}
