package entityDatabase;

import jakarta.persistence.*;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;


@Entity
@NamedQuery(name = "Students.GetStudentsInGroup", query = "SELECT student FROM StudentsEntity student WHERE student.idGroup = :idGroup")
@NamedQuery(name = "Students.GetSingleStudents", query = "SELECT student FROM  StudentsEntity student WHERE student.firstName = :firstName AND student.lastName = :lastName")
@NamedQuery(name = "Students.SearchStudentsByName", query = "SELECT student FROM  StudentsEntity student WHERE student.firstName LIKE :firstName AND student.idGroup = :idGroup")
@Table(name = "students", schema = "lab4_student_managment_system", catalog = "")
public class StudentsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "first_name")
    private String firstName;
    @Basic
    @Column(name = "last_name")
    private String lastName;
    @Basic
    @Column(name = "student_condition")
    private int studentCondition;
    @Basic
    @Column(name = "year_of_birth")
    private int yearOfBirth;
    @Basic
    @Column(name = "scores")
    private double scores;
    @Basic

    @Column(name = "id_group")
    private int idGroup;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getStudentCondition() {
        return studentCondition;
    }

    public void setStudentCondition(int studentCondition) {
        this.studentCondition = studentCondition;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public double getScores() {
        return scores;
    }

    public void setScores(double scores) {
        this.scores = scores;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentsEntity that = (StudentsEntity) o;

        if (id != that.id) return false;
        if (studentCondition != that.studentCondition) return false;
        if (yearOfBirth != that.yearOfBirth) return false;
        if (Double.compare(that.scores, scores) != 0) return false;
        if (idGroup != that.idGroup) return false;
        if (firstName != null ? !firstName.equals(that.firstName) : that.firstName != null) return false;
        if (lastName != null ? !lastName.equals(that.lastName) : that.lastName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (int) studentCondition;
        result = 31 * result + yearOfBirth;
        temp = Double.doubleToLongBits(scores);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + idGroup;
        return result;
    }


}

