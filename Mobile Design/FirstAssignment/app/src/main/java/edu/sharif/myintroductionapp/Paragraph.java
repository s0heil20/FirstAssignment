package edu.sharif.myintroductionapp;

public class Paragraph {
    //public static ArrayList<Paragraph> allParagraphs = new ArrayList<Paragraph>();
    private String title;
    private String text;

    public Paragraph(String title, String text){
        this.title = title;
        this.text = text;
        //Paragraph.allParagraphs.add(this);
    }

    public String get_title(){
        return this.title;
    }

    public String get_text(){
        return this.text;
    }
}
