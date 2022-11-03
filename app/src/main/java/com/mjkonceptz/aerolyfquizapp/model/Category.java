package com.mjkonceptz.aerolyfquizapp.model;


import androidx.annotation.NonNull;

/**
 * Author: Kemmy MO Jones
 * Project: AerolyfQuizApp
 * Date: 2022/07/21
 * Email: mjkonceptz@gmail.com
 * Copyright (c) 2022 MJKonceptz. All rights reserved.
 * CLASS ~ Category Data Fields
 */

@SuppressWarnings("unused")
public class Category {

    /* Constants: Quiz Topic Categories */
    public static final int PROGRAMMING = 1;
    public static final int MATHEMATICS = 2;
    public static final int COMPUTERSCIENCE = 3;
    public static final int GENERALKNOWLEDGE = 4;
    public static final int GEOGRAPHY = 5;
    public static final int SCIENCE = 6;
    public static final int ENTERTAINMENT = 7;

    /*VARIABLES: Global Variables */
    private int id;
    private String name;

    public Category() {} /* end: empty constructor ~ Category */

    public Category(String name) { this.name = name; } /*end: constructor with parameter */

    public int getId() { return id; }

    public void setId(int id) { this.id = id; } /*end: id */

    public String getName() { return name; }

    public void setName(String name) { this.name = name; } /*end: Name */


    @NonNull
    @Override
    public String toString() {
        return getName();
    } /*end: toString ~ Convert Name To String */


} /*end: CLASS ~ Category*/
