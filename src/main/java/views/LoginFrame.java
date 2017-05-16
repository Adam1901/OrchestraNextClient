package views;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.NumberFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.seaglasslookandfeel.SeaGlassLookAndFeel;

import utils.Props;
import utils.Utils;

import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPasswordField;

import java.io.IOException;
import java.text.NumberFormat;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

public class LoginFrame extends JFrame {

	/**
	 * 
	 */
	private final static Logger log = LogManager.getLogger(LoginFrame.class);
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername = new JTextField();;
	private JPasswordField passwordField = new JPasswordField();
	private JTextField txtIp = new JTextField();
	private final JComboBox<String> cmbProtocol = new JComboBox<String>();
	private JFormattedTextField txtPort;
	private final JLabel lblUrl = new JLabel("URL:");
	private final JLabel lblNewLabel = new JLabel("Username:");
	private final JLabel lblNewLabel_1 = new JLabel("Password:");;

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
					log.error("failed to load screen", e);
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

		txtIp.setText(Props.getUserProperty("ip"));
		txtPort.setText(Props.getUserProperty("port"));
		cmbProtocol.setSelectedItem(Props.getUserProperty("proto"));

		Props.setUserProperty("ip", txtIp.getText());
		Props.setUserProperty("port", txtPort.getText());
		Object selectedItem = cmbProtocol.getSelectedItem();
		if (selectedItem != null) {
			Props.setUserProperty("proto", selectedItem.toString());
		} else {
			cmbProtocol.setSelectedIndex(0);
		}
		txtUsername.setText(Props.getUserProperty("username"));
		try {
			passwordField.setText(Utils.decode(Props.getUserProperty("password")));
		} catch (Throwable e) {
			log.error(e);
		}
	}

	private void jbInit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 449, 176);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 5, 0, 50, 0, 50, 5, 0 };
		gbl_contentPane.rowHeights = new int[] { 5, 0, 0, 0, 0, 5, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		GridBagConstraints gbc_lblUrl = new GridBagConstraints();
		gbc_lblUrl.insets = new Insets(0, 0, 5, 5);
		gbc_lblUrl.anchor = GridBagConstraints.EAST;
		gbc_lblUrl.gridx = 1;
		gbc_lblUrl.gridy = 1;
		contentPane.add(lblUrl, gbc_lblUrl);

		GridBagConstraints gbc_cmbProtocol = new GridBagConstraints();
		gbc_cmbProtocol.insets = new Insets(0, 0, 5, 5);
		gbc_cmbProtocol.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbProtocol.gridx = 2;
		gbc_cmbProtocol.gridy = 1;
		cmbProtocol.setModel(new DefaultComboBoxModel<String>(new String[] { "http://", "https://" }));
		contentPane.add(cmbProtocol, gbc_cmbProtocol);

		txtIp.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtIp = new GridBagConstraints();
		gbc_txtIp.insets = new Insets(0, 0, 5, 5);
		gbc_txtIp.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtIp.gridx = 3;
		gbc_txtIp.gridy = 1;
		contentPane.add(txtIp, gbc_txtIp);
		txtIp.setColumns(10);

		NumberFormat format = NumberFormat.getInstance();
		NumberFormatter formatter = new NumberFormatter(format);
		formatter.setValueClass(Integer.class);
		formatter.setMinimum(0);
		formatter.setMaximum(Integer.MAX_VALUE);
		formatter.setAllowsInvalid(false);
		format.setGroupingUsed(false);
		// If you want the value to be committed on each keystroke instead of
		// focus lost
		formatter.setCommitsOnValidEdit(true);
		txtPort = new JFormattedTextField(formatter);

		GridBagConstraints gbc_txtPort = new GridBagConstraints();
		gbc_txtPort.insets = new Insets(0, 0, 5, 5);
		gbc_txtPort.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtPort.gridx = 4;
		gbc_txtPort.gridy = 1;
		txtPort.setText("8080");
		contentPane.add(txtPort, gbc_txtPort);

		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 2;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		txtUsername.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtSuperadmin = new GridBagConstraints();
		gbc_txtSuperadmin.gridwidth = 3;
		gbc_txtSuperadmin.insets = new Insets(0, 0, 5, 5);
		gbc_txtSuperadmin.fill = GridBagConstraints.HORIZONTAL;
		gbc_txtSuperadmin.gridx = 2;
		gbc_txtSuperadmin.gridy = 2;
		contentPane.add(txtUsername, gbc_txtSuperadmin);
		txtUsername.setColumns(10);

		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 3;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 3;
		contentPane.add(passwordField, gbc_passwordField);

		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(arg0 -> {

			Props.setUserProperty("ip", txtIp.getText());
			Props.setUserProperty("port", txtPort.getText());
			Props.setUserProperty("proto", cmbProtocol.getSelectedItem().toString());
			Props.setUserProperty("username", txtUsername.getText());
			try {
				Props.setUserProperty("password", Utils.encode(new String(passwordField.getPassword())));
			} catch (Exception e) {
				log.error(e);
			}

			String connectionString = cmbProtocol.getSelectedItem().toString() + txtIp.getText() + ":"
					+ txtPort.getText();

			LoginUser lu = new LoginUser(txtUsername.getText(), new String(passwordField.getPassword()),
					connectionString);
			new Main(lu).setVisible(true);
			setVisible(false);
		});
		GridBagConstraints gbc_btnLogin = new GridBagConstraints();
		gbc_btnLogin.gridwidth = 4;
		gbc_btnLogin.insets = new Insets(0, 0, 5, 5);
		gbc_btnLogin.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnLogin.gridx = 1;
		gbc_btnLogin.gridy = 4;
		contentPane.add(btnLogin, gbc_btnLogin);
	}
}
