package TestingPage;

import SubSystem.CommentDisplayer.CommentDisplayer;
import UtilityClasses.ProjectCompileUtil;
import adtImplementation.CircularQueue;
import entity.Comment;


class displayDemo
{
    public static void main(String[] args)
    {
        // (ISSUE)
        ProjectCompileUtil.compileAndGenerate(new displayDemo());
        CommentDisplayer cd = new CommentDisplayer();
        CircularQueue<Comment> cq = cd.getCommentData().getCommentQueue();
        while (!cq.isEmpty())
        {
            System.out.println(cq.poll().getFormatter().toBlockString());
        }
    }
}