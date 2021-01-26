package com.atc.sample.common.utils;

public class NewAccountInfo {

	public enum GENDER {
		MR("id_gender1"), MRS("id_gender2");

		private String value;

		private GENDER(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

	};

	public enum STATE {
		NEW_YORK("New York"), ALASK("Alaska");

		private String value;

		private STATE(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}
	};

	public enum COUNTRY {
		UNITED_STATES("United States");

		private String value;

		private COUNTRY(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

	};

	private String emailId;

	private String customerFirstName;
	private String customerLastName;
	private String password;
	private String company;
	private String address1;
	private String address2;
	private String city;
	private Integer postalCode;
	private Long phoneNumber;
	private Long mobilePhone;
	private String alias;
	private GENDER gender;
	private STATE state;
	private COUNTRY country;

	private String additionalInformation;
	private String dateOfBirth;
	private boolean signUpForNewletter;
	private boolean receiveSpecialOffer;

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isSignUpForNewletter() {
		return signUpForNewletter;
	}

	public void setSignUpForNewletter(boolean signUpForNewletter) {
		this.signUpForNewletter = signUpForNewletter;
	}

	public boolean isReceiveSpecialOffer() {
		return receiveSpecialOffer;
	}

	public void setReceiveSpecialOffer(boolean receiveSpecialOffer) {
		this.receiveSpecialOffer = receiveSpecialOffer;
	}

	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setMobilePhone(Long mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public Long getMobilePhone() {
		return mobilePhone;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public GENDER getGender() {
		return gender;
	}

	public void setGender(GENDER gender) {
		this.gender = gender;
	}

	public STATE getState() {
		return state;
	}

	public void setState(STATE state) {
		this.state = state;
	}

	public COUNTRY getCountry() {
		return country;
	}

	public void setCountry(COUNTRY country) {
		this.country = country;
	}

}
