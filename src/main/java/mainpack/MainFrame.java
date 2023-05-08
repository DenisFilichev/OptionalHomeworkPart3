package mainpack;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.util.Arrays;


public class MainFrame extends JFrame {
    private final StudentListTableModel studentTableModel = new StudentListTableModel();
    private final JTable studentTable = new JTable();

    private final StudentDialog studentDialog = new StudentDialog(this);

    public MainFrame() {
        super("Denis Ltd. student grades");

        initMenu();
        initLayout();

        setBounds(300, 200, 600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void initMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu operations = new JMenu("Operations");
        operations.setMnemonic('O');
        menuBar.add(operations);

        addMenuItemTo(operations, "Add student", 'A',
                KeyStroke.getKeyStroke('A', InputEvent.ALT_DOWN_MASK),
                e -> addStudent());

        addMenuItemTo(operations, "Change student grades", 'C',
                KeyStroke.getKeyStroke('C', InputEvent.ALT_DOWN_MASK),
                e -> changeStudent());

        addMenuItemTo(operations, "Delete student", 'D',
                KeyStroke.getKeyStroke('D', InputEvent.ALT_DOWN_MASK),
                e -> delStudent());

        setJMenuBar(menuBar);
    }

    private void addMenuItemTo(JMenu parent, String text, char mnemonic,
                               KeyStroke accelerator, ActionListener al) {
        JMenuItem mi = new JMenuItem(text, mnemonic);
        mi.setAccelerator(accelerator);
        mi.addActionListener(al);
        parent.add(mi);
    }

    private void initLayout() {
        studentTable.setModel(studentTableModel);
        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(studentTable.getTableHeader(), BorderLayout.NORTH);
        add(new JScrollPane(studentTable), BorderLayout.CENTER);
    }

    private void addStudent() {
        studentDialog.prepareForAdd();
        while(studentDialog.showModal()) {
            try {
                studentTableModel.addStudent(studentDialog.getStudentName(), studentDialog.getGrades());
                return;
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Student registration error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void changeStudent() {
        int seldRow = studentTable.getSelectedRow();
        if(seldRow == -1)
            return;

        Student student = studentTableModel.getStudent(seldRow);
        studentDialog.prepareForChange(student);
        if(studentDialog.showModal()) {
            try {
                student.setGrades(Arrays.asList(studentDialog.getGrades()));
                studentTableModel.studentChanged(seldRow, student);
            } catch(Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Student change error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void delStudent() {
        int seldRow = studentTable.getSelectedRow();
        if(seldRow == -1)
            return;

        Student student = studentTableModel.getStudent(seldRow);
        if(JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete student\n"
                        + "with phone number " + student.getName() + "?",
                "Delete confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
            studentTableModel.dropStudent(seldRow);
        }
    }
}
