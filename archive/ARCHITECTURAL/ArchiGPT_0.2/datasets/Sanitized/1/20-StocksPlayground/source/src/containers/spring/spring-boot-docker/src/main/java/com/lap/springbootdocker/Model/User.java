package com.lap.springbootdocker.Model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "users")
public class User {

	@Id
	private ObjectId id;

	@Indexed(unique = true)
	private String username;

	private String name;
	private String surname;
	private String email;
	private String password;
	private String description;
	private HashSet<Role> roles = new HashSet<>();
	private HashSet<String> favorites = new HashSet<>();

	public User() {
	}

	public User(String username, String name, String surname, String email, String password) {
		this.username = username;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.description = "";
	}

	public Set<String> getFavorites() {
		return favorites;
	}

	public void setFavorites(Set<String> favorites) {
		this.favorites = (HashSet)favorites;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String d) {
		this.description = d;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = (HashSet)roles;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + '}';
	}
}
