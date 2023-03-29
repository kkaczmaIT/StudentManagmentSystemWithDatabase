package entityDatabase;

import jakarta.persistence.*;

import java.util.List;

@Entity
@NamedQuery(name = "Groups.GetAll", query = "SELECT group FROM GroupsEntity group")
@NamedQuery(name = "Groups.GetGroupByName", query = "SELECT group FROM GroupsEntity group WHERE group.nameGroup = :nameGroup")
//@NamedQuery(name = "Groups.Update", query = "SELECT group FROM GroupsEntity group")
//@NamedQuery(name = "Groups.Remove", query = "SELECT group FROM GroupsEntity group")
@Table(name = "groups", schema = "lab4_student_managment_system", catalog = "")
public class GroupsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "name_group")
    private String nameGroup;
    @Basic
    @Column(name = "max_list_size")
    private int maxListSize;
    @OneToMany
    @JoinColumn(name="id_group", referencedColumnName="ID")
    private List<StudentsEntity> studentsList;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupsEntity that = (GroupsEntity) o;

        if (id != that.id) return false;
        if (maxListSize != that.maxListSize) return false;
        if (nameGroup != null ? !nameGroup.equals(that.nameGroup) : that.nameGroup != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (nameGroup != null ? nameGroup.hashCode() : 0);
        result = 31 * result + maxListSize;
        return result;
    }
}
