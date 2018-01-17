package views;

import javax.swing.JPanel;

import com.mashape.unirest.http.exceptions.UnirestException;

import controller.Controller;
import dto.DTOBranch;
import dto.DTOEntryPoint;
import dto.DTOService;
import dto.DTOVisit;
import dto.LoginUser;

import java.awt.GridBagLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import javax.swing.border.TitledBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;

import javax.swing.border.EtchedBorder;
import utils.Props;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;

public class ReceptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private LoginUser lu;
	private ReceptionSelectionFrame frm;
	private MainView mv;
	JLabel lblBranch = new JLabel("A");
	JLabel lblEntryPoint = new JLabel("AAAAAAA");
	JComboBox<DTOService> cmbServices = new JComboBox<DTOService>();
	JLabel lblA = new JLabel("Not Serving");
	DTOVisit visit = null;
	private final static Logger log = LogManager.getLogger(ReceptionPanel.class);
	private final JPanel panel_1 = new JPanel();
	private final JButton btnNewButton = new JButton("Create Casual caller");
	private final JComboBox cmbCasualCaller = new JComboBox();
	/**
	 * Create the panel.
	 */
	public ReceptionPanel(LoginUser lu, MainView mv) {
		this.lu = lu;
		this.mv = mv;
		frm = new ReceptionSelectionFrame(this.lu, this, this.mv);
		jbInit();

	}

	private void jbInit() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 5, 0, 5, 0 };
		gridBagLayout.rowHeights = new int[] { 5, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JButton btnNewButton_1 = new JButton("Create");
		btnNewButton_1.addActionListener(arg0 -> {
            Controller cont = new Controller();
            try {
                DTOBranch branch = (DTOBranch) frm.getCmbBranch().getSelectedItem();
                DTOEntryPoint ep = (DTOEntryPoint) frm.getCmbEntryPoints().getSelectedItem();
                DTOService service = (DTOService) cmbServices.getSelectedItem();
                visit = cont.createVisit(lu, branch, ep, service).getValue();
                lblA.setText(visit.getTicketId());
            } catch (UnirestException e) {
                e.printStackTrace();
            }
        });
		

		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		lblA.setFont(new Font("Tahoma", Font.BOLD, 24));
		GridBagConstraints gbc_lblNotServiing = new GridBagConstraints();
		gbc_lblNotServiing.insets = new Insets(0, 0, 5, 5);
		gbc_lblNotServiing.gridx = 1;
		gbc_lblNotServiing.gridy = 1;
		add(lblA, gbc_lblNotServiing);
		
		
		GridBagConstraints gbc_cmbServices = new GridBagConstraints();
		gbc_cmbServices.insets = new Insets(0, 0, 5, 5);
		gbc_cmbServices.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbServices.gridx = 1;
		gbc_cmbServices.gridy = 2;
		add(cmbServices, gbc_cmbServices);
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.gridx = 1;
		gbc_btnNewButton_1.gridy = 3;
		add(btnNewButton_1, gbc_btnNewButton_1);
		
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 4;
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Casual Caller", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[]{0, 0};
		gbl_panel_1.rowHeights = new int[]{0, 0, 0};
		gbl_panel_1.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_1.rowWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		panel_1.setLayout(gbl_panel_1);
		
		GridBagConstraints gbc_cmbCasualCaller = new GridBagConstraints();
		gbc_cmbCasualCaller.insets = new Insets(0, 0, 5, 0);
		gbc_cmbCasualCaller.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCasualCaller.gridx = 0;
		gbc_cmbCasualCaller.gridy = 0;
		panel_1.add(cmbCasualCaller, gbc_cmbCasualCaller);
		
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		panel_1.add(btnNewButton, gbc_btnNewButton);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				Props.getLangProperty("MainFrame.SettingBorderText"), TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 5;
		add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblSettings = new JLabel("");
		lblSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frm.setVisible(true);
			}
		});
		GridBagConstraints gbc_lblSettings = new GridBagConstraints();
		gbc_lblSettings.gridheight = 4;
		gbc_lblSettings.insets = new Insets(0, 0, 0, 5);
		gbc_lblSettings.gridx = 0;
		gbc_lblSettings.gridy = 0;
		panel.add(lblSettings, gbc_lblSettings);

		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 0);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 0;
		panel.add(lblBranch, gbc_label_1);

		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 0);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 1;
		panel.add(lblEntryPoint, gbc_label_2);

		panel_1.setVisible(false);

		BufferedImage image;
		try {
			image = ImageIO.read(getClass().getClassLoader().getResource("settings.png"));
			image = Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.AUTOMATIC, 80, 80);
			ImageIcon imageIcon = new ImageIcon(image);
			lblSettings.setIcon(imageIcon);
		} catch (IOException e) {
			log.error(e);
		}
	}
	
	public void fillCombo(List<DTOService> services){
		cmbServices.removeAllItems();
		for (DTOService dtoService : services) {
			cmbServices.addItem(dtoService);
		}
		
	}

	public JLabel getLblBranch() {
		return lblBranch;
	}

	public JLabel getLblEntryPoint() {
		return lblEntryPoint;
	}

}
