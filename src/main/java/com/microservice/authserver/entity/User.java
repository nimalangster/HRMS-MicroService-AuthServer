package com.microservice.authserver.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import com.google.common.base.Optional;

import io.swagger.annotations.ApiModelProperty;

@Component
@Entity
@Table(name = "user", schema = "profile")
public class User implements Serializable {

	private static final long serialVersionUID = 8918656601415434315L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(notes = "The database generated user id of the user")
	private Long id;

	@ApiModelProperty(notes = "The user name of the user used to login")
	private String userName;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private Boolean enabled;
	private Boolean tokenExpired;
	@ManyToMany
	private List<Role> roles;
	
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> list) {
		this.roles = list;
	}

	@ApiModelProperty(notes = "The appointment id of the user to connect appointment micro service")
	private Integer appointmentId;
	private String gender;
	private String telephoneNumber;
	private String mobileNumber;
	
	private String permenentAddress;
	private Date joinDate;
	private Integer servicePeriod;
	private String profilePhoto;
	private String nationality;
	private String nic;
	private String religion;
	private Date dateOfBirth;

	@ApiModelProperty(notes = "The residential address of the user")
	private String residentialAddress;
	private String maritalStatus;

	private Integer departmentId;

	@UpdateTimestamp
	private Date updateAt;

	@CreationTimestamp
	private Date createdAt;

	public User() {
		super();
	}

	public User(Long id, String userName, String password, String firstName, String lastName, String email,
			Boolean enabled, Boolean tokenExpired, Integer appointmentId, String gender, String telephoneNumber,
			String mobileNumber, String permenentAddress, Date joinDate, Integer servicePeriod, String profilePhoto,
			String nationality, String nic, String religion, Date dateOfBirth, String residentialAddress,
			String maritalStatus, Integer departmentId, Date updateAt, Date createdAt) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.enabled = enabled;
		this.tokenExpired = tokenExpired;
		this.appointmentId = appointmentId;
		this.gender = gender;
		this.telephoneNumber = telephoneNumber;
		this.mobileNumber = mobileNumber;
		this.permenentAddress = permenentAddress;
		this.joinDate = joinDate;
		this.servicePeriod = servicePeriod;
		this.profilePhoto = profilePhoto;
		this.nationality = nationality;
		this.nic = nic;
		this.religion = religion;
		this.dateOfBirth = dateOfBirth;
		this.residentialAddress = residentialAddress;
		this.maritalStatus = maritalStatus;
		this.departmentId = departmentId;
		this.updateAt = updateAt;
		this.createdAt = createdAt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(Boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public Integer getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Integer appointmentId) {
		this.appointmentId = appointmentId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getPermenentAddress() {
		return permenentAddress;
	}

	public void setPermenentAddress(String permenentAddress) {
		this.permenentAddress = permenentAddress;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public Integer getServicePeriod() {
		return servicePeriod;
	}

	public void setServicePeriod(Integer servicePeriod) {
		this.servicePeriod = servicePeriod;
	}

	public String getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(String profilePhoto) {
		this.profilePhoto = profilePhoto;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getNic() {
		return nic;
	}

	public void setNic(String nic) {
		this.nic = nic;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(String residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	public Date getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
