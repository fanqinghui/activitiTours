package com.fqh.activitiDemo.variable;

import java.io.Serializable;

/**
 * @author fqh
 * @Description: ${todo}
 * @date 2020/4/6下午11:54
 */
public class Student implements Serializable {
    Long id;
    String name;

    public Student(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Student() {
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

    @Override
    public String toString() {
        return "Student{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}
