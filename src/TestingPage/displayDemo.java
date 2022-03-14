package TestingPage;

import SubSystem.CommentDisplayer.CommentDisplayer;
import UtilityClasses.ProjectCompileUtil;
import entity.Comment;

import java.util.Queue;

class displayDemo
{
    public static void main(String[] args)
    {
        ProjectCompileUtil.compileAndGenerate(new displayDemo());
        CommentDisplayer cd = new CommentDisplayer("roomA");
        Queue<Comment> cq = cd.getCommentData().getCommentQueue();
        for (Comment c : cq)
        {
            System.out.println(c.getFormatter().toBlockString());
        }
    }
}