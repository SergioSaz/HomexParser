import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestRegExp {

    public static void main(String[] args) {

        String s = "fgh<div><h1>Напольная плитка Coliseum Gres Sicilia Россо Красный 45х45</h1>fghfh";
        Pattern p = Pattern.compile("(?<=<div><h1>).+?(?=</h1>)");
        Matcher m = p.matcher(s);
        if (m.find()) {
            System.out.println(m.group().trim());
        }
        ArrayList<String> list = new ArrayList<>();
        list.add("sa");
        System.out.println(list.size());
    }

}
