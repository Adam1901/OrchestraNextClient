package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mashape.unirest.http.exceptions.UnirestException;

import controller.Controller;
import dto.DTOBranch;
import dto.DTOQueue;
import dto.DTOServicePoint;
import dto.DTOUserStatus;
import dto.DTOWorkProfile;
import utils.Props;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Main extends JFrame {

	private final static Logger log = LogManager.getLogger(Main.class);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginUser lu;
	private DTOUserStatus visit;

	private final JComboBox<DTOBranch> cmbBranch = new JComboBox<DTOBranch>();
	private final JComboBox<DTOServicePoint> cmbCounter = new JComboBox<DTOServicePoint>();
	private final JComboBox<DTOWorkProfile> cmbWorkProfile = new JComboBox<DTOWorkProfile>();

	private final JLabel lblNewLabel = new JLabel("Currently Serving:");
	private final JLabel lblA = new JLabel("Not Serving");
	private final JLabel lblProfile = new JLabel("Profile");
	private final JLabel lblNewLabel_1 = new JLabel("Counter");
	private final JLabel lblNewLabel_2 = new JLabel("Branch");
	private final JButton btnOpenCounter = new JButton("Open");
	private final JButton btnRecall = new JButton("Recall");
	private final JButton btnClose = new JButton("Close");
	private final JButton btnEnd = new JButton("End");
	private final JButton btnInfo = new JButton("Queue Info");
	private final JButton btnNext = new JButton("Next");

	/**
	 * Create the frame.
	 * 
	 * @param lu
	 */
	public Main(LoginUser lu) {
		this.lu = lu;
		jbInit();
		populate();
	}

	private void populate() {
		Controller cont = new Controller();
		try {
			Integer branchIdLastUser = null;
			try {
				branchIdLastUser = Integer.valueOf(Props.getProperty("branchIdLastUsed"));
			} catch (NumberFormatException e) {
				log.error(e);
			}

			List<DTOBranch> branches = cont.getBranches(lu);
			getCmbBranch().removeAllItems();
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
			DTOBranch selBranch = (DTOBranch) getCmbBranch().getSelectedItem();
			if (cmbCounter.getItemCount() != 0) {
				cmbCounter.removeAllItems();
			}
			for (DTOServicePoint dtoServicePoint : cont.getServicePoints(lu, selBranch)) {
				cmbCounter.addItem(dtoServicePoint);
			}
		} catch (Exception e) {
			log.error("Failed to load combo boxes", e);
			JOptionPane.showMessageDialog(this,
					"Failed to load combo boxes\n Please try again and contact support with the log files", "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void jbInit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 5, 0, 50, 0, 0, 0, 0, 5, 0 };
		gbl_contentPane.rowHeights = new int[] { 5, 0, 0, 0, 0, 0, 5, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 1;
		gbc_lblNewLabel_2.gridy = 1;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);

		GridBagConstraints gbc_cmbBranch = new GridBagConstraints();
		gbc_cmbBranch.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbBranch.gridwidth = 5;
		gbc_cmbBranch.insets = new Insets(0, 0, 5, 5);
		gbc_cmbBranch.gridx = 2;
		gbc_cmbBranch.gridy = 1;
		contentPane.add(getCmbBranch(), gbc_cmbBranch);

		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 2;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

		GridBagConstraints gbc_cmbCounter = new GridBagConstraints();
		gbc_cmbCounter.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCounter.gridwidth = 5;
		gbc_cmbCounter.insets = new Insets(0, 0, 5, 5);
		gbc_cmbCounter.gridx = 2;
		gbc_cmbCounter.gridy = 2;

		contentPane.add(cmbCounter, gbc_cmbCounter);

		GridBagConstraints gbc_cmbWorkProfile = new GridBagConstraints();
		gbc_cmbWorkProfile.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbWorkProfile.gridwidth = 5;
		gbc_cmbWorkProfile.insets = new Insets(0, 0, 5, 5);
		gbc_cmbWorkProfile.gridx = 2;
		gbc_cmbWorkProfile.gridy = 3;

		GridBagConstraints gbc_lblProfile = new GridBagConstraints();
		gbc_lblProfile.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblProfile.insets = new Insets(0, 0, 5, 5);
		gbc_lblProfile.gridx = 1;
		gbc_lblProfile.gridy = 3;
		contentPane.add(lblProfile, gbc_lblProfile);
		contentPane.add(getCmbWorkProfile(), gbc_cmbWorkProfile);

		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 4;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.gridwidth = 3;
		gbc_lblA.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 2;
		gbc_lblA.gridy = 4;
		contentPane.add(lblA, gbc_lblA);

		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.gridwidth = 2;
		gbc_btnNext.insets = new Insets(0, 0, 5, 5);
		gbc_btnNext.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNext.gridx = 5;
		gbc_btnNext.gridy = 4;
		contentPane.add(btnNext, gbc_btnNext);

		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_2.gridx = 6;
		gbc_btnNewButton_2.gridy = 5;

		GridBagConstraints gbc_btnNewButton2 = new GridBagConstraints();
		gbc_btnNewButton2.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton2.gridx = 5;
		gbc_btnNewButton2.gridy = 5;

		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 5;

		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
		gbc_btnNewButton.gridx = 3;
		gbc_btnNewButton.gridy = 5;

		GridBagConstraints gbc_btnInfo = new GridBagConstraints();
		gbc_btnInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnInfo.insets = new Insets(0, 0, 5, 5);
		gbc_btnInfo.gridx = 1;
		gbc_btnInfo.gridy = 5;

		contentPane.add(btnInfo, gbc_btnInfo);
		contentPane.add(btnOpenCounter, gbc_btnNewButton);
		contentPane.add(btnRecall, gbc_btnNewButton_1);
		contentPane.add(btnEnd, gbc_btnNewButton2);
		contentPane.add(btnClose, gbc_btnNewButton_2);

		getCmbWorkProfile().addActionListener(e -> {
			try {
				if (getCmbWorkProfile().getItemCount() == 0) {
					return;
				}
				Controller cont = new Controller();
				DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();

				DTOWorkProfile wp = (DTOWorkProfile) getCmbWorkProfile().getSelectedItem();
				cont.setWorkProfile(lu, branch, wp);
			} catch (Exception e1) {
				log.error("Failed to data", e1);
				JOptionPane.showMessageDialog(this,
						"Failed to load data\n Please try again and contact support with the log files", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		btnNext.addActionListener(arg0 -> {
			Controller cont = new Controller();
			try {
				DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
				DTOServicePoint sp = (DTOServicePoint) cmbCounter.getSelectedItem();
				DTOWorkProfile wp = (DTOWorkProfile) getCmbWorkProfile().getSelectedItem();

				boolean custWaiting = false;
				List<DTOQueue> queueInfoForWorkprofile = cont.getQueueInfoForWorkprofile(lu, branch, wp);
				for (DTOQueue dtoQueue : queueInfoForWorkprofile) {
					if (dtoQueue.getCustomersWaiting() != 0) {
						custWaiting = true;
					}
				}

				if (custWaiting) {
					JOptionPane.showMessageDialog(this, "No Customers waiting", "Info",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}

				DTOUserStatus callNext = cont.callNext(lu, branch, sp);
				String ticketId = callNext.getVisit().getTicketId();
				lblA.setText(ticketId);
				visit = callNext;
			} catch (Exception e) {
				lblA.setText("ERROR - Please try Again");
				log.error("Failed to data", e);
				JOptionPane.showMessageDialog(this,
						"Failed to load data\nPlease try again and contact support with the log files", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		btnClose.addActionListener(arg0 -> {
			try {
				DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
				DTOServicePoint sp = (DTOServicePoint) cmbCounter.getSelectedItem();

				Controller cont = new Controller();
				cont.endSession(lu, branch, sp);
			} catch (Exception e) {
				log.error("Failed to data", e);
				JOptionPane.showMessageDialog(this,
						"Failed to load data\n Please try again and contact support with the log files", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		btnEnd.addActionListener(arg0 -> {
			try {
				if (visit == null) {
					JOptionPane.showMessageDialog(this, "You are not currently serving a customer", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
				String visitId = visit.getVisit().getIdAsString();
				Controller cont = new Controller();
				visit = null;
				lblA.setText("Not Serving");
				cont.endVisit(lu, branch, visitId);
			} catch (Exception e) {
				log.error("Failed to data", e);
				JOptionPane.showMessageDialog(this,
						"Failed to load data\n Please try again and contact support with the log files", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		btnRecall.addActionListener(arg0 -> {
			try {
				DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
				DTOServicePoint sp = (DTOServicePoint) cmbCounter.getSelectedItem();

				if (visit == null) {
					JOptionPane.showMessageDialog(this, "You are not currently serving a customer", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				Controller cont = new Controller();
				DTOUserStatus recall = cont.recall(lu, branch, sp);
				visit = recall;
				lblA.setText(recall.getVisit().getTicketId());
			} catch (Exception e) {
				log.error("Failed to data", e);
				JOptionPane.showMessageDialog(this,
						"Failed to load data\n Please try again and contact support with the log files", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		btnOpenCounter.addActionListener(arg0 -> {
			DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
			DTOServicePoint sp = (DTOServicePoint) cmbCounter.getSelectedItem();

			Controller cont = new Controller();
			try {
				cont.startSession(lu, branch, sp);
			} catch (Exception e) {
				log.error("Failed to data", e);
				JOptionPane.showMessageDialog(this,
						"Failed to load data\n Please try again and contact support with the log files", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});

		btnInfo.addActionListener(arg0 -> {
			QueueInfoFrame qf = new QueueInfoFrame(lu, this);
			//JOptionPane.showMessageDialog(this, "Adam was here", "Info", JOptionPane.INFORMATION_MESSAGE);
		});

		getCmbBranch().addActionListener(arg0 -> {
			if (getCmbBranch().getItemCount() == 0) {
				return;
			}
			Controller cont = new Controller();
			DTOBranch selBranch = (DTOBranch) getCmbBranch().getSelectedItem();
			Props.setProperty("branchIdLastUsed", String.valueOf(selBranch.getId()));
			cmbCounter.removeAllItems();
			try {
				for (DTOServicePoint dtoServicePoint : cont.getServicePoints(lu, selBranch)) {
					cmbCounter.addItem(dtoServicePoint);
				}
			} catch (UnirestException e) {
				e.printStackTrace();
			}
		});

		cmbCounter.addActionListener(e -> {
			try {
				if (cmbCounter.getItemCount() == 0) {
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
			} catch (Exception ee) {
				log.error("Failed to data", ee);
				JOptionPane.showMessageDialog(this,
						"Failed to load data\n Please try again and contact support with the log files", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	public JComboBox<DTOBranch> getCmbBranch() {
		return cmbBranch;
	}

	public JComboBox<DTOWorkProfile> getCmbWorkProfile() {
		return cmbWorkProfile;
	}

}
