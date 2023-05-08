package mainpack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.stream.Collectors;

public class StudentDialog extends JDialog {
    private final JTextField nameField;
    private final JTextField gradesField;
    private boolean okPressed;

    public StudentDialog(JFrame owner) {
        super(owner, true);
        nameField = new JTextField(30);
        gradesField = new JTextField(30);
        initLayout();
        setResizable(false);
    }

    private void initLayout() {
        initControls();
        initOkCancelButtons();
    }

    private void initControls() {
        JPanel controlsPane = new JPanel(null);
        controlsPane.setLayout(new BoxLayout(controlsPane, BoxLayout.Y_AXIS));

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel("Student name");
        lbl.setLabelFor(nameField);
        p.add(lbl);
        p.add(nameField);
        controlsPane.add(p);

        p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lbl = new JLabel("Customer grades (\"5, 5, 4, ...\")");
        lbl.setLabelFor(gradesField);
        p.add(lbl);
        p.add(gradesField);
        controlsPane.add(p);

        add(controlsPane, BorderLayout.CENTER);
    }

    private void initOkCancelButtons() {
        JPanel btnsPane = new JPanel();

        JButton okBtn = new JButton("OK");
        okBtn.addActionListener(e -> {
            okPressed = true;
            setVisible(false);
        });
        okBtn.setDefaultCapable(true);
        btnsPane.add(okBtn);

        Action cancelDialogAction = new AbstractAction("Cancel") {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        };

        JButton cancelBtn = new JButton(cancelDialogAction);
        btnsPane.add(cancelBtn);

        add(btnsPane, BorderLayout.SOUTH);

        final String esc = "escape";
        getRootPane()
                .getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), esc);
        getRootPane()
                .getActionMap()
                .put(esc, cancelDialogAction);
    }

    public boolean showModal() {
        pack();
        setLocationRelativeTo(getOwner());
        if(nameField.isEnabled())
            nameField.requestFocusInWindow();
        okPressed = false;
        setVisible(true);
        return okPressed;
    }

    public void prepareForAdd() {
        setTitle("New student registration");

        nameField.setText("");
        gradesField.setText("");

        nameField.setEnabled(true);
        gradesField.setEnabled(true);
    }

    public void prepareForChange(Student student) {
        setTitle("Student properties change");

        nameField.setText(student.getName());
        java.util.List<Byte> list = student.getGrades();
        gradesField.setText(list.stream().map(e -> Byte.toString(e)).collect(Collectors.joining(", ")));

        nameField.setEnabled(false);
        gradesField.setEnabled(true);
    }

    public Byte[] getGrades() {
        String text = gradesField.getText().replaceAll(" ", "");
        if(text.matches("(?:[1-5])||(?:[1-5],)+||(?:([1-5],)+[1-5])")){
            String[]lines = text.split(",");
            Byte[] grades = new Byte[lines.length];
            for(int i=0 ; i<lines.length ; i++){
                grades[i] = Byte.parseByte(lines[i]);
            }
            return grades;
        }
        else {
            throw new IllegalArgumentException("String may appear numbers and commas");
        }
    }

    public String getStudentName() {
        return nameField.getText();
    }
}
