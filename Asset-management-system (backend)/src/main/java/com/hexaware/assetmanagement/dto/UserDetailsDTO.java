package com.hexaware.assetmanagement.dto;

public class UserDetailsDTO {
	private int id;
    private String name;
    private String email;
    private String contactNumber;
    private String department;
    private String role;
    
    // Only for employees
    private String designation;
    private String joinDate;

    public UserDetailsDTO() {}

    public UserDetailsDTO(int id,String name, String email, String contactNumber,
                          String department, String role, String designation, String joinDate) {
    	this.id=id;
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.department = department;
        this.role = role;
        this.designation = designation;
        this.joinDate = joinDate;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(String joinDate) {
		this.joinDate = joinDate;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id=id;
	}

    // Getters & Setters
    // ... (generate them using IDE)
}
