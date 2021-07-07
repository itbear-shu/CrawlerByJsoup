package poem.by_writer_name.Task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import poem.by_writer_name.utils.FileWriter;
import poem.by_writer_name.utils.GetAllUrl;
import poem.by_writer_name.utils.ParseDetailedUrl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/*
Jsoup解析爬虫训练
爬取古诗文网诗歌
https://so.gushiwen.cn/shiwens/
按诗歌作者划分
主要解析了诗歌标题，作者名，朝代，诗歌原文和详情页url
*/
public class Task {
    public static void main(String[] args) throws Exception{
        //获取页面源码
        Document document = Jsoup.parse(new URL("https://so.gushiwen.cn/shiwens/"), 10000);
        //获取存储url的tag
        Elements elements = document.select("#type2 > div.sright > a");

        //存储意外情况，如果某一类诗歌无法爬取，就先将其存储进exp，暂不爬取
        String exp = "" ;
        for (Element element:elements) {
            //用*将内容分割，便于读取
            String all = "title*writer_name*dynasty*content*url\n";
            //获取作者名
            String writer_name = element.text();
            //存储文件路径
            String path = "D:\\Test\\Poem\\poems_by_type\\poems4.0\\" + writer_name + ".txt";
            //获取该作者名对应页面的url
            String type_url = "https://so.gushiwen.cn/" + element.attr("href") + "&page=";
            System.out.println("正在爬取: " + writer_name);
            //存储每类的每一首诗歌的url
            List<String> url_list = new ArrayList<String>();
            //如果这个类的古诗文件已经存在，就不再存储
            if (exp.contains(writer_name) || new File(path).exists()) {
                System.out.println(writer_name + ".txt exits!");
            } else {
                //获取这类古诗里所有古诗的url列表
                url_list = new GetAllUrl().getUrl(type_url);
                for (String url : url_list) {
                    //逐个解析，全部为字符串形式
                    all = new ParseDetailedUrl().parseUrl(url, writer_name, all);
                }
                try {
                    new FileWriter().fw(all, path);
                } catch (Exception e) {
                    System.exit(0);
                }
                System.out.println(writer_name + "爬取完成！");
            }
        }
    }
}
