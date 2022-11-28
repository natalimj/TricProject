package tric.tricproject.Model;

import javax.persistence.*;


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
