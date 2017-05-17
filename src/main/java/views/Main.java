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
import javax.swing.border.TitledBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.EtchedBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

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

	private final String ERROR_MESSAGE = "Failed to load data\nPlease try again and contact support with the log files";
	private final JPanel pblCounter = new JPanel();
	private final JLabel lblImageNext = new JLabel("");

	private BufferedImage nextImage;
	private BufferedImage nextImageClicked;
	private final JPanel panel = new JPanel();
	private final JPanel pnlSelection = new JPanel();

	/**
	 * Create the frame.
	 * 
	 * @param lu
	 * @throws IOException
	 */
	public Main(LoginUser lu) {
		setResizable(false);
		this.lu = lu;
		jbInit();
		createImagesForButtons();
		readFromProperties();
		populate();
		setVisible(true);
		postVisible();
	}

	private void postVisible() {
		Boolean showCounter = Boolean.valueOf(Props.getGlobalProperty(GlobalProperties.SHOW_COUNTER_OPTIONS));
		Dimension sizeCurrent = getSize();
		if (!showCounter) {
			Dimension newDim = new Dimension((int) sizeCurrent.getWidth(), (int) sizeCurrent.getHeight() - 60);
			setPreferredSize(newDim);
			setSize(newDim);
		}
	}

	private void readFromProperties() {
		try {
			Boolean showCounter = Boolean.valueOf(Props.getGlobalProperty(GlobalProperties.SHOW_COUNTER_OPTIONS));
			pblCounter.setVisible(showCounter);
			String appName = Props.getGlobalProperty(GlobalProperties.APP_NAME);
			setTitle("Orchestra Next | Registered to: " + appName);
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
			GridBagConstraints gbc_cmbBranch = new GridBagConstraints();
			gbc_cmbBranch.fill = GridBagConstraints.HORIZONTAL;
			gbc_cmbBranch.insets = new Insets(0, 0, 5, 0);
			gbc_cmbBranch.gridx = 1;
			gbc_cmbBranch.gridy = 0;
			pnlSelection.add(cmbBranch, gbc_cmbBranch);
			GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
			gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel_1.gridx = 0;
			gbc_lblNewLabel_1.gridy = 1;
			pnlSelection.add(lblNewLabel_1, gbc_lblNewLabel_1);
			GridBagConstraints gbc_lblProfile = new GridBagConstraints();
			gbc_lblProfile.insets = new Insets(0, 0, 0, 5);
			gbc_lblProfile.gridx = 0;
			gbc_lblProfile.gridy = 2;
			pnlSelection.add(lblProfile, gbc_lblProfile);
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
			showMessageDialog();
		}
	}

	private void jbInit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 374, 356);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 5, 0, 50, 0, 5, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 5, 0, 0, 0, 10, 30, 0, 0, 0, 0, 5, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		GridBagConstraints gbc_pnlSelection = new GridBagConstraints();
		gbc_pnlSelection.gridwidth = 4;
		gbc_pnlSelection.insets = new Insets(0, 0, 5, 5);
		gbc_pnlSelection.fill = GridBagConstraints.BOTH;
		gbc_pnlSelection.gridx = 1;
		gbc_pnlSelection.gridy = 1;
		contentPane.add(pnlSelection, gbc_pnlSelection);
		GridBagLayout gbl_pnlSelection = new GridBagLayout();
		gbl_pnlSelection.columnWidths = new int[] { 0, 0, 0 };
		gbl_pnlSelection.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_pnlSelection.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
		gbl_pnlSelection.rowWeights = new double[] { 0.0, 0.0, 0.0, Double.MIN_VALUE };
		pnlSelection.setLayout(gbl_pnlSelection);
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		pnlSelection.add(lblNewLabel_2, gbc_lblNewLabel_2);

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

		GridBagConstraints gbc_pblCoutner = new GridBagConstraints();
		gbc_pblCoutner.gridwidth = 4;
		gbc_pblCoutner.insets = new Insets(0, 0, 5, 5);
		gbc_pblCoutner.fill = GridBagConstraints.BOTH;
		gbc_pblCoutner.gridx = 1;
		gbc_pblCoutner.gridy = 3;
		pblCounter.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Counter",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		contentPane.add(pblCounter, gbc_pblCoutner);
		GridBagLayout gbl_pblCoutner = new GridBagLayout();
		gbl_pblCoutner.columnWidths = new int[] { 0, 0, 0 };
		gbl_pblCoutner.rowHeights = new int[] { 0, 0 };
		gbl_pblCoutner.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_pblCoutner.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		pblCounter.setLayout(gbl_pblCoutner);
		GridBagConstraints gbc_btnOpenCounter = new GridBagConstraints();
		gbc_btnOpenCounter.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnOpenCounter.insets = new Insets(0, 0, 0, 5);
		gbc_btnOpenCounter.gridx = 0;
		gbc_btnOpenCounter.gridy = 0;
		pblCounter.add(btnOpenCounter, gbc_btnOpenCounter);
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.fill = GridBagConstraints.HORIZONTAL;
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
				showMessageDialog();
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
				showMessageDialog();
			}
		});
		
				GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
				gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
				gbc_lblNewLabel.anchor = GridBagConstraints.SOUTH;
				gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
				gbc_lblNewLabel.gridx = 1;
				gbc_lblNewLabel.gridy = 5;
				contentPane.add(lblNewLabel, gbc_lblNewLabel);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = 2;
		gbc.gridheight = 4;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 3;
		gbc.gridy = 5;
		contentPane.add(lblImageNext, gbc);

		lblImageNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblImageNext.setIcon(new ImageIcon(nextImageClicked));
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
						showMessageDialog();
						return;
					}

					DTOUserStatus callNext = cont.callNext(lu, branch, sp);
					String ticketId = callNext.getVisit().getTicketId();
					lblA.setText(ticketId);
					visit = callNext;
				} catch (Exception ee) {
					lblA.setText("ERROR - Please try Again");
					log.error("Failed to data", e);
					showMessageDialog();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				lblImageNext.setIcon(new ImageIcon(nextImage));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblImageNext.setIcon(new ImageIcon(nextImage));
			}
		});
		
				GridBagConstraints gbc_lblA = new GridBagConstraints();
				gbc_lblA.anchor = GridBagConstraints.SOUTHWEST;
				gbc_lblA.insets = new Insets(0, 0, 5, 5);
				gbc_lblA.gridx = 1;
				gbc_lblA.gridy = 6;
				lblA.setFont(new Font("Tahoma", Font.BOLD, 17));
				contentPane.add(lblA, gbc_lblA);
		GridBagConstraints gbc_btnInfo = new GridBagConstraints();
		gbc_btnInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnInfo.insets = new Insets(0, 0, 5, 5);
		gbc_btnInfo.gridx = 1;
		gbc_btnInfo.gridy = 9;
		contentPane.add(btnInfo, gbc_btnInfo);
		
				btnInfo.addActionListener(arg0 -> {
					new QueueInfoFrame(lu, this);
				});

		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.gridwidth = 2;
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 3;
		gbc_panel.gridy = 9;
		contentPane.add(panel, gbc_panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);
		GridBagConstraints gbc_btnRecall = new GridBagConstraints();
		gbc_btnRecall.insets = new Insets(0, 0, 0, 5);
		gbc_btnRecall.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRecall.gridx = 0;
		gbc_btnRecall.gridy = 0;
		panel.add(btnRecall, gbc_btnRecall);
		
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
						showMessageDialog();
					}
				});
		GridBagConstraints gbc_btnEnd = new GridBagConstraints();
		gbc_btnEnd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEnd.gridx = 1;
		gbc_btnEnd.gridy = 0;
		panel.add(btnEnd, gbc_btnEnd);
		
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
						showMessageDialog();
					}
				});
		GridBagConstraints gbc_cmbWorkProfile = new GridBagConstraints();
		gbc_cmbWorkProfile.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbWorkProfile.gridx = 1;
		gbc_cmbWorkProfile.gridy = 2;
		pnlSelection.add(cmbWorkProfile, gbc_cmbWorkProfile);

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
				showMessageDialog();
			}
		});
		GridBagConstraints gbc_cmbCounter = new GridBagConstraints();
		gbc_cmbCounter.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCounter.insets = new Insets(0, 0, 5, 0);
		gbc_cmbCounter.gridx = 1;
		gbc_cmbCounter.gridy = 1;
		pnlSelection.add(cmbCounter, gbc_cmbCounter);

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

				showMessageDialog();
			}
		});

	}

	private void showMessageDialog() {
		JOptionPane.showMessageDialog(this, ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
	}

	private void createImagesForButtons() {
		try {
			nextImage = ImageIO.read(getClass().getClassLoader().getResource("button-1.png"));
			nextImageClicked = ImageIO.read(getClass().getClassLoader().getResource("button-2.png"));
			lblImageNext.setIcon(new ImageIcon(nextImage));
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
