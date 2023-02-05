package report_management;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class Exec {

    public static void main(String[] args) {

        Charset charset = Charset.defaultCharset();//utf-8
        StandardOpenOption create = StandardOpenOption.CREATE;
        StandardOpenOption existing = StandardOpenOption.TRUNCATE_EXISTING;
        ArrayList<ReportData> reports = new ArrayList<ReportData>();
        try {
            BufferedReader readData = Files.newBufferedReader(Paths.get("reportcards.csv"),charset);
            String line;
            while ((line = readData.readLine()) != null) { // nullになるまで1行ずつ読み込み
                String[] cols = line.split(","); //カンマで分割
                ReportData tempReportData = new ReportData(); //各項目を代入
                tempReportData.setId(Integer.parseInt(cols[0]));
                tempReportData.setClassNumber(Integer.parseInt(cols[1]));
                tempReportData.setName(cols[2]);
                tempReportData.setEnglishScore(Integer.parseInt(cols[3]));
                tempReportData.setMathScore(Integer.parseInt(cols[4]));
                reports.add(tempReportData);
            }
        readData.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int lastId = 0; // 最後のIDを入れる変数
        try { // 過去に保存したiDファイルを読み込み
            BufferedReader readData = Files.newBufferedReader(Paths.get("lastId.csv"),charset);
            String line = readData.readLine();
                String[] cols = line.split(","); //カンマで分割
                lastId = (Integer.parseInt(cols[0])); //過去のlastIdファイルの最大idを代入
        readData.close();
        } catch (IOException e) { // lastiDファイルがない場合、データから最後のID算出
            for (int i = 0;i < reports.size()-1;i++) {
                lastId = Math.max(reports.get(i).getId(),reports.get(i+1).getId());
            }
        }

        menu:
        while (true) {// メニューループ
            System.out.println("1:一覧 2:検索 3:追加 4:修正 5:削除 6:保存して終了");
            Input menu = new Input();
            String inputMenu = menu.inputNumberCheck(""); //メニュー番号入力
            
            switch (inputMenu) {
                case ("1"):
                case ("１"):
                    System.out.println("一覧------");
                    for (ReportData report : reports) { // 一覧表示
                      System.out.println(report.getContent());
                    }
                    break;
                case ("2"):
                case ("２"):
                    System.out.println("検索------");
                    Input seachReport = new Input();
                    ArrayList<ReportData> search = new ArrayList<ReportData>();
                    seachReport.ReportSeach(reports,search,"名前"); // 検索結果を入れるList
                    for (ReportData report :search) { // 検索結果Listを表示、要素なしなら何も起きない
                        System.out.println(report.getContent());
                    }
                    break;
                case ("3"):
                case ("３"):
                    System.out.println("追加------");
                    ReportData addReport = new ReportData(); //追加情報を一時的に入れる変数
                    lastId += 1; //既存の最大idより大きくする
                    Input inputAdd = new Input(); 
                    addReport = inputAdd.inputReport(lastId); //追加メソッド呼び出し
                    reports.add(addReport);
                    System.out.println(addReport.getContent() + "を追加しました"); //キャンセルなしなので必ず追加表示
                    break;
                case ("4"):
                case ("４"):
                    System.out.println("修正------");
                    Input inputEdit = new Input(); 
                    ArrayList<ReportData> edit = new ArrayList<ReportData>(); // 修正対象を入れるList
                    edit = inputEdit.checkTheEdit(reports);
                    if (!edit.isEmpty()) { // Listがあれば削除
                        reports.set(reports.indexOf(edit.get(0)),edit.get(0));
                        System.out.println("編集しました");
                    }
                    break; // caseまでブレーク
                case ("5"):
                case ("５"):
                    System.out.println("削除------");
                    Input deleteReport = new Input();
                    ArrayList<ReportData> delete = new ArrayList<ReportData>(); // 削除対象を入れるList
                    delete = deleteReport.deleteReport(reports,delete); // 削除対象を入れるList
                    if (delete.size() != 0) { // Listにあれば削除
                        reports.remove(delete.get(0));
                        System.out.println("ID:" + delete.get(0).getId() + "\tを削除しました。");
                    }
                    break;
                case ("6"):
                case ("６"):
                    System.out.println("保存して終了------");
                    try {
                        BufferedWriter writeData = Files.newBufferedWriter(Paths.get("reportcards.csv"), charset,create,existing);
                        for (ReportData report:reports) {
                            writeData.write(report.getId() + ",");
                            writeData.write(report.getClassNumber() + ",");
                            writeData.write(report.getName() + ",");
                            writeData.write(report.getEnglishScore() + "," + report.getMathScore());
                            writeData.newLine();
                        }
                        writeData.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    try { // lastIndexの書き出し
                        BufferedWriter writeId = Files.newBufferedWriter(Paths.get("lastId.csv"), charset,create,existing);
                        writeId.write(lastId + ",");
                        writeId.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("保存しました。終了。");
                    break menu;
            }
        }
        
    }

}
