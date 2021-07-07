package poem.by_type.Task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import poem.by_type.utils.FileWriter;
import poem.by_type.utils.GetAllUrl;
import poem.by_type.utils.GetAllUrlExcept;
import poem.by_type.utils.ParseDetailedUrl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
Jsoup解析爬虫训练
爬取古诗文网诗歌
https://so.gushiwen.cn/shiwens/
按诗歌类型划分
主要解析了诗歌标题，作者名，朝代，诗歌原文，诗歌类型和详情页url
总共12206首诗文*/
public class Task {
    public static void main(String[] args) throws Exception{
        //获取页面源码
        Document document = Jsoup.parse(new URL("https://so.gushiwen.cn/shiwens/"), 10000);
        //获取存储url的tag
        Elements elements = document.select("body > div.main3 > div.right > div:nth-child(2) > div.cont > a");

        //特殊情况：小学古诗 初中古诗 高中古诗 小学文言 初中文言 高中文言url不需要加上https://so.gushiwen.cn/shiwens/
        String exp = "小学古诗 初中古诗 高中古诗 小学文言 初中文言 高中文言" ;

        for (Element element:elements) {
            //便于解析得到的文件
            String all = "title*writer_name*dynasty*content*type*url\n";
            //获取名字
            String type_name = element.text();
            //存储文件路径
            String path = "D:\\Test\\Poem\\poems_by_type\\poems4.0\\" + type_name + ".txt";

            String type_url = "https://so.gushiwen.cn/" + element.attr("href");
            System.out.println("正在爬取: " + type_name + "\t" + type_url);
            //存储每类的每一首诗歌的url
            List<String> url_list = new ArrayList<String>();
            //如果这个类的古诗文件已经存在，就不再存储
            if (new File(path).exists()) {
                System.out.println(type_name + " exits!");
            } else {
                //获取每类的每一首诗歌的url
                if (exp.contains(type_name)) {
                    url_list = new GetAllUrlExcept().getUrl(type_url);
                } else {
                    url_list = new GetAllUrl().getUrl(type_url);
                }
                for (String url : url_list) {
                    //逐个解析并返回字符串
                    all = new ParseDetailedUrl().parseUrl(url, type_name, all);
                }
                new FileWriter().fw(all, path);
                System.out.println(type_name + "爬取完成！");
            }
        }
    }
}
