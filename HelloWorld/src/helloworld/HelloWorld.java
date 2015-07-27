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
    static int MAX_ARTICLE = 10;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        //System.out.println("HelloWorld! \n");
        //GetLikeNumber("http://tuoitre.vn/tin/kinh-te/20150726/ba-mai-kieu-lien-khong-con-la-chu-tich-hdqt-vinamilk/783399.html");
        linkList = new ArrayList<String>();
        GetLinkHTTP("http://www.tuoitre.vn/");
        
    }
    
    public static void GetLinkHTTP (String _link){
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
