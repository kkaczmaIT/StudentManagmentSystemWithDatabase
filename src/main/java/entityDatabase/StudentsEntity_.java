package entityDatabase;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(StudentsEntity.class)
public class StudentsEntity_ {
    public static volatile SingularAttribute<StudentsEntity, Integer> id;
    public static volatile SingularAttribute<StudentsEntity, String> firstName;
    public static volatile SingularAttribute<StudentsEntity, String> lastName;
    public static volatile SingularAttribute<StudentsEntity, Integer> studentCondition;
    public static volatile SingularAttribute<StudentsEntity, Integer> yearOfBirth;
    public static volatile SingularAttribute<StudentsEntity, Integer> scores;
    public static volatile SingularAttribute<StudentsEntity, Integer> idGroup;
}
