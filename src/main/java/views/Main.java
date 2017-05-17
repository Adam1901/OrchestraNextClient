package views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;

import com.mashape.unirest.http.exceptions.UnirestException;

import controller.Controller;
import dto.DTOBranch;
import dto.DTOQueue;
import dto.DTOServicePoint;
import dto.DTOUserStatus;
import dto.DTOWorkProfile;
import utils.Props;
import utils.Props.GlobalProperties;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

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

	private final String ERROR_MESSAGE = "Failed to load data\nPlease try again and contact support with the log files";
	private final JPanel pblCounter = new JPanel();
	private final JPanel pnlVisit = new JPanel();
	private final JPanel pngQueue = new JPanel();

	/**
	 * Create the frame.
	 * 
	 * @param lu
	 * @throws IOException
	 */
	public Main(LoginUser lu) {
		String appName = Props.getGlobalProperty(GlobalProperties.APP_NAME);
		setTitle("Orchestra Next Client | Registerd to: " + appName);
		this.lu = lu;
		jbInit();
		createImagesForButtons();
		readFromProperties();
		populate();
	}

	private void readFromProperties() {
		try {
			pblCounter.setVisible(Boolean.valueOf(Props.getGlobalProperty(GlobalProperties.SHOW_COUNTER_OPTIONS)));
		} catch (Exception ex) {
			log.error(ex);
		}
		
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
			JOptionPane.showMessageDialog(this, ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void jbInit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 496, 228);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 5, 0, 50, 0, 0, 5, 0 };
		gbl_contentPane.rowHeights = new int[] { 5, 0, 0, 0, 0, 30, 5, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
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
		gbc_cmbBranch.gridwidth = 3;
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
		gbc_cmbCounter.gridwidth = 3;
		gbc_cmbCounter.insets = new Insets(0, 0, 5, 5);
		gbc_cmbCounter.gridx = 2;
		gbc_cmbCounter.gridy = 2;

		contentPane.add(cmbCounter, gbc_cmbCounter);

		GridBagConstraints gbc_cmbWorkProfile = new GridBagConstraints();
		gbc_cmbWorkProfile.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbWorkProfile.gridwidth = 3;
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
		gbc_lblA.gridwidth = 2;
		gbc_lblA.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 2;
		gbc_lblA.gridy = 4;
		contentPane.add(lblA, gbc_lblA);

		GridBagConstraints gbc_pngQueue = new GridBagConstraints();
		gbc_pngQueue.fill = GridBagConstraints.BOTH;
		gbc_pngQueue.insets = new Insets(0, 0, 5, 5);
		gbc_pngQueue.gridx = 1;
		gbc_pngQueue.gridy = 5;
		pngQueue.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Queue Info",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(pngQueue, gbc_pngQueue);
		GridBagLayout gbl_pngQueue = new GridBagLayout();
		gbl_pngQueue.columnWidths = new int[] { 0, 0 };
		gbl_pngQueue.rowHeights = new int[] { 30, 0 };
		gbl_pngQueue.columnWeights = new double[] { 0.0, Double.MIN_VALUE };
		gbl_pngQueue.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pngQueue.setLayout(gbl_pngQueue);
		GridBagConstraints gbc_btnInfo = new GridBagConstraints();
		gbc_btnInfo.fill = GridBagConstraints.BOTH;
		gbc_btnInfo.gridx = 0;
		gbc_btnInfo.gridy = 0;
		pngQueue.add(btnInfo, gbc_btnInfo);

		btnInfo.addActionListener(arg0 -> {
			new QueueInfoFrame(lu, this);
		});

		GridBagConstraints gbc_pblCoutner = new GridBagConstraints();
		gbc_pblCoutner.insets = new Insets(0, 0, 5, 5);
		gbc_pblCoutner.fill = GridBagConstraints.BOTH;
		gbc_pblCoutner.gridx = 3;
		gbc_pblCoutner.gridy = 5;
		pblCounter.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Counter",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(pblCounter, gbc_pblCoutner);
		GridBagLayout gbl_pblCoutner = new GridBagLayout();
		gbl_pblCoutner.columnWidths = new int[] { 0, 0, 0 };
		gbl_pblCoutner.rowHeights = new int[] { 0, 0 };
		gbl_pblCoutner.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_pblCoutner.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pblCounter.setLayout(gbl_pblCoutner);
		GridBagConstraints gbc_btnOpenCounter = new GridBagConstraints();
		gbc_btnOpenCounter.insets = new Insets(0, 0, 0, 5);
		gbc_btnOpenCounter.gridx = 0;
		gbc_btnOpenCounter.gridy = 0;
		pblCounter.add(btnOpenCounter, gbc_btnOpenCounter);
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.gridx = 1;
		gbc_btnClose.gridy = 0;
		pblCounter.add(btnClose, gbc_btnClose);

		btnClose.addActionListener(arg0 -> {
			try {
				DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
				DTOServicePoint sp = (DTOServicePoint) cmbCounter.getSelectedItem();

				Controller cont = new Controller();
				cont.endSession(lu, branch, sp);
				visit = null;
			} catch (Exception e) {
				log.error("Failed to data", e);
				JOptionPane.showMessageDialog(this, ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(this, ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		GridBagConstraints gbc_pnlVisit = new GridBagConstraints();
		gbc_pnlVisit.gridheight = 2;
		gbc_pnlVisit.insets = new Insets(0, 0, 5, 5);
		gbc_pnlVisit.fill = GridBagConstraints.BOTH;
		gbc_pnlVisit.gridx = 4;
		gbc_pnlVisit.gridy = 4;
		pnlVisit.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Visit",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(pnlVisit, gbc_pnlVisit);
		GridBagLayout gbl_pnlVisit = new GridBagLayout();
		gbl_pnlVisit.columnWidths = new int[] { 0, 0, 0 };
		gbl_pnlVisit.rowHeights = new int[] { 0, 0, 0 };
		gbl_pnlVisit.columnWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		gbl_pnlVisit.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		pnlVisit.setLayout(gbl_pnlVisit);
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNext.gridwidth = 2;
		gbc_btnNext.insets = new Insets(0, 0, 5, 5);
		gbc_btnNext.gridx = 0;
		gbc_btnNext.gridy = 0;
		pnlVisit.add(btnNext, gbc_btnNext);

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

				if (!custWaiting) {
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
				JOptionPane.showMessageDialog(this, ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		GridBagConstraints gbc_btnEnd = new GridBagConstraints();
		gbc_btnEnd.insets = new Insets(0, 0, 0, 5);
		gbc_btnEnd.gridx = 0;
		gbc_btnEnd.gridy = 1;
		pnlVisit.add(btnEnd, gbc_btnEnd);
		GridBagConstraints gbc_btnRecall = new GridBagConstraints();
		gbc_btnRecall.gridx = 1;
		gbc_btnRecall.gridy = 1;
		pnlVisit.add(btnRecall, gbc_btnRecall);

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
				JOptionPane.showMessageDialog(this, ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(this, ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

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
				JOptionPane.showMessageDialog(this, ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
			}
		});

		getCmbBranch().addActionListener(arg0 -> {
			if (getCmbBranch().getItemCount() == 0) {
				return;
			}
			Controller cont = new Controller();
			DTOBranch selBranch = (DTOBranch) getCmbBranch().getSelectedItem();
			Props.setUserProperty("branchIdLastUsed", String.valueOf(selBranch.getId()));
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

				JOptionPane.showMessageDialog(this, ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

	private void createImagesForButtons() {
		try {
			BufferedImage read = ImageIO.read(getClass().getClassLoader().getResource("next.png"));
			read = Scalr.resize(read, Scalr.Method.SPEED, Scalr.Mode.AUTOMATIC, 20, 20);
			btnNext.setToolTipText("Next");
			btnNext.setIcon(new ImageIcon(read));
		} catch (Throwable e) {
			log.error(e);
		}
	}

	public JComboBox<DTOBranch> getCmbBranch() {
		return cmbBranch;
	}

	public JComboBox<DTOWorkProfile> getCmbWorkProfile() {
		return cmbWorkProfile;
	}

}
