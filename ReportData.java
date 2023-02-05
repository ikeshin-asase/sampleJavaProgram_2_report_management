package report_management;

import java.util.Scanner;

public class ReportData {
    
    //Field
    private int id; //自動生成
    private int classNumber; //組
    private String name; // 名前
    private int englishScore; // 英語点数
    private int mathScore; // 英語点数
    
    //Constructor
    public ReportData() {
    }
    
    //Setter
    public void setId(int id) {
        this.id = id;
    }
    public void setClassNumber(int classNumber) {
        this.classNumber = classNumber;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEnglishScore(int englishScore) {
        this.englishScore = englishScore;
    }
    public void setMathScore(int mathScore) {
        this.mathScore = mathScore;
    }

    //Getter
    public int getId() {
        return id;
    }
    public int getClassNumber() {
        return classNumber;
    }
    public String getName() {
        return name;
    }
    public int getEnglishScore() {
        return englishScore;
    }
    public int getMathScore() {
        return mathScore;
    }
    public String getContent() { // Studentの全データをStringで返す
        String date = "ID:" + this.getId() + "\t" + this.getClassNumber() + "組" + "\t" + this.getName() + "\t" + "英語" + this.getEnglishScore() + "点\t数学" + this.getMathScore() + "点";
        return date;
    }
    


}
