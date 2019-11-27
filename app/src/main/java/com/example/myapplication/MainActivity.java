package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;

import org.apache.xml.serialize.XMLSerializer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;
import okhttp3.HttpUrl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Url;

import org.leibnizcenter.xml.TerseJson;
import org.leibnizcenter.xml.helpers.DomHelper;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {
    String xmlString;
    HttpURLConnection urlConnection = null;
    URL url;

    {
        try {
            url = new URL("http://www.hidoc.xyz/category/health-tips/");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    String userName = "test";
    String password = "password";



    String HTML = "";
    ProgressDialog pd;
    private static final TerseJson.WhitespaceBehaviour COMPACT_WHITE_SPACE = TerseJson.WhitespaceBehaviour.Compact;

    Document doc = null;
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;
//    String xml = ("<root>" +
//            "<!-- thïs ïs à cómmënt. -->" +
//            "  <el ampersand=\"    a &amp;    b\">" +
//            "    <selfClosing/>" +
//            "  </el>" +
//            "</root>");


    String xml = ("<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss version=\"2.0\"\n" +
            "\txmlns:content=\"http://purl.org/rss/1.0/modules/content/\"\n" +
            "\txmlns:wfw=\"http://wellformedweb.org/CommentAPI/\"\n" +
            "\txmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
            "\txmlns:atom=\"http://www.w3.org/2005/Atom\"\n" +
            "\txmlns:sy=\"http://purl.org/rss/1.0/modules/syndication/\"\n" +
            "\txmlns:slash=\"http://purl.org/rss/1.0/modules/slash/\"\n" +
            "\t>\n" +
            "\n" +
            "<channel>\n" +
            "\t<title>Health Tips &#8211; Hidoc Dr Blog</title>\n" +
            "\t<atom:link href=\"http://www.hidoc.xyz/category/health-tips/feed/\" rel=\"self\" type=\"application/rss+xml\" />\n" +
            "\t<link>http://www.hidoc.xyz</link>\n" +
            "\t<description>#1 Digital Platform &#124; Weekly Digital Updates</description>\n" +
            "\t<lastBuildDate>Wed, 25 Sep 2019 10:46:09 +0000</lastBuildDate>\n" +
            "\t<language>en-GB</language>\n" +
            "\t<sy:updatePeriod>\n" +
            "\thourly\t</sy:updatePeriod>\n" +
            "\t<sy:updateFrequency>\n" +
            "\t1\t</sy:updateFrequency>\n" +
            "\t<generator>https://wordpress.org/?v=5.2.3</generator>\n" +
            "\t<item>\n" +
            "\t\t<title>New Year’s Resolutions for Your Health</title>\n" +
            "\t\t<link>http://www.hidoc.xyz/2018/12/28/new-years-resolutions-for-your-health/</link>\n" +
            "\t\t\t\t<comments>http://www.hidoc.xyz/2018/12/28/new-years-resolutions-for-your-health/#respond</comments>\n" +
            "\t\t\t\t<pubDate>Fri, 28 Dec 2018 07:53:46 +0000</pubDate>\n" +
            "\t\t<dc:creator><![CDATA[Abhishek Dekhar]]></dc:creator>\n" +
            "\t\t\t\t<category><![CDATA[Health Tips]]></category>\n" +
            "\n" +
            "\t\t<guid isPermaLink=\"false\">http://www.hidoc.xyz/?p=4204</guid>\n" +
            "\t\t\t\t<description><![CDATA[<p>Here are a few New Year’s resolutions which can increase the overall quality of your health.</p>\n" +
            "<p>The post <a rel=\"nofollow\" href=\"http://www.hidoc.xyz/2018/12/28/new-years-resolutions-for-your-health/\">New Year’s Resolutions for Your Health</a> appeared first on <a rel=\"nofollow\" href=\"http://www.hidoc.xyz\">Hidoc Dr Blog</a>.</p>\n" +
            "]]></description>\n" +
            "\t\t\t\t\t\t<wfw:commentRss>http://www.hidoc.xyz/2018/12/28/new-years-resolutions-for-your-health/feed/</wfw:commentRss>\n" +
            "\t\t<slash:comments>0</slash:comments>\n" +
            "\t\t\t\t\t\t\t</item>\n" +
            "\t\t<item>\n" +
            "\t\t<title>Simple Tips to Stay Healthy this Winter</title>\n" +
            "\t\t<link>http://www.hidoc.xyz/2018/11/29/simple-tips-to-stay-healthy-this-winter/</link>\n" +
            "\t\t\t\t<comments>http://www.hidoc.xyz/2018/11/29/simple-tips-to-stay-healthy-this-winter/#respond</comments>\n" +
            "\t\t\t\t<pubDate>Thu, 29 Nov 2018 08:36:26 +0000</pubDate>\n" +
            "\t\t<dc:creator><![CDATA[Hidoc Health &#38; Wellness]]></dc:creator>\n" +
            "\t\t\t\t<category><![CDATA[Health Tips]]></category>\n" +
            "\n" +
            "\t\t<guid isPermaLink=\"false\">http://www.hidoc.xyz/?p=4198</guid>\n" +
            "\t\t\t\t<description><![CDATA[<p>Here are a few simple things you can do to stay healthy and well during the winter season.</p>\n" +
            "<p>The post <a rel=\"nofollow\" href=\"http://www.hidoc.xyz/2018/11/29/simple-tips-to-stay-healthy-this-winter/\">Simple Tips to Stay Healthy this Winter</a> appeared first on <a rel=\"nofollow\" href=\"http://www.hidoc.xyz\">Hidoc Dr Blog</a>.</p>\n" +
            "]]></description>\n" +
            "\t\t\t\t\t\t<wfw:commentRss>http://www.hidoc.xyz/2018/11/29/simple-tips-to-stay-healthy-this-winter/feed/</wfw:commentRss>\n" +
            "\t\t<slash:comments>0</slash:comments>\n" +
            "\t\t\t\t\t\t\t</item>\n" +
            "\t\t<item>\n" +
            "\t\t<title>How Firecrackers Affect Your Health</title>\n" +
            "\t\t<link>http://www.hidoc.xyz/2018/11/05/how-firecrackers-affect-your-health/</link>\n" +
            "\t\t\t\t<comments>http://www.hidoc.xyz/2018/11/05/how-firecrackers-affect-your-health/#respond</comments>\n" +
            "\t\t\t\t<pubDate>Mon, 05 Nov 2018 07:08:38 +0000</pubDate>\n" +
            "\t\t<dc:creator><![CDATA[Hidoc Health &#38; Wellness]]></dc:creator>\n" +
            "\t\t\t\t<category><![CDATA[Health Tips]]></category>\n" +
            "\n" +
            "\t\t<guid isPermaLink=\"false\">http://www.hidoc.xyz/?p=4194</guid>\n" +
            "\t\t\t\t<description><![CDATA[<p>Here are some health-related reasons why you should give up bursting crackers this year. </p>\n" +
            "<p>The post <a rel=\"nofollow\" href=\"http://www.hidoc.xyz/2018/11/05/how-firecrackers-affect-your-health/\">How Firecrackers Affect Your Health</a> appeared first on <a rel=\"nofollow\" href=\"http://www.hidoc.xyz\">Hidoc Dr Blog</a>.</p>\n" +
            "]]></description>\n" +
            "\t\t\t\t\t\t<wfw:commentRss>http://www.hidoc.xyz/2018/11/05/how-firecrackers-affect-your-health/feed/</wfw:commentRss>\n" +
            "\t\t<slash:comments>0</slash:comments>\n" +
            "\t\t\t\t\t\t\t</item>\n" +
            "\t\t<item>\n" +
            "\t\t<title>Diwali 2018: Eat Healthy, Eat Safe</title>\n" +
            "\t\t<link>http://www.hidoc.xyz/2018/11/01/diwali-2018-eat-healthy-eat-safe/</link>\n" +
            "\t\t\t\t<comments>http://www.hidoc.xyz/2018/11/01/diwali-2018-eat-healthy-eat-safe/#respond</comments>\n" +
            "\t\t\t\t<pubDate>Thu, 01 Nov 2018 07:55:52 +0000</pubDate>\n" +
            "\t\t<dc:creator><![CDATA[Hidoc Health &#38; Wellness]]></dc:creator>\n" +
            "\t\t\t\t<category><![CDATA[Health Tips]]></category>\n" +
            "\n" +
            "\t\t<guid isPermaLink=\"false\">http://www.hidoc.xyz/?p=4190</guid>\n" +
            "\t\t\t\t<description><![CDATA[<p>Here are some food-related tips to help you enjoy a healthy and safe Diwali.</p>\n" +
            "<p>The post <a rel=\"nofollow\" href=\"http://www.hidoc.xyz/2018/11/01/diwali-2018-eat-healthy-eat-safe/\">Diwali 2018: Eat Healthy, Eat Safe</a> appeared first on <a rel=\"nofollow\" href=\"http://www.hidoc.xyz\">Hidoc Dr Blog</a>.</p>\n" +
            "]]></description>\n" +
            "\t\t\t\t\t\t<wfw:commentRss>http://www.hidoc.xyz/2018/11/01/diwali-2018-eat-healthy-eat-safe/feed/</wfw:commentRss>\n" +
            "\t\t<slash:comments>0</slash:comments>\n" +
            "\t\t\t\t\t\t\t</item>\n" +
            "\t\t<item>\n" +
            "\t\t<title>Diwali 2018: Skin Care for the Festive Season</title>\n" +
            "\t\t<link>http://www.hidoc.xyz/2018/10/26/4186/</link>\n" +
            "\t\t\t\t<comments>http://www.hidoc.xyz/2018/10/26/4186/#respond</comments>\n" +
            "\t\t\t\t<pubDate>Fri, 26 Oct 2018 07:38:39 +0000</pubDate>\n" +
            "\t\t<dc:creator><![CDATA[Hidoc Health &#38; Wellness]]></dc:creator>\n" +
            "\t\t\t\t<category><![CDATA[Health Tips]]></category>\n" +
            "\n" +
            "\t\t<guid isPermaLink=\"false\">http://www.hidoc.xyz/?p=4186</guid>\n" +
            "\t\t\t\t<description><![CDATA[<p>Here are some skin care tips, that will help protect your skin during the Diwali season.</p>\n" +
            "<p>The post <a rel=\"nofollow\" href=\"http://www.hidoc.xyz/2018/10/26/4186/\">Diwali 2018: Skin Care for the Festive Season</a> appeared first on <a rel=\"nofollow\" href=\"http://www.hidoc.xyz\">Hidoc Dr Blog</a>.</p>\n" +
            "]]></description>\n" +
            "\t\t\t\t\t\t<wfw:commentRss>http://www.hidoc.xyz/2018/10/26/4186/feed/</wfw:commentRss>\n" +
            "\t\t<slash:comments>0</slash:comments>\n" +
            "\t\t\t\t\t\t\t</item>\n" +
            "\t\t<item>\n" +
            "\t\t<title>Health Benefits of a Vegetarian Diet</title>\n" +
            "\t\t<link>http://www.hidoc.xyz/2018/10/16/health-benefits-of-a-vegetarian-diet/</link>\n" +
            "\t\t\t\t<comments>http://www.hidoc.xyz/2018/10/16/health-benefits-of-a-vegetarian-diet/#respond</comments>\n" +
            "\t\t\t\t<pubDate>Tue, 16 Oct 2018 09:08:27 +0000</pubDate>\n" +
            "\t\t<dc:creator><![CDATA[Hidoc Health &#38; Wellness]]></dc:creator>\n" +
            "\t\t\t\t<category><![CDATA[Health Tips]]></category>\n" +
            "\n" +
            "\t\t<guid isPermaLink=\"false\">http://www.hidoc.xyz/?p=4182</guid>\n" +
            "\t\t\t\t<description><![CDATA[<p>If you want to improve your health, adopting a vegetarian diet may be one of the best things you can do. Here are some of the health benefits of becoming a vegetarian.</p>\n" +
            "<p>The post <a rel=\"nofollow\" href=\"http://www.hidoc.xyz/2018/10/16/health-benefits-of-a-vegetarian-diet/\">Health Benefits of a Vegetarian Diet</a> appeared first on <a rel=\"nofollow\" href=\"http://www.hidoc.xyz\">Hidoc Dr Blog</a>.</p>\n" +
            "]]></description>\n" +
            "\t\t\t\t\t\t<wfw:commentRss>http://www.hidoc.xyz/2018/10/16/health-benefits-of-a-vegetarian-diet/feed/</wfw:commentRss>\n" +
            "\t\t<slash:comments>0</slash:comments>\n" +
            "\t\t\t\t\t\t\t</item>\n" +
            "\t\t<item>\n" +
            "\t\t<title>Which Type of Meditation is Best for You?</title>\n" +
            "\t\t<link>http://www.hidoc.xyz/2018/10/04/which-type-of-meditation-is-best-for-you/</link>\n" +
            "\t\t\t\t<comments>http://www.hidoc.xyz/2018/10/04/which-type-of-meditation-is-best-for-you/#comments</comments>\n" +
            "\t\t\t\t<pubDate>Thu, 04 Oct 2018 08:20:31 +0000</pubDate>\n" +
            "\t\t<dc:creator><![CDATA[Hidoc Health &#38; Wellness]]></dc:creator>\n" +
            "\t\t\t\t<category><![CDATA[Health Tips]]></category>\n" +
            "\n" +
            "\t\t<guid isPermaLink=\"false\">http://www.hidoc.xyz/?p=4178</guid>\n" +
            "\t\t\t\t<description><![CDATA[<p>Meditation has been proven to reduce stress, improve health, and increase inner peace. Learn about some popular types of meditation techniques.</p>\n" +
            "<p>The post <a rel=\"nofollow\" href=\"http://www.hidoc.xyz/2018/10/04/which-type-of-meditation-is-best-for-you/\">Which Type of Meditation is Best for You?</a> appeared first on <a rel=\"nofollow\" href=\"http://www.hidoc.xyz\">Hidoc Dr Blog</a>.</p>\n" +
            "]]></description>\n" +
            "\t\t\t\t\t\t<wfw:commentRss>http://www.hidoc.xyz/2018/10/04/which-type-of-meditation-is-best-for-you/feed/</wfw:commentRss>\n" +
            "\t\t<slash:comments>2</slash:comments>\n" +
            "\t\t\t\t\t\t\t</item>\n" +
            "\t\t<item>\n" +
            "\t\t<title>8 Foods that Help Reduce Acidity</title>\n" +
            "\t\t<link>http://www.hidoc.xyz/2018/09/12/8-foods-that-help-reduce-acidity/</link>\n" +
            "\t\t\t\t<comments>http://www.hidoc.xyz/2018/09/12/8-foods-that-help-reduce-acidity/#comments</comments>\n" +
            "\t\t\t\t<pubDate>Wed, 12 Sep 2018 11:40:34 +0000</pubDate>\n" +
            "\t\t<dc:creator><![CDATA[Hidoc Health &#38; Wellness]]></dc:creator>\n" +
            "\t\t\t\t<category><![CDATA[Health Tips]]></category>\n" +
            "\n" +
            "\t\t<guid isPermaLink=\"false\">http://www.hidoc.xyz/?p=4174</guid>\n" +
            "\t\t\t\t<description><![CDATA[<p>Acidity is a common problem that affects people of all ages. It occurs when there is an excess production of acid by the gastric glands <a class=\"mh-excerpt-more\" href=\"http://www.hidoc.xyz/2018/09/12/8-foods-that-help-reduce-acidity/\" title=\"8 Foods that Help Reduce Acidity\">[...]</a></p>\n" +
            "<p>The post <a rel=\"nofollow\" href=\"http://www.hidoc.xyz/2018/09/12/8-foods-that-help-reduce-acidity/\">8 Foods that Help Reduce Acidity</a> appeared first on <a rel=\"nofollow\" href=\"http://www.hidoc.xyz\">Hidoc Dr Blog</a>.</p>\n" +
            "]]></description>\n" +
            "\t\t\t\t\t\t<wfw:commentRss>http://www.hidoc.xyz/2018/09/12/8-foods-that-help-reduce-acidity/feed/</wfw:commentRss>\n" +
            "\t\t<slash:comments>1</slash:comments>\n" +
            "\t\t\t\t\t\t\t</item>\n" +
            "\t\t<item>\n" +
            "\t\t<title>8 Natural Ways to Lower Your Blood Pressure</title>\n" +
            "\t\t<link>http://www.hidoc.xyz/2018/08/14/8-natural-ways-to-lower-your-blood-pressure/</link>\n" +
            "\t\t\t\t<comments>http://www.hidoc.xyz/2018/08/14/8-natural-ways-to-lower-your-blood-pressure/#respond</comments>\n" +
            "\t\t\t\t<pubDate>Tue, 14 Aug 2018 06:31:45 +0000</pubDate>\n" +
            "\t\t<dc:creator><![CDATA[Hidoc Health &#38; Wellness]]></dc:creator>\n" +
            "\t\t\t\t<category><![CDATA[Health Tips]]></category>\n" +
            "\n" +
            "\t\t<guid isPermaLink=\"false\">http://www.hidoc.xyz/?p=4163</guid>\n" +
            "\t\t\t\t<description><![CDATA[<p>High blood pressure can lead to severe health problems, including heart attack and stroke. Fortunately, you can bring down blood pressure naturally by following a healthy lifestyle. Here are some tips.</p>\n" +
            "<p>The post <a rel=\"nofollow\" href=\"http://www.hidoc.xyz/2018/08/14/8-natural-ways-to-lower-your-blood-pressure/\">8 Natural Ways to Lower Your Blood Pressure</a> appeared first on <a rel=\"nofollow\" href=\"http://www.hidoc.xyz\">Hidoc Dr Blog</a>.</p>\n" +
            "]]></description>\n" +
            "\t\t\t\t\t\t<wfw:commentRss>http://www.hidoc.xyz/2018/08/14/8-natural-ways-to-lower-your-blood-pressure/feed/</wfw:commentRss>\n" +
            "\t\t<slash:comments>0</slash:comments>\n" +
            "\t\t\t\t\t\t\t</item>\n" +
            "\t\t<item>\n" +
            "\t\t<title>Top Tips to Stay Healthy this Rainy Season</title>\n" +
            "\t\t<link>http://www.hidoc.xyz/2018/07/10/top-tips-to-stay-healthy-this-rainy-season/</link>\n" +
            "\t\t\t\t<comments>http://www.hidoc.xyz/2018/07/10/top-tips-to-stay-healthy-this-rainy-season/#comments</comments>\n" +
            "\t\t\t\t<pubDate>Tue, 10 Jul 2018 10:16:33 +0000</pubDate>\n" +
            "\t\t<dc:creator><![CDATA[Hidoc Health &#38; Wellness]]></dc:creator>\n" +
            "\t\t\t\t<category><![CDATA[Health Tips]]></category>\n" +
            "\n" +
            "\t\t<guid isPermaLink=\"false\">http://www.hidoc.xyz/?p=4151</guid>\n" +
            "\t\t\t\t<description><![CDATA[<p>The monsoon rains bring relief from the summer heat, but also give rise to several health problems and diseases. Here are a few tips and precautions you can take to stay safe and healthy during the rainy season.</p>\n" +
            "<p>The post <a rel=\"nofollow\" href=\"http://www.hidoc.xyz/2018/07/10/top-tips-to-stay-healthy-this-rainy-season/\">Top Tips to Stay Healthy this Rainy Season</a> appeared first on <a rel=\"nofollow\" href=\"http://www.hidoc.xyz\">Hidoc Dr Blog</a>.</p>\n" +
            "]]></description>\n" +
            "\t\t\t\t\t\t<wfw:commentRss>http://www.hidoc.xyz/2018/07/10/top-tips-to-stay-healthy-this-rainy-season/feed/</wfw:commentRss>\n" +
            "\t\t<slash:comments>2</slash:comments>\n" +
            "\t\t\t\t\t\t\t</item>\n" +
            "\t</channel>\n" +
            "</rss>");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//
//        StrictMode.setThreadPolicy(policy);

        new Connection().execute();
    }


    private class Connection extends AsyncTask {

        @Override
        protected Object doInBackground(Object... arg0) {
            connect();
            return null;
        }

        private void connect() {

            Thread thread = new Thread(new Runnable() {

                @Override
                public void run() {
                    try {

//=========================================================
                        XmlToJson xmlToJson = new XmlToJson.Builder(xml).build();

// convert to a JSONObject
                        JSONObject jsonObject = xmlToJson.toJson();
//                        System.out.println("jjjjjjjjjjjjjjjjjjjjjj++++++++++++"+jsonObject.toString());

                        JSONObject rss = jsonObject.getJSONObject("rss");
                        System.out.println("rss++++++++++++"+rss.toString());

                        JSONObject channel = rss.getJSONObject("channel");
                        System.out.println("channel++++++++++++"+channel.toString());

                        JSONArray item = channel.getJSONArray("item");
                        System.out.println("item.length++++++++++++"+item.length());
                        System.out.println("item.tostring---"+item.toString());

                        for (int i=0; i<item.length(); i++) {
                            JSONObject value = item.getJSONObject(i);
                            String description = value.getString("description");
                            System.out.println("description++++++++++++"+description);
                            String title = value.getString("title");
                            System.out.println("title++++++++++++"+title);

                        }
//===============================================================


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }

    }

    static class Page {
        String content;

        Page(String content) {
            this.content = content;
        }
    }

    static final class PageAdapter implements Converter<ResponseBody, MainActivity.Page> {
        static final Converter.Factory FACTORY = new Converter.Factory() {
            @Override
            public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                if (type == MainActivity.Page.class) return new MainActivity.PageAdapter();
                return null;
            }
        };

        @Override
        public MainActivity.Page convert(ResponseBody responseBody) throws IOException {
            Document document = Jsoup.parse(responseBody.string());
            Element value = document.select("script").get(1);
            System.out.println("------------------"+value);

            String content = value.html();
            return new MainActivity.Page(content);
        }
    }

    interface PageService {
        @GET
        Call<MainActivity.Page> get(@Url HttpUrl url);
    }


}