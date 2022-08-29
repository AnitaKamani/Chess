package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import game.Main;
import game.socket;

public class Timer_Request extends JFrame {

	private JPanel contentPane;
	private final Action ok_action = new SwingAction();
	private JSlider n_slider;
	private JSlider m_slider;
	private String[] mtable = { "0", "1/4", "1/2", "3/4", "1", "1.5", "2", "3", "4", "5", "6", "7", "8", "9", "10",
			"11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "25", "30", "35", "40", "45", "60", "90", "120",
			"150", "180" };// =35
	private String[] ntable = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15",
			"16", "17", "18", "19", "20", "25", "30", "35", "40", "45", "60", "90", "120", "150", "180" };// =31

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Timer_Request frame = new Timer_Request();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Timer_Request() {
		Bord.CustomCursor(this, 0);// set cursor
		setUndecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(480, 0, 960, 1030);
		if (game.Main.app_mode == 1)
			setBounds(0, 0, 960, 1030);
		else if (game.Main.app_mode == 2)
			setBounds(960, 0, 960, 1030);

		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel note = new JLabel("You are the white player now please set the chess clock");
		note.setHorizontalAlignment(SwingConstants.CENTER);

		note.setAlignmentX(Component.CENTER_ALIGNMENT);
		note.setForeground(new Color(250, 245, 245));
		note.setFont(new Font("Segoe Print", Font.PLAIN, 26));
		note.setBounds(0, 56, 960, 209);
		contentPane.add(note);

		JLabel mValue = new JLabel("5");
		mValue.setForeground(new Color(255, 254, 254));
		mValue.setFont(new Font("Segoe Print", Font.PLAIN, 22));
		mValue.setBackground(Color.RED);
		mValue.setBounds(489, 360, 60, 30);
		contentPane.add(mValue);

		JLabel nValue = new JLabel("8");
		nValue.setForeground(new Color(255, 254, 254));
		nValue.setFont(new Font("Segoe Print", Font.PLAIN, 22));
		nValue.setBackground(Color.RED);
		nValue.setBounds(549, 520, 60, 30);
		contentPane.add(nValue);

		JLabel mTextLabel = new JLabel("Minutes per side: ");
		mTextLabel.setForeground(new Color(255, 254, 254));
		mTextLabel.setFont(new Font("Segoe Print", Font.PLAIN, 22));
		mTextLabel.setBounds(279, 360, 250, 30);
		contentPane.add(mTextLabel);

		m_slider = new JSlider();
		m_slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				mValue.setText(mtable[(((JSlider) e.getSource()).getValue())]);
			}
		});
		m_slider.setSnapToTicks(true);
		m_slider.setOpaque(false);
		m_slider.setBackground(new Color(255, 255, 255));
		m_slider.setMajorTickSpacing(1);
		m_slider.setMinorTickSpacing(1);
		m_slider.setForeground(new Color(255, 255, 255));
		m_slider.setValue(9);
		m_slider.setMaximum(34);
		m_slider.setBounds(299, 415, 360, 30);
		contentPane.add(m_slider);

		JLabel nTextLabel = new JLabel("Increment in seconds:");
		nTextLabel.setForeground(new Color(255, 254, 254));
		nTextLabel.setFont(new Font("Segoe Print", Font.PLAIN, 22));
		nTextLabel.setBounds(279, 520, 250, 30);
		contentPane.add(nTextLabel);

		n_slider = new JSlider();
		n_slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				nValue.setText(ntable[(((JSlider) e.getSource()).getValue())]);
			}
		});
		n_slider.setValue(8);
		n_slider.setSnapToTicks(true);
		n_slider.setOpaque(false);
		n_slider.setMinorTickSpacing(1);
		n_slider.setMaximum(30);
		n_slider.setMajorTickSpacing(1);
		n_slider.setForeground(Color.WHITE);
		n_slider.setBackground(Color.WHITE);
		n_slider.setBounds(299, 575, 360, 30);
		contentPane.add(n_slider);

		JButton ok_button = new JButton("OK");
		ok_button.setAction(ok_action);
		ok_button.setOpaque(false);
		ok_button.setForeground(new Color(255, 254, 254));
		ok_button.setFont(new Font("Segoe Print", Font.BOLD, 21));
		ok_button.setFocusPainted(false);
		ok_button.setContentAreaFilled(false);
		ok_button.setBorderPainted(false);
		ok_button.setBackground(new Color(40, 30, 30));
		ok_button.setBounds(429, 660, 100, 35);
		contentPane.add(ok_button);

		JLabel back_label = new JLabel("");
		back_label.setIcon(new ImageIcon(Timer_Request.class.getResource("/background/menu_background.png")));
		back_label.setBounds(0, 0, 960, 1030);
		contentPane.add(back_label);

		setVisible(true);
	}

	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "OK");
		}

		public void actionPerformed(ActionEvent e) {
			if (m_slider.getValue() != 0) {
				try {
					Main.m = Double.parseDouble(mtable[m_slider.getValue()]);
					Main.my_remaining_time = (long) (Main.m * 60 * 1000);// in milliseconds
					Main.other_remaining_time = (long) (Main.m * 60 * 1000);// in milliseco

				} catch (Exception eee) {
					double m1 = Double.parseDouble(mtable[m_slider.getValue()].substring(0, 1));
					double m2 = Double.parseDouble(mtable[m_slider.getValue()].substring(2));
					Main.m = m1 / m2;
					///
					Main.my_remaining_time = (long) (Main.m * 60 * 1000);// in milliseconds
					Main.other_remaining_time = (long) (Main.m * 60 * 1000);// in milliseco
					//

				}
				Main.n = Double.parseDouble(ntable[n_slider.getValue()]);
				if (game.Main.app_mode == 0) {
					game.Main.bord=new gui.Bord();
				}

				else {
					socket.send("!@~$2#" + Main.m + "%" + Main.n);// "!@~$3#0.25%7"
					game.Main.bord=new gui.Bord();
				}
				dispose();
			}
		}
	}
}
