/*
 * Copyright (c) 2017. MIT-license for Jari Van Melckebeke
 * Note that there was a lot of educational work in this project,
 * this project was (or is) used for an assignment from Realdolmen in Belgium.
 * Please just don't abuse my work
 */

package org.jarivm.relationGraph.domains;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.DateString;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author Jari Van Melckebeke
 * @since 20.09.16
 */
@Component
@NodeEntity
public class Employee {
	@GraphId
	private Long id;
	private String surname;
	private String name;
	private String email;
	private Character gender;
	private Long experience;
	private Long age;
	@DateString(value = "MM/dd/yyyy")
	private Date startDateOfWork;

//    public Date getStartDateOfWork() {
//        return startDateOfWork;
//    }
//
//    public void setStartDateOfWork(Date startDateOfWork) {
//        this.startDateOfWork = startDateOfWork;
//    }

	public Employee() {

	}

	public Employee(String name, String first_name) {
		this.surname = name;
		this.name = first_name;
	}

	//structure: name, surname,email, Gender, startDateOfWork, Age, experience,
	// total_efficiency, start_date_project,end_date_project, on_time, role_on_project,
	// n_of_project, deathline, name_of_project, version, cost, client_experience, client_name
	public Employee(String surname, String name, String email, Long age) {
		this.surname = surname;
		this.name = name;
		this.email = email;
		this.age = age;
//        this.startDateOfWork = startDateOfWork;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getExperience() {
		return experience;
	}

	public void setExperience(Long experience) {
		this.experience = experience;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getStartDateOfWork() {
		return startDateOfWork;
	}

	public void setStartDateOfWork(Date startDateOfWork) {
		this.startDateOfWork = startDateOfWork;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	@Override
	public String toString() {
		return "Employee{" +
				"id=" + id +
				", surname='" + surname + '\'' +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", gender=" + gender +
				", experience=" + experience +
				", age=" + age +
				", startDateOfWork=" + startDateOfWork +
				'}';
	}
}
