package poem.by_type.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;

/*解析每一首古诗页面的详情信息*/
public class ParseDetailedUrl {
    public String parseUrl(String url, String type, String all) throws Exception{
        //解析详情页url
        Document document = null;
        try {
             document = Jsoup.parse(new URL(url), 100000);
        } catch (Exception e) {
            System.out.println(url + "无法解析！");
        }
        //获取诗歌名字
        try {
            String title = document.select("#sonsyuanwen > div.cont > h1").text();
            all += title + "*";
        } catch (Exception e) {
            System.out.println(type + "中的title无法解析！");
        }

        //获取作者姓名
        try {
            String name = document.select("#sonsyuanwen > div.cont > p > a:nth-child(1)").text();
            all += name + "*";
        } catch (Exception e) {
            System.out.println(type + "中的name无法解析！");
        }

        //获取作者朝代
        try {
            String dynasty = document.select("#sonsyuanwen > div.cont > p > a:nth-child(2)").text().replace("〔", "")
                    .replace("〕", "");
            all += dynasty + "*";
        } catch (Exception e) {
            System.out.println(type + "中的dynasty无法解析！");
        }

        //获取诗歌原文
        try{
            String content = document.select("div.contson").first().text();
            all += content + "*";
        } catch (Exception e) {
            System.out.println(type + "中的content无法解析！" );
        }

        all += type + "*" + url + "\n";
        return all;
   }
}
