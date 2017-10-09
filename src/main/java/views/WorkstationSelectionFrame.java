package views;

import com.mashape.unirest.http.exceptions.UnirestException;
import controller.Controller;
import dto.DTOBranch;
import dto.DTOServicePoint;
import dto.DTOWorkProfile;
import dto.LoginUser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.Props;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WorkstationSelectionFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private final static Logger log = LogManager.getLogger(WorkstationSelectionFrame.class);
    private JComboBox<DTOWorkProfile> cmbWorkProfile = new JComboBox<DTOWorkProfile>();
    private JComboBox<DTOServicePoint> cmbServicePoint = new JComboBox<DTOServicePoint>();
    private JComboBox<DTOBranch> cmbBranch = new JComboBox<DTOBranch>();
    private WorkstationPanel main;
    private LoginUser lu;
    private DTOWorkProfile selectedWp = null;
    private MainView mv;

    /**
     * Create the frame.
     */
    public WorkstationSelectionFrame(LoginUser lu, WorkstationPanel main, MainView mv) {
        this.mv = mv;
        setTitle("Branch | Counter | Profile");
        setResizable(false);
        this.lu = lu;
        this.main = main;
        jbInit();
        setLocationRelativeTo(null);
        populate();

        getCmbServicePoint().addActionListener(servicePointAL);
        getCmbWorkProfile().addActionListener(workProfileAL);
    }

    public JComboBox<DTOWorkProfile> getCmbWorkProfile() {
        return cmbWorkProfile;
    }

    public JComboBox<DTOServicePoint> getCmbServicePoint() {
        return cmbServicePoint;
    }

    public JComboBox<DTOBranch> getCmbBranch() {
        return cmbBranch;
    }

    private void populate() {
        Controller cont = new Controller();
        try {

            Integer branchIdLastUser = null;
            Integer lastWorkProfile = null;
            Integer lastCounter = null;


            try {
                branchIdLastUser = Integer.valueOf(Props.getUserProperty("branchIdLastUsed"));
            } catch (NumberFormatException e) {
                log.error(e);
            }

            if (!lu.isAdditionalUser()) {
                try {
                    lastCounter = Integer.valueOf(Props.getUserProperty("lastCounter"));
                } catch (NumberFormatException e) {
                    log.error(e);
                }


                try {
                    lastWorkProfile = Integer.valueOf(Props.getUserProperty("lastWorkProfile"));
                } catch (NumberFormatException e) {
                    log.error(e);
                }
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
                if (branch.getId() == branchIdLastUser) {
                    branchLastUsed = branch;
                }
            }

            if (branchLastUsed != null) {
                getCmbBranch().setSelectedItem(branchLastUsed);

                if (getCmbServicePoint().getItemCount() != 0) {
                    getCmbServicePoint().removeAllItems();
                }
                List<DTOServicePoint> servicePoints = cont.getServicePoints(lu, branchLastUsed).getValue();
                for (DTOServicePoint dtoServicePoint : servicePoints) {
                    getCmbServicePoint().addItem(dtoServicePoint);
                }

                // Now try and set SP
                if (!lu.isAdditionalUser()) {
                    if (lastCounter != null) {
                        for (DTOServicePoint dtoServicePoint : servicePoints) {
                            if (lastCounter == dtoServicePoint.getId()) {
                                getCmbServicePoint().setSelectedItem(dtoServicePoint);
                                servicePointStart();
                                break;
                            }
                        }
                    }

                    // and WP
                    if (lastWorkProfile != null) {
                        DTOBranch selBranch = (DTOBranch) getCmbBranch().getSelectedItem();
                        List<DTOWorkProfile> workProfile = cont.getWorkProfile(lu, selBranch).getValue();
                        for (DTOWorkProfile dtoWorkProfile : workProfile) {
                            if (lastWorkProfile == dtoWorkProfile.getId()) {
                                getCmbWorkProfile().setSelectedItem(dtoWorkProfile);
                                workProfileStart();
                                break;
                            }
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
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 0;
        getContentPane().add(panel, gbc_panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{5, 0, 0, 5, 0};
        gbl_panel.rowHeights = new int[]{5, 0, 0, 0, 0, 5, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
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

        JLabel label_1 = new JLabel("Counter");
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
        panel.add(cmbServicePoint, gbc_comboBox_1);

        JLabel label_2 = new JLabel("Profile");
        GridBagConstraints gbc_label_2 = new GridBagConstraints();
        gbc_label_2.anchor = GridBagConstraints.EAST;
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
                main.getLblBranch().setText("Branch: None");
                return;
            }
            Controller cont = new Controller();
            DTOBranch selBranch = (DTOBranch) getCmbBranch().getSelectedItem();
            Props.setUserProperty("branchIdLastUsed", String.valueOf(selBranch.getId()));
            getCmbServicePoint().removeAllItems();
            try {
                for (DTOServicePoint dtoServicePoint : cont.getServicePoints(lu, selBranch).getValue()) {
                    getCmbServicePoint().addItem(dtoServicePoint);
                }
                main.getLblBranch().setText("Branch: " + selBranch.getName());
            } catch (UnirestException e) {
                log.error("Failed to data", e);
                mv.showMessageDialog();
            }
        });

    }


    private ActionListener servicePointAL =
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (servicePointStart()) return;
                    } catch (Exception ee) {
                        log.error("Failed to data", ee);
                        mv.showMessageDialog();
                    }
                }
            };

    private boolean servicePointStart() throws UnirestException {
        if (getCmbServicePoint().getItemCount() == 0) {
            main.getLblCounter().setText("Counter: None");
            return true;
        }
        Controller cont = new Controller();
        DTOBranch selBranch = (DTOBranch) getCmbBranch().getSelectedItem();

        if (getCmbWorkProfile().getItemCount() != 0) {
            getCmbWorkProfile().removeAllItems();
        }
        for (DTOWorkProfile dtoWp : cont.getWorkProfile(lu, selBranch).getValue()) {
            getCmbWorkProfile().addItem(dtoWp);
        }
        DTOServicePoint selectedSp = (DTOServicePoint) getCmbServicePoint().getSelectedItem();
        cont.startSession(lu, selBranch, selectedSp);
        main.getLblCounter().setText("Counter: " + selectedSp.getName());
        Props.setUserProperty("lastCounter", selectedSp.getIdAsString());
        return false;
    }


    private ActionListener workProfileAL =
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        if (workProfileStart()) return;
                    } catch (Exception e1) {
                        log.error("Failed to data", e1);
                        mv.showMessageDialog();
                    }
                }
            };

    private boolean workProfileStart() throws UnirestException {
        if (getCmbWorkProfile().getItemCount() == 0) {
            main.getLblWorkProfile().setText("Workprofile: None");
            return true;
        }

        Controller cont = new Controller();
        DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();

        DTOWorkProfile wp = (DTOWorkProfile) getCmbWorkProfile().getSelectedItem();
        if (selectedWp != null && selectedWp.getId() == wp.getId()) {
            main.getLblWorkProfile().setText("Workprofile: " + wp.getName());
            if (!lu.isAdditionalUser())
                Props.setUserProperty("lastWorkProfile", wp.getIdAsString());
            return true;
        }
        selectedWp = wp;
        cont.startSession(lu, branch, (DTOServicePoint) getCmbServicePoint().getSelectedItem());
        cont.setWorkProfile(lu, branch, wp);
        main.getLblWorkProfile().setText("Workprofile: " + wp.getName());
        if (!lu.isAdditionalUser())
            Props.setUserProperty("lastWorkProfile", wp.getIdAsString());
        return false;
    }

}
