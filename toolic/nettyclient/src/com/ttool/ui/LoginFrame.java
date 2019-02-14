package com.ttool.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.ttool.biz.actualclass.ActualClassServiceImpl;
import com.ttool.biz.entity.ActualClass;
import com.ttool.biz.student.StudentServiceImpl;
import com.ttool.util.Constant;
import com.ttool.util.IpUtil;
import com.ttool.util.PropertyUtil;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LoginFrame extends JFrame {
	
	private static LoginFrame loginFrame;

	private JPanel contentPane;
	private JTextField txtName;
	private JPasswordField txtPwd;
	private JLabel lblMsg;
	
	public static LoginFrame getInstance() {
		if(loginFrame==null)
			loginFrame=new LoginFrame();
		return loginFrame;
	}

	/**
	 * Create the frame.
	 */
	private LoginFrame() {
		this.setResizable(false);
		this.setSize(360, 240);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblName = new JLabel("账号：");
		lblName.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblName.setBounds(60, 32, 60, 20);
		contentPane.add(lblName);

		JLabel lblPwd = new JLabel("密码：");
		lblPwd.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblPwd.setBounds(60, 80, 60, 20);
		contentPane.add(lblPwd);

		txtName = new JTextField("2016010102");
		txtName.setBounds(130, 32, 170, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);

		txtPwd = new JPasswordField("123");
		txtPwd.setBounds(130, 80, 170, 20);
		contentPane.add(txtPwd);

		lblMsg = new JLabel("");
		lblMsg.setForeground(Color.RED);
		lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsg.setBounds(60, 110, 240, 20);
		contentPane.add(lblMsg);

		JButton btnLogin = new JButton("登  录");
		btnLogin.addActionListener(new LoginActionListener(this));
		btnLogin.setBounds(60, 140, 180, 36);
		contentPane.add(btnLogin);

		JLabel lblConfig = new JLabel("设置");
		lblConfig.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				loginFrame.setVisible(false);
				new IpSetFrame();
			}
		});
		lblConfig.setBounds(270, 150, 30, 20);
		contentPane.add(lblConfig);
	}

	public void login() {
		PropertyUtil.readConfig();
		if (Constant.academicYear == null || Constant.academicYear.equals("") || Constant.semester == null
				|| Constant.semester.equals("") || Constant.startDate == null || Constant.startDate.equals("")) {
			showConfig();
			lblMsg.setText("请先设置学年、学期、本学期起始日期！");
		} else {
			String name = txtName.getText();
			String pwd = new String(txtPwd.getPassword());
			if (name != null && !name.equals("") && pwd != null && !pwd.equals("")) {
				StudentServiceImpl studentServiceImpl = new StudentServiceImpl();
				Constant.student = studentServiceImpl.login(name, pwd, IpUtil.getRealIP());
				if (Constant.student != null) {
					showClasses();
				} else {
					lblMsg.setText("账号密码不正确，请重新填写！");
					txtPwd.setText("");
					txtName.grabFocus();
				}
			} else {
				lblMsg.setText("请填写账号和密码！");
				txtName.grabFocus();
			}
		}
	}

	public void showClasses() {
		ActualClassServiceImpl actualClassServiceImpl = new ActualClassServiceImpl();
		List<ActualClass> list = actualClassServiceImpl.getTodayClasses();
		if (list != null && list.size() > 0) {
			this.contentPane.removeAll();
			JLabel lblMsg = new JLabel("请选择班级:");
			lblMsg.setBounds(80, 20, 200, 20);
			this.contentPane.add(lblMsg);
			int i = 0;
			for (ActualClass ac : list) {
				JButton btn = new JButton(ac.getName());
				this.contentPane.add(btn);
				btn.setBounds(80, 50 + (i * 50), 200, 40);
				btn.addActionListener(new ChooseClassActionListener(this, ac));
				i++;
			}
			this.setSize(360, (i + 1) * 50 + 70);
		} else {
			if(Constant.serverIp==null || Constant.serverIp.equals("")) {
				lblMsg.setText("今天您没课！请先设置教师端IP！");
			}else {
				this.dispose();
				MainFrame.getInstance();
			}
		}
	}

	public void chooseClass(ActualClass ac) {
		Constant.nowClass = ac;
		Constant.serverIp = ac.getTeacherIp();
		// TODO 连接教师端命令服务，后续还应该有手动签到功能（比如当前教师端服务没有开启）
		// TODO 签到？还是后续手动签到
		this.dispose();
		MainFrame.getInstance();
	}

	public void showConfig() {
		// TODO 改变窗体外观，显示设置配置的部分

	}

	public void saveConfig() {
		// TODO 修改并保存配置
	}
}
