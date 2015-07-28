/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helloworld;

/**
 *
 * @author ngthi
 */
public class CommentInformation {
    public int idNews;
    public int idComment;
    public String CommentInfo;
    public int LikeNumber;
    public CommentInformation()
    {
    }
    
    public void GetInfo()
    {
        System.out.println(idNews + "\n" + idComment + "\n Noi dung: " + CommentInfo + "\n So like:" + LikeNumber);
    }
}
