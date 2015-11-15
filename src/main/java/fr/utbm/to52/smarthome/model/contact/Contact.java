/**
 * 
 */
package fr.utbm.to52.smarthome.model.contact;

import fr.utbm.to52.smarthome.services.couchdb.StorableEntity;

/**
 * @author Alexandre Guyon
 *
 */
public class Contact extends StorableEntity {
	
	private String firstName;
	
	private String lastName;
	
	private String nickName;
	
	private String organization;
	
	private String email;
	
	private String tel;
	
	/**
	 * Create contact
	 */
	public Contact() {
		super(Contact.class.getName());
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the nickName
	 */
	public String getNickName() {
		return this.nickName;
	}

	/**
	 * @param nickName the nickName to set
	 */
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/**
	 * @return the organization
	 */
	public String getOrganization() {
		return this.organization;
	}

	/**
	 * @param organization the organization to set
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the tel
	 */
	public String getTel() {
		return this.tel;
	}

	/**
	 * @param tel the tel to set
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
		result = prime * result + ((this.firstName == null) ? 0 : this.firstName.hashCode());
		result = prime * result + ((this.lastName == null) ? 0 : this.lastName.hashCode());
		result = prime * result + ((this.nickName == null) ? 0 : this.nickName.hashCode());
		result = prime * result + ((this.organization == null) ? 0 : this.organization.hashCode());
		result = prime * result + ((this.tel == null) ? 0 : this.tel.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Contact other = (Contact) obj;
		if (this.email == null) {
			if (other.email != null)
				return false;
		} else if (!this.email.equals(other.email))
			return false;
		if (this.firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!this.firstName.equals(other.firstName))
			return false;
		if (this.lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!this.lastName.equals(other.lastName))
			return false;
		if (this.nickName == null) {
			if (other.nickName != null)
				return false;
		} else if (!this.nickName.equals(other.nickName))
			return false;
		if (this.organization == null) {
			if (other.organization != null)
				return false;
		} else if (!this.organization.equals(other.organization))
			return false;
		if (this.tel == null) {
			if (other.tel != null)
				return false;
		} else if (!this.tel.equals(other.tel))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Contact [firstName=" + this.firstName + ", lastName=" + this.lastName + ", nickName=" + this.nickName
				+ ", organization=" + this.organization + ", email=" + this.email + ", tel=" + this.tel + "]";
	}

}
