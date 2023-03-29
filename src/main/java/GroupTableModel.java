import javax.swing.table.AbstractTableModel;
import java.util.List;


public class GroupTableModel extends AbstractTableModel {
    private List<String> columns;
    private List<Class> data;
    private List<Double[]> ratingsResult;

    protected GroupTableModel(List<String> columns, List<Class> data, List<Double[]> ratingsResult) {
        super();
        this.columns = columns;
        this.data = data;
        this.ratingsResult = ratingsResult;
    }


    @Override
    public String getColumnName(int column) {
        return this.columns.get(column);
    }

    @Override
    public java.lang.Class<?> getColumnClass(int columnIndex) {
        if(this.getValueAt(0, columnIndex) != null)
            return this.getValueAt(0, columnIndex).getClass();
        else
            return Object.class;
    }

    @Override
    public int getRowCount() {
        return this.data.size();
    }

    @Override
    public int getColumnCount() {
        return this.columns.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        //System.out.println("rowIndex: " + rowIndex + " columnIndex: " + columnIndex);
        return switch(columnIndex) {
            case 0 -> this.data.get(rowIndex).getNameGroup();
            case 1 -> this.data.get(rowIndex).getMaxListSize();
            case 2 -> this.data.get(rowIndex).getPercentageOfCapacity();
            case 3 -> this.ratingsResult.get(rowIndex)[0];
            case 4 -> this.ratingsResult.get(rowIndex)[1];
            default -> "";
        };

    }
}
