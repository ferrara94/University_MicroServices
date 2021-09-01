package com.develop.webapp.entity.Student;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "STUDENTS")
@Data
public class Student implements Serializable {

	private static final long serialVersionUID = -3930581579277984029L;

	@Id
    private Long id;

    private String name;
    private String surname;
    
    private Date birthdate;

    private String university;
    
    private boolean active;

}
