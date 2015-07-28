/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package helloworld;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
/**
 *
 * @author ngthi
 */

public class HelloWorld {
    static List<String> linkList;
    static List<CommentInformation> listComment;
    static int MAX_ARTICLE = 10;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        //System.out.println("HelloWorld! \n");
        listComment = new ArrayList<CommentInformation>();
        linkList = new ArrayList<String>();
        GetLinkHTTP("http://www.tuoitre.vn/");
        //GetQuanTamNumber("http://tuoitre.vn/tin/kinh-te/20150726/ba-mai-kieu-lien-khong-con-la-chu-tich-hdqt-vinamilk/783399.html");
        //GetDateNews("http://tuoitre.vn/tin/kinh-te/20150726/ba-mai-kieu-lien-khong-con-la-chu-tich-hdqt-vinamilk/783399.html");
        //GetLikeNumber("http://tuoitre.vn/tin/kinh-te/20150726/ba-mai-kieu-lien-khong-con-la-chu-tich-hdqt-vinamilk/783399.html");
        //GetComment("http://tuoitre.vn/tin/giao-duc/20150728/tu-16-18-diem/784044.html");
    }
    
    public static void GetComment(String _link)
    {
        try
        {
            int strObject = GetObject(_link);
            String strTitle = GetTitle(_link);
            String linkComment = "http://cm.tuoitre.vn/comment/ttocreateiframe?app_id=6&app_url=tuoitre.vn&object_id="+ strObject +"&object_title=" + strTitle + "&layout=3" + "&offset=0";
            Document doc = Jsoup.connect(linkComment).get();
            Elements ele = doc.select("dd");
            System.out.println(ele.size());
            for (int i = 0; i < ele.size(); i++) {
                CommentInformation cm = new CommentInformation();
                cm.idNews = strObject;
                cm.CommentInfo  = ele.eq(i).select("p.cm-content").text();
                cm.LikeNumber = Integer.parseInt(ele.eq(i).select("span.like_number").text());
                cm.idComment = Integer.parseInt(ele.eq(i).select("div.block-tool > span.like_comment_div > a.like_btn").attr("id").replaceAll("[^0-9]", ""));
                listComment.add(cm);
                cm.GetInfo();
                System.out.println("------------------------------------------------");
            }
        }
        catch(Exception e)
        {
            return;
        }
    }
    
    public static void GetLinkHTTP (String _link)
    {
        if(linkList.size() > MAX_ARTICLE)
            {
                return;
            }
        try {
            Document doc = Jsoup.connect(_link).get();
            Elements links = doc.select("a[href]");
            
            for (int i = 0; i < links.size(); i++) {
                if (links.eq(i).attr("href").contains(".html") && links.eq(i).attr("href").contains("tuoitre.vn")
                        && !links.eq(i).attr("href").contains("google.com")&& !links.eq(i).attr("href").contains("www.facebook.com")
                        && !links.eq(i).attr("href").contains("chu-de")) {
                    Boolean isFind = true;
                    for (int j = 0; j < linkList.size(); j++) {
                        if(linkList.get(j).equals(links.eq(i).attr("href")))
                        {
                            isFind = false;
                            break;
                        }
                    }
                    if(isFind)
                    {
                        linkList.add(links.eq(i).attr("href"));
                        System.out.println(links.eq(i).attr("href") + "\n");
                        System.out.println("So luong bai co trong he thong: " + linkList.size());
                        Document tit = Jsoup.connect(links.eq(i).attr("href")).get();
                        System.out.println(tit.title() + "\n");
                        GetComment(links.eq(i).attr("href"));
                        GetLikeNumber(links.eq(i).attr("href"));
                        GetLinkHTTP(links.eq(i).attr("href"));
                        if(linkList.size() > MAX_ARTICLE)
            {
                break;
            }
                    }
                }
            }
        } catch (Exception ex) {
            return;
        }
    }
    
    public static void GetDateNews(String _link)
    {
        try
        {
            Document doc = Jsoup.connect(_link).get();
            Elements ele = doc.select("span.date");
            System.out.println(ele.eq(0).text());
        }
        catch(Exception e)
        {
            return;
        }
    }
    
    public static int GetCategory(String _link)
    {
        try
        {
            Document doc = Jsoup.connect(_link).get();
            Elements ele = doc.select("img[src]");
            String strSource = "";
            String strCategory = "";
            for (int i = 0; i < ele.size(); i++) {
                if (ele.eq(i).attr("src").contains("&cat=")) {
                    strSource = ele.eq(i).attr("src");
                    break;
                }
            }
            if (!strSource.equals("")) {
                strCategory = strSource.substring(strSource.indexOf("&cat="), strSource.indexOf("&obj"));
                strCategory = strCategory.replaceAll("[^0-9]", "");
            }
            
            System.out.println(strCategory);
            return Integer.parseInt(strCategory);
        }
        catch(Exception e)
        {
            return -1;
        }
    }
    
    public static String GetTitle(String _link)
    {
        try
        {
            Document doc = Jsoup.connect(_link).get();
            String strTitle = doc.title();
            return strTitle;
        }
        catch(Exception e)
        {
            return "";
        }
    }
    
    public static int GetObject(String _link)
    {
        try
        {
            Document doc = Jsoup.connect(_link).get();
            Elements ele = doc.select("img[src]");
            String strSource = "";
            String strObject = "";
            for (int i = 0; i < ele.size(); i++) {
                if (ele.eq(i).attr("src").contains("&cat=")) {
                    strSource = ele.eq(i).attr("src");
                    break;
                }
            }
            if (!strSource.equals("")) {
                strObject = strSource.substring(strSource.indexOf("&obj="), strSource.length());
                strObject = strObject.replaceAll("[^0-9]", "");
            }
            
            return Integer.parseInt(strObject);
        }
        catch(Exception e)
        {
            return -1;
        }
    }

    
    public static int GetQuanTamNumber(String _link)
    {
        try
        {
            int strCategory = GetCategory(_link);
            int strObject = GetObject(_link);
            
            String strTitle = GetTitle(_link);
            String linkQuanTam = "http://cm.tuoitre.vn/like/ttocreateiframe?app_id=6&app_id_tracker=1&cat_id=" + strCategory + "&object_id="+ strObject + "&object_title=" + strTitle +"&host=tuoitre.vn&user_agent=Mozilla/5.0%20%28Windows%20NT%2010.0;%20WOW64;%20rv:39.0%29%20Gecko/20100101%20Firefox/39.0&layout=2";
            Document docLinkQuanTam = Jsoup.connect(linkQuanTam).get();
            Elements eleQuanTam = docLinkQuanTam.select("span.sl");
            System.out.println(eleQuanTam.eq(0).text());
            return Integer.parseInt(eleQuanTam.eq(0).text());
            
            //String CommentInfo = "http://cm.tuoitre.vn/comment/ttocreateiframe?app_id=6&app_url=tuoitre.vn&object_id="+ strObject +"&object_title=" + strTitle + "&layout=3";
        }
        catch(Exception e)
        {
            return -1;
        }
    }
    
    public static void GetLikeNumber (String _link) throws IOException
    {
        //List
        Document doc = Jsoup.connect(_link).get();
        Elements links = doc.select("iframe[src]");
        String mylink = links.eq(0).attr("src");
        mylink = mylink.substring(0, mylink.indexOf(".html") + 5);
        Document doc1 = Jsoup.connect(mylink).get();
        Elements links1 = doc1.select("span#u_0_1");

        for (int i = 0; i < links1.size(); i++) {
            String number = links1.eq(i).text().replaceAll("[^0-9]", "");
            if(number.equals(""))
            {
                number += "0";
            }
            System.out.println("So luong like: "+ number + "\n");
            
        }
    }
}