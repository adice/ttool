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
import com.ttool.biz.teacher.TeacherServiceImpl;
import com.ttool.util.Constant;
import com.ttool.util.IpUtil;
import com.ttool.util.PropertyUtil;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JPasswordField txtPwd;
	private JLabel lblMsg;

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
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

		txtName = new JTextField("926169");
		txtName.setBounds(130, 32, 170, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);

		txtPwd = new JPasswordField("123456");
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
				TeacherServiceImpl teacherServiceImpl = new TeacherServiceImpl();
				Constant.teacher = teacherServiceImpl.login(name, pwd);
				if (Constant.teacher != null) {
					showClasses();
					Constant.serverIp = IpUtil.getRealIP();
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
		this.contentPane.removeAll();
		JLabel lbl = new JLabel("请选择班级:");
		lbl.setBounds(80, 20, 200, 20);
		this.contentPane.add(lbl);
		if (list != null && list.size() > 0) {
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
			this.dispose();
			MainFrame.getInstance();
		}
	}

	public void chooseClass(ActualClass ac) {
		Constant.nowClass = ac;
		ActualClassServiceImpl actualClassServiceImpl = new ActualClassServiceImpl();
		actualClassServiceImpl.editTeacherIp(ac.getId(), Constant.serverIp);
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
