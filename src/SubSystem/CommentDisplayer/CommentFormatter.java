package SubSystem.CommentDisplayer;

import entity.Comment;

public class CommentFormatter
{
    Comment comment;
    CommentHeader header;
    CommentContent content;

    public CommentFormatter(Comment comment)
    {
        this.comment = comment;
        this.header = new CommentHeader(comment);
        this.content = new CommentContent(comment);
    }

    public String toBlockString()
    {
        String headerStr = header.toString() + "\n";
        String contentStr = content.toString() + "\n\n";
        return headerStr + contentStr;
    }
}
