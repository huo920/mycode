package com.client;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.client.vo.Request_Command;

public class Reg {

	private JFrame jFrame = null;  //  @jve:decl-index=0:visual-constraint="241,22"
	private JPanel jContentPane = null;
	private JLabel jLabelRegUserName = null;
	private JLabel jLabelRegPassword = null;
	private JLabel jLabelRegRepassword = null;
	private JTextField jTextFieldRegUserName = null;
	private JPasswordField jTextFieldRegPassword = null;
	private JPasswordField jTextFieldRegRepassword = null;
	public JButton jButtonReg = null;
	public JButton jButtonLogin = null;
	private InputStream in;  
	private OutputStream out;  //  @jve:decl-index=0:
	private GetServerInfo gif;
	private LinkInfo linkInfo;
	
	public Reg(LinkInfo linkInfo){
		this.linkInfo = linkInfo;
		this.linkInfo.setReg(this);
		this.getJFrame().setVisible(true);
	}
	
	public void closeFrame(){
		this.jFrame.dispose();
		this.linkInfo.setReg(null);
	}
	
	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(398, 337));
			jFrame.setTitle("注册");
			jFrame.setResizable(false);
			jFrame.setContentPane(getJContentPane());
			jFrame.addWindowListener(new java.awt.event.WindowAdapter() {
				public void windowClosing(java.awt.event.WindowEvent e) {
					System.exit(0);
				}
			});
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
			jLabelRegRepassword = new JLabel();
			jLabelRegRepassword.setBounds(new Rectangle(15, 162, 357, 47));
			jLabelRegRepassword.setFont(new Font("Dialog", Font.BOLD, 18));
			jLabelRegRepassword.setText("  确认密码：");
			jLabelRegPassword = new JLabel();
			jLabelRegPassword.setBounds(new Rectangle(15, 94, 357, 47));
			jLabelRegPassword.setFont(new Font("Dialog", Font.BOLD, 18));
			jLabelRegPassword.setText("  密       码：");
			jLabelRegUserName = new JLabel();
			jLabelRegUserName.setBounds(new Rectangle(15, 31, 357, 47));
			jLabelRegUserName.setFont(new Font("Dialog", Font.BOLD, 18));
			jLabelRegUserName.setText("  昵       称：");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(jLabelRegUserName, null);
			jContentPane.add(jLabelRegPassword, null);
			jContentPane.add(jLabelRegRepassword, null);
			jContentPane.add(getJTextFieldRegUserName(), null);
			jContentPane.add(getJTextFieldRegPassword(), null);
			jContentPane.add(getJTextFieldRegRepassword(), null);
			jContentPane.add(getJButtonReg(), null);
			jContentPane.add(getJButtonLogin(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jTextFieldRegUserName	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getJTextFieldRegUserName() {
		if (jTextFieldRegUserName == null) {
			jTextFieldRegUserName = new JTextField();
			jTextFieldRegUserName.setBounds(new Rectangle(110, 34, 258, 41));
		}
		return jTextFieldRegUserName;
	}

	/**
	 * This method initializes jTextFieldRegPassword	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JPasswordField getJTextFieldRegPassword() {
		if (jTextFieldRegPassword == null) {
			jTextFieldRegPassword = new JPasswordField();
			jTextFieldRegPassword.setBounds(new Rectangle(110, 97, 258, 41));
		}
		return jTextFieldRegPassword;
	}

	/**
	 * This method initializes jTextFieldRegRepassword	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JPasswordField getJTextFieldRegRepassword() {
		if (jTextFieldRegRepassword == null) {
			jTextFieldRegRepassword = new JPasswordField();
			jTextFieldRegRepassword.setBounds(new Rectangle(111, 165, 258, 41));
		}
		return jTextFieldRegRepassword;
	}

	/**
	 * This method initializes jButtonReg	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonReg() {
		if (jButtonReg == null) {
			jButtonReg = new JButton();
			jButtonReg.setBounds(new Rectangle(146, 221, 131, 49));
			jButtonReg.setText("注册");
			jButtonReg.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					doReg();
				}
			});
		}
		return jButtonReg;
	}

	/**
	 * This method initializes jButtonLogin	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButtonLogin() {
		if (jButtonLogin == null) {
			jButtonLogin = new JButton();
			jButtonLogin.setBounds(new Rectangle(289, 266, 88, 28));
			jButtonLogin.setText("去登陆");
			jButtonLogin.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					//如果点击了去登陆按钮
					//关闭注册框
					closeFrame();
					//打开登陆框
					linkInfo.setLogin(new Login(linkInfo));
				}
			});
		}
		return jButtonLogin;
	}
//	注册方法
	private void doReg(){
		try{
			//连接服务器端
			this.linkInfo.initSocket();
			if(this.linkInfo.getSocket()!=null){
				//做一些简单的验证，实际上这里应该有比较复杂的验证，比如敏感字符
				if(jTextFieldRegUserName.getText()!=null&&!jTextFieldRegUserName.getText().equals("")&&jTextFieldRegPassword.getText()!=null&&!jTextFieldRegPassword.getText().equals("")&&jTextFieldRegRepassword.getText()!=null&&!jTextFieldRegRepassword.getText().equals("")){
					if(!jTextFieldRegRepassword.getText().equals(jTextFieldRegPassword.getText())){
						this.linkInfo.getAlert().showAlert("两次密码输入不一致");
					}else{
						if(in==null){
							in = this.linkInfo.getSocket().getInputStream();
						}
						if(out==null){
							out = this.linkInfo.getSocket().getOutputStream();
						}
						//如果获取来自服务器端信息的线程没有启动，则启动该线程
						if(gif==null){
							gif = new GetServerInfo(this.linkInfo);
							new Thread(gif).start();
						}
						//注册过程中，使注册界面上的注册按钮不可用
						jButtonReg.setEnabled(false);
//						注册过程中，使注册界面上的去登陆按钮不可用
						jButtonLogin.setEnabled(false);
						this.linkInfo.setMe(jTextFieldRegUserName.getText());
						IOUtil.writeShort(Request_Command.REG, out);
						IOUtil.writeString(jTextFieldRegUserName.getText(), out);
						IOUtil.writeString(jTextFieldRegPassword.getText(), out);
					}
				}else{
					this.linkInfo.getAlert().showAlert("用户名或密码为空");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
