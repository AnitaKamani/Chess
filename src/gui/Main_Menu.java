package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Main_Menu extends JFrame {
	private JPanel contentPane;
	private final Action new_game = new SwingAction_New_Game();
	private final Action show_records = new SwingAction_Records();
	private final Action to_exit = new SwingAction_Exit();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main_Menu frame = new Main_Menu();
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
	public Main_Menu() {
		game.Main.reset_values();	
		///
		
		Bord.CustomCursor(this, 0);// set cursor
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setBounds(0, 0, 960, 1030);
	
		setUndecorated(true);
		getContentPane().setLayout(null);

		JButton new_game_button = new JButton("New Game");
		new_game_button.setAction(new_game);
		new_game_button.setContentAreaFilled(false);
		new_game_button.setOpaque(false);
		new_game_button.setBorderPainted(false);
		new_game_button.setForeground(new Color(255, 254, 254));
		new_game_button.setBackground(new Color(40, 30, 30));
		new_game_button.setFocusPainted(false);
		new_game_button.setFont(new Font("Segoe Print", Font.BOLD, 30));
		new_game_button.setBounds(349, 197, 262, 80);
		getContentPane().add(new_game_button);

		JButton records_button = new JButton("Records");
		records_button.setAction(show_records);
		records_button.setContentAreaFilled(false);
		records_button.setOpaque(false);
		records_button.setBorderPainted(false);
		records_button.setForeground(new Color(255, 254, 254));
		records_button.setBackground(new Color(40, 30, 30));
		records_button.setFocusPainted(false);
		records_button.setFont(new Font("Segoe Print", Font.BOLD, 30));
		records_button.setBounds(349, 474, 262, 80);
		getContentPane().add(records_button);

		JButton exit_button = new JButton("Exit");
		exit_button.setAction(to_exit);
		exit_button.setContentAreaFilled(false);
		exit_button.setOpaque(false);
		exit_button.setBorderPainted(false);
		exit_button.setForeground(new Color(255, 254, 254));
		exit_button.setBackground(new Color(25, 20, 20));
		exit_button.setFocusPainted(false);
		exit_button.setFont(new Font("Segoe Print", Font.BOLD, 30));
		exit_button.setBounds(349, 751, 262, 80);
		getContentPane().add(exit_button);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Main_Menu.class.getResource("/background/menu_background.png")));
		lblNewLabel.setBounds(0, 0, 960, 1030);
		getContentPane().add(lblNewLabel);
		setVisible(true);
	}

	private class SwingAction_New_Game extends AbstractAction {
		public SwingAction_New_Game() {
			putValue(NAME, "New Game");
		}

		public void actionPerformed(ActionEvent e) {
			game.Main.ng=new New_Game();
			dispose();
		}
	}

	private class SwingAction_Records extends AbstractAction {
		public SwingAction_Records() {
			putValue(NAME, "Records");
		}

		public void actionPerformed(ActionEvent e) {

		}

	}

	private class SwingAction_Exit extends AbstractAction {
		public SwingAction_Exit() {
			putValue(NAME, "Exit");
		}

		public void actionPerformed(ActionEvent e) {
			int input = JOptionPane.showConfirmDialog(null, "Do you want to exit ?");
			if (input == 0) {
				System.exit(0);
			}
		}
	}
}