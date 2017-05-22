package views;

import java.awt.GridBagLayout;
import javax.swing.JPanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mashape.unirest.http.exceptions.UnirestException;

import controller.Controller;

import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import java.awt.Insets;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import dto.DTOWorkProfile;
import utils.Props;
import dto.DTOServicePoint;
import dto.DTOBranch;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SelectionFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final static Logger log = LogManager.getLogger(SelectionFrame.class);
	private JComboBox<DTOWorkProfile> cmbWorkProfile = new JComboBox<DTOWorkProfile>();
	private JComboBox<DTOServicePoint> cmbServicePoint = new JComboBox<DTOServicePoint>();
	private JComboBox<DTOBranch> cmbBranch = new JComboBox<DTOBranch>();
	private Main main;
	private LoginUser lu;

	public JComboBox<DTOWorkProfile> getCmbWorkProfile() {
		return cmbWorkProfile;
	}

	public JComboBox<DTOServicePoint> getCmbServicePoint() {
		return cmbServicePoint;
	}

	public JComboBox<DTOBranch> getCmbBranch() {
		return cmbBranch;
	}

	/**
	 * Create the frame.
	 */
	public SelectionFrame(LoginUser lu, Main main) {
		setTitle("Branch | Counter | Profile");
		setResizable(false);
		this.lu = lu;
		this.main = main;
		jbInit();
		populate();
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

			List<DTOBranch> branches = cont.getBranches(lu);
			for (DTOBranch dtoBranch : branches) {
				getCmbBranch().addItem(dtoBranch);
			}

			if (branchIdLastUser == null) {
				branchIdLastUser = 0;
			}

			DTOBranch branchLastUsed = null;
			for (DTOBranch branch : branches) {
				if (branch.getId() == branchIdLastUser) {
					branchLastUsed = branch;
				}
			}

			if (branchLastUsed != null) {
				getCmbBranch().setSelectedItem(branchLastUsed);
			}
			if (getCmbServicePoint().getItemCount() != 0) {
				getCmbServicePoint().removeAllItems();
			}
			for (DTOServicePoint dtoServicePoint : cont.getServicePoints(lu, branchLastUsed)) {
				getCmbServicePoint().addItem(dtoServicePoint);
			}
		} catch (Exception e) {
			log.error("Failed to load combo boxes", e);
			main.showMessageDialog();
		}
	}

	private void jbInit() {
		setBounds(100, 100, 373, 145);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		getContentPane().setLayout(gridBagLayout);

		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		getContentPane().add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel label = new JLabel("Branch");
		GridBagConstraints gbc_label = new GridBagConstraints();
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

		JLabel label_1 = new JLabel("Counter");
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 1;
		gbc_label_1.gridy = 2;
		panel.add(label_1, gbc_label_1);

		GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
		gbc_comboBox_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox_1.gridx = 2;
		gbc_comboBox_1.gridy = 2;
		panel.add(cmbServicePoint, gbc_comboBox_1);

		JLabel label_2 = new JLabel("Profile");
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.gridx = 1;
		gbc_label_2.gridy = 3;
		panel.add(label_2, gbc_label_2);

		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 2;
		gbc_comboBox.gridy = 3;
		panel.add(cmbWorkProfile, gbc_comboBox);

		getCmbWorkProfile().addActionListener(e -> {
			try {
				if (getCmbWorkProfile().getItemCount() == 0) {
					main.getLblWorkProfile().setText("Workprofile: None");
					return;
				}
				Controller cont = new Controller();
				DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();

				DTOWorkProfile wp = (DTOWorkProfile) getCmbWorkProfile().getSelectedItem();
				cont.setWorkProfile(lu, branch, wp);
				main.getLblWorkProfile().setText("Workprofile: " + wp.getName());
			} catch (Exception e1) {
				log.error("Failed to data", e1);
				main.showMessageDialog();
			}
		});

		getCmbBranch().addActionListener(arg0 -> {
			if (getCmbBranch().getItemCount() == 0) {
				main.getLblBranch().setText("Branch: None");
				return;
			}
			Controller cont = new Controller();
			DTOBranch selBranch = (DTOBranch) getCmbBranch().getSelectedItem();
			Props.setUserProperty("branchIdLastUsed", String.valueOf(selBranch.getId()));
			getCmbServicePoint().removeAllItems();
			getCmbServicePoint().removeAllItems();
			try {
				for (DTOServicePoint dtoServicePoint : cont.getServicePoints(lu, selBranch)) {
					getCmbServicePoint().addItem(dtoServicePoint);
				}
				main.getLblBranch().setText("Branch: " + selBranch.getName());
			} catch (UnirestException e) {
				log.error("Failed to data", e);
				main.showMessageDialog();
			}
		});

		getCmbServicePoint().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					if (getCmbServicePoint().getItemCount() == 0) {
						main.getLblCounter().setText("Counter: None");
						return;
					}
					Controller cont = new Controller();
					DTOBranch selBranch = (DTOBranch) getCmbBranch().getSelectedItem();

					if (getCmbWorkProfile().getItemCount() != 0) {
						getCmbWorkProfile().removeAllItems();
					}
					for (DTOWorkProfile dtoWp : cont.getWorkProfile(lu, selBranch)) {
						getCmbWorkProfile().addItem(dtoWp);
					}
					main.getLblCounter().setText(
							"Counter: " + ((DTOServicePoint) getCmbServicePoint().getSelectedItem()).getName());
				} catch (Exception ee) {
					log.error("Failed to data", ee);
					main.showMessageDialog();
				}
			}
		});
	}
}
