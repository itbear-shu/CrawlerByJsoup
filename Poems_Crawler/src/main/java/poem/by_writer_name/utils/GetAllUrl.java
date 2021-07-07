package poem.by_writer_name.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
 * 在某类古诗页面下获取所有古诗的url
 * */
public class GetAllUrl {
    public List<String> getUrl(String url) throws Exception {
        //创建List存储url
        List<String> list = new ArrayList<String>();
        //逐个解析每一页（由于网站保护机制，最多访问十个页面）
        for (int i = 1; i <= 10; i++) {
            String new_url = url + i;
            //解析
            Document document = Jsoup.parse(new URL(new_url), 10000);
            //获取element信息
            Elements elements = document.getElementsByClass("cont");
            //逐个解析所有url
            try {
                //部分作者诗歌少于10页，所有为防止出现异常而终止程序，使用try catch保护机制
                for (int j = 1; j < 11; j++) {
                    String p_url = "https://so.gushiwen.cn/" + elements.get(j).select("p:nth-child(2) > a").attr("href");
                    list.add(p_url);
                }
            } catch (Exception e) {
            }
        }
        //返回所有诗歌url
        return list;
    }
}