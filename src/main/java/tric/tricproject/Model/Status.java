package tric.tricproject.Model;


/**
 * Status class is created as a DTO
 * holding the current application status which is a boolean value representing active/inactive status
 *
 * @author Natali Munk-Jakobsen
 * @version 1.0, November 2022
 */
public class Status {

    private boolean isActive;

    public Status(boolean isActive) {
        this.isActive = isActive;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
