package com.example.sqltesttask.models;

import javax.persistence.*;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


@Entity(name="objects")
public class DBObject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(name="object_type")
    private int objectType;
    @Column(name="parent_object_id")
    private int parentObjectId;
    @Column(name="uid")
    private String uid;
    @Column(name="data")
    private String data;
    @Transient
    private String name;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getObjectType() {
        return objectType;
    }

    public void setObjectType(int objectType) {
        this.objectType = objectType;
    }

    public int getParentObjectId() {
        return parentObjectId;
    }

    public void setParentObjectId(int parentObjectId) {
        this.parentObjectId = parentObjectId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setCorrectName() {
        JSONParser parser = new JSONParser();
        JSONObject jsonObj = null;
        try {
            jsonObj = (JSONObject) parser.parse(data);
            name = (String) jsonObj.get("name");
        } catch (ParseException | NullPointerException e) {
            e.printStackTrace();
        }
        if (name == null) name = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
