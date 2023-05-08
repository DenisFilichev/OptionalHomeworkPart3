package mainpack;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.HashSet;
import java.util.Set;

public class StudentListTableModel implements TableModel {
    private static final String[] COLUMN_HEADERS = new String[]{
            "id",
            "name",
            "grades"
    };

    private final Set<TableModelListener> modelListeners = new HashSet<>();

    @Override
    public int getColumnCount() {
        return COLUMN_HEADERS.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0:
                return Byte.class;
            case 1:
                return Student.class;
            case 2:
                return String.class;
        }
        throw new IllegalArgumentException("unknown columnIndex");
    }

    @Override
    public String getColumnName(int columnIndex) {
        return COLUMN_HEADERS[columnIndex];
    }

    @Override
    public int getRowCount() {
        return StudentList.getInstance().getStudentCount();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = StudentList.getInstance().getStudent(rowIndex);
        switch(columnIndex) {
            case 0: return student.getId();
            case 1: return student.getName();
            case 2: return student.getGrades();
        }
        throw new IllegalArgumentException("unknown columnIndex");
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        /* Nothing to do, since isCellEditable is always false. */
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        modelListeners.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        modelListeners.remove(l);
    }

    public Student getStudent(int rowNdx) {
        return StudentList.getInstance().getStudent(rowNdx);
    }

    public void addStudent(String studentName, Byte...grades) {
        StudentList.getInstance().addStudent(studentName, grades);
        int rowNdx = StudentList.getInstance().getStudentCount() - 1;
        fireTableModelEvent(rowNdx, TableModelEvent.INSERT);
    }

    public void studentChanged(int index, Student student) {
        StudentList.getInstance().changeStudent(student);
        fireTableModelEvent(index, TableModelEvent.UPDATE);
    }

    public void dropStudent(int index) {
        StudentList.getInstance().remove(index);
        fireTableModelEvent(index, TableModelEvent.DELETE);
    }

    private void fireTableModelEvent(int rowNdx, int evtType) {
        TableModelEvent tme = new TableModelEvent(this, rowNdx, rowNdx,
                TableModelEvent.ALL_COLUMNS, evtType);
        for (TableModelListener l : modelListeners) {
            l.tableChanged(tme);
        }
    }
}
