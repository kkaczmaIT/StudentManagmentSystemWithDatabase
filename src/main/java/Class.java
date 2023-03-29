import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;
public class Class implements Comparator<Student> {
    private String nameGroup;
    private List<Student> studentList;
    private int maxListSize;

    public Class(String nameGroup, int maxListSize) {
        this.nameGroup = nameGroup;
        this.studentList = new ArrayList<Student>();
        this.maxListSize = maxListSize;
    }

    public Class(){
        this.studentList = new ArrayList<Student>();
    }


    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public int getMaxListSize() {
        return maxListSize;
    }

    public void setMaxListSize(int maxListSize) {
        this.maxListSize = maxListSize;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public void addStudent(Student student) {
        for(Student st: this.studentList)
        {
            if(st.getFirstName() == student.getFirstName() && st.getLastName() == student.getLastName())
                System.out.println("Student exists in group");
        }
//        if(this.studentList.indexOf(student) != -1)
//           System.out.println("Student exists in group");
        if(this.maxListSize > this.studentList.size())
            this.studentList.add(student);
        else
            System.err.println("Group is full");

    }

    public void addPoints(Student student, double points) {
        student.setScores(student.getScores() + points);
    }

    public void getStudent(Student student) {
        int index = this.studentList.indexOf(student);
        if(index == -1)
        {
            System.err.println("Student is not exists");
        }
        if(student.getScores() == 0)
            this.studentList.remove(index);
    }

    public void changeCondition(Student student, StudentCondition status) {
        student.setStatus(status);
    }

    public void removePoints(Student student, double points) {
        student.setScores(student.getScores() - points);
    }

    public int compare(Student st1, Student st2) {
        int result = st1.getLastName().compareTo(st2.getLastName());
        if(result == 0) return result;
        return -1;
    }



    public Student search(String lastName) {
        Student st = new Student("", lastName, StudentCondition.DOING,-1, -1);
        for(Student student: this.studentList)
        {
            if(this.compare(student, st) == 0) return student;
        }
        System.out.println("Student not found");
        return st;
    }

    public List<Student> searchPartial(String phrase, String mode) {
        List<Student> matchStudents = new ArrayList<Student>();

        if(mode == "firstName") {
            for(Student student: this.studentList) {
                if(student.getFirstName().contains(phrase)) {
                    matchStudents.add(student);
                }
            }
        } else if(mode == "lastName") {
            for(Student student: this.studentList) {
                if(student.getLastName().contains(phrase)) {
                    matchStudents.add(student);
                }
            }
        }
        return matchStudents;
    }

    public int countByCondition(StudentCondition status) {
        int numStudents = 0;
            for(Student student: this.studentList) {
                if (student.getStatus() == status) {
                    numStudents++;
                }
            }

        return numStudents;
    }

    public void summary() {
        System.out.println("Student's list in group: ");
        for(Student st: this.studentList) {
            st.print();
        }
    }

    public void sortByName() {
        Collections.sort(this.studentList);
    }

    public void sortByPoints() {
        Collections.sort(this.studentList, new Comparator<Student>() {
            @Override
            public int compare(Student st1, Student st2) {
                return -1 * Double.compare(st1.getScores(), st2.getScores());
            }
        });
    }

    public void max() {
        Student st = Collections.max(this.studentList, new Comparator<Student>() {
            @Override
            public int compare(Student st1, Student st2){
                return Double.compare(st1.getScores(), st2.getScores());
            }

        });
        System.out.println("Max is " );
        st.print();
    }


    public int getNumberStudents()
    {
        return this.studentList.size();
    }

    public double getPercentageOfCapacity() {

        return (double)this.studentList.size() / (double)this.maxListSize * 100.0;
    }
    public void test() {
        this.addPoints(this.studentList.get(0), 10.4);
        this.getStudent(this.studentList.get(0));
        this.studentList.get(0).print();
        this.studentList.get(1).print();
        this.getStudent(this.studentList.get(1));
        this.changeCondition(this.studentList.get(1),StudentCondition.ILL);
        this.studentList.get(1).print();
        System.out.println("Szukanie studenta");
        Student findSt = this.search("Czwarty");
        findSt.print();
        System.out.println("Lista pasujacych nazwisk lub imion");
        List<Student> matchStudents = this.searchPartial("Mar", "firstName");
        for(Student student: matchStudents) {
            student.print();
        }
        System.out.println("Koniec Listy pasujacych studentow pod wzgledem nazwiska/imienia");
        System.out.println("Liczba studentow o statusie ILL: " + this.countByCondition(StudentCondition.ILL));
    }
}
