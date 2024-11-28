package com.ca.ui.panels;

import com.ca.db.model.BranchOffice;
import com.ca.db.service.DBUtils;
import com.gt.common.constants.Status;
import com.gt.common.utils.UIUtils;
import com.gt.uilib.components.AbstractFunctionPanel;
import com.gt.uilib.components.input.GTextArea;
import com.gt.uilib.components.table.BetterJTable;
import com.gt.uilib.components.table.EasyTableModel;
import com.gt.uilib.inputverifier.Validator;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import org.apache.commons.lang3.SystemUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.List;

public class BranchOfficePanel extends AbstractFunctionPanel {
    private final String[] header = new String[]{"S.N.", "ID", "Name", "Address", "District", "PhoneNumber"};
    private JPanel formPanel = null;
    private JPanel buttonPanel;
    private Validator v;
    private JTextField nameFLD;
    private JTextField phoneNumberFLD;
    private GTextArea addressFLD;
    private JButton btnReadAll;
    private JButton btnNew;
    private JButton btnSave;
    private JPanel upperPane;
    private JPanel lowerPane;
    private BetterJTable table;
    private EasyTableModel dataModel;
    private int editingPrimaryId = 0;
    private JButton btnCancel;
    private JTextField txtDistrict;

    public BranchOfficePanel() {
        /*
          all gui components added from here;
         */
    	setBackground(new Color(210, 189, 252));
        JSplitPane splitPane = new JSplitPane();
        splitPane.setContinuousLayout(true);
        splitPane.setResizeWeight(0.2);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        add(splitPane, BorderLayout.CENTER);
        splitPane.setLeftComponent(getUpperSplitPane());
        splitPane.setRightComponent(getLowerSplitPane());
        Font buttonFont = new Font("Arial", Font.BOLD, 12);
        btnReadAll.setFont(buttonFont);
        btnNew.setFont(buttonFont);
        btnSave.setFont(buttonFont);
        btnCancel.setFont(buttonFont);
        
        btnReadAll.setPreferredSize(new Dimension(120, 40));
        btnNew.setPreferredSize(new Dimension(120, 40));
        btnSave.setPreferredSize(new Dimension(120, 40));
        btnCancel.setPreferredSize(new Dimension(120, 40));

        btnReadAll.setBackground(new Color(70, 130, 180));  // Steel Blue
        btnNew.setBackground(new Color(34, 139, 34));  // Forest Green
        btnSave.setBackground(new Color(0, 191, 255));  // Deep Sky Blue
        btnCancel.setBackground(new Color(255, 69, 0));  // Red-Orange
        
        btnReadAll.setForeground(Color.BLACK);
        btnNew.setForeground(Color.BLUE);
        btnSave.setForeground(Color.BLACK);
        btnCancel.setForeground(Color.BLACK);
        /*
          never forget to call after setting up UI
         */
        init();
    }

    public static void main(String[] args) throws Exception {
        if (SystemUtils.IS_OS_WINDOWS) {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        EventQueue.invokeLater(() -> {
            try {
                JFrame jf = new JFrame();
                BranchOfficePanel panel = new BranchOfficePanel();
                jf.setBounds(panel.getBounds());
                jf.getContentPane().add(panel);
                jf.setVisible(true);
                jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public final void init() {
        /* never forget to call super.init() */
        super.init();
        UIUtils.clearAllFields(upperPane);
        changeStatus(Status.NONE);
    }

    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            btnReadAll = new JButton("Read All");
            btnReadAll.addActionListener(e -> {
                readAndShowAll(true);
                changeStatus(Status.READ);
            });
            buttonPanel.add(btnReadAll);

            btnNew = new JButton("New");
            btnNew.addActionListener(e -> changeStatus(Status.CREATE));
            buttonPanel.add(btnNew);

            JButton btnDeleteThis = new JButton("Delete This");
            btnDeleteThis.setForeground(Color.BLACK);
            btnDeleteThis.setBackground(new Color(255, 69, 0));
            btnDeleteThis.setPreferredSize(new Dimension(120, 40));
            btnDeleteThis.setFont(new Font("Arial", Font.BOLD, 12));
            btnDeleteThis.addActionListener(e -> {
                if (editingPrimaryId > 0)
                    handleDeleteAction();
            });

            JButton btnModify = new JButton("Modify");
            btnModify.setForeground(Color.BLACK);
            btnModify.setBackground(new Color(255, 69, 0));
            btnModify.setPreferredSize(new Dimension(120, 40));
            btnModify.setFont(new Font("Arial", Font.BOLD, 12));
            btnModify.addActionListener(e -> {
                if (editingPrimaryId > 0)
                    changeStatus(Status.MODIFY);
            });
            buttonPanel.add(btnModify);
            buttonPanel.add(btnDeleteThis);

            btnCancel = new JButton("Cancel");
            btnCancel.addActionListener(e -> changeStatus(Status.READ));
            buttonPanel.add(btnCancel);
        }
        return buttonPanel;
    }

    private void handleDeleteAction() {
        if (status == Status.READ) {
            deleteSelectedBranchOffice();
        }

    }

    private void deleteSelectedBranchOffice() {
        try {
            DBUtils.deleteById(BranchOffice.class, editingPrimaryId);
            changeStatus(Status.READ);
            JOptionPane.showMessageDialog(null, "Deleted");
            readAndShowAll(false);
        } catch (Exception e) {
            handleDBError(e);
        }
    }

    @Override
    public final void enableDisableComponents() {
        switch (status) {
            case NONE:
                UIUtils.toggleAllChildren(buttonPanel, false);
                UIUtils.toggleAllChildren(formPanel, false);
                UIUtils.clearAllFields(formPanel);
                btnReadAll.setEnabled(true);
                btnNew.setEnabled(true);
                table.setEnabled(true);
                break;
            case CREATE:
                UIUtils.toggleAllChildren(buttonPanel, false);
                UIUtils.toggleAllChildren(formPanel, true);
                table.setEnabled(false);
                btnCancel.setEnabled(true);
                btnSave.setEnabled(true);
                break;
            case MODIFY:
                UIUtils.toggleAllChildren(formPanel, true);
                UIUtils.toggleAllChildren(buttonPanel, false);
                btnCancel.setEnabled(true);
                btnSave.setEnabled(true);
                table.setEnabled(false);
                break;

            case READ:
                UIUtils.toggleAllChildren(formPanel, false);
                UIUtils.toggleAllChildren(buttonPanel, true);
                UIUtils.clearAllFields(formPanel);
                table.clearSelection();
                table.setEnabled(true);
                editingPrimaryId = -1;
                btnCancel.setEnabled(false);
                break;

            default:
                break;
        }
    }

    @Override
    public final void handleSaveAction() {
        switch (status) {
            case CREATE:
                // create new
                save(false);
                break;
            case MODIFY:
                // modify
                save(true);
                break;

            default:
                break;
        }
    }

    private void initValidator() {

        if (v != null) {
            v.resetErrors();
        }

        v = new Validator(mainApp, true);
        v.addTask(nameFLD, "Req", null, true);
    }

    private BranchOffice getModelFromForm() {
        BranchOffice bo = new BranchOffice();
        bo.setName(nameFLD.getText().trim());
        bo.setAddress(addressFLD.getText().trim());
        bo.setPhoneNumber(phoneNumberFLD.getText().trim());
        bo.setDistrict(txtDistrict.getText().trim());
        bo.setdFlag(1);
        return bo;
    }

    private void setModelIntoForm(BranchOffice bro) {
        nameFLD.setText(bro.getName());
        addressFLD.setText(bro.getAddress());
        phoneNumberFLD.setText(bro.getPhoneNumber());
        txtDistrict.setText(bro.getDistrict());
    }

    private void save(boolean isModified) {
        initValidator();
        if (v.validate()) {
            try {

                BranchOffice newBo = getModelFromForm();
                if (isModified) {
                    BranchOffice bo = (BranchOffice) DBUtils.getById(BranchOffice.class, editingPrimaryId);
                    bo.setAddress(newBo.getAddress());
                    bo.setName(newBo.getName());
                    bo.setPhoneNumber(newBo.getPhoneNumber());
                    bo.setDistrict(newBo.getDistrict());
                    DBUtils.saveOrUpdate(bo);
                } else {
                    DBUtils.saveOrUpdate(newBo);
                }
                JOptionPane.showMessageDialog(null, "Saved Successfully");
                changeStatus(Status.READ);
                UIUtils.clearAllFields(upperPane);
                readAndShowAll(false);
            } catch (Exception e) {
                handleDBError(e);
            }
        }
    }

    private JPanel getUpperFormPanel() {
        if (formPanel == null) {
            formPanel = new JPanel();
            formPanel.setBackground(new Color(230, 240, 255));

           // formPanel.setBorder(new TitledBorder(null, "Branch Office Information", TitledBorder.LEADING, TitledBorder.TOP, null, null));
            formPanel.setBorder(new TitledBorder(
                    null,
                    "Branch Office Information",
                    TitledBorder.LEADING,
                    TitledBorder.TOP,
                    new Font("Tahoma", Font.BOLD, 16), // Font for title
                    Color.DARK_GRAY
                ));
            formPanel.setBounds(10, 49, 474, 135);
            formPanel.setLayout(new FormLayout(new ColumnSpec[]{
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    FormFactory.DEFAULT_COLSPEC,
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:default"),
                    FormFactory.RELATED_GAP_COLSPEC,
                    ColumnSpec.decode("left:default"),},
                    new RowSpec[]{
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,
                            FormFactory.RELATED_GAP_ROWSPEC,
                            FormFactory.DEFAULT_ROWSPEC,}));

            Font labelFont = new Font("Arial", Font.BOLD, 14);
            JLabel lblN = new JLabel("Name");
            lblN.setFont(labelFont);
            formPanel.add(lblN, "4, 2");

            nameFLD = new JTextField();
            nameFLD.setFont(new Font("Courier New", Font.PLAIN, 14));
            formPanel.add(nameFLD, "8, 2, fill, default");
            nameFLD.setColumns(10);

            JLabel lblAddress = new JLabel("Address");
            lblAddress.setFont(labelFont);
            formPanel.add(lblAddress, "4, 4, default, top");

            addressFLD = new GTextArea(5, 30);
            formPanel.add(addressFLD, "8, 4, fill, fill");

            JLabel lblDistrict = new JLabel("District");
            lblDistrict.setFont(labelFont);
            formPanel.add(lblDistrict, "4, 6");

            txtDistrict = new JTextField();
            formPanel.add(txtDistrict, "8, 6, fill, default");
            txtDistrict.setColumns(10);

            JLabel lblPhoneNumber = new JLabel("Phone Number");
            lblPhoneNumber.setFont(labelFont);
            formPanel.add(lblPhoneNumber, "4, 8");

            phoneNumberFLD = new JTextField();
            formPanel.add(phoneNumberFLD, "8, 8, fill, default");
            phoneNumberFLD.setColumns(10);

            btnSave = new JButton("Save");
            btnSave.setFont(new Font("Verdana", Font.BOLD, 18));
            btnSave.setPreferredSize(new Dimension(150,40));
            btnSave.addActionListener(e -> {
                btnSave.setEnabled(false);
                handleSaveAction();
                btnSave.setEnabled(true);
            });
            formPanel.add(btnSave, "10, 8");
        }
        return formPanel;
    }

    private void readAndShowAll(boolean showSize0Error) {
        try {
            List<BranchOffice> brsL = DBUtils.readAll(BranchOffice.class);
            editingPrimaryId = -1;
            if (brsL == null || brsL.size() == 0) {
                if (showSize0Error) {
                    JOptionPane.showMessageDialog(null, "No Records Found");
                }
            }
            showBranchOfficesInGrid(brsL);
        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    private void showBranchOfficesInGrid(List<BranchOffice> brsL) {
        dataModel.resetModel();
        int sn = 0;
        for (BranchOffice bo : brsL) {
            dataModel.addRow(new Object[]{++sn, bo.getId(), bo.getName(), bo.getAddress(), bo.getDistrict(), bo.getPhoneNumber()});
        }
        table.setModel(dataModel);
        dataModel.fireTableDataChanged();
        table.adjustColumns();
        editingPrimaryId = -1;
    }

    @Override
    public final String getFunctionName() {
        return "Branch-Office Information";
    }

    private JPanel getUpperSplitPane() {
        if (upperPane == null) {
            upperPane = new JPanel();
            upperPane.setBackground(new Color(200, 255, 200));
            upperPane.setLayout(new BorderLayout(0, 0));
            upperPane.add(getUpperFormPanel(), BorderLayout.CENTER);
            upperPane.add(getButtonPanel(), BorderLayout.SOUTH);
        }
        return upperPane;
    }

    private JPanel getLowerSplitPane() {
        if (lowerPane == null) {
            lowerPane = new JPanel();
            lowerPane.setBackground(new Color(250, 250, 210));
            lowerPane.setLayout(new BorderLayout());
            dataModel = new EasyTableModel(header);

            table = new BetterJTable(dataModel);
            table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            lowerPane.add(sp, BorderLayout.CENTER);
            table.getSelectionModel().addListSelectionListener(e -> {
                int selRow = table.getSelectedRow();
                if (selRow != -1) {
                    /*
                      if second column doesnot have primary id info, then
                     */
                    int selectedId = (Integer) dataModel.getValueAt(selRow, 1);
                    populateSelectedRowInForm(selectedId);
                }
            });
        }
        return lowerPane;
    }

    private void populateSelectedRowInForm(int selectedId) {
        try {
            BranchOffice bro = (BranchOffice) DBUtils.getById(BranchOffice.class, selectedId);
            if (bro != null) {
                setModelIntoForm(bro);
                editingPrimaryId = bro.getId();
            }
        } catch (Exception e) {
            handleDBError(e);
        }

    }

}