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
import utils.UpdateThread;
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
import javax.swing.SwingConstants;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Timer;

public class Main extends JFrame {

	// TODO add remember sp and wp

	private final static Logger log = LogManager.getLogger(Main.class);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginUser lu;
	private DTOUserStatus visit;

	private final JLabel lblNewLabel = new JLabel(Props.getLangProperty("MainFrame.CurrentlyServing"));
	private final JLabel lblA = new JLabel(Props.getLangProperty("MainFrame.CurrentlyServingInit"));
	private final JButton btnOpenCounter = new JButton(Props.getLangProperty("MainFrame.OpenBtn"));
	private final JButton btnRecall = new JButton(Props.getLangProperty("MainFrame.RecallBtn"));
	private final JButton btnClose = new JButton(Props.getLangProperty("MainFrame.CloseBtn"));
	private final JButton btnEnd = new JButton(Props.getLangProperty("MainFrame.endBtn"));
	private final JButton btnInfo = new JButton(Props.getLangProperty("MainFrame.QueuInfoBtn"));

	private final String ERROR_MESSAGE = Props.getLangProperty("MainFrame.ErrorMessage");
	private final JPanel pblCounter = new JPanel();
	private final JLabel lblImageNext = new JLabel("");

	private BufferedImage nextImage;
	private BufferedImage nextImageClicked;
	private final JPanel panel = new JPanel();
	private final JLabel lblSettings = new JLabel("");

	private SelectionFrame frm;
	private final JPanel panel_1 = new JPanel();
	private final JLabel lblBranch = new JLabel("A");
	private final JLabel lblCounter = new JLabel("AAAAAAA");
	private final JLabel lblWorkProfile = new JLabel("A");
	private final JPanel panel_2 = new JPanel();
	private boolean flash = false;

	public JLabel getLblWorkProfile() {
		return lblWorkProfile;
	}

	public JLabel getLblBranch() {
		return lblBranch;
	}

	public JLabel getLblCounter() {
		return lblCounter;
	}

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
		setVisible(true);
		postVisible();
		frm = new SelectionFrame(lu, this);

		Thread t = new Thread(new Flash());
		t.start();
		
		UpdateThread updateThread = new UpdateThread(lu, this);
		new Thread(updateThread).start();
	}

	private void postVisible() {
		Boolean showCounter = Boolean.valueOf(Props.getGlobalProperty(GlobalProperties.SHOW_COUNTER_OPTIONS));
		Dimension sizeCurrent = getSize();
		if (!showCounter) {
			Dimension newDim = new Dimension((int) sizeCurrent.getWidth(), (int) sizeCurrent.getHeight() - 50);
			setPreferredSize(newDim);
			setSize(newDim);
		}
	}

	private void readFromProperties() {
		try {
			Boolean showCounter = Boolean.valueOf(Props.getGlobalProperty(GlobalProperties.SHOW_COUNTER_OPTIONS));
			pblCounter.setVisible(showCounter);
			String appName = Props.getGlobalProperty(GlobalProperties.APP_NAME);
			setTitle(Props.getLangProperty("MainFrame.title") + appName);
		} catch (Exception ex) {
			log.error(ex);
		}
	}

	private void jbInit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 390, 402);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 5, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 30, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNewLabel.anchor = GridBagConstraints.SOUTH;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.gridwidth = 2;
		gbc_lblA.anchor = GridBagConstraints.NORTH;
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 1;
		gbc_lblA.gridy = 1;
		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		lblA.setFont(new Font("Tahoma", Font.BOLD, 24));
		contentPane.add(lblA, gbc_lblA);

		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.gridwidth = 2;
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		panel_1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				Props.getLangProperty("MainFrame.VisitBorderText"), TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		contentPane.add(panel_1, gbc_panel_1);
		GridBagLayout gbl_panel_1 = new GridBagLayout();
		gbl_panel_1.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_1.rowHeights = new int[] { 0, 0, 0 };
		gbl_panel_1.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_1.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		panel_1.setLayout(gbl_panel_1);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 1.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.gridx = 0;
		gbc_panel.gridy = 0;
		panel_1.add(panel, gbc_panel);
		panel.setLayout(gbl_panel);
		GridBagConstraints gbc_btnRecall = new GridBagConstraints();
		gbc_btnRecall.insets = new Insets(0, 0, 5, 0);
		gbc_btnRecall.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnRecall.gridx = 0;
		gbc_btnRecall.gridy = 0;
		panel.add(btnRecall, gbc_btnRecall);

		btnRecall.addActionListener(arg0 -> {
			try {
				DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
				DTOServicePoint sp = (DTOServicePoint) frm.getCmbServicePoint().getSelectedItem();

				if (visit == null) {
					JOptionPane.showMessageDialog(this, Props.getLangProperty("MainFrame.notservingMessage"), "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				Controller cont = new Controller();
				DTOUserStatus recall = cont.recall(lu, branch, sp);
				visit = recall;
				lblA.setText(recall.getVisit().getTicketId());
				flash = true;
			} catch (Exception e) {
				log.error("Failed to data", e);
				showMessageDialog();
			}
		});
		GridBagConstraints gbc_btnEnd = new GridBagConstraints();
		gbc_btnEnd.insets = new Insets(0, 0, 5, 0);
		gbc_btnEnd.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnEnd.gridx = 0;
		gbc_btnEnd.gridy = 1;
		panel.add(btnEnd, gbc_btnEnd);

		btnEnd.addActionListener(arg0 -> {
			try {
				if (visit == null) {
					JOptionPane.showMessageDialog(this, Props.getLangProperty("MainFrame.notCurrentlyServing"), "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				if (checkIfDSNeeded()) {
					return;
				}

				DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
				String visitId = visit.getVisit().getIdAsString();
				Controller cont = new Controller();
				visit = null;
				lblA.setText(Props.getLangProperty("MainFrame.notServingText"));
				cont.endVisit(lu, branch, visitId);
			} catch (Exception e) {
				log.error("Failed to data", e);
				showMessageDialog();
			}
		});
		GridBagConstraints gbc_btnInfo = new GridBagConstraints();
		gbc_btnInfo.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnInfo.insets = new Insets(0, 0, 5, 0);
		gbc_btnInfo.gridx = 0;
		gbc_btnInfo.gridy = 2;
		panel.add(btnInfo, gbc_btnInfo);
		GridBagConstraints gbc_lblImageNext = new GridBagConstraints();
		gbc_lblImageNext.insets = new Insets(0, 0, 5, 0);
		gbc_lblImageNext.gridx = 1;
		gbc_lblImageNext.gridy = 0;
		panel_1.add(lblImageNext, gbc_lblImageNext);

		GridBagConstraints gbc_panel_2 = new GridBagConstraints();
		gbc_panel_2.gridwidth = 2;
		gbc_panel_2.fill = GridBagConstraints.BOTH;
		gbc_panel_2.gridx = 0;
		gbc_panel_2.gridy = 1;
		panel_2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				Props.getLangProperty("MainFrame.SettingBorderText"), TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
		panel_1.add(panel_2, gbc_panel_2);
		GridBagLayout gbl_panel_2 = new GridBagLayout();
		gbl_panel_2.columnWidths = new int[] { 0, 0, 0 };
		gbl_panel_2.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel_2.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_panel_2.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel_2.setLayout(gbl_panel_2);
		GridBagConstraints gbc_lblSettings = new GridBagConstraints();
		gbc_lblSettings.gridheight = 4;
		gbc_lblSettings.insets = new Insets(0, 0, 5, 5);
		gbc_lblSettings.gridx = 0;
		gbc_lblSettings.gridy = 0;
		panel_2.add(lblSettings, gbc_lblSettings);
		GridBagConstraints gbc_lblBranch = new GridBagConstraints();
		gbc_lblBranch.insets = new Insets(0, 0, 5, 0);
		gbc_lblBranch.gridx = 1;
		gbc_lblBranch.gridy = 0;
		panel_2.add(lblBranch, gbc_lblBranch);
		GridBagConstraints gbc_lblCounter = new GridBagConstraints();
		gbc_lblCounter.insets = new Insets(0, 0, 5, 0);
		gbc_lblCounter.gridx = 1;
		gbc_lblCounter.gridy = 1;
		panel_2.add(lblCounter, gbc_lblCounter);
		GridBagConstraints gbc_lblWorkProfile = new GridBagConstraints();
		gbc_lblWorkProfile.insets = new Insets(0, 0, 5, 0);
		gbc_lblWorkProfile.gridx = 1;
		gbc_lblWorkProfile.gridy = 2;
		panel_2.add(lblWorkProfile, gbc_lblWorkProfile);

		GridBagConstraints gbc_pblCoutner = new GridBagConstraints();
		gbc_pblCoutner.gridwidth = 2;
		gbc_pblCoutner.insets = new Insets(0, 0, 0, 5);
		gbc_pblCoutner.fill = GridBagConstraints.BOTH;
		gbc_pblCoutner.gridx = 1;
		gbc_pblCoutner.gridy = 3;
		pblCounter.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
				Props.getLangProperty("MainFrame.CounterBorderText"), TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));
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
				DTOServicePoint sp = (DTOServicePoint) frm.getCmbServicePoint().getSelectedItem();

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
			DTOServicePoint sp = (DTOServicePoint) frm.getCmbServicePoint().getSelectedItem();

			Controller cont = new Controller();
			try {
				cont.startSession(lu, branch, sp);
			} catch (Exception e) {
				log.error("Failed to data", e);
				showMessageDialog();
			}
		});
		lblSettings.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				frm.setVisible(true);
			}
		});

		lblImageNext.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				lblImageNext.setIcon(new ImageIcon(nextImage));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				lblImageNext.setIcon(new ImageIcon(nextImage));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				lblImageNext.setIcon(new ImageIcon(nextImageClicked));
				Controller cont = new Controller();
				try {
					DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
					DTOServicePoint sp = (DTOServicePoint) frm.getCmbServicePoint().getSelectedItem();
					DTOWorkProfile wp = (DTOWorkProfile) getCmbWorkProfile().getSelectedItem();

					boolean custWaiting = false;
					List<DTOQueue> queueInfoForWorkprofile = cont.getQueueInfoForWorkprofile(lu, branch, wp);
					for (DTOQueue dtoQueue : queueInfoForWorkprofile) {
						if (dtoQueue.getCustomersWaiting() != 0) {
							custWaiting = true;
						}
					}

					if (!custWaiting) {
						showMessageDialog(Props.getLangProperty("MainFrame.NoWatingCustomers"),
								JOptionPane.INFORMATION_MESSAGE);
						return;
					}

					if (checkIfDSNeeded()) {
						return;
					}

					DTOUserStatus callNext = cont.callNext(lu, branch, sp);
					String ticketId = callNext.getVisit().getTicketId();
					lblA.setText(ticketId);
					visit = callNext;
					flash = true;
				} catch (Exception ee) {
					lblA.setText(Props.getLangProperty("MainFrame.ErrorCurretServing"));
					log.error("Failed to data", ee);
					showMessageDialog(Props.getLangProperty("MainFrame.noWaitingCustText"),
							JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});

		btnInfo.addActionListener(arg0 -> {
			new QueueInfoFrame(lu, this);
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				Controller cont = new Controller();
				try {
					cont.logout(lu);
					log.info("Logged out and killed unirest");
				} catch (UnirestException | IOException e) {
					log.error(e);
				}
			}
		});

	}

	public void showMessageDialog() {
		JOptionPane.showMessageDialog(this, ERROR_MESSAGE, "Error", JOptionPane.ERROR_MESSAGE);
	}

	public void showMessageDialog(String message, int type) {
		JOptionPane.showMessageDialog(this, message, "Error", type);
	}

	private void createImagesForButtons() {
		try {
			nextImage = ImageIO.read(getClass().getClassLoader().getResource("button-1.png"));
			nextImageClicked = ImageIO.read(getClass().getClassLoader().getResource("button-2.png"));
			lblImageNext.setIcon(new ImageIcon(nextImage));

			BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource("settings.png"));
			image = Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.AUTOMATIC, 80, 80);
			ImageIcon imageIcon = new ImageIcon(image);
			lblSettings.setIcon(imageIcon);

			setIconImage(ImageIO.read(getClass().getClassLoader().getResource("qmaticBigTransparent.png")));
		} catch (Throwable e) {
			log.error(e);
		}
	}

	public JComboBox<DTOBranch> getCmbBranch() {
		return frm.getCmbBranch();
	}

	public JComboBox<DTOWorkProfile> getCmbWorkProfile() {
		return frm.getCmbWorkProfile();
	}

	private boolean checkIfDSNeeded() {
		if (visit == null) {
			return false;
		}
		String[] arrays = { "OUTCOME_FOR_DELIVERED_SERVICE_NEEDED", "OUTCOME_OR_DELIVERED_SERVICE_NEEDED",
				"DELIVERED_SERVICE_NEEDED", "OUTCOME_NEEDED" };
		String visitState = visit.getVisitState();
		for (String state : arrays) {
			if (visitState.equals(state)) {
				showMessageDialog(Props.getLangProperty("MainFrame.DsOutEtNeeded"), JOptionPane.ERROR_MESSAGE);
				return true;
			}
		}
		return false;
	}

	int amount = 0;
	boolean started = false;
	Timer timer = new Timer(500, evt -> {
		Color foreground2 = lblA.getForeground();
		if (foreground2 == Color.BLACK) {
			lblA.setForeground(Color.GRAY);
		} else {
			lblA.setForeground(Color.BLACK);
		}
		revalidate();
		repaint();
		System.out.println("coloring");
		amount++;

	});

	private class Flash implements Runnable {
		public void run() {
			while (true) {
				if (flash) {
					if (!started) {
						timer.start();
						started = true;
					}
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					log.error(e);
				}
				if (amount >= 10) {
					timer.stop();
					lblA.setForeground(Color.BLACK);
					amount = 0;
					flash = false;
					started = false;
				}
			}
		}
	}
}
