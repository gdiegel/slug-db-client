package io.slug.slug_db_client;

public class Tenant {

    private String firstName;
    private String lastName;
    private String occupation;

    public Tenant() {

    }

    /**
     * @return the firstName
     */
    protected String getFirstName() {

        return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    protected void setFirstName(String firstName) {

        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    protected String getLastName() {

        return lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    protected void setLastName(String lastName) {

        this.lastName = lastName;
    }

    /**
     * @return the occupation
     */
    protected String getOccupation() {

        return occupation;
    }

    /**
     * @param occupation
     *            the occupation to set
     */
    protected void setOccupation(String occupation) {

        this.occupation = occupation;
    }

    @Override
    public String toString() {

        return new StringBuffer("First Name: ").append(this.firstName).append(", Last Name: ").append(this.lastName).append(", Occupation: ").append(this.occupation).toString();
    }

}
