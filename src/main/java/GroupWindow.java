import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import com.opencsv.CSVWriter;

import java.io.FileWriter;

public class GroupWindow extends JFrame {
    private JLabel mainLabel;
    private JPanel commentLabel;
    private JButton addGroup;
    private JButton deleteGroup;
    private JButton editGroup;
    private JTable groupTable;
    private JPanel container;
    private JPanel groupForm;
    private JLabel groupNameLabel;
    private JTextField groupNameInput;
    private JTextField sizeGroupInput;
    private JLabel sizeGroupLabel;
    private JScrollPane GroupScrollPane;
    private JTable studentTable;
    private JScrollPane studentScrollPane;
    private JPanel studentForm;
    private JLabel firstNameLabel;
    private JTextField firstNameInput;
    private JTextField lastNameInput;
    private JComboBox studentConditionInput;
    private JTextField scoresInput;
    private JLabel lastNameLabel;
    private JLabel studentConditionLabel;
    private JLabel scoresLabel;
    private JPanel groupStudentButtons;
    private JButton addStudent;
    private JButton deleteStudentButton;
    private JButton editStudent;
    private JTextField yearOfBirthInput;
    private JLabel yearOfBirthLabel;
    private JTextField _filter_textbox_;
    private JLabel searchInputLabel;
    private JButton importCSVButton;
    private JTextField ratingInput;
    private JTextField commentInput;
    private JLabel ratingLabel;
    private JLabel commentRatingLabel;
    private JButton addRatingsBtn;
    private JFrame frame = new JFrame();
    private Class selectedGroup;
    private int groupSelectedIndex;
    private Student selectedStudent;
    private int studentSelectedIndex;
    private GroupTableModel groupTableModel;
    private StudentTableModel studentTableModel;
    private ModelData modelData;
    private TableRowSorter<GroupTableModel> groupSorter;
    private TableRowSorter<StudentTableModel> studentSorter;
    private List<Double[]> ratingData;
    public GroupWindow(String title)
    {
        super(title);
        ratingData = new ArrayList<Double[]>();
        int idGroupLocal;
        List<Double[]> CountAvglocalTmp = new ArrayList<Double[]>();
        Double[] tmpLocalRatingInfo = new Double[2];
        groupSelectedIndex = -1;
        studentSelectedIndex = -1;
        selectedGroup = new Class();
        modelData = new ModelData();
        modelData.init();
        List<Class> groups = modelData.getGroups();
        loadGroups();
        for(Class group: groups)
        {
           CountAvglocalTmp =  modelData.getAmountAndAverageRatings(modelData.getIdGroup(group.getNameGroup(), true));
           System.out.println(CountAvglocalTmp.get(0)[0] + " " + CountAvglocalTmp.get(0)[1]);
//           tmpLocalRatingInfo[0] = CountAvglocalTmp.get(0)[0];
//            tmpLocalRatingInfo[1] = CountAvglocalTmp.get(0)[1];
            ratingData.add(new Double[]{CountAvglocalTmp.get(0)[0], CountAvglocalTmp.get(0)[1]});
        }
        for(Double[] loc: ratingData)
            System.out.println("Info rating " + loc[0] +", " + loc[1]);
        //List<Student> studentsInGroup = modelData.getStudentsInGroup();
        groupTableModel = new GroupTableModel(modelData.getGroupColumns(), groups, ratingData);
        if(groupSelectedIndex != -1)
            studentTableModel = new StudentTableModel(modelData.getStudentColumns(), modelData.getStudentsInGroup(modelData.getIdGroup(groups.get(groupSelectedIndex).getNameGroup(), true)));//studentsInGroup);
        else
            studentTableModel = new StudentTableModel(modelData.getStudentColumns(), selectedGroup.getStudentList());
            //studentTableModel = new StudentTableModel(modelData.getStudentColumns(), modelData.getStudentsInGroup(groupSelectedIndex));
        groupTable.setModel(groupTableModel);
        studentTable.setModel(studentTableModel);
        groupSorter = new TableRowSorter<GroupTableModel>(groupTableModel);
        groupTable.setRowSorter(groupSorter);

        studentConditionInput.setSelectedIndex(1);
        //studentConditioInput.addActionListener(this);
        //studentConditioInput.setModel(new DefaultComboBoxModel<>(StudentCondition.values()));




        this.setContentPane(container);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        importCSVButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(groupSelectedIndex != -1)
                    saveDataToCSVFile();
                else
                    JOptionPane.showMessageDialog(frame, "Group must be selected first", "Need action", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        _filter_textbox_.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    System.out.println(_filter_textbox_.getText());
                    //nameFilter();
                    matchStudentsByFirstName(_filter_textbox_.getText(), modelData.getIdGroup(selectedGroup.getNameGroup(), true));
                }
            }
        });
        addGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(selectedGroup != null && selectedGroup.getNameGroup().equals(groupNameInput.getText()))
                {
                    JOptionPane.showMessageDialog(frame, "Group exists");
                }
                else
                {
                    if(groupNameInput.getText().equals("") || sizeGroupInput.getText().equals(""))
                        JOptionPane.showMessageDialog(frame, "All fields must be filled");
                    else
                    {
                        Class newGroup = new Class();
                        newGroup.setNameGroup(groupNameInput.getText());
                        newGroup.setMaxListSize(Integer.valueOf(sizeGroupInput.getText()));
                        groups.add(newGroup);
                        groupNameInput.setText("");
                        sizeGroupInput.setText("");
                        modelData.addGroup(newGroup);
                        ratingData = new ArrayList<Double[]>();
                        int idGroupLocal;
                        List<Double[]> CountAvglocalTmp = new ArrayList<Double[]>();
                        Double[] tmpLocalRatingInfo = new Double[2];
                        List<Class> groups = modelData.getGroups();
                        loadGroups();
                        for(Class group: groups)
                        {
                            CountAvglocalTmp =  modelData.getAmountAndAverageRatings(modelData.getIdGroup(group.getNameGroup(), true));
                            System.out.println(CountAvglocalTmp.get(0)[0] + " " + CountAvglocalTmp.get(0)[1]);
//           tmpLocalRatingInfo[0] = CountAvglocalTmp.get(0)[0];
//            tmpLocalRatingInfo[1] = CountAvglocalTmp.get(0)[1];
                            ratingData.add(new Double[]{CountAvglocalTmp.get(0)[0], CountAvglocalTmp.get(0)[1]});
                        }
                        refreshGroupTable(groups);
                        groupTableModel.fireTableDataChanged();
                    }
                }


            }
        });

        addRatingsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(commentInput.getText() != "" && ratingInput.getText() != "")
                {
                    modelData.addRating(Integer.valueOf(ratingInput.getText()), commentInput.getText(), modelData.getIdGroup(selectedGroup.getNameGroup(), true), modelData.getIdStudent(selectedStudent.getFirstName(), selectedStudent.getLastName()));
                    commentInput.setText("");
                    ratingInput.setText("");
                    ratingData = new ArrayList<Double[]>();
                    int idGroupLocal;
                    List<Double[]> CountAvglocalTmp = new ArrayList<Double[]>();
                    Double[] tmpLocalRatingInfo = new Double[2];
                    List<Class> groups = modelData.getGroups();
                    loadGroups();
                    for(Class group: groups)
                    {
                        CountAvglocalTmp =  modelData.getAmountAndAverageRatings(modelData.getIdGroup(group.getNameGroup(), true));
                        System.out.println(CountAvglocalTmp.get(0)[0] + " " + CountAvglocalTmp.get(0)[1]);
//           tmpLocalRatingInfo[0] = CountAvglocalTmp.get(0)[0];
//            tmpLocalRatingInfo[1] = CountAvglocalTmp.get(0)[1];
                        ratingData.add(new Double[]{CountAvglocalTmp.get(0)[0], CountAvglocalTmp.get(0)[1]});
                    }
                    refreshGroupTable(groups);
                    groupTableModel.fireTableDataChanged();
                }
                else
                {
                    JOptionPane.showMessageDialog(frame, "All field must be filled", "Empty Inputs", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        deleteGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(groupSelectedIndex == -1) {
                    JOptionPane.showMessageDialog(frame, "Group must be selected");
                }
                else if(groupSelectedIndex != -1)
                {
                    Class group = groups.get(groupSelectedIndex);
                    if(modelData.getStudentsInGroup(modelData.getIdGroup(groups.get(groupSelectedIndex).getNameGroup(), true)).size() == 0)
                    {
                        modelData.removeRating(modelData.getIdGroup(group.getNameGroup(), true));
                        modelData.clearStudentsListInGroup();
                        refreshStudentTable();

                        modelData.removeGroup(group.getNameGroup());
                        groups.remove(groupSelectedIndex);
                        groupTableModel.fireTableDataChanged();
                        groupNameInput.setText("");
                        sizeGroupInput.setText("");
                        groupSelectedIndex = -1;
                    }
                    else if(JOptionPane.showConfirmDialog(frame, "Are you sure to delete this group? Group contains students") == 0) {
                        List<Student> studentsToDelete = modelData.getStudentsInGroup(modelData.getIdGroup(groups.get(groupSelectedIndex).getNameGroup(), true));
                        modelData.removeRating(modelData.getIdGroup(group.getNameGroup(), true));
                        int id = modelData.getIdGroup(group.getNameGroup(), true);
                        for(Student student: studentsToDelete)
                            modelData.removeStudentFromGroup(student, id);
                        modelData.removeGroup(group.getNameGroup());
                        groups.remove(groupSelectedIndex);
                        groupTableModel.fireTableDataChanged();
                        groupNameInput.setText("");
                        sizeGroupInput.setText("");
                    }


                }
            }
        });
        editGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(groupNameInput.getText().equals("") || sizeGroupInput.getText().equals(""))
                    JOptionPane.showMessageDialog(frame, "All fields must be filled");
                else
                {
                    String oldGroupName = selectedGroup.getNameGroup();
                    selectedGroup.setNameGroup(groupNameInput.getText());
                    selectedGroup.setMaxListSize(Integer.valueOf(sizeGroupInput.getText()));
                    groupNameInput.setText("");
                    sizeGroupInput.setText("");
                    modelData.updateGroup(oldGroupName, selectedGroup);
                    groups.set(groupSelectedIndex, selectedGroup);
                    //refreshGroupTable(modelData, groups);
                    groupTableModel.fireTableDataChanged();
                    groupSelectedIndex = -1;
                }

            }

        });
        addStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //if(firstNameInput.getText().equals("") || lastNameInput.getText().equals("") || studentConditioInput.getToolTipText().equals("") || scoresInput.getText().equals(""))


                if(firstNameInput.getText().equals("") || lastNameInput.getText().equals("") || yearOfBirthInput.getText().equals("") || scoresInput.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled");
                }
                else
                {
                    if(selectedGroup != null)
                    {
                        if(selectedGroup.getStudentList().size() < selectedGroup.getMaxListSize())
                        {
                            Student newStudent = new Student();
                            newStudent.setFirstName(firstNameInput.getText());
                            newStudent.setLastName(lastNameInput.getText());
                            newStudent.setStatus(StudentCondition.DOING);
                    switch((String)studentConditionInput.getSelectedItem()) {
                        case "DOING":
                            newStudent.setStatus(StudentCondition.DOING);
                            break;
                        case "ILL":
                            newStudent.setStatus(StudentCondition.ILL);
                            break;
                        case "ABSENT":
                            newStudent.setStatus(StudentCondition.ABSENT);
                            break;
                    }
                            newStudent.setDateOfBirth(Integer.valueOf(yearOfBirthInput.getText()));
                            newStudent.setScores(Double.valueOf(scoresInput.getText()));
                            modelData.addStudentToGroup(newStudent, modelData.getIdGroup(selectedGroup.getNameGroup(), true));
                            firstNameInput.setText("");
                            lastNameInput.setText("");
                            scoresInput.setText("");
                            yearOfBirthInput.setText("");

                            loadGroups();
                            studentTableModel.fireTableDataChanged();
                            groupTableModel.fireTableDataChanged();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(frame, "Group is full");
                        }

                    }
                    else
                    {
                        JOptionPane.showMessageDialog(frame, "Group is not selected");
                    }

                }
            }
        });
        editStudent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(firstNameInput.getText().equals("") || lastNameInput.getText().equals("") || yearOfBirthInput.getText().equals("") || scoresInput.getText().equals(""))
                {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled");
                }
                else {
                    if (selectedGroup != null && selectedStudent != null) {
                        Student editStudent = new Student();
                        editStudent.setFirstName(firstNameInput.getText());
                        editStudent.setLastName(lastNameInput.getText());
                        editStudent.setStatus(StudentCondition.DOING);
                    switch((String)studentConditionInput.getSelectedItem()) {
                        case "DOING":
                            editStudent.setStatus(StudentCondition.DOING);
                            break;
                        case "ILL":
                            editStudent.setStatus(StudentCondition.ILL);
                            break;
                        case "ABSENT":
                            editStudent.setStatus(StudentCondition.ABSENT);
                            break;
                    }
                        editStudent.setDateOfBirth(Integer.valueOf(yearOfBirthInput.getText()));
                        editStudent.setScores(Double.valueOf(scoresInput.getText()));
                        modelData.updateStudent(studentSelectedIndex, editStudent);
                        firstNameInput.setText("");
                        lastNameInput.setText("");
                        scoresInput.setText("");
                        yearOfBirthInput.setText("");

                        studentTableModel.fireTableDataChanged();
                        refreshStudentTable();

                    } else {
                        JOptionPane.showMessageDialog(frame, "Group or student is not selected");
                    }
                }
            }
        });
        deleteStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(studentSelectedIndex == -1)
                {
                    JOptionPane.showMessageDialog(frame, "Student must be selected");
                }
                else if(studentSelectedIndex != -1)
                {
                    Student student = modelData.getStudentsInGroup(modelData.getIdGroup(selectedGroup.getNameGroup(), true)).get(studentSelectedIndex);
                    modelData.removeStudentFromGroup(student, modelData.getIdGroup(selectedGroup.getNameGroup(), true));
                    //modelData.getStudentsInGroup(modelData.getIdGroup(selectedGroup.getNameGroup(), true)).remove(studentSelectedIndex);
                    refreshStudentTable();
                    //refreshGroupTable();
                    studentTableModel.fireTableDataChanged();
                    groupTableModel.fireTableDataChanged();
                    loadGroups();
                    firstNameInput.setText("");
                    lastNameInput.setText("");
                    scoresInput.setText("");
                    yearOfBirthInput.setText("");
                    studentSelectedIndex = -1;
                }
            }
        });

        groupTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!groupTable.getSelectionModel().isSelectionEmpty()) {
                    groupSelectedIndex = groupTable.convertRowIndexToModel(groupTable.getSelectedRow());
                    selectedGroup = groups.get(groupSelectedIndex);
                    selectedGroup = modelData.getGroupClassByName(selectedGroup.getNameGroup());
                    modelData.setStudentsInGroup(selectedGroup.getStudentList());
                    System.out.println(selectedGroup.getNameGroup() + " " + selectedGroup.getMaxListSize());
                    List<Student> list = selectedGroup.getStudentList();

                    for(Student st: list)
                    {
                        System.out.println(st.getFirstName() + st.getLastName());
                    }
                    refreshStudentTable();
                    if(selectedGroup != null) {
                        setDataGroup(selectedGroup);
                    }
                }
            }
        });

        studentTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!studentTable.getSelectionModel().isSelectionEmpty()) {
                    studentSelectedIndex = studentTable.convertRowIndexToModel(studentTable.getSelectedRow());
                   System.out.println(studentSelectedIndex);
                    selectedStudent = modelData.getStudentsInGroup(modelData.getIdGroup(groups.get(groupSelectedIndex).getNameGroup(), true)).get(studentSelectedIndex);
                    if(selectedStudent != null) {
                        setDataStudent(selectedStudent);
                    }
                }
            }
        });

    }

    private void loadGroups()
    {
        List<Class> groups = modelData.getGroups();
        int idGroupLocal;
        for(Class group: groups)
        {
            idGroupLocal = modelData.getIdGroup(group.getNameGroup(), true);
            group.setStudentList(modelData.getStudentsInGroup(idGroupLocal));
        }
    }

    //Search by name
    private void nameFilter() {
        RowFilter<StudentTableModel, Object> rowFilter = null;
        try {
            rowFilter = RowFilter.regexFilter(_filter_textbox_.getText(), 0);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        studentSorter.setRowFilter(rowFilter);
    }

    private void matchStudentsByFirstName(String firstName, int idGroup)
    {
        List<Student> matchStudentsList = modelData.findStudentsInGroupByName(firstName, idGroup);
        studentTable.setRowSorter(null);
        //if(!modelData.getStudentsInGroup(groupSelectedIndex).isEmpty())
        //if(!modelData.getStudentsInGroup(modelData.getIdGroup(selectedGroup.getNameGroup(), true)).isEmpty())
        if(!matchStudentsList.isEmpty())
        {
            studentTableModel = new StudentTableModel(modelData.getStudentColumns(), matchStudentsList);
            //studentTableModel = new StudentTableModel(modelData.getStudentColumns(), selectedGroup.getStudentList());
            studentTable.setModel(studentTableModel);
            studentSorter = new TableRowSorter<StudentTableModel>(studentTableModel);
            studentTable.setRowSorter(studentSorter);
        }
        else
        {
            studentTableModel = new StudentTableModel(modelData.getStudentColumns(), matchStudentsList);
            studentTable.setModel(studentTableModel);
        }

    }

    public void setDataGroup(Class data) {
        groupNameInput.setText(data.getNameGroup());
        sizeGroupInput.setText(String.valueOf(data.getMaxListSize()));
    }

    public void setDataStudent(Student data)
    {
        firstNameInput.setText(data.getFirstName());
        lastNameInput.setText(data.getLastName());
        scoresInput.setText(String.valueOf(data.getScores()));
        yearOfBirthInput.setText(String.valueOf(data.getDateOfBirth()));
    }

    public void refreshStudentTable()
    {
        studentTable.setRowSorter(null);
        //if(!modelData.getStudentsInGroup(groupSelectedIndex).isEmpty())
        if(!modelData.getStudentsInGroup(modelData.getIdGroup(selectedGroup.getNameGroup(), true)).isEmpty())
        {
            studentTableModel = new StudentTableModel(modelData.getStudentColumns(), modelData.getStudentsInGroup(modelData.getIdGroup(selectedGroup.getNameGroup(), true)));
            //studentTableModel = new StudentTableModel(modelData.getStudentColumns(), selectedGroup.getStudentList());
            studentTable.setModel(studentTableModel);
            studentSorter = new TableRowSorter<StudentTableModel>(studentTableModel);
            studentTable.setRowSorter(studentSorter);
        }
        else
        {
            studentTableModel = new StudentTableModel(modelData.getStudentColumns(), modelData.getStudentsInGroup(modelData.getIdGroup(selectedGroup.getNameGroup(), true)));
            studentTable.setModel(studentTableModel);
        }

    }

    public void refreshGroupTable( List<Class> groups)
    {
        GroupTableModel groupTableModel = new GroupTableModel(modelData.getGroupColumns(), groups, ratingData);
        groupTable.setModel(groupTableModel);
    }

    public void getData(Class data) {
        data.setNameGroup(groupNameInput.getText());
    }

    public boolean isModified(Class data) {
        if (groupNameInput.getText() != null ? !groupNameInput.getText().equals(data.getNameGroup()) : data.getNameGroup() != null)
            return true;
        return false;
    }

    public void saveDataToCSVFile()
    {
        List<Student> data = modelData.getStudentsInGroup(modelData.getIdGroup(selectedGroup.getNameGroup(), true));
        List<String[]> csvData = new ArrayList<String[]>();
        for(Student student: data)
        {
            String[] tmp = {student.getFirstName(), student.getLastName(), String.valueOf(student.getStatusName()), String.valueOf(student.getDateOfBirth()), String.valueOf(student.getScores())};
            csvData.add(tmp);
        }
        try (CSVWriter writer = new CSVWriter(new FileWriter("./" + selectedGroup.getNameGroup() + ".csv"))) {
            writer.writeAll(csvData);
        } catch(Exception e)
        {
            JOptionPane.showMessageDialog(frame, "Import to csv file failed", "Problem occur", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args)
    {

        GroupWindow window = new GroupWindow("Student's Group");
        window.setVisible(true);


    }

}
