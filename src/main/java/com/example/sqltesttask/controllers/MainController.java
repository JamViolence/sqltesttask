package com.example.sqltesttask.controllers;

import com.example.sqltesttask.models.DBObject;
import com.example.sqltesttask.repository.DBObjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Controller
public class MainController {

    private List<DBObject> treeList;
    private HashMap<Integer, Integer> types;

    @Autowired
    private DBObjectRepository dbObjectRepository;

    @GetMapping("/")
    public String result(Model model) {
        dbObjectRepository.findAll().forEach(DBObject::setCorrectName);
        createTreeList();
        Set<Integer> keySet = types.keySet();
        model.addAttribute("dbObjects", treeList);
        model.addAttribute("types", types);
        model.addAttribute("keySet", keySet);
        return "result";
    }

    private void createTreeList() {
        treeList = new ArrayList<>();
        types = new HashMap<>();
        ArrayList<DBObject> list = new ArrayList<>(dbObjectRepository.findAll());
        for (DBObject dbObj: list) {
            if (dbObj.getParentObjectId() == 0) {
                treeList.add(dbObj);
                if (!types.containsKey(dbObj.getObjectType())) types.put(dbObj.getObjectType(), 1);
                else types.put(dbObj.getObjectType(), types.get(dbObj.getObjectType()) + 1);
                getChildren(dbObj, list, 0);
            }
        }
    }

    private void getChildren(DBObject dbObj, List<DBObject> list, int space) {
        space++;
        for (DBObject dbObj1: list) {
            if (dbObj1.getParentObjectId() == dbObj.getId()) {
                String str = "";
                for (int i = 0; i < space; i++) {
                    str += "__";
                }
                dbObj1.setName(str + dbObj1.getName());
                treeList.add(dbObj1);
                if (!types.containsKey(dbObj1.getObjectType())) types.put(dbObj1.getObjectType(), 1);
                else types.put(dbObj1.getObjectType(), types.get(dbObj1.getObjectType()) + 1);
                getChildren(dbObj1, list, space);
            }
        }
    }

}
