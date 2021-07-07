# Jsoup解析

## 准备

建立`maven`工程，在`pom.xml`中导入下列包。

```html
<dependencies>
<!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-log4j12 -->
        <!--日志-->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.25</version>
            <!--<scope>test</scope>-->
        </dependency>

<!-- https://mvnrepository.com/artifact/org.jsoup/jsoup -->
        <dependency>
            <groupId>org.jsoup</groupId>
            <artifactId>jsoup</artifactId>
            <version>1.13.1</version>
        </dependency>
        
<!-- https://mvnrepository.com/artifact/commons-io/commons-io -->
        <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.6</version>
        </dependency>
</dependencies>
```

## Jsoup

主要流程：`Jsoup.parse()`解析出`Document`对象，再有`Document`具体解析出`Elements`对象，最后获取`Elements`中的信息。

### Jsoup解析url

```java
//解析url地址，第一个参数是访问的url，第二个参数是访问的超时时间
//(即超时指定时间后就结束)
Document doc = Jsoup.parse(new URL("https://www.itcast.cn"), 10000);

//使用标签选择器,获取title标签中的内容
String text = doc.getElementsByTag("title").text();

//打印
System.out.println(text);
```

### Jsoup解析字符串

```java
//使用工具栏读取文件，获取字符串
String content = FileUtils.readFileToString(new File("C:\\Users\\test.html"), "utf-8");

//解析字符串
Document doc = Jsoup.parse(content);

Elements tag = doc.getElementsByTag("title");
System.out.println(tag.first().text());
```

### Jsoup解析文件

```java
//解析文件
Document doc = Jsoup.parse(new File("C:\\Users\\test.html"), "utf-8");

String title = doc.getElementsByTag("title").first().text();
System.out.println(title);
```

## Dom

### 使用Dom获取元素

```java
//解析文件，获取Document对象
Document doc = Jsoup.parse(new File("C:\\Users\\est.html"), "utf-8");
```

```java
//获取所有元素
Elements elementsByAll = doc.getAllElements();
//通过Id获取元素(只能获取一个Element)
Element elementById = doc.getElementById("city_bj");
//通过Tag获取元素
Elements elementsByTag = doc.getElementsByTag("span");
//通过Class获取元素
Elements elementsByClass = doc.getElementsByClass("class_a class_b");
//通过Attribute获取元素(属性名称)
Elements elementsByAttribute = doc.getElementsByAttribute("abc");

//通过AttributeValue获取元素(属性名称，属性值)
Elements elementsByAttributeValue = doc.getElementsByAttributeValue("href", "http://gz.itcast.cn");
```

## Element

获取元素element中的数据

```java
String str = "";//存储info
```

### 获取id

```java
str = element.id();
```

### 获取classname(s)

```java
str = element.className();
```

```java
Set<String> strings = element.classNames();//set集合
```

### 获取属性值attr

```java
str = element.attr("href");
```

### 获取attributes

```java
Attributes attributes = element.attributes();
```

### 获取文本内容text

```java
String text = element.text();
```

## select选择器

### 单独

tagname:通过标签查找，比如：**span**

```java
Elements elements = doc.select("span");
```

#id:通过id的值查找，比如：#city_bj(加了#)

```java
Elements elements = doc.select("#city_bj")
```

.class:通过class的值查找，比如：.class_a

```java
Elements elements = doc.select(".class_a")
```

[attribute]：通过属性名查找，比如：[abc]

```java
Elements elements = doc.select("[abc]");
```

\[atrr=value]:通过属性值查找，比如:[class=s_name]

```java
Elements elements = doc.select("[class=s_name]");
```

### **选择器组合**

el#id；元素+id

```java
Elements elements = doc.select("li#test");
```

el.class；元素＋class

```java
Elements elements = doc.select("span.s_name");
```

el[attr]:元素＋属性名

```java
Elements elements = doc.select("a[href]");
```

可以任意组合:比如:span.s_name[abc]

```java
Elements elements = doc.select("span.s_name[abc]");
```

ancestor child：查找某个元素的子元素，如(.city_con li)查找(city_con) 下所有li标签

```java
Elements elements = doc.select(".city_con li");
```

parent > child：查找某个父元素下的直接子元素，如：(.city_con > ul > li){查找city_con第一级（直接子元素）ul,再查找所有ul的第一级li}

```java
Elements elements = doc.select(".city_con > ul > li");
```

parent > *:查找某个父元素下所有的直接子元素

```java
Elements elements = doc.select(".city_con > *");
```

### tips

在浏览器中右键`检查`，定位标签后，右键`复制`-->`复制selector`可以直接复制出路径。

# 