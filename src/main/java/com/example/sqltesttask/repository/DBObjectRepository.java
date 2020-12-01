package com.example.sqltesttask.repository;

import com.example.sqltesttask.models.DBObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DBObjectRepository extends JpaRepository<DBObject, Integer> {
}
