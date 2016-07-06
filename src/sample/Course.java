/**
 * This class is for getters and setters of the delete operation to delete
 * classrooms in the login dashboard. It returns the name of the classroom
 */
package sample;

public class Course {
    private String name;

    public Course (String name) {
        this.name = name;
    }

    /**
     * This getter returns the classroom name
     *
     * @return
     */
    public String getName () {
        return name;
    }

    /**
     * This setter sets the classroom name
     * @param name
     */
    public void setName (String name) {
        this.name = name;
    }
}
