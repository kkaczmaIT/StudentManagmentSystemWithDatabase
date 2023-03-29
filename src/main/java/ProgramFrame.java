import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class ProgramFrame {
    private String frameName;
    private String[][] userData;
    private String[] dataLabels;
    private String mainLabel;
    ProgramFrame(String frameName, String mainLabel,String[][] userData, String[] dataLabels) {
        this.frameName = frameName;
        this.mainLabel = mainLabel;
        this.userData = userData;
        this.dataLabels = dataLabels;
    }

    public void initFrame()
    {
        JFrame frame = new JFrame(frameName);
        //JPanel heading = new JPanel();
        JPanel groupTable = new JPanel();
        JPanel studentTable = new JPanel();
        JPanel actions = new JPanel();
        //JLabel title = new JLabel(this.mainLabel);
        JButton openGroup = new JButton("Otwórz grupę");
        JButton createGroup = new JButton("Nowa grupa");
        JButton editGroup = new JButton("Edytuj grupę");
        JButton removeGroup = new JButton("Usuń grupę");

        JTable groups = new JTable(this.userData, this.dataLabels);
        JScrollPane groupSp = new JScrollPane(groups);
        actions.setLayout(new GridLayout(1, 4));
        //heading.add(title);
        actions.add(openGroup);
        actions.add(createGroup);
        actions.add(editGroup);
        actions.add(removeGroup);
        groupTable.add(groupSp);

        frame.setLayout(new GridLayout(3, 2));

        //frame.add(heading);
        frame.add(actions);
        frame.add(groupTable);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700, 560);
        frame.setVisible(true);
    }
}
