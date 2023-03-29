public class Student implements Comparable<Student>{
    private String firstName;
    private String lastName;
    private StudentCondition status;
    private int dateOfBirth;
    private double scores;

    public Student() {
    }

    public Student(String firstName, String lastName, StudentCondition status, int dateOfBirth, double scores) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.status = status;
        this.dateOfBirth = dateOfBirth;
        this.scores = scores;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public StudentCondition getStatus() {
        return status;
    }

    public String getStatusName() {
        return this.status.name();
    }
    public void setStatus(StudentCondition status) {
        this.status = status;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getScores() {
        return scores;
    }

    public void setScores(double scores) {
        this.scores = scores;
    }

    public void print() {
        System.out.println("Student: " + this.firstName + " " + this.lastName);
        System.out.println("Student status: " + this.status);
        System.out.println("Date of birth: " + this.dateOfBirth);
        System.out.println("Scores: " + this.scores);
    }

    public int compareTo(Student st) {
        int resLastName = this.lastName.compareTo(st.getLastName());
        if(resLastName == 0) {
            return this.firstName.compareTo(st.getFirstName());
        }
        return resLastName;
    }
}
