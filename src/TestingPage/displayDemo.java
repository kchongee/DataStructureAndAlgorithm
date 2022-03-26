package TestingPage;

import SubSystem.CommentDisplayer.CommentDisplayer;
import SubSystem.CommentDisplayer.CommentFormatter;
import UtilityClasses.ProjectCompileUtil;
import adtImplementation.CircularQueue;
import adtInterfaces.QueueInterface;
import entity.Comment;


class displayDemo
{
    public static void main(String[] args)
    {
        // (ISSUE)
        ProjectCompileUtil.compileAndGenerate(new displayDemo());
        CommentDisplayer cd = new CommentDisplayer();
        QueueInterface<Comment> cq = cd.getCommentQueue().getCommentQueue();
        while (!cq.isEmpty())
        {
            CommentFormatter formatter = new CommentFormatter(cq.poll());
            System.out.println(formatter.toBlockString());
        }
    }
}