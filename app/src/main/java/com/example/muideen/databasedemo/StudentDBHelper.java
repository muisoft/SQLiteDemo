package com.example.muideen.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adewale on 12/6/2016.
 */
public class StudentDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="student.db";
    private static final int SCHEMA_VERSION=1;
    private static final String[] STUDENT_COLUMNS=new String[]{
            StudColumn._ID,
            StudColumn.STUDENT_MATRIC,
            StudColumn.STUDENT_NAME,
            StudColumn.STUDENT_AGE
    };
    private static final String CREATE_SQL_ENTRIES= "CREATE TABLE "
            +StudColumn.STUDENT_TABLE + " ("
            +StudColumn._ID + " INTEGER PRIMARY KEY AUTOINCREAMENT, "
            +StudColumn.STUDENT_MATRIC + "TEXT, "
            +StudColumn.STUDENT_NAME + "TEXT, "
            +StudColumn.STUDENT_AGE + "INTEGER )";
    private static final String DELETE_SQL_ENTRIES="DROP TABLE IF EXISTS "
            +StudColumn.STUDENT_TABLE;

    public StudentDBHelper(Context context) {
        super(context,DATABASE_NAME,null, SCHEMA_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
       sqLiteDatabase.execSQL(CREATE_SQL_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
       sqLiteDatabase.execSQL(DELETE_SQL_ENTRIES);
       onCreate(sqLiteDatabase);
    }
    //Adding new student record from database
    private void addStudent(Student student) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(StudColumn.STUDENT_MATRIC, student.getMatricNo());
        contentValues.put(StudColumn.STUDENT_NAME, student.getName());
        contentValues.put(StudColumn.STUDENT_AGE, student.getAge());
        database.insert(StudColumn.STUDENT_TABLE, null, contentValues);
        database.close();
    }
    //Getting a student record from database
    private Student getStudent(String matric){
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.query(StudColumn.STUDENT_TABLE,STUDENT_COLUMNS,
                                     StudColumn.STUDENT_MATRIC + "=?",
                                     new String[]{matric},null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();
        }
        String matricNo=cursor.getString(cursor.getColumnIndexOrThrow(StudColumn.STUDENT_MATRIC));
        String name=cursor.getString(cursor.getColumnIndexOrThrow(StudColumn.STUDENT_NAME));
        int age=cursor.getInt(cursor.getColumnIndexOrThrow(StudColumn.STUDENT_AGE));

        return new Student(matricNo,name,age);
    }
    //Getting all student record
    private List<Student> getAllStudent(){
        List<Student> students=new ArrayList<>();
        Student student;
        SQLiteDatabase database=this.getReadableDatabase();
        Cursor cursor=database.query(StudColumn.STUDENT_TABLE,STUDENT_COLUMNS,null,null,null,null,null);
        if(cursor!=null){
            //If there is student record in the database iterate through it and add it to student list
            do{
                String matricNo=cursor.getString(cursor.getColumnIndexOrThrow(StudColumn.STUDENT_MATRIC));
                String name=cursor.getString(cursor.getColumnIndexOrThrow(StudColumn.STUDENT_NAME));
                int age=cursor.getInt(cursor.getColumnIndexOrThrow(StudColumn.STUDENT_AGE));
                student=new Student(matricNo,name,age);
                students.add(student);
            }while (cursor.moveToFirst());
        }
        //After filling up our student list then return it
        return students;
    }
    //Deleting a student record
    private void deleteStudent(String matric){
       SQLiteDatabase database=this.getWritableDatabase();
       database.delete(StudColumn.STUDENT_TABLE,StudColumn.STUDENT_MATRIC + " = ?",new String[]{matric});
       database.close();
    }
    //Updating student record
    private void updateStudent(Student student){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(StudColumn.STUDENT_MATRIC,student.getMatricNo());
        contentValues.put(StudColumn.STUDENT_NAME,student.getName());
        contentValues.put(StudColumn.STUDENT_AGE,student.getAge());
        database.update(StudColumn.STUDENT_TABLE,contentValues,StudColumn.STUDENT_MATRIC + " = ?",new String[]{student.getMatricNo()});
    }
    public interface StudColumn extends BaseColumns{
        public static final String STUDENT_TABLE="students";
        public static final String STUDENT_MATRIC="matric";
        public static final String STUDENT_NAME="name";
        public static final String STUDENT_AGE="age";
    }

}
