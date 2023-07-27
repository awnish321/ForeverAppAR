package foreverappar.com.dynamics;

import java.io.Serializable;

public class ChapterModel implements Serializable {


    private String Class_Name;
    private String Subject_Name;
    public String Book_Name;
    private String School_UI;
    private String Restrict_SD;
    private String Class_ID;
    private String Message;

    private String DataSet_Name;


    private String Chapter_Id;
    private String Chapter_Name;
    private String Assessment_Name;
    private String Video_Name;
    private String DB_Name;
    public String Zip_Name;
    private String Download_Link;
    private String Download_Status;
    private String Assesment_Value;


    public String getClass_Name() {

        return Class_Name;
    }

    public ChapterModel(String class_Name, String subject_Name,String book_Name, String school_UI, String restrict_SD, String class_ID, String message,
                        String chapter_Id, String chapter_Name, String assessment_Name, String video_Name, String DB_Name,
                        String download_Link, String Download_Status, String zip_Name,String assesment_Value, String dataSet_Name) {
        this.Class_Name = class_Name;
        this.Subject_Name = subject_Name;
        this.Book_Name=book_Name;
        this.School_UI = school_UI;
        this.Restrict_SD = restrict_SD;
        this.Class_ID = class_ID;
        this.Message = message;
        this.Chapter_Id = chapter_Id;
        this.Chapter_Name = chapter_Name;
        this.Assessment_Name = assessment_Name;
        this.Video_Name = video_Name;
        this.DB_Name = DB_Name;
        this.Zip_Name = zip_Name;
        this.Download_Link = download_Link;
        this.Download_Status=Download_Status;
        this.DataSet_Name=dataSet_Name;
        this.Assesment_Value=assesment_Value;
    }

    public String getDownload_Status() {
        return Download_Status;
    }

    public void setDownload_Status(String download_Status) {
        Download_Status = download_Status;
    }

    public void setClass_Name(String class_Name) {
        Class_Name = class_Name;
    }

    public String getSubject_Name() {
        return Subject_Name;
    }

    public void setSubject_Name(String subject_Name) {
        Subject_Name = subject_Name;
    }

    public String getBook_Name() {
        return Book_Name;
    }

    public void setBook_Name(String book_Name) {
        Book_Name = book_Name;
    }

    public String getSchool_UI() {
        return School_UI;
    }

    public void setSchool_UI(String school_UI) {
        School_UI = school_UI;
    }

    public String getRestrict_SD() {
        return Restrict_SD;
    }

    public void setRestrict_SD(String restrict_SD) {
        Restrict_SD = restrict_SD;
    }

    public String getClass_ID() {
        return Class_ID;
    }

    public void setClass_ID(String class_ID) {
        Class_ID = class_ID;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getChapter_Id() {
        return Chapter_Id;
    }

    public void setChapter_Id(String chapter_Id) {
        Chapter_Id = chapter_Id;
    }

    public String getChapter_Name() {
        return Chapter_Name;
    }

    public void setChapter_Name(String chapter_Name) {
        Chapter_Name = chapter_Name;
    }

    public String getAssessment_Name() {
        return Assessment_Name;
    }

    public void setAssessment_Name(String assessment_Name) {
        Assessment_Name = assessment_Name;
    }

    public String getVideo_Name() {
        return Video_Name;
    }

    public void setVideo_Name(String video_Name) {
        Video_Name = video_Name;
    }

    public String getDB_Name() {
        return DB_Name;
    }

    public void setDB_Name(String DB_Name) {
        this.DB_Name = DB_Name;
    }

    public String getZip_Name()
    {
        return Zip_Name;
    }

    public void setZip_Name(String zip_Name)
    {
        Zip_Name = zip_Name;
    }

    public String getDownload_Link() {
        return Download_Link;
    }

    public void setDownload_Link(String download_Link) {
        Download_Link = download_Link;
    }

    public String getDataSet_Name() {
        return DataSet_Name;
    }

    public void setDataSet_Name(String dataSet_Name) {
        DataSet_Name = dataSet_Name;
    }

    public String getAssesment_Value() {
        return Assesment_Value;
    }

    public void setAssesment_Value(String assesment_Value) {
        Assesment_Value = assesment_Value;
    }



}
