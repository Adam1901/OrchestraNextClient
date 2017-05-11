package Views;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;

public class LoginFrame extends JFrame {

	private JPanel contentPane;
	private JTextField txtSuperadmin;
	private JPasswordField passwordField;
	private JTextField txtHttp;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
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
	public LoginFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 167);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		txtHttp = new JTextField();
		txtHttp.setHorizontalAlignment(SwingConstants.CENTER);
		txtHttp.setText("http://54.229.6.91:8080");
		GridBagConstraints gbc_txtHttp = new GridBagConstraints();
		gbc_txtHttp.insets = new Insets(0, 0, 5, 0);
		gbc_txtHttp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHttp.gridx = 0;
		gbc_txtHttp.gridy = 0;
		contentPane.add(txtHttp, gbc_txtHttp);
		txtHttp.setColumns(10);
		
		txtSuperadmin = new JTextField();
		txtSuperadmin.setHorizontalAlignment(SwingConstants.CENTER);
		txtSuperadmin.setText("superadmin");
		GridBagConstraints gbc_txtSuperadmin = new GridBagConstraints();
		gbc_txtSuperadmin.insets = new Insets(0, 0, 5, 0);
		gbc_txtSuperadmin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSuperadmin.gridx = 0;
		gbc_txtSuperadmin.gridy = 1;
		contentPane.add(txtSuperadmin, gbc_txtSuperadmin);
		txtSuperadmin.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 0);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 0;
		gbc_passwordField.gridy = 2;
		contentPane.add(passwordField, gbc_passwordField);
		
		passwordField.setText("ulan");
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoginUser lu = new LoginUser(txtSuperadmin.getText(), passwordField.getText(), txtHttp.getText());
				new Main(lu).setVisible(true);
				setVisible(false);
			}
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.gridx = 0;
		gbc_btnLogin.gridy = 3;
		contentPane.add(btnLogin, gbc_btnLogin);
	}

}
