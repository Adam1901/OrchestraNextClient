package views;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import io.github.openunirest.http.exceptions.UnirestException;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

public class ReceptionPanel {
    private final static Logger log = LogManager.getLogger(ReceptionPanel.class);
    DTOVisit visit = null;
    private MainView mv;
    private LoginUser lu;
    private JPanel mainPanel;
    private JButton createVisitButton;
    private JComboBox cmbServices;
    private JLabel lblImage;
    private JPanel settings;
    private JLabel lblEntryPoint;
    private JLabel lblBranch;
    private JPanel pnlExtra;
    private JLabel lblA;
    private ReceptionSelectionFrame frm;

    public ReceptionPanel(LoginUser lu, MainView mv) {
        this.lu = lu;
        this.mv = mv;
        frm = new ReceptionSelectionFrame(this.lu, this, this.mv);
        settings.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
                Props.getLangProperty("MainFrame.SettingBorderText"), TitledBorder.LEADING, TitledBorder.TOP, null,
                new Color(0, 0, 0)));
        pnlExtra.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null),
                "TBC", TitledBorder.LEADING, TitledBorder.TOP, null,
                new Color(0, 0, 0)));

        BufferedImage image;
        try {
            image = ImageIO.read(getClass().getClassLoader().getResource("settings.png"));
            image = Scalr.resize(image, Scalr.Method.SPEED, Scalr.Mode.AUTOMATIC, 80, 80);
            ImageIcon imageIcon = new ImageIcon(image);
            lblImage.setText("");
            lblImage.setIcon(imageIcon);
        } catch (IOException e) {
            log.error(e);
        }

        lblImage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                frm.setVisible(true);
            }
        });

        createVisitButton.addActionListener(e -> {
            Controller cont = new Controller();
            try {
                DTOBranch branch = (DTOBranch) frm.getCmbBranch().getSelectedItem();
                DTOEntryPoint ep = (DTOEntryPoint) frm.getCmbEntryPoints().getSelectedItem();
                DTOService service = (DTOService) cmbServices.getSelectedItem();
                visit = cont.createVisit(lu, branch, ep, service).getValue();
                lblA.setText(visit.getTicketId());
            } catch (UnirestException ee) {
                ee.printStackTrace();
            }
        });
    }

    public void fillCombo(List<DTOService> services) {
        cmbServices.removeAllItems();
        for (DTOService dtoService : services) {
            cmbServices.addItem(dtoService);
        }

    }

    public JLabel getLblBranch() {
        return lblBranch;
    }

    public JLabel getLblEntryPoint() {
        return lblEntryPoint;
    }

    public JPanel getMainPanel() {
        return mainPanel;
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
        mainPanel.setLayout(new GridLayoutManager(8, 5, new Insets(0, 0, 0, 0), -1, -1));
        lblA = new JLabel();
        Font lblAFont = this.$$$getFont$$$("Tahoma", Font.BOLD, 24, lblA.getFont());
        if (lblAFont != null) lblA.setFont(lblAFont);
        lblA.setText("Not Serving");
        mainPanel.add(lblA, new GridConstraints(1, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        cmbServices = new JComboBox();
        mainPanel.add(cmbServices, new GridConstraints(2, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        settings = new JPanel();
        settings.setLayout(new GridLayoutManager(3, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(settings, new GridConstraints(4, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, 1, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(79, 62), null, 0, false));
        settings.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Settings", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, this.$$$getFont$$$(null, -1, -1, settings.getFont()), new Color(-16777216)));
        final Spacer spacer1 = new Spacer();
        settings.add(spacer1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        lblBranch = new JLabel();
        lblBranch.setHorizontalAlignment(0);
        lblBranch.setHorizontalTextPosition(0);
        lblBranch.setText("A");
        settings.add(lblBranch, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        lblEntryPoint = new JLabel();
        lblEntryPoint.setHorizontalAlignment(0);
        lblEntryPoint.setHorizontalTextPosition(0);
        lblEntryPoint.setText("AA");
        settings.add(lblEntryPoint, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        settings.add(spacer2, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        lblImage = new JLabel();
        lblImage.setHorizontalAlignment(0);
        lblImage.setHorizontalTextPosition(0);
        lblImage.setText("image");
        settings.add(lblImage, new GridConstraints(0, 0, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        mainPanel.add(spacer3, new GridConstraints(1, 3, 3, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 30), new Dimension(-1, 30), 0, false));
        createVisitButton = new JButton();
        createVisitButton.setText("Create Visit");
        mainPanel.add(createVisitButton, new GridConstraints(3, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        mainPanel.add(spacer4, new GridConstraints(6, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(5, -1), new Dimension(5, -1), new Dimension(5, -1), 0, false));
        final Spacer spacer5 = new Spacer();
        mainPanel.add(spacer5, new GridConstraints(6, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final Spacer spacer6 = new Spacer();
        mainPanel.add(spacer6, new GridConstraints(0, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, new Dimension(-1, 5), new Dimension(-1, 5), new Dimension(-1, 5), 0, false));
        final Spacer spacer7 = new Spacer();
        mainPanel.add(spacer7, new GridConstraints(7, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer8 = new Spacer();
        mainPanel.add(spacer8, new GridConstraints(6, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_FIXED, 1, new Dimension(5, -1), new Dimension(5, -1), new Dimension(5, -1), 0, false));
        pnlExtra = new JPanel();
        pnlExtra.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(pnlExtra, new GridConstraints(5, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        final Spacer spacer9 = new Spacer();
        mainPanel.add(spacer9, new GridConstraints(5, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, new Dimension(-1, 50), null, 0, false));
        final JLabel label1 = new JLabel();
        label1.setText("Service:");
        mainPanel.add(label1, new GridConstraints(2, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
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
}
