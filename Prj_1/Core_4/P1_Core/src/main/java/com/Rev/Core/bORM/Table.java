package com.Rev.Core.bORM;

import com.Rev.Core.Primitive.aList;

public class Table {
    private String tableName;
    private aList<Column> columns;

    public Table(String tableName) {
        this.tableName = tableName;
        this.columns = new aList();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public aList<Column> getColumns() {
        return columns;
    }

    private void setColumns(aList<Column> columns) {
        this.columns = columns;
    }

    //Method to return a Column object from the columns list with given index
    public Column get(int index) {
        return this.columns.get(index);
    }

    //Method to add column to table - returns false if fieldName already exists
    public boolean addColumn(Column column) {
        for (Column c : columns) {
            if (c.getFieldHash() == column.getFieldHash()) {
                return false;
            }
        }

        this.columns.append(column);
        return true;
    }

    //Method to return size of columns array list
    public int size() {
        return this.columns.getSize();
    }

    //Method to return a list of valid writeable fields
    public aList<Column> getWriteableFields() {
        //Create array list of columns
    	aList<Column> columns = new aList();

        //Iterate through table and add valid writeable columns to array list
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).isValidWriteField()) {
                columns.append(this.get(i));
            }
        }

        //Return the array list
        return columns;
    }

    //Method to return a list of valid readable fields
    public aList<Column> getReadableFields() {
        //Create array list of columns
    	aList<Column> columns = new aList();

        //Iterate through table and add valid readable columns to array list
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).isValidReadField()) {
                columns.append(this.get(i));
            }
        }

        //Return the array list
        return columns;
    }

    //Method to return whether the table holds a valid primary key field
    public boolean hasValidPrimaryKey() {
        return (this.getPrimaryKeyField() >= 0);
    }

    //Method to retrieve index of single primary key field
    //Returns -1 if not found
    public int getPrimaryKeyField() {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).isValidPrimaryKey()) {
                return i;
            }
        }

        return -1;
    }

    //Method to set columns to empty arraylist
    public void emptyColumns() {
        this.columns = new aList();
    }
}