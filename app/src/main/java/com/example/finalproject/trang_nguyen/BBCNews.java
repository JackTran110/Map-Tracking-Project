package com.example.finalproject.trang_nguyen;

public class BBCNews {
    private  long id;
    private String title;
    private String data;
    private String date;
    private String link;
public BBCNews(){}
  public BBCNews(long id, String title, String data, String date, String link ){
    this.id=id;
    this.title=title;
    this.data=data;
    this.date=date;
    this.link=link;
}
    public void setId(long id){
        this.id=id;
    }
    public void setTitle(String title){
        this.title=title;
    }
    public void setData(String data){
        this.data=data;
    }
    public void setDate(String date){
        this.date=date;
    }
    public void setLink(String link){
        this.link=link;
    }
    public String getTitle(){return title;}
    public String getData(){return data; }
    public String getDate(){return date;}
    public String getLink(){return link;}
    public long getId(){return id;}
    public String toString(){
        return "BBC [title="+title+",link="+link+",description="+data
                +",date="+date+"]";
    }
}
