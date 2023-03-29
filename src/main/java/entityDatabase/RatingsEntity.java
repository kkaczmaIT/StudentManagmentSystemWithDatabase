package entityDatabase;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@NamedQuery(name = "Ratings.GetRatingsFromGroup", query = "SELECT ratings FROM RatingsEntity ratings WHERE ratings.idGroup = :idGroup")
//@NamedQuery(name="Rating.CountAmountRating", query = "SELECT COUNT(rating) FROM RatingsEntity rating WHERE RatingsEntity.idGroup = :idGroup")
//@NamedQuery(name="Rating.CalcAverageRating", query = "SELECT AVG(rating.rating) FROM RatingsEntity rating WHERE RatingsEntity.idGroup = :idGroup")
@Table(name = "ratings", schema = "lab4_student_managment_system", catalog = "")
public class RatingsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "rating")
    private int rating;
    @Basic
    @Column(name = "id_group")
    private int idGroup;

    @Basic
    @Column(name="id_student")
    private int idStudent;

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    //@ManyToOne
    //@JoinColumn()
    //private GroupsEntity group;
    @Basic
    @Column(name = "created_at")
    private Date createdAt;
    @Basic
    @Column(name = "comment")
    private String comment;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(int idGroup) {
        this.idGroup = idGroup;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingsEntity that = (RatingsEntity) o;

        if (id != that.id) return false;
        if (rating != that.rating) return false;
        if (idGroup != that.idGroup) return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null) return false;
        if (comment != null ? !comment.equals(that.comment) : that.comment != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + rating;
        result = 31 * result + idGroup;
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (comment != null ? comment.hashCode() : 0);
        return result;
    }
}
