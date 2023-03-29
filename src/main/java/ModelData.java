import javax.swing.*;
import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;
import java.util.ArrayList;

import entityDatabase.GroupsEntity;
import entityDatabase.*;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ModelData {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction transaction;
    private List<String> studentColumns;
    private List<Student> studentsInGroup;

    private List<String> groupColumns;
    private ClassContainer studentGroups;


    public ModelData()
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("default");
        entityManager = entityManagerFactory.createEntityManager();
        transaction = entityManager.getTransaction();

        this.groupColumns = new ArrayList<String>();
        this.groupColumns.add("Group Name");
        this.groupColumns.add("Size Group");
        this.groupColumns.add("Group Status(%)");
        this.groupColumns.add("Amount ratings");
        this.groupColumns.add("Average rating");
        this.studentGroups = new ClassContainer();


        this.studentColumns = new ArrayList<String>();
        this.studentColumns.add("First Name");
        this.studentColumns.add("Last Name");
        this.studentColumns.add("Status");
        this.studentColumns.add("Year of Birth");
        this.studentColumns.add("Scores");
        this.studentsInGroup = new ArrayList<Student>();

    }


    public List<String> getStudentColumns() {
        return studentColumns;
    }

    public void setStudentColumns(List<String> studentColumns) {
        this.studentColumns = studentColumns;
    }

    public List<Student> getStudentsInGroup(int idGroup) {
        this.studentsInGroup = new ArrayList<Student>();
        StudentCondition studentCondition = StudentCondition.ABSENT;
        try {
            transaction.begin();
//            UsersEntity users = new UsersEntity();
//            //users.setId(10);
//            users.setName("Nickname123");
//            users.setLogin("hairdresser5000");
//            users.setPassword("pass34");
//            entityManager.persist(users);
            TypedQuery<entityDatabase.StudentsEntity> getListStudentsInGroupQuery = entityManager.createNamedQuery("Students.GetStudentsInGroup", entityDatabase.StudentsEntity.class);
            getListStudentsInGroupQuery.setParameter("idGroup", idGroup);
            //getListGroupQuery.setParameter(1, "login1");
            for(entityDatabase.StudentsEntity student: getListStudentsInGroupQuery.getResultList())
            {
                if(student.getStudentCondition() == 1)
                    studentCondition = StudentCondition.ABSENT;
                else if(student.getStudentCondition() == 2)
                    studentCondition = StudentCondition.ILL;
                else if(student.getStudentCondition() == 3)
                    studentCondition = StudentCondition.DOING;
                this.studentsInGroup.add(new Student(student.getFirstName(), student.getLastName(), studentCondition, student.getYearOfBirth(), student.getScores()));
            }

            transaction.commit();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
        } finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }

        }
        return studentsInGroup;
    }
    public void updateStudent(int index, Student editStudent)
    {
        try {
            transaction.begin();
//            TypedQuery<entityDatabase.GroupsEntity> getGroupByNameQuery = entityManager.createNamedQuery("Groups.GetGroupByName", entityDatabase.GroupsEntity.class);
//            getGroupByNameQuery.setParameter("nameGroup", oldGroupName);
//            //for(GroupsEntity group: getListGroupQuery.getResultList())
//            GroupsEntity groupEntity = getGroupByNameQuery.getSingleResult();
            Student oldStudent = this.studentsInGroup.get(index);
            TypedQuery<entityDatabase.StudentsEntity> getSingleStudent = entityManager.createNamedQuery("Students.GetSingleStudents", entityDatabase.StudentsEntity.class);
            getSingleStudent.setParameter("firstName", oldStudent.getFirstName());
            getSingleStudent.setParameter("lastName", oldStudent.getLastName());
//            //for(GroupsEntity group: getListGroupQuery.getResultList())
            StudentsEntity studentEntity = getSingleStudent.getSingleResult();
            studentEntity.setFirstName(editStudent.getFirstName());
            studentEntity.setLastName(editStudent.getLastName());
            studentEntity.setStudentCondition(editStudent.getStatus().getCode());
            studentEntity.setYearOfBirth(editStudent.getDateOfBirth());
            studentEntity.setScores(editStudent.getScores());
            //studentEntity.setIdGroup(groupId);


            entityManager.merge(studentEntity);
            //studentGroups.set(groupName);
            studentsInGroup.set(index, editStudent);
            transaction.commit();
        }  catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
        }finally {
            if(transaction.isActive())
                transaction.rollback();
        }

    }
    public void clearStudentsListInGroup() {
        studentsInGroup = new ArrayList<Student>();
    }

    public void printStudentsList()
    {
        for(Student st: studentsInGroup)
        {
            System.out.println(st.getFirstName() + ' ' + st.getLastName());
        }
    }

    public void removeGroup(String groupName)
    {
        try {
            transaction.begin();
//            TypedQuery<entityDatabase.GroupsEntity> getGroupByNameQuery = entityManager.createNamedQuery("Groups.GetGroupByName", entityDatabase.GroupsEntity.class);
//            getGroupByNameQuery.setParameter("nameGroup", groupName);
            //for(GroupsEntity group: getListGroupQuery.getResultList())
            //GroupsEntity groupEntity = getGroupByNameQuery.getSingleResult();
            GroupsEntity groupEntity = getGroupByName(groupName);

            entityManager.remove(groupEntity);
            studentGroups.removeClass(groupName);
            transaction.commit();
        }  catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
        }finally {
            if(transaction.isActive())
                transaction.rollback();
        }
    }

    public GroupsEntity getGroupByName(String groupName)
    {
        GroupsEntity selectedGroup = new GroupsEntity();
        try
        {
            TypedQuery<entityDatabase.GroupsEntity> getGroupByNameQuery = entityManager.createNamedQuery("Groups.GetGroupByName", entityDatabase.GroupsEntity.class);
            getGroupByNameQuery.setParameter("nameGroup", groupName);
            //for(GroupsEntity group: getListGroupQuery.getResultList())
            selectedGroup = getGroupByNameQuery.getSingleResult();
        } catch(Exception e)
        {
            System.err.println("Process failed");
        }
        return selectedGroup;
    }

    public int getIdStudent(String firstName, String lastName)
    {
        StudentsEntity selectedStudent = new StudentsEntity();
        try {
         transaction.begin();

                TypedQuery<entityDatabase.StudentsEntity> getSingleStudentQuery = entityManager.createNamedQuery("Students.GetSingleStudents", entityDatabase.StudentsEntity.class);
                getSingleStudentQuery.setParameter("firstName", firstName);
            getSingleStudentQuery.setParameter("lastName", lastName);
                //for(GroupsEntity group: getListGroupQuery.getResultList())
            selectedStudent = getSingleStudentQuery.getSingleResult();
         transaction.commit();
        } catch(Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
        }finally {
            if(transaction.isActive())
                transaction.rollback();
        }
        return selectedStudent.getId();
    }

    public void addRating(int rating, String comment, int idGroup, int idStudent)
    {
        Long timeMililis = System.currentTimeMillis();
        Timestamp dateTime = new Timestamp(timeMililis);
        Date date = new Date(dateTime.getTime());
        if(rating > 0 && rating <= 5 )
            try {
                transaction.begin();
                RatingsEntity ratingsEntity = new RatingsEntity();
                ratingsEntity.setRating(rating);
                ratingsEntity.setComment(comment);
                ratingsEntity.setIdGroup(idGroup);
                ratingsEntity.setCreatedAt(date);
                ratingsEntity.setIdStudent(idStudent);
                entityManager.persist(ratingsEntity);
                //this.studentsInGroup.add(student);
                transaction.commit();
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
            } finally {
                if(transaction.isActive())
                    transaction.rollback();
            }
        else
            JOptionPane.showMessageDialog(null, "Rating must be greater than 0 and less than 6", "Problem" ,JOptionPane.ERROR_MESSAGE);
    }



    public void removeRating(int idGroup)
    {
        List<RatingsEntity> ratingsList;
            try {
                transaction.begin();
                TypedQuery<entityDatabase.RatingsEntity> getRatingsFromGroupQuery = entityManager.createNamedQuery("Ratings.GetRatingsFromGroup", entityDatabase.RatingsEntity.class);
                getRatingsFromGroupQuery.setParameter("idGroup", idGroup);
                ratingsList = getRatingsFromGroupQuery.getResultList();
                for(RatingsEntity ratings: ratingsList)
                {
                    entityManager.remove(ratings);
                }

                //this.studentsInGroup.add(student);
                transaction.commit();
            } catch(Exception e) {
                JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
            } finally {
                if(transaction.isActive())
                    transaction.rollback();
            }

    }

    public List<Double[]> getAmountAndAverageRatings(int idGroup)
    {
        List<Double[]> list = new ArrayList<Double[]>();
        Double countRes;
        Double tmp[] = new Double[2];
        Double avgRes;
        try {
            transaction.begin();
            TypedQuery<Long> getRatingsAmountFromGroupQuery = entityManager.createQuery("SELECT COUNT(rating) FROM RatingsEntity rating WHERE rating.idGroup = :idGroup", Long.class);
            getRatingsAmountFromGroupQuery.setParameter("idGroup", idGroup);
            TypedQuery<Double> getRatingsAverageFromGroupQuery = entityManager.createQuery("SELECT AVG(rating.rating) FROM RatingsEntity rating WHERE rating.idGroup = :idGroup", Double.class);
            getRatingsAverageFromGroupQuery.setParameter("idGroup", idGroup);
            countRes = Double.valueOf(getRatingsAmountFromGroupQuery.getSingleResult());
            avgRes = getRatingsAverageFromGroupQuery.getSingleResult();
            //System.out.println("countRes: " + countRes + ", avgRes: " + avgRes);
            tmp[0] = countRes;
            tmp[1] = avgRes;
            list.add(tmp);
            //System.out.println("list " + list.get(0)[0] + ", " + list.get(0)[1]);
            //this.studentsInGroup.add(student);
            transaction.commit();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
            System.out.println(e);
        } finally {
            if(transaction.isActive())
                transaction.rollback();
        }
        return list;
    }

    public Class getGroupClassByName(String groupName)
    {
        Class group = new Class();
        GroupsEntity selectedGroup;
        try
        {
            transaction.begin();
            selectedGroup = getGroupByName(groupName);
            group.setNameGroup(selectedGroup.getNameGroup());
            group.setMaxListSize(selectedGroup.getMaxListSize());
            transaction.commit();
            //transaction.begin();

            group.setStudentList(this.getStudentsInGroup(this.getIdGroup(group.getNameGroup(), true)));
            //transaction.commit();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
        } finally {
            if(transaction.isActive())
                transaction.rollback();
        }
        return group;
    }

    public int getIdGroup(String groupName, boolean isDatabaseUse)
    {
        if(isDatabaseUse)
            return getGroupByName(groupName).getId();
        else
            return getGroupByName(groupName).getId() - 1;
    }

    public void updateGroup(String oldGroupName, Class group)
    {
        try {
            transaction.begin();
//            TypedQuery<entityDatabase.GroupsEntity> getGroupByNameQuery = entityManager.createNamedQuery("Groups.GetGroupByName", entityDatabase.GroupsEntity.class);
//            getGroupByNameQuery.setParameter("nameGroup", oldGroupName);
//            //for(GroupsEntity group: getListGroupQuery.getResultList())
//            GroupsEntity groupEntity = getGroupByNameQuery.getSingleResult();
            GroupsEntity groupEntity = getGroupByName(oldGroupName);
            groupEntity.setNameGroup(group.getNameGroup());
            groupEntity.setMaxListSize(group.getMaxListSize());
            entityManager.merge(groupEntity);
            //studentGroups.set(groupName);
            transaction.commit();
        }  catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
        }finally {
            if(transaction.isActive())
                transaction.rollback();
        }
    }

    public void removeStudentFromGroup(Student student, int groupID)
    {
        try {
            transaction.begin();
            TypedQuery<entityDatabase.StudentsEntity> getSingleStudent = entityManager.createNamedQuery("Students.GetSingleStudents", entityDatabase.StudentsEntity.class);
            getSingleStudent.setParameter("firstName", student.getFirstName());
            getSingleStudent.setParameter("lastName", student.getLastName());
//            //for(GroupsEntity group: getListGroupQuery.getResultList())
            StudentsEntity studentEntity = getSingleStudent.getSingleResult();
            entityManager.remove(studentEntity);
            transaction.commit();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
        } finally {
            if(transaction.isActive())
                transaction.rollback();
        }

    }

    public List<Student> findStudentsInGroupByName(String firstName, int idGroup)
    {

        List<Student> list = new ArrayList<Student>();
        StudentCondition studentCondition = StudentCondition.ABSENT;
        //Student tmpStudent = new Student();
        try {

            transaction.begin();
            //TypedQuery<entityDatabase.StudentsEntity> getListStudent = entityManager.createNamedQuery("Students.SearchStudentsByName", entityDatabase.StudentsEntity.class);
            //getListStudent.setParameter("firstName", firstName + '%');
            //getListStudent.setParameter("idGroup", idGroup);

            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<StudentsEntity> criteria = builder.createQuery(StudentsEntity.class);
            Root<StudentsEntity> root = criteria.from(StudentsEntity.class);
            criteria.select(root);
            criteria.where(builder.like(root.get(StudentsEntity_.firstName), firstName + "%"),
                    builder.equal(root.get(StudentsEntity_.idGroup), idGroup));
            //criteria.where(builder.equal(root.get(StudentsEntity_.idGroup), idGroup));

            //for(GroupsEntity group: getListGroupQuery.getResultList())

            //List<StudentsEntity> studentsEntity = getListStudent.getResultList();
            List<StudentsEntity> studentsEntity = entityManager.createQuery(criteria).getResultList();
            for(StudentsEntity singleEntityStudent: studentsEntity)
            {
                //tmpStudent.setFirstName(singleEntityStudent.getFirstName());
                if(singleEntityStudent.getStudentCondition() == 1)
                    studentCondition = StudentCondition.ABSENT;
                else if(singleEntityStudent.getStudentCondition() == 2)
                    studentCondition = StudentCondition.ILL;
                else if(singleEntityStudent.getStudentCondition() == 3)
                    studentCondition = StudentCondition.DOING;
                list.add(new Student(singleEntityStudent.getFirstName(), singleEntityStudent.getLastName(), studentCondition, singleEntityStudent.getYearOfBirth(), singleEntityStudent.getScores())); //this.studentsInGroup.add(new Student(student.getFirstName(), student.getLastName(), studentCondition, student.getYearOfBirth(), student.getScores()));
                //Student tmpStudent = new Student(singleEntityStudent.getFirstName(), singleEntityStudent.getLastName(), singleEntityStudent.getStudentCondition()););
            }
            //entityManager.remove(studentEntity);
            transaction.commit();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
        }finally
         {
            if(transaction.isActive())
                transaction.rollback();
        }
        return list;

    }

    public void addStudentToGroup(Student student, int groupId)
    {
        try {
            transaction.begin();
            StudentsEntity studentEntity = new StudentsEntity();
            studentEntity.setFirstName(student.getFirstName());
            studentEntity.setLastName(student.getLastName());
            studentEntity.setStudentCondition(student.getStatus().getCode());
            studentEntity.setYearOfBirth(student.getDateOfBirth());
            studentEntity.setScores(student.getScores());
            studentEntity.setIdGroup(groupId);
            entityManager.persist(studentEntity);
            this.studentsInGroup.add(student);
            transaction.commit();
        } catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
        } finally {
            if(transaction.isActive())
                transaction.rollback();
        }

    }

    public void addGroup(Class group) {
        try {
            transaction.begin();
            GroupsEntity groupEntity = new GroupsEntity();
            groupEntity.setNameGroup(group.getNameGroup());
            groupEntity.setMaxListSize(group.getMaxListSize());
            entityManager.persist(groupEntity);
            this.studentGroups.addClass(group.getNameGroup(), group.getMaxListSize());
            transaction.commit();
        }  catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
        }finally {
            if(transaction.isActive())
                transaction.rollback();
        }

    }

    public void setStudentsInGroup(List<Student> studentsInGroup) {
        this.studentsInGroup = studentsInGroup;
    }

    public List<String> getGroupColumns() {
        return groupColumns;
    }

    public void setGroupColumns(List<String> groupColumns) {
        this.groupColumns = groupColumns;
    }

    public List<Class> getGroups() {
        List<Class> groups = new ArrayList<Class>(this.studentGroups.getGroup().values());
        return groups;
    }

    public void init()
    {
        try {
            transaction.begin();
//            UsersEntity users = new UsersEntity();
//            //users.setId(10);
//            users.setName("Nickname123");
//            users.setLogin("hairdresser5000");
//            users.setPassword("pass34");
//            entityManager.persist(users);
            TypedQuery<entityDatabase.GroupsEntity> getListGroupQuery = entityManager.createNamedQuery("Groups.GetAll", entityDatabase.GroupsEntity.class);
            //getListGroupQuery.setParameter(1, "login1");
            for(GroupsEntity group: getListGroupQuery.getResultList())
            {

                this.studentGroups.addClass(group.getNameGroup(), group.getMaxListSize());
                //this.studentsInGroup = getStudentsInGroup();
            }

            transaction.commit();
        }  catch(Exception e) {
            JOptionPane.showMessageDialog(null, "Error occur. Try again", "Problem" ,JOptionPane.ERROR_MESSAGE);
        }finally {
            if (transaction.isActive()) {
                transaction.rollback();
            }
        }

        //studentGroups.addClass("Grupa 1", 5);
        //studentGroups.addClass("Grupa 2", 7);
        //studentGroups.addClass("Grupa 3", 2);

//        Student st1 = new Student("Adam", "Pierwszy", StudentCondition.DOING, 1999, 10);
//        Student st2 = new Student("Adam", "Pierwszymat", StudentCondition.DOING, 1999, 0);
//        Student st3 = new Student("Tomasz", "Drugi", StudentCondition.ABSENT, 2001, 20);
//        Student st4 = new Student("Paweł", "Trzeci", StudentCondition.DOING, 2000, 15);
//        Student st5 = new Student("Marcin", "Czwarty", StudentCondition.ILL, 1999, 23.7);
//        Student st6 = new Student("Marek", "Piąty", StudentCondition.ABSENT, 1998, 3.6);
//
//        Class tmp = studentGroups.getGroup("Grupa 1");
//        tmp.addStudent(st1);
//        tmp.addStudent(st2);
//        tmp.addStudent(st3);
//
//        tmp = studentGroups.getGroup("Grupa 3");
//        tmp.addStudent(st4);
//
//        tmp = studentGroups.getGroup("Grupa 2");
//        tmp.addStudent(st5);
//        tmp.addStudent(st6);


    }
    public void endWorkModelData()
    {
        entityManager.close();
        entityManagerFactory.close();
    }


}
