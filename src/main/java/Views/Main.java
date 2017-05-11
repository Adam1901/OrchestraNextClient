package Views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mashape.unirest.http.exceptions.UnirestException;

import dto.DTOBranch;
import dto.DTOServicePoint;
import dto.DTOWorkProfile;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.util.List;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Main extends JFrame {

	private JPanel contentPane;
	private LoginUser lu;

	JComboBox<DTOBranch> cmbBranch = new JComboBox<DTOBranch>();
	JComboBox<DTOServicePoint> cmbCounter = new JComboBox<DTOServicePoint>();
	JComboBox<DTOWorkProfile> cmbWorkProfile = new JComboBox<DTOWorkProfile>();

	/**
	 * Launch the application.
	 */

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
			for (DTOBranch dtoBranch : cont.getBranches(lu)) {
				cmbBranch.addItem(dtoBranch);
			}

			cmbBranch.setSelectedIndex(0);
			DTOBranch selBranch = (DTOBranch) cmbBranch.getSelectedItem();

			for (DTOServicePoint dtoServicePoint : cont.getServicePoints(lu, String.valueOf(selBranch.getId()))) {
				cmbCounter.addItem(dtoServicePoint);
			}
			// cmbCounter.setSelectedIndex(0);
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}

	private void jbInit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 467, 203);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		GridBagConstraints gbc_cmbBranch = new GridBagConstraints();
		gbc_cmbBranch.insets = new Insets(0, 0, 5, 0);
		gbc_cmbBranch.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbBranch.gridx = 0;
		gbc_cmbBranch.gridy = 0;
		contentPane.add(cmbBranch, gbc_cmbBranch);

		GridBagConstraints gbc_cmbCounter = new GridBagConstraints();
		gbc_cmbCounter.insets = new Insets(0, 0, 5, 0);
		gbc_cmbCounter.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCounter.gridx = 0;
		gbc_cmbCounter.gridy = 1;

		contentPane.add(cmbCounter, gbc_cmbCounter);

		GridBagConstraints gbc_cmbWorkProfile = new GridBagConstraints();
		gbc_cmbWorkProfile.insets = new Insets(0, 0, 5, 0);
		gbc_cmbWorkProfile.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbWorkProfile.gridx = 0;
		gbc_cmbWorkProfile.gridy = 2;
		cmbWorkProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller cont = new Controller();
				DTOBranch branch = (DTOBranch) cmbBranch.getSelectedItem();

				DTOWorkProfile wp = (DTOWorkProfile) cmbWorkProfile.getSelectedItem();
				try {
					cont.setWorkProfile(lu, String.valueOf(branch.getId()), String.valueOf(wp.getId()));
				} catch (UnirestException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.add(cmbWorkProfile, gbc_cmbWorkProfile);

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller cont = new Controller();
				try {
					cont.callNext(lu, "2", "6");
				} catch (UnirestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.gridx = 0;
		gbc_btnNext.gridy = 3;
		contentPane.add(btnNext, gbc_btnNext);

		cmbBranch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller cont = new Controller();
				DTOBranch selBranch = (DTOBranch) cmbBranch.getSelectedItem();

				try {
					for (DTOServicePoint dtoServicePoint : cont.getServicePoints(lu,
							String.valueOf(selBranch.getId()))) {
						cmbCounter.addItem(dtoServicePoint);
					}
				} catch (UnirestException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		cmbCounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Controller cont = new Controller();
				DTOBranch selBranch = (DTOBranch) cmbBranch.getSelectedItem();

				try {
					for (DTOWorkProfile dtoWp : cont.getWorkProfile(lu, String.valueOf(selBranch.getId()))) {
						cmbWorkProfile.addItem(dtoWp);
					}
				} catch (UnirestException ee) {
					// TODO Auto-generated catch block
					ee.printStackTrace();
				}
			}
		});
	}

}
