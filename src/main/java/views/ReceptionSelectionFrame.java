package views;

import com.mashape.unirest.http.exceptions.UnirestException;
import controller.Controller;
import dto.DTOBranch;
import dto.DTOEntryPoint;
import dto.DTOService;
import dto.LoginUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Props;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ReceptionSelectionFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final static Logger log = LogManager.getLogger(ReceptionSelectionFrame.class);
	private JComboBox<DTOEntryPoint> cmbEntryPoint = new JComboBox<DTOEntryPoint>();
	private JComboBox<DTOBranch> cmbBranch = new JComboBox<DTOBranch>();
	private LoginUser lu;
	private MainView mv;
	private ReceptionPanel rsf;
	private List<DTOService> services;

	/**
	 * Create the frame.
	 */
	public ReceptionSelectionFrame(LoginUser lu, ReceptionPanel receptionPanel, MainView mv) {
		this.rsf = receptionPanel;
		this.mv = mv;
		this.lu = lu;
		setTitle("Branch | Reception Counter");
		setResizable(false);
		jbInit();
		setLocationRelativeTo(null);
		populate();
	}

	public JComboBox<DTOEntryPoint> getCmbEntryPoints() {
		return cmbEntryPoint;
	}

	public JComboBox<DTOBranch> getCmbBranch() {
		return cmbBranch;
	}

	private void populate() {
		Controller cont = new Controller();
		try {
			Integer branchIdLastUser = null;
			try {
				branchIdLastUser = Integer.valueOf(Props.getUserProperty("branchIdLastUsed"));
			} catch (NumberFormatException e) {
				log.error(e);
			}

			Integer lastCounter = null;
			try {
				lastCounter = Integer.valueOf(Props.getUserProperty("lastReception"));
			} catch (NumberFormatException e) {
				log.error(e);
			}

			List<DTOBranch> branches = cont.getBranches(lu).getValue();
			for (DTOBranch dtoBranch : branches) {
				getCmbBranch().addItem(dtoBranch);
			}

			if (branchIdLastUser == null) {
				branchIdLastUser = 0;
			}

			DTOBranch branchLastUsed = null;
			for (DTOBranch branch : branches) {
				if (branch.getId().equals(branchIdLastUser)) {
					branchLastUsed = branch;
				}
			}

			if (branchLastUsed != null) {
				getCmbBranch().setSelectedItem(branchLastUsed);

				if (getCmbEntryPoints().getItemCount() != 0) {
					getCmbEntryPoints().removeAllItems();
				}
				List<DTOEntryPoint> entryPoints = cont.getEntryPoints(lu, branchLastUsed).getValue();
				for (DTOEntryPoint dtoServicePoint : entryPoints) {
					getCmbEntryPoints().addItem(dtoServicePoint);
				}

				// Now try and set SP
				if (lastCounter != null) {
					for (DTOEntryPoint entryPoint : entryPoints) {
						if (lastCounter .equals( entryPoint.getId())) {
							getCmbEntryPoints().setSelectedItem(entryPoint);
							break;
						}
					}
				}

			}
		} catch (Exception e) {
			log.error("Failed to load combo boxes", e);
			mv.showMessageDialog();
		}
	}

	private void jbInit() {
		setBounds(100, 100, 373, 175);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 5, 0, 0, 5, 0 };
		gbl_panel.rowHeights = new int[] { 5, 0, 0, 0, 0, 5, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel label = new JLabel("Branch");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 1;
		gbc_label.gridy = 1;
		panel.add(label, gbc_label);

		GridBagConstraints gbc_comboBox_2 = new GridBagConstraints();
		gbc_comboBox_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_2.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_2.gridx = 2;
		gbc_comboBox_2.gridy = 1;
		panel.add(cmbBranch, gbc_comboBox_2);

		JLabel label_1 = new JLabel("Reception");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 2;
		panel.add(label_1, gbc_label_1);

		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 2;
		panel.add(cmbEntryPoint, gbc_comboBox_1);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(arg0 -> setVisible(false));
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClose.gridwidth = 2;
		gbc_btnClose.insets = new Insets(0, 0, 5, 5);
		gbc_btnClose.gridx = 1;
		gbc_btnClose.gridy = 4;
		panel.add(btnClose, gbc_btnClose);

		getCmbBranch().addActionListener(arg0 -> {
			if (getCmbBranch().getItemCount() == 0) {
				rsf.getLblBranch().setText("Branch: None");
				return;
			}
			Controller cont = new Controller();
			DTOBranch selBranch = (DTOBranch) getCmbBranch().getSelectedItem();
			Props.setUserProperty("branchIdLastUsed", String.valueOf(selBranch.getId()));
			getCmbEntryPoints().removeAllItems();
			try {
				for (DTOEntryPoint dtoServicePoint : cont.getEntryPoints(lu, selBranch).getValue()) {
					getCmbEntryPoints().addItem(dtoServicePoint);
				}
				rsf.getLblBranch().setText("Branch: " + selBranch.getName());
				services = cont.getServices(lu, selBranch).getValue();
				rsf.fillCombo(services);
			} catch (UnirestException e) {
				log.error("Failed to data", e);
				mv.showMessageDialog();
			}
			
		});

		getCmbEntryPoints().addActionListener(e -> {
			try {
				if (getCmbEntryPoints().getItemCount() == 0) {
					rsf.getLblEntryPoint().setText("Reception: None");
					return;
				}
				Controller cont = new Controller();
				DTOBranch selBranch = (DTOBranch) getCmbBranch().getSelectedItem();
				// TODO imple
				DTOEntryPoint selectedSp = (DTOEntryPoint) getCmbEntryPoints().getSelectedItem();
				rsf.getLblEntryPoint().setText("Reception: " + selectedSp.getName());
				Props.setUserProperty("lastReception", selectedSp.getIdAsString());
			} catch (Exception ee) {
				log.error("Failed to data", ee);
				mv.showMessageDialog();
			}
		});
	}
}
