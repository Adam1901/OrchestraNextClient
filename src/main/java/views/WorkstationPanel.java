package views;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import controller.Controller;
import dto.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.imgscalr.Scalr;
import utils.Props;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class WorkstationPanel {
    private final static Logger log = LogManager.getLogger(WorkstationPanel.class);
    int amount = 0;
    boolean started = false;
    private LoginUser lu;
    private MainView mv;
    private JButton btnRecall;
    private JButton btnEnd;
    private JButton btnQueueInfo;
    private JPanel mainPanel = new JPanel();
    private JLabel lblNextButton;
    private JLabel lblSettings;
    private JPanel pnlVist;
    private JPanel pnlSettings;
    private JLabel lblBranch;
    private JLabel lblCounter;
    private JLabel lblWorkProfile;
    private JLabel lblA;
    private JButton btnOpen;
    private JButton btnClose;
    private JPanel pnlCoutner;
    private BufferedImage nextImage;
    private BufferedImage nextImageClicked;
    private boolean flash = false;
    private WorkstationSelectionFrame frm;
    private QueueInfoFrame queueInfoFrame = null;
    private DTOUserStatus visit;
    private Timer timer = new Timer(500, (ActionEvent evt) -> {
        Color foreground2 = lblA.getForeground();
        if (foreground2 == Color.BLACK) {
            lblA.setForeground(Color.GRAY);
        } else {
            lblA.setForeground(Color.BLACK);
        }
        // revalidate();
        // repaint();
        amount++;

    });

    public WorkstationPanel(LoginUser lu, MainView mainView) {
        this.lu = lu;
        this.mv = mainView;
        mainPanel.setVisible(true);
        frm = new WorkstationSelectionFrame(lu, this, mv);
        queueInfoFrame = new QueueInfoFrame(lu, this);
        jbInit();
        createImagesForButtons();
        readFromProperties();
        postJbInit();
    }

    private void jbInit() {
        pnlSettings.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
                Props.getLangProperty("MainFrame.SettingBorderText"), TitledBorder.LEADING, TitledBorder.TOP, null,
                new Color(0, 0, 0)));
        pnlVist.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
                Props.getLangProperty("MainFrame.VisitBorderText"), TitledBorder.LEADING, TitledBorder.TOP, null,
                new Color(0, 0, 0)));
        btnQueueInfo.addActionListener(arg0 -> queueInfoFrame.setVisible(true));
        lblNextButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                lblNextButton.setIcon(new ImageIcon(nextImage));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblNextButton.setIcon(new ImageIcon(nextImage));
            }

            @Override
            public void mousePressed(MouseEvent arg0) {
                lblNextButton.setIcon(new ImageIcon(nextImageClicked));
                Controller cont = new Controller();
                try {
                    DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
                    DTOServicePoint sp = (DTOServicePoint) frm.getCmbServicePoint().getSelectedItem();
                    DTOWorkProfile wp = (DTOWorkProfile) getCmbWorkProfile().getSelectedItem();

                    String ticketId;
                    DTOUserStatus callNext;

                    Boolean callFowards = Boolean.valueOf(Props.getGlobalProperty(Props.GlobalProperties.CALL_FORWARDS));
                    if (callFowards) {
                        String serviceId = Props.getGlobalProperty(Props.GlobalProperties.CALL_FORWARDS_SERVICE);
                        cont.callNextAndEnd(lu, branch, sp, Integer.valueOf(serviceId));
                        callNext = null;
                        ticketId = Props.getLangProperty("MainFrame.callForwardText");
                    } else {
                        boolean custWaiting = false;
                        java.util.List<DTOQueue> queueInfoForWorkprofile = cont.getQueueInfoForWorkprofile(lu, branch, wp).getValue();
                        for (DTOQueue dtoQueue : queueInfoForWorkprofile) {
                            if (dtoQueue.getCustomersWaiting() != 0) {
                                custWaiting = true;
                            }
                        }

                        if (!custWaiting) {
                            mv.showMessageDialog(Props.getLangProperty("MainFrame.NoWatingCustomers"),
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        if (checkIfDSNeeded()) {
                            return;
                        }

                        callNext = cont.callNext(lu, branch, sp).getValue();
                        ticketId = callNext.getVisit().getTicketId();
                    }
                    lblA.setText(ticketId);
                    visit = callNext;
                    flash = true;
                } catch (Exception ee) {
                    lblA.setText(Props.getLangProperty("MainFrame.ErrorCurretServing"));
                    log.error("Failed to data", ee);
                    mv.showMessageDialog(Props.getLangProperty("MainFrame.noWaitingCustText"),
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        lblSettings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                frm.setVisible(true);
            }
        });
        btnClose.addActionListener(arg0 -> {
            try {
                DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
                DTOServicePoint sp = (DTOServicePoint) frm.getCmbServicePoint().getSelectedItem();

                Controller cont = new Controller();
                cont.endSession(lu, branch, sp);
                visit = null;
            } catch (Exception e) {
                log.error("Failed to data", e);
                mv.showMessageDialog();
            }
        });

        btnOpen.addActionListener(arg0 -> {
            DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
            DTOServicePoint sp = (DTOServicePoint) frm.getCmbServicePoint().getSelectedItem();

            Controller cont = new Controller();
            try {
                cont.startSession(lu, branch, sp);
            } catch (Exception e) {
                log.error("Failed to data", e);
                mv.showMessageDialog();
            }
        });

        btnEnd.addActionListener(arg0 -> {
            try {
                if (visit == null) {
                    mv.showMessageDialog(Props.getLangProperty("MainFrame.notCurrentlyServing"),
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (checkIfDSNeeded()) {
                    return;
                }

                DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
                String visitId = visit.getVisit().getTicketId();
                Controller cont = new Controller();
                visit = null;
                lblA.setText(Props.getLangProperty("MainFrame.notServingText"));
                cont.endVisit(lu, branch, visitId);
            } catch (Exception e) {
                log.error("Failed to data", e);
                mv.showMessageDialog();
            }
        });
        btnRecall.addActionListener(arg0 -> {
            try {
                DTOBranch branch = (DTOBranch) getCmbBranch().getSelectedItem();
                DTOServicePoint sp = (DTOServicePoint) frm.getCmbServicePoint().getSelectedItem();

                if (visit == null) {
                    mv.showMessageDialog(Props.getLangProperty("MainFrame.notservingMessage"),
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Controller cont = new Controller();
                DTOUserStatus recall = cont.recall(lu, branch, sp).getValue();
                visit = recall;
                lblA.setText(recall.getVisit().getTicketId());
                flash = true;
            } catch (Exception e) {
                log.error("Failed to data", e);
                mv.showMessageDialog();
            }
        });

    }

    private void createImagesForButtons() {
        try {
            nextImage = ImageIO.read(getClass().getClassLoader().getResource("button-1.png"));
            nextImageClicked = ImageIO.read(getClass().getClassLoader().getResource("button-2.png"));
            lblNextButton.setText("");
            lblNextButton.setIcon(new ImageIcon(nextImage));

            BufferedImage image = ImageIO.read(getClass().getClassLoader().getResource("settings.png"));
            image = Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.AUTOMATIC, 80, 80);
            ImageIcon imageIcon = new ImageIcon(image);
            lblSettings.setText("");
            lblSettings.setIcon(imageIcon);

        } catch (Throwable e) {
            log.error(e);
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void postJbInit() {
        if (Boolean.valueOf(Props.getGlobalProperty(Props.GlobalProperties.SHOW_COUTER_POPUP_EACH_START))) {
            frm.setVisible(true);
        }

        String langProperty = Props.getLangProperty("MainFrame.CurrentlyServingInit");
        String langProperty2 = Props.getLangProperty("MainFrame.callForwardInitText");
        lblA.setText(Boolean.valueOf(Props.getGlobalProperty(Props.GlobalProperties.CALL_FORWARDS)) ? langProperty2
                : langProperty);

        Thread t = new Thread(new WorkstationPanel.Flash());
        t.start();
    }

    private void readFromProperties() {
        try {
            Boolean showCounter = Boolean.valueOf(Props.getGlobalProperty(Props.GlobalProperties.SHOW_COUNTER_OPTIONS));
            pnlCoutner.setVisible(showCounter);
        } catch (Exception ex) {
            log.error(ex);
        }
    }

    public JLabel getLblWorkProfile() {
        return lblWorkProfile;
    }

    public JLabel getLblBranch() {
        return lblBranch;
    }

    public JLabel getLblCounter() {
        return lblCounter;
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
        String[] arrays = {"OUTCOME_FOR_DELIVERED_SERVICE_NEEDED", "OUTCOME_OR_DELIVERED_SERVICE_NEEDED",
                "DELIVERED_SERVICE_NEEDED", "OUTCOME_NEEDED"};
        String visitState = visit.getVisitState().toString();
        for (String state : arrays) {
            if (visitState.equals(state)) {
                mv.showMessageDialog(Props.getLangProperty("MainFrame.DsOutEtNeeded"), JOptionPane.ERROR_MESSAGE);
                return true;
            }
        }
        return false;
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(7, 4, new Insets(0, 0, 0, 0), -1, -1));
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(2, 0, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        lblA = new JLabel();
        Font lblAFont = this.$$$getFont$$$("Tahoma", Font.BOLD, 24, lblA.getFont());
        if (lblAFont != null) lblA.setFont(lblAFont);
        lblA.setText("Not Serving");
        mainPanel.add(lblA, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Currently Serving:");
        mainPanel.add(label1, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pnlVist = new JPanel();
        pnlVist.setLayout(new GridLayoutManager(5, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(pnlVist, new GridConstraints(3, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pnlVist.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        btnEnd = new JButton();
        btnEnd.setText("End");
        pnlVist.add(btnEnd, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(120, -1), new Dimension(120, -1), null, 0, false));
        btnQueueInfo = new JButton();
        btnQueueInfo.setText("Queue Info");
        pnlVist.add(btnQueueInfo, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(120, -1), new Dimension(50, -1), null, 0, false));
        lblNextButton = new JLabel();
        lblNextButton.setHorizontalAlignment(0);
        lblNextButton.setHorizontalTextPosition(0);
        lblNextButton.setText("image");
        pnlVist.add(lblNextButton, new GridConstraints(0, 1, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        pnlSettings = new JPanel();
        pnlSettings.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        pnlVist.add(pnlSettings, new GridConstraints(4, 0, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pnlSettings.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), null));
        lblSettings = new JLabel();
        lblSettings.setHorizontalAlignment(0);
        lblSettings.setHorizontalTextPosition(0);
        lblSettings.setText("image");
        pnlSettings.add(lblSettings, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(3, 1, new Insets(0, 0, 0, 0), -1, -1));
        pnlSettings.add(panel1, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblBranch = new JLabel();
        lblBranch.setText("branch");
        panel1.add(lblBranch, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblCounter = new JLabel();
        lblCounter.setText("coutner");
        panel1.add(lblCounter, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblWorkProfile = new JLabel();
        lblWorkProfile.setText("workP");
        panel1.add(lblWorkProfile, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        pnlSettings.add(spacer2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        pnlSettings.add(spacer3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        btnRecall = new JButton();
        btnRecall.setText("Recall");
        pnlVist.add(btnRecall, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, new Dimension(120, -1), new Dimension(120, -1), null, 0, false));
        final Spacer spacer4 = new Spacer();
        pnlVist.add(spacer4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer5 = new Spacer();
        pnlVist.add(spacer5, new GridConstraints(3, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        mainPanel.add(spacer6, new GridConstraints(6, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(5, 5), new Dimension(5, 5), new Dimension(5, 5), 0, false));
        pnlCoutner = new JPanel();
        pnlCoutner.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(pnlCoutner, new GridConstraints(5, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        pnlCoutner.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), null));
        btnOpen = new JButton();
        btnOpen.setText("Open");
        pnlCoutner.add(btnOpen, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        btnClose = new JButton();
        btnClose.setText("Close");
        pnlCoutner.add(btnClose, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer7 = new Spacer();
        mainPanel.add(spacer7, new GridConstraints(2, 3, 4, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        mainPanel.add(spacer8, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(5, 5), new Dimension(5, 5), new Dimension(5, 5), 0, false));
        final Spacer spacer9 = new Spacer();
        mainPanel.add(spacer9, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    private Font $$$getFont$$$(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

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
