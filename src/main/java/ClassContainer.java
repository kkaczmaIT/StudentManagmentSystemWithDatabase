import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Map.Entry;

public class ClassContainer {
    private Map<String, Class> group;

    public ClassContainer() {
        this.group =  new HashMap<>();
    }

    public Map<String, Class> getGroup() {
        return group;
    }

    public void addClass(String groupName, int size) {
        this.group.put(groupName, new Class(groupName, size));
    }

    public void removeClass(String groupName) {
        this.group.remove(groupName);
    }

    public List<Class> findEmpty() {
        List<Class> groups = new ArrayList<Class>();
        for(Map.Entry mapElement : this.group.entrySet()) {
            Class gr = (Class)mapElement.getValue();
            if(gr.getNumberStudents() == 0)
            {
                groups.add(gr);
            }
        }
        return groups;
    }

    public Class getGroup(String name) {
        for(Map.Entry mapElement : this.group.entrySet()) {
            Class gr = (Class)mapElement.getValue();
            if(gr.getNameGroup() == name)
            {
                return gr;
            }
        }
        return new Class("Null", -1);
    }

    public void summary() {
        for(Map.Entry mapElement : this.group.entrySet()) {
            Class gr = (Class)mapElement.getValue();
            String nameGroup = (String)mapElement.getKey();
            System.out.println("Name group: " + nameGroup + " percentage of students: " + gr.getPercentageOfCapacity());
        }
    }

}
