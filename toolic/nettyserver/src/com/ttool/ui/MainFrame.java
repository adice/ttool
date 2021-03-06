package com.ttool.ui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowStateListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import com.ttool.biz.entity.Student;
import com.ttool.communication.CommandInterface;
import com.ttool.communication.ScreenInterface;
import com.ttool.communication.service.CommandChannelContainer;
import com.ttool.util.Constant;
import com.ttool.util.IpUtil;

import io.netty.channel.ChannelHandlerContext;

public class MainFrame extends JFrame {
	private static MainFrame mainFrame;

	private JSplitPane splitPane;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JScrollPane scrollPane;
	private JPanel lblPanel;
	private List<JLabel> lblList = new ArrayList<JLabel>(0);
	// 工具栏
	private JToolBar toolBar;
	private JButton btnScreen;
	private JButton btnRandomCall;
	// 学生上的右键菜单
	private JPopupMenu studentMenu;
	private JMenuItem screenMenu;
	// 随机点名线程
	private CallNameThread callNameThread;
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
		// 设置右键菜单
		setPopupMenu();
		// 设置Split面板
		setSplitPanel();
		// 设置左侧面板
		setLeft();
		// 设置右侧面板
		setRight();
		// 显示已经连接的学生
		setStudents();

		// 添加监听，适应窗体大小变化时
		this.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				toolBar.setBounds(0, 0, e.getComponent().getWidth(), 50);
				splitPane.setBounds(0, 50, e.getComponent().getWidth(), e.getComponent().getHeight() - 50);
				scrollPane.setBounds(0, 0, splitPane.getWidth() - splitPane.getDividerLocation() - 25,
						splitPane.getHeight() - 40);
				setStudents();
			}
		});
		this.addWindowStateListener(new WindowStateListener() {
			@Override
			public void windowStateChanged(WindowEvent e) {
				if (e.getNewState() == JFrame.MAXIMIZED_BOTH) {
					toolBar.setBounds(0, 0, e.getComponent().getWidth(), 50);
					splitPane.setBounds(0, 50, e.getComponent().getWidth(), e.getComponent().getHeight() - 50);
					scrollPane.setBounds(0, 0, splitPane.getWidth() - splitPane.getDividerLocation() - 25,
							splitPane.getHeight() - 40);
					setStudents();
				}

			}
		});
		// 开启命令服务
		CommandInterface.openCommandServer();
		// 开启共享屏幕服务
		ScreenInterface.openScreenServer();
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
		btnScreen = new JButton("共享屏幕");
		btnScreen.setToolTipText("给学生播放自己的电脑屏幕");
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
		Image tempRecordScreen = imgRecordScreen.getImage().getScaledInstance(btnRecordScreen.getWidth(),
				btnRecordScreen.getHeight(), Image.SCALE_DEFAULT);
		btnRecordScreen.setIcon(new ImageIcon(tempRecordScreen));
		toolBar.add(btnRecordScreen);
		// 随机点名
		btnRandomCall = new JButton("随机点名");
		btnRandomCall.setToolTipText("随机选择学生回答问题！");
		btnRandomCall.addActionListener(toolBarActionListener);
		btnRandomCall.setSize(50, 50);
		ImageIcon imgRandomCall = new ImageIcon("image/xsys.png");
		Image tempRandomCall = imgRandomCall.getImage().getScaledInstance(btnRandomCall.getWidth(),
				btnRandomCall.getHeight(), Image.SCALE_DEFAULT);
		btnRandomCall.setIcon(new ImageIcon(tempRandomCall));
		toolBar.add(btnRandomCall);

		this.getContentPane().add(toolBar);
		toolBar.setBounds(0, 0, initWidth, 50);
	}
	
	public void setPopupMenu() {
		studentMenu = new JPopupMenu();
		screenMenu = new JMenuItem("共享学生屏幕");
		studentMenu.add(screenMenu);
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
		// 显示教师信息
		JLabel lblTeacher = new JLabel();
		if (Constant.teacher != null) {
			lblTeacher.setText("教师：" + Constant.teacher.getName());
			leftPanel.add(lblTeacher);
		}
		JLabel lblIp = new JLabel();
		lblIp.setText("您的Ip地址是：" + IpUtil.getRealIP());
		leftPanel.add(lblIp);
		// 显示课程信息
		JLabel lblClass = new JLabel();
		leftPanel.add(lblClass);
		if (Constant.nowClass != null) {
			lblClass.setText("课程：" + Constant.nowClass.getName());
		} else {
			lblClass.setText("今天您没有课！");
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
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		rightPanel.add(scrollPane);
		scrollPane.setBounds(0, 0, splitPane.getWidth() - splitPane.getDividerLocation() - 25,
				splitPane.getHeight() - 40);
		lblPanel = new JPanel();
		lblPanel.setLayout(null);
	}

	public void setStudents() {
		lblPanel.removeAll();		
		int numPerRow = (splitPane.getWidth() - splitPane.getDividerLocation()) / 100;
		int rows;
		int numStudent = CommandChannelContainer.getContainer().size();
		if (numStudent % numPerRow == 0)
			rows = numStudent / numPerRow;
		else
			rows = numStudent / numPerRow + 1;
		ImageIcon imgScreen = new ImageIcon("image/yckz.png");
		int i = 0, j = 0;
		for (Student stu : CommandChannelContainer.getContainer().keySet()) {
			JLabel lbl = new JLabel();
			lbl.setIcon(imgScreen);
			lbl.setToolTipText(stu.getId());
			lbl.setText(stu.getName());
			lbl.setVerticalTextPosition(JLabel.BOTTOM);
			lbl.setHorizontalTextPosition(JLabel.CENTER);
			lbl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(e.isPopupTrigger()) {
						studentMenu.show(e.getComponent(), e.getX(), e.getY());
					}
				}
			});
			lblPanel.add(lbl);
			lblList.add(lbl);
			lbl.setBounds(30 + i * 100, 10 + j * 100, 100, 100);
			i++;
			if (i >= numPerRow - 1) {
				i = 0;
				j++;
			}
		}
		lblPanel.setPreferredSize(
				new Dimension(splitPane.getWidth() - splitPane.getDividerLocation(), 30 + rows * 100));
		scrollPane.setViewportView(lblPanel);
		this.repaint();
	}

	/**
	 * 给学生发送截屏
	 */
	public void sendScreen() {
		ScreenInterface.sendScreen();
		btnScreen.setText("停止共享屏幕");
		btnScreen.setToolTipText("停止给学生播放自己的电脑屏幕");
	}

	/**
	 * 停止给学生发送截屏
	 */
	public void stopSendScreen() {
		ScreenInterface.stopSendScreen();
		for (ChannelHandlerContext ctx : CommandChannelContainer.getContainer().values()) {
			CommandInterface.sendCommand(ctx, Constant.COMMAND_NOT_SEND_TEACHER_SCREEN_REQUEST);
		}
		btnScreen.setText("共享屏幕");
		btnScreen.setToolTipText("给学生播放自己的电脑屏幕");
	}

	/**
	 * 随机点名
	 */
	public void randomCall() {
		callNameThread = new CallNameThread(lblList);
		new Thread(callNameThread).start();
		btnRandomCall.setText("停");
	}

	/**
	 * 停
	 */
	public void stopRandomCall() {
		callNameThread.setBegin(false);
		btnRandomCall.setText("随机点名");
	}
}
