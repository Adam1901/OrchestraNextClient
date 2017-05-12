package Views;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.exceptions.UnirestException;

import dto.DTOBranch;
import dto.DTOServicePoint;
import dto.DTOWorkProfile;

import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import javax.swing.JComboBox;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Main extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginUser lu;
	private JSONObject visit;

	JComboBox<DTOBranch> cmbBranch = new JComboBox<DTOBranch>();
	JComboBox<DTOServicePoint> cmbCounter = new JComboBox<DTOServicePoint>();
	JComboBox<DTOWorkProfile> cmbWorkProfile = new JComboBox<DTOWorkProfile>();
	private final JLabel lblNewLabel = new JLabel("Currently Serving:");
	private final JLabel lblA = new JLabel("Not Serving");
	private final JLabel lblProfile = new JLabel("Profile");
	private final JLabel lblNewLabel_1 = new JLabel("Counter");
	private final JLabel lblNewLabel_2 = new JLabel("Branch");
	private final JButton btnOpenCounter = new JButton("Open");
	private final JButton btnRecall = new JButton("Recall");
	private final JButton btnClose = new JButton("Close");
	private final JButton btnEnd = new JButton("End");

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
			Integer branchIdLastUser = Integer.valueOf(Props.getProperty("branchIdLastUsed"));
			List<DTOBranch> branches = cont.getBranches(lu);
			cmbBranch.removeAllItems();
			for (DTOBranch dtoBranch : branches) {
				cmbBranch.addItem(dtoBranch);
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
				cmbBranch.setSelectedItem(branchLastUsed);
			}
			DTOBranch selBranch = (DTOBranch) cmbBranch.getSelectedItem();
			if (cmbCounter.getItemCount() != 0) {
				cmbCounter.removeAllItems();
			}
			for (DTOServicePoint dtoServicePoint : cont.getServicePoints(lu, String.valueOf(selBranch.getId()))) {
				cmbCounter.addItem(dtoServicePoint);
			}
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
		gbl_contentPane.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPane.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 0;
		gbc_lblNewLabel_2.gridy = 0;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);

		GridBagConstraints gbc_cmbBranch = new GridBagConstraints();
		gbc_cmbBranch.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbBranch.gridwidth = 5;
		gbc_cmbBranch.insets = new Insets(0, 0, 5, 0);
		gbc_cmbBranch.gridx = 1;
		gbc_cmbBranch.gridy = 0;
		contentPane.add(cmbBranch, gbc_cmbBranch);

		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 0;
		gbc_lblNewLabel_1.gridy = 1;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);

		GridBagConstraints gbc_cmbCounter = new GridBagConstraints();
		gbc_cmbCounter.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbCounter.gridwidth = 5;
		gbc_cmbCounter.insets = new Insets(0, 0, 5, 0);
		gbc_cmbCounter.gridx = 1;
		gbc_cmbCounter.gridy = 1;

		contentPane.add(cmbCounter, gbc_cmbCounter);

		GridBagConstraints gbc_cmbWorkProfile = new GridBagConstraints();
		gbc_cmbWorkProfile.fill = GridBagConstraints.HORIZONTAL;
		gbc_cmbWorkProfile.gridwidth = 5;
		gbc_cmbWorkProfile.insets = new Insets(0, 0, 5, 0);
		gbc_cmbWorkProfile.gridx = 1;
		gbc_cmbWorkProfile.gridy = 2;
		cmbWorkProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbWorkProfile.getItemCount() == 0) {
					return;
				}
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

		GridBagConstraints gbc_lblProfile = new GridBagConstraints();
		gbc_lblProfile.anchor = GridBagConstraints.WEST;
		gbc_lblProfile.insets = new Insets(0, 0, 5, 5);
		gbc_lblProfile.gridx = 0;
		gbc_lblProfile.gridy = 2;
		contentPane.add(lblProfile, gbc_lblProfile);
		contentPane.add(cmbWorkProfile, gbc_cmbWorkProfile);

		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 3;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);

		GridBagConstraints gbc_lblA = new GridBagConstraints();
		gbc_lblA.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblA.insets = new Insets(0, 0, 5, 5);
		gbc_lblA.gridx = 1;
		gbc_lblA.gridy = 3;
		contentPane.add(lblA, gbc_lblA);

		JButton btnNext = new JButton("Next");
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Controller cont = new Controller();
				try {
					DTOBranch branch = (DTOBranch) cmbBranch.getSelectedItem();
					DTOServicePoint sp = (DTOServicePoint) cmbCounter.getSelectedItem();
					JSONObject callNext = cont.callNext(lu, String.valueOf(branch.getId()), String.valueOf(sp.getId()));
					System.out.println(callNext);
					lblA.setText(callNext.getJSONObject("object").getJSONObject("visit").get("ticketId").toString());
					visit = callNext;
				} catch (UnirestException | JSONException e) {
					lblA.setText("ERROR - Please try Again");
					e.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnNext = new GridBagConstraints();
		gbc_btnNext.gridwidth = 3;
		gbc_btnNext.insets = new Insets(0, 0, 5, 0);
		gbc_btnNext.fill = GridBagConstraints.HORIZONTAL;
		gbc_btnNext.gridx = 3;
		gbc_btnNext.gridy = 3;
		contentPane.add(btnNext, gbc_btnNext);
				
						GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
						gbc_btnNewButton_2.gridx = 5;
						gbc_btnNewButton_2.gridy = 4;
						btnClose.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								DTOBranch branch = (DTOBranch) cmbBranch.getSelectedItem();
								DTOServicePoint sp = (DTOServicePoint) cmbCounter.getSelectedItem();

								Controller cont = new Controller();
								try {
									cont.endSession(lu, String.valueOf(branch.getId()), String.valueOf(sp.getId()));
								} catch (UnirestException e) {
									e.printStackTrace();
								}
							}
						});
						
								GridBagConstraints gbc_btnNewButton2 = new GridBagConstraints();
								gbc_btnNewButton2.insets = new Insets(0, 0, 0, 5);
								gbc_btnNewButton2.gridx = 4;
								gbc_btnNewButton2.gridy = 4;
								btnEnd.addActionListener(new ActionListener() {
									public void actionPerformed(ActionEvent arg0) {

										DTOBranch branch = (DTOBranch) cmbBranch.getSelectedItem();
										DTOServicePoint sp = (DTOServicePoint) cmbCounter.getSelectedItem();
										String visitId = visit.getJSONObject("object").getJSONObject("visit").get("id").toString();
										Controller cont = new Controller();
										visit = null;
										lblA.setText("Not Serving");
										try {
											cont.endVisit(lu, String.valueOf(branch.getId()), visitId);
										} catch (UnirestException e) {
											e.printStackTrace();
										}
									}
								});
								
										GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
										gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
										gbc_btnNewButton_1.gridx = 3;
										gbc_btnNewButton_1.gridy = 4;
										btnRecall.addActionListener(new ActionListener() {
											public void actionPerformed(ActionEvent arg0) {
												DTOBranch branch = (DTOBranch) cmbBranch.getSelectedItem();
												DTOServicePoint sp = (DTOServicePoint) cmbCounter.getSelectedItem();

												Controller cont = new Controller();
												try {
													JSONObject recall = cont.recall(lu, String.valueOf(branch.getId()), String.valueOf(sp.getId()));
													visit = recall;
												} catch (UnirestException e) {
													// TODO Auto-generated catch block
													e.printStackTrace();
												}
											}
										});
										
												GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
												gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
												gbc_btnNewButton.gridx = 2;
												gbc_btnNewButton.gridy = 4;
												btnOpenCounter.addActionListener(new ActionListener() {
													public void actionPerformed(ActionEvent arg0) {
														DTOBranch branch = (DTOBranch) cmbBranch.getSelectedItem();
														DTOServicePoint sp = (DTOServicePoint) cmbCounter.getSelectedItem();

														Controller cont = new Controller();
														try {
															cont.startSession(lu, String.valueOf(branch.getId()), String.valueOf(sp.getId()));
														} catch (UnirestException e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
													}
												});
												contentPane.add(btnOpenCounter, gbc_btnNewButton);
										contentPane.add(btnRecall, gbc_btnNewButton_1);
								contentPane.add(btnEnd, gbc_btnNewButton2);
						contentPane.add(btnClose, gbc_btnNewButton_2);

		cmbBranch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cmbBranch.getItemCount() == 0) {
					return;
				}
				Controller cont = new Controller();
				DTOBranch selBranch = (DTOBranch) cmbBranch.getSelectedItem();
				Props.setProperty("branchIdLastUsed", String.valueOf(selBranch.getId()));
				cmbCounter.removeAllItems();
				try {
					for (DTOServicePoint dtoServicePoint : cont.getServicePoints(lu,
							String.valueOf(selBranch.getId()))) {
						cmbCounter.addItem(dtoServicePoint);
					}
				} catch (UnirestException e) {
					e.printStackTrace();
				}
			}
		});

		cmbCounter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (cmbCounter.getItemCount() == 0) {
					return;
				}
				Controller cont = new Controller();
				DTOBranch selBranch = (DTOBranch) cmbBranch.getSelectedItem();

				try {
					if (cmbWorkProfile.getItemCount() != 0) {
						cmbWorkProfile.removeAllItems();
					}
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
