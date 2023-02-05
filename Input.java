package report_management;

import java.util.Scanner;
import java.util.ArrayList;

public class Input {
    
    //入力時空白チェック
    public String inputBlankCheck(String item) { // 入力と空白チェック
        return inputBlankCheck(item,false,null); 
    }    
    public String inputBlankCheck(String item,boolean blank,String before) { // 入力と空白チェック
        Scanner input = new Scanner(System.in);
        while (true) {
            System.out.print(item + ":");
            if (before != null) { // 修正前項目があるときに表示
                System.out.print(before);
            }
            String inputContents = input.nextLine();
            if (inputContents.equals("")) { // 空欄チェック
                if (blank) { // 空欄trueなら空欄のまま返す
                    return inputContents;
                } else { // 空欄falseならエラーで再入力
                    System.out.println(noInputError());
                }
            } else {// 空欄以外は入力内容を返す
                return inputContents;
            }
        }
    }    
    // 入力用数値NG判別
    public String inputNumberCheck(String item){ //空白指定なければfalse
        return inputNumberCheck(item,false);
    }    
    public String inputNumberCheck(String item,boolean blank){ //beforeなければnull
        return inputNumberCheck(item,blank,null);
    }    
    
    public String inputNumberCheck(String item,boolean blank,String before){ //入力数値判断
        while (true) {
            String inputContents = inputBlankCheck(item,blank,before); 
            boolean exclusion = true;
            String error = null;
            switch (item) { //入力内容ごとに入れられない数字とエラー文を変更
                case (""): // メニュー
                if (!inputContents.equals("")) {
                    exclusion = Integer.parseInt(inputContents) > 0 && Integer.parseInt(inputContents) < 7;
                }
                    error = noMenu();
                    break;
                case ("名前"): // 組入力空白以外エラーなし
                    break;
                case ("英語"): // 点数入力
                case ("数学"):
                    if (!inputContents.equals("")) {
                        exclusion = Integer.parseInt(inputContents) >= 0 && Integer.parseInt(inputContents) <= 100;
                    }
                    error = scoreError();
                    break;
                default : // 他、id入力や組入力
                    if (!inputContents.equals("")) {
                        exclusion = Integer.parseInt(inputContents) > 0;
                    }
                    error = underZeroError();
                    break;
            }
            if (exclusion) { 
                return inputContents;
            } else {
                System.out.println(error);
            }
        }
    }

    // 検索機能2、4、5で利用
    public ArrayList<ReportData> ReportSeach(ArrayList<ReportData> reports,ArrayList<ReportData> search,String searchCondition) { // 2検索用
        while (true) {
            String searcthReport; //入力
            if (searchCondition.equals("名前")) { // 名前で検索する場合(2検索)
                searcthReport = inputNumberCheck("名前"); //入力
                for (ReportData report:reports) { // 名前一致でArryListに代入
                    if (searcthReport.equals(report.getName() )) {
                        search.add(report);
                    }
                }
            } else { // idで検索する場合(4修正)
                if (searchCondition.equals("id?")) {
                    searcthReport = inputNumberCheck("id?"); // 編集ID入力
                    for (ReportData report:reports) {
                        if (Integer.parseInt(searcthReport) == report.getId()) { //入力idと一致する生徒を検索
                            search.add(inputReport(report)); // 編集内容を代入
                            return search; //一致していれば代入してリターン
                        }
                    }
                } else {// idで検索する場合(5削除)
                    searcthReport = inputNumberCheck("id"); // 削除ID入力
                    for (ReportData report:reports) {
                        if (Integer.parseInt(searcthReport) == report.getId()) { //入力idと一致する生徒を検索
                            search.add(report); // 一致内容を代入
                            return search; //一致していれば代入してリターン
                        }
                    }
                }
            }
            if (search.isEmpty()) {
                System.out.println(notFound()); // 一致なしで見つかりません表示
            }
            return search;
        }
    }

    // 4修正関連のメソッド
    public ArrayList<ReportData> checkTheEdit(ArrayList<ReportData> reports){ //修正用のメニュー
        ArrayList<ReportData> edit = new ArrayList<ReportData>(); // 修正対象を入れる変数
        ArrayList<ReportData> search = new ArrayList<ReportData>(); // 検索結果を入れる変数
        search = ReportSeach(reports,search,"id?");
        if (!search. isEmpty()) { //search
            edit.add(search.get(0)); // 一致していれば編集内容を代入
        }
        return edit; //リターン
    }

    // レコード情報入力、3追加と4修正で利用
    public ReportData inputReport(int id) {// idがある場合は追加用、項目のみ表示
        ReportData addStudent = new ReportData();
        inputScore(addStudent,"組");
        inputScore(addStudent,"名前");
        inputScore(addStudent,"英語");
        inputScore(addStudent,"数学");
        addStudent.setId(id);
        return addStudent;
    }
    public ReportData inputReport(ReportData reports) { // レポートデータの場合は修正用、項目と変更前の値表示
        inputScore(reports,"クラス",true,"(" + reports.getClassNumber() + ")");
        inputScore(reports,"名前",true,"(" + reports.getName() + ")");
        inputScore(reports,"英語",true,"(" + reports.getEnglishScore() + ")");
        inputScore(reports,"数学",true,"(" + reports.getMathScore() + ")");
        return reports;
    }

    public void inputScore(ReportData reports,String item) { // 追加や修正用入力、空白と既存データ指定なければfalseとnullで渡す
        inputScore(reports,item,false,null);
    }
    public void inputScore(ReportData reports,String item,boolean blank,String before) { // 追加や修正用入力、
        String inputContents = inputNumberCheck(item,blank,before);
        if (!inputContents.equals("")) { //空欄時以外対応
            if (item.equals("名前")) { // 名前の場合はint変換なし
                reports.setName(inputContents);
            } else {
                int editContents = Integer.parseInt(inputContents);
                switch (item)  {
                    case ("クラス"):
                    case ("組"):
                        reports.setClassNumber(editContents); //代入
                        break;
                    case ("英語"):
                        reports.setEnglishScore(editContents); //代入
                        break;
                    case ("数学"):
                        reports.setMathScore(editContents); //代入
                        break;
                } 
            }
        }
    }
    // 削除メソッド
    public ArrayList<ReportData> deleteReport(ArrayList<ReportData> reports,ArrayList<ReportData> delete) {
        while (true) {
            ArrayList<ReportData> search = new ArrayList<ReportData>(); //　検索内容保管list
            search = ReportSeach(reports,search,"id");
            if (!search. isEmpty()) { //searchに要素あれば削除確認
                System.out.println(search.get(0).getContent());
                while (true) {
                    String inputConfirmatoin = inputBlankCheck("削除しますか?(y/n)");
                    switch (inputConfirmatoin) {
                        case ("y"):
                        case ("ｙ"):
                        case ("Y"):
                        case ("Ｙ"):
                            delete.add(search.get(0));
                            return delete; // 代入してリターン
                        case ("n"):
                        case ("ｎ"):
                        case ("N"):
                        case ("Ｎ"):
                            System.out.println(deletecCancel());
                            return delete; // 代入せずリターン
                        default:
                        System.out.println(errorYN());
                    }
                }
            }
            return delete; // 代入せずリターン
        }
    }

    // エラー表示のMethod
    public String noInputError() {
        return "何も入力されていません。";
    }
    public String underZeroError() {
        return "0以下が入力されました。";
    }
    public String noMenu() {
        return "メニューにない番号が入力されました。";
    }
    public String scoreError() {
        return "0〜100に該当しない数値が入力されました。";
    }
    public String notFound() {
        return "該当の生徒が見つかりませんでした。";
    }
    public String errorYN() {
        return "yかnを入力してください。";
    }
    public String deletecCancel() {
        return "削除をキャンセルします。";
    }
}
