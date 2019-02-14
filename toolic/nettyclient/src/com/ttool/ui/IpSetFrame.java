package com.ttool.ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.ttool.util.Constant;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class IpSetFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtIp1;
	private JTextField txtIp2;
	private JTextField txtIp3;
	private JTextField txtIp4;

	/**
	 * Create the frame.
	 */
	public IpSetFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				close();
				LoginFrame.getInstance().setVisible(true);
			}
		});
		this.setResizable(false);
		this.setSize(360, 240);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblIp = new JLabel("教师IP：");
		lblIp.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblIp.setBounds(60, 32, 60, 20);
		contentPane.add(lblIp);
		
		txtIp1 = new JTextField();
		txtIp1.setBounds(120, 32, 30, 20);
		contentPane.add(txtIp1);
		txtIp1.setColumns(10);
		
		JLabel lblPoint1 = new JLabel(".");
		lblPoint1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblPoint1.setBounds(160, 32, 10, 20);
		contentPane.add(lblPoint1);
		
		txtIp2 = new JTextField();
		txtIp2.setColumns(10);
		txtIp2.setBounds(180, 32, 30, 20);
		contentPane.add(txtIp2);
		
		txtIp3 = new JTextField();
		txtIp3.setColumns(10);
		txtIp3.setBounds(240, 32, 30, 20);
		contentPane.add(txtIp3);
		
		JLabel lblPoint2 = new JLabel(".");
		lblPoint2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblPoint2.setBounds(220, 32, 10, 20);
		contentPane.add(lblPoint2);
		
		txtIp4 = new JTextField();
		txtIp4.setColumns(10);
		txtIp4.setBounds(300, 32, 30, 20);
		contentPane.add(txtIp4);
		
		JLabel lblPoint3 = new JLabel(".");
		lblPoint3.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblPoint3.setBounds(280, 32, 10, 20);
		contentPane.add(lblPoint3);
		
		JButton btnNewButton = new JButton("确 认");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ip=txtIp1.getText()+"."+txtIp2.getText()+"."+txtIp3.getText()+"."+txtIp4.getText();
				Constant.serverIp = ip;
				close();
				LoginFrame.getInstance().setVisible(true);
			}
		});
		btnNewButton.setBounds(200, 90, 80, 36);
		contentPane.add(btnNewButton);
	}
	
	public void close() {
		this.dispose();
	}
}
