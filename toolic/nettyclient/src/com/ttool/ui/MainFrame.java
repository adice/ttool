package com.ttool.ui;

import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import com.ttool.biz.entity.SignInRecord;
import com.ttool.biz.signinrecord.SignInRecordServiceImpl;
import com.ttool.communication.CommandInterface;
import com.ttool.communication.ScreenInterface;
import com.ttool.util.Constant;
import com.ttool.util.IpUtil;

public class MainFrame extends JFrame {
	private static MainFrame mainFrame;

	private static JSplitPane splitPane;
	private static JPanel leftPanel;
	private static JPanel rightPanel;
	private static JLabel lblScreen;
	// 工具栏
	private static JToolBar toolBar;
	private static JButton btnScreen;
	// 屏幕初始宽高
	private int initWidth = 1280;
	private int initHeight = 720;

	private MainFrame() {
		this.getContentPane().setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(initWidth, initHeight);
		this.setVisible(true);

		// 设置工具栏
		setToolBar();
		// 设置Split面板
		setSplitPanel();
		// 设置左侧面板
		setLeft();
		// 设置右侧面板
		setRight();

		// 添加监听，适应窗体大小变化时
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				toolBar.setBounds(0, 0, e.getComponent().getWidth(), 50);
				splitPane.setBounds(0, 50, e.getComponent().getWidth(), e.getComponent().getHeight() - 50);
				if (lblScreen != null)
					lblScreen.setBounds(0, 0, rightPanel.getWidth(), rightPanel.getHeight());
			}
		});
		this.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				if (e.getNewState() == JFrame.MAXIMIZED_BOTH) {
					toolBar.setBounds(0, 0, e.getComponent().getWidth(), 50);
					splitPane.setBounds(0, 50, e.getComponent().getWidth(), e.getComponent().getHeight() - 50);
					if (lblScreen != null)
						lblScreen.setBounds(0, 0, rightPanel.getWidth(), rightPanel.getHeight());
				}

			}
		});
		// 连接命令服务
		try {
			CommandInterface.connectCommandServer(Constant.serverIp);
			ScreenInterface.connectScreenServer(Constant.serverIp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MainFrame getInstance() {
		if (MainFrame.mainFrame == null)
			MainFrame.mainFrame = new MainFrame();
		return MainFrame.mainFrame;
	}

	/**
	 * 设置工具栏
	 */
	public void setToolBar() {
		ToolBarActionListener toolBarActionListener = new ToolBarActionListener(this);
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		// Logo
		JLabel lblLogo = new JLabel();
		lblLogo.setSize(80, 50);
		ImageIcon imgLogo = new ImageIcon("image/logo1.png");
		Image tempLogo = imgLogo.getImage().getScaledInstance(lblLogo.getWidth(), lblLogo.getHeight(),
				Image.SCALE_DEFAULT);
		lblLogo.setIcon(new ImageIcon(tempLogo));
		toolBar.add(lblLogo);
		// 共享屏幕的工具
		btnScreen = new JButton("查看屏幕");
		btnScreen.setToolTipText("查看教师的电脑屏幕");
		btnScreen.addActionListener(toolBarActionListener);
		btnScreen.setSize(50, 50);
		ImageIcon imgScreen = new ImageIcon("image/yckz.png");
		Image tempScreen = imgScreen.getImage().getScaledInstance(btnScreen.getWidth(), btnScreen.getHeight(),
				Image.SCALE_DEFAULT);
		btnScreen.setIcon(new ImageIcon(tempScreen));
		toolBar.add(btnScreen);
		// 录制屏幕的工具
		JButton btnRecordScreen = new JButton("录制屏幕");
		btnRecordScreen.setToolTipText("录制自己的电脑屏幕！");
		btnRecordScreen.addActionListener(toolBarActionListener);
		btnRecordScreen.setSize(50, 50);
		ImageIcon imgRecordScreen = new ImageIcon("image/lp.png");
		btnRecordScreen.setIcon(imgRecordScreen);
		Image tempRecordScreen = imgRecordScreen.getImage().getScaledInstance(btnRecordScreen.getWidth(),
				btnRecordScreen.getHeight(), Image.SCALE_DEFAULT);
		btnRecordScreen.setIcon(new ImageIcon(tempRecordScreen));
		toolBar.add(btnRecordScreen);
		// 人脸识别签到
		if (Constant.nowClass != null) {
			JButton btnFaceRecord = new JButton("人脸签到");
			btnFaceRecord.setToolTipText("人脸识别并上课签到！");
			btnFaceRecord.addActionListener(toolBarActionListener);
			btnFaceRecord.setSize(50, 50);
			ImageIcon imgFaceRecord = new ImageIcon("image/fr.png");
			btnFaceRecord.setIcon(imgFaceRecord);
			Image tempFaceRecord = imgFaceRecord.getImage().getScaledInstance(btnFaceRecord.getWidth(),
					btnFaceRecord.getHeight(), Image.SCALE_DEFAULT);
			btnFaceRecord.setIcon(new ImageIcon(tempFaceRecord));
			toolBar.add(btnFaceRecord);
		}
		this.getContentPane().add(toolBar);
		toolBar.setBounds(0, 0, initWidth, 50);
	}

	/**
	 * 设置分隔面板
	 */
	public void setSplitPanel() {
		splitPane = new JSplitPane();
		splitPane.setBounds(0, 50, initWidth, initHeight - 50);
		splitPane.setDividerLocation(200);
		splitPane.setDividerSize(8);
		splitPane.setOneTouchExpandable(true);
		splitPane.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(java.beans.PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(JSplitPane.DIVIDER_LOCATION_PROPERTY)) {
					if (lblScreen != null) {
						lblScreen.setBounds(0, 0, rightPanel.getWidth(), rightPanel.getHeight());
					}
				}
			}
		});
		this.getContentPane().add(splitPane);
	}

	/**
	 * 设置分隔面板左侧内容
	 */
	public void setLeft() {
		leftPanel = new JPanel();
		splitPane.setLeftComponent(leftPanel);
		leftPanel.setLayout(new GridLayout(8, 1));
		leftPanel.setAlignmentX(CENTER_ALIGNMENT);
		// 显示学生信息
		JLabel lblTeacher = new JLabel();
		if (Constant.student != null) {
			lblTeacher.setText("学生：" + Constant.student.getName());
			leftPanel.add(lblTeacher);
		}
		// 显示课程信息
		JLabel lblClass = new JLabel();
		leftPanel.add(lblClass);
		if (Constant.nowClass != null) {
			lblClass.setText("课程：" + Constant.nowClass.getName());
		} else {
			lblClass.setText("今天您没有课！");
			if (Constant.serverIp == null || Constant.serverIp.equals("")) {
				lblClass.setText("今天您没有课！");
			}
		}
		// 显示教学周
		JLabel lblWeeks = new JLabel();
		leftPanel.add(lblWeeks);
		lblWeeks.setText("教学周：" + Constant.weeks);
	}

	public void setRight() {
		rightPanel = new JPanel();
		rightPanel.setLayout(null);
		splitPane.setRightComponent(rightPanel);
	}

	/**
	 * 连接并显示教师端共享的屏幕
	 */
	public void connectAndDisplayScreen() {
		CommandInterface.sendCommand(Constant.COMMAND_SEND_TEACHER_SCREEN_REQUEST);
		btnScreen.setText("停止查看屏幕");
		btnScreen.setToolTipText("停止查看教师的电脑屏幕");

		lblScreen = new JLabel();
		lblScreen.setBounds(0, 0, rightPanel.getWidth(), rightPanel.getHeight());
		rightPanel.add(lblScreen);
	}

	/**
	 * 断开连接，不再显示教师端的共享屏幕
	 */
	public void stopDisplayScreen() {
		CommandInterface.sendCommand(Constant.COMMAND_NOT_SEND_TEACHER_SCREEN_REQUEST);
		btnScreen.setText("查看屏幕");
		btnScreen.setToolTipText("查看教师的电脑屏幕");

		lblScreen = null;
		rightPanel.removeAll();
		rightPanel.repaint();
	}

	/**
	 * 显示教师端屏幕
	 * 
	 * @param img
	 */
	public void showImg(BufferedImage img) {
		lblScreen.setIcon(new ImageIcon(resize(img, rightPanel.getWidth(), rightPanel.getHeight())));
		lblScreen.repaint();
	}

	public BufferedImage resize(BufferedImage img, int newW, int newH) {
		BufferedImage dimg = new BufferedImage(newW, newH, img.getType());
		Graphics2D g = dimg.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g.drawImage(img, 0, 0, newW, newH, null);
		g.dispose();
		return dimg;
	}

	public void faceRecord() {
		SignInRecord signInRecord = new SignInRecord();
		signInRecord.setClassId(Constant.nowClass.getId());
		signInRecord.setStuId(Constant.student.getId());
		signInRecord.setStuIp(IpUtil.getRealIP());
		signInRecord.setSignInTime(new Date());
		SignInRecordServiceImpl signInRecordServiceImpl = new SignInRecordServiceImpl();
		signInRecordServiceImpl.checkIn(signInRecord);
	}
}
