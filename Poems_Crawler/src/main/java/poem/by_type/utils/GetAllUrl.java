package poem.by_type.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
* 在某类古诗页面下获取所有古诗的url
* */
public class GetAllUrl {
    public List<String> getUrl(String url) throws Exception{
        Document document = Jsoup.parse(new URL(url), 10000);
        //获取typecont信息
        Elements typeconts = document.getElementsByClass("typecont");
        //创建List存储url
        List<String> list = new ArrayList<String>();
        //逐个解析所以url
        for (Element typecont : typeconts) {
            //获取spans
            Elements spans = typecont.getElementsByTag("span");
            //便利spans
            for (Element span : spans) {
                String p_url = "https://so.gushiwen.cn/" + span.select("a").attr("href");
                list.add(p_url);
            }
        }
        return list;
    }
}