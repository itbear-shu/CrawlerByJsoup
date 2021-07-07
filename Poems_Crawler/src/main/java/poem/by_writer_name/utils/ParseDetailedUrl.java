package poem.by_writer_name.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.net.URL;

/*解析每一首古诗页面的详情信息*/
public class ParseDetailedUrl {
    public String parseUrl(String url, String type, String all) throws Exception{
        //存储本首诗歌info
        String part = "";
        int flag = 0;
        //解析详情页url
        Document document = null;
        try {
            document = Jsoup.parse(new URL(url), 100000);
        } catch (Exception e) {
            System.out.println(url + "无法解析！");
            flag = 1;
        }
        //获取诗歌名字
        try {
            String title = document.select("#sonsyuanwen > div.cont > h1").text();
            part += title + "*";
        } catch (Exception e) {
            System.out.println(type + "中的title无法解析！");
            flag = 1;
        }

        //获取作者姓名
        try {
            String name = document.select("#sonsyuanwen > div.cont > p > a:nth-child(1)").text();
            part += name + "*";
        } catch (Exception e) {
            System.out.println(type + "中的name无法解析！");
            flag = 1;
        }

        //获取作者朝代
        try {
            String dynasty = document.select("#sonsyuanwen > div.cont > p > a:nth-child(2)").text().replace("〔", "")
                    .replace("〕", "");
            part += dynasty + "*";
        } catch (Exception e) {
            System.out.println(type + "中的dynasty无法解析！");
            flag = 1;
        }

        //获取诗歌原文
        try{
            String content = document.select("div.contson").first().text();
            part += content + "*";
        } catch (Exception e) {
            System.out.println(type + "中的content无法解析！" );
            flag = 1;
        }

        if(flag == 1) {
            System.out.println("本次info不保存！");
        } else {
            all += part + url + "\n";
        }
        return all;
   }
}
