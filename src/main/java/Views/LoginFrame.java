package Views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.SwingConstants;
import javax.swing.UIManager;

public class LoginFrame extends JFrame {

	/**
	 * 
	 */
	private final static Logger log = LogManager.getLogger(LoginFrame.class);
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername = new JTextField();;
	private JPasswordField passwordField = new JPasswordField();
	private JTextField txtHttp = new JTextField();;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(new SeaGlassLookAndFeel());
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					log.error("failed to load screen" ,e);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @throws IOException
	 */
	public LoginFrame() throws IOException {
		setTitle("Orchestra Next Client");
		setResizable(false);
		jbInit();

		new FileOutputStream(Props.CONFIG_PROPERTIES, true).close();

		txtHttp.setText(Props.getProperty("ip"));
		txtUsername.setText(Props.getProperty("username"));
		passwordField.setText(Props.getProperty("password"));
	}

	private void jbInit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 176);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 5, 0, 5, 0 };
		gbl_contentPane.rowHeights = new int[] { 5, 0, 0, 0, 0, 5, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		txtHttp.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtHttp = new GridBagConstraints();
		gbc_txtHttp.insets = new Insets(0, 0, 5, 5);
		gbc_txtHttp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtHttp.gridx = 1;
		gbc_txtHttp.gridy = 1;
		contentPane.add(txtHttp, gbc_txtHttp);
		txtHttp.setColumns(10);

		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtSuperadmin = new GridBagConstraints();
		gbc_txtSuperadmin.insets = new Insets(0, 0, 5, 5);
		gbc_txtSuperadmin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSuperadmin.gridx = 1;
		gbc_txtSuperadmin.gridy = 2;
		contentPane.add(txtUsername, gbc_txtSuperadmin);
		txtUsername.setColumns(10);

		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 1;
		gbc_passwordField.gridy = 3;
		contentPane.add(passwordField, gbc_passwordField);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(arg0 -> {

			Props.setProperty("ip", txtHttp.getText());
			Props.setProperty("username", txtUsername.getText());
			Props.setProperty("password", passwordField.getText());

			LoginUser lu = new LoginUser(txtUsername.getText(), passwordField.getText(), txtHttp.getText());
			new Main(lu).setVisible(true);
			setVisible(false);
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 4;
		contentPane.add(btnLogin, gbc_btnLogin);
	}
}
