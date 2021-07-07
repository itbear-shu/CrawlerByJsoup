package poem.by_writer_name.utils;
/*存储*/
public class FileWriter {
    public void fw(String text, String outPath) {
        try {
            java.io.FileWriter fw = new java.io.FileWriter(outPath);

            fw.write(text);//写到内存

            fw.flush();//刷入硬盘

            fw.close();//关闭流
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
