package views;

import com.mashape.unirest.http.exceptions.UnirestException;
import controller.Controller;
import dto.DTOBranch;
import dto.DTOQueue;
import dto.DTOWorkProfile;
import dto.LoginUser;
import utils.Props;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.util.List;

public class QueueInfoFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private final static Logger log = LogManager.getLogger(QueueInfoFrame.class);
	JTextPane txtWPQueueInfo = new JTextPane();
	private LoginUser lu;
	private Workstation workstation;
	private JTextPane txtQueueInfo = new JTextPane();

	public QueueInfoFrame(LoginUser lu, Workstation workstation) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.lu = lu;
		this.workstation = workstation;
		jbInit();
		Thread t = new Thread(this);
		t.start();
	}

	public void jbInit() {
		setBounds(100, 100, 573, 354);
		setLocationRelativeTo(null);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		JLabel lblNewLabel = new JLabel("Queue Information");
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		scrollPane.setViewportView(txtQueueInfo);

		txtQueueInfo.setEditable(false);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);

		scrollPane_1.setViewportView(txtWPQueueInfo);
		txtWPQueueInfo.setEditable(false);

		JButton btnClose = new JButton("Close");
		btnClose.addActionListener(arg0 -> setVisible(false));
		GridBagConstraints gbc_btnClose = new GridBagConstraints();
		gbc_btnClose.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnClose.gridwidth = 2;
		gbc_btnClose.insets = new Insets(0, 0, 0, 5);
		gbc_btnClose.gridx = 0;
		gbc_btnClose.gridy = 2;
		contentPane.add(btnClose, gbc_btnClose);

		setTitle("Queue Info");

		setPreferredSize(new Dimension(100, 100));
		setLocationRelativeTo(null);
	}

	@Override
	public void run() {
		Controller cont = new Controller();
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
			}
			while (isVisible()) {
				try {
					List<DTOQueue> queueInfo;
					DTOBranch branch = (DTOBranch) workstation.getCmbBranch().getSelectedItem();
					DTOWorkProfile workProfile = (DTOWorkProfile) workstation.getCmbWorkProfile().getSelectedItem();
					queueInfo = cont.getQueueInfo(lu, branch).getValue();
					drawEverything(queueInfo, true);
					queueInfo = cont.getQueueInfoForWorkprofile(lu, branch, workProfile).getValue();
					drawEverything(queueInfo, false);

					Thread.sleep(5000);
				} catch (InterruptedException | UnirestException e) {
					log.error(e);
				}
			}
		}
	}

	private void drawEverything(List<DTOQueue> queueInfo, boolean showWP) throws InterruptedException {

		String txt = "";
		for (DTOQueue dtoQueue : queueInfo) {

			String name = dtoQueue.getName();
			int customersWaiting = dtoQueue.getCustomersWaiting();

			txt += customersWaiting + " - " + name + System.lineSeparator();
		}
		if (showWP) {
			txtQueueInfo.setText("All queue information" + System.lineSeparator() + txt);
		} else {
			txtWPQueueInfo.setText("Related queue information" + System.lineSeparator() + txt);
		}
	}
}
