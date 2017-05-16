package views;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import java.util.Comparator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mashape.unirest.http.exceptions.UnirestException;

import controller.Controller;
import dto.DTOBranch;
import dto.DTOQueue;
import dto.DTOWorkProfile;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JTextPane;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.JScrollPane;

public class QueueInfoFrame extends JFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private final static Logger log = LogManager.getLogger(QueueInfoFrame.class);
	private LoginUser lu;
	private Main main;
	private JTextPane txtQueueInfo = new JTextPane();
	JTextPane txtWPQueueInfo = new JTextPane();

	public QueueInfoFrame(LoginUser lu, Main main) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.lu = lu;
		this.main = main;
		jbInit();
		setVisible(true);
		Thread t = new Thread(this);
		t.start();
	}

	public void jbInit() {
		setBounds(100, 100, 573, 234);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
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
		gbc_scrollPane.insets = new Insets(0, 0, 0, 5);
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 1;
		contentPane.add(scrollPane, gbc_scrollPane);
		scrollPane.setViewportView(txtQueueInfo);

		txtQueueInfo.setEditable(false);

		JScrollPane scrollPane_1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 1;
		gbc_scrollPane_1.gridy = 1;
		contentPane.add(scrollPane_1, gbc_scrollPane_1);

		scrollPane_1.setViewportView(txtWPQueueInfo);
		txtWPQueueInfo.setEditable(false);

		setTitle("Queue Info");

		setPreferredSize(new Dimension(100, 100));
	}

	@Override
	public void run() {
		Controller cont = new Controller();
		while (isVisible()) {
			try {
				List<DTOQueue> queueInfo;
				DTOBranch branch = (DTOBranch) main.getCmbBranch().getSelectedItem();
				DTOWorkProfile workProfile = (DTOWorkProfile) main.getCmbWorkProfile().getSelectedItem();
				queueInfo = cont.getQueueInfo(lu, branch);
				drawEverything(queueInfo, true);
				queueInfo = cont.getQueueInfoForWorkprofile(lu, branch, workProfile);
				drawEverything(queueInfo, false);

				Thread.sleep(5000);
			} catch (InterruptedException | UnirestException e) {
				log.error(e);
			}
		}

	}

	private void drawEverything(List<DTOQueue> queueInfo, boolean showWP) throws InterruptedException {
		queueInfo.sort(new Comparator<DTOQueue>() {
			@Override
			public int compare(DTOQueue arg0, DTOQueue arg1) {
				return arg0.getName().compareTo(arg1.getName());
			}
		});

		// Remove casual called (J8 FTW)
		queueInfo.removeIf(p -> p.getName().toLowerCase().equals("casual caller"));

		String txt = "";
		for (DTOQueue dtoQueue : queueInfo) {
			txt += dtoQueue.getName() + " - " + dtoQueue.getCustomersWaiting() + System.lineSeparator();
		}
		if (showWP) {
			txtQueueInfo.setText("All queue information" + System.lineSeparator() + txt);
		} else {
			txtWPQueueInfo.setText("Related queue information" + System.lineSeparator() + txt);
		}

		System.out.println("Loop");

	}

}
