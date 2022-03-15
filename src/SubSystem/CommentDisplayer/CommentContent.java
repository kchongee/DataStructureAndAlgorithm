package SubSystem.CommentDisplayer;

import entity.Comment;

public class CommentContent
{
    Comment comment;
    final static int LINE_SIZE = 55;

    CommentContent(Comment comment) {
        this.comment = comment;
    }


    public String toString()
    {
        String[] words = comment.getContent().split(" ");
        String line = "";
        String text = "";
        for (String word : words)
        {
            String tempWord = word + " ";
            if ((line + tempWord).length()-1 == LINE_SIZE) // exceed
            {
                text = text + line + word + '\n';
                line = "";
            }
            else if ((line + tempWord).length()-1 > LINE_SIZE)
            {
                text = text + line + '\n';
                line = tempWord;
            }
            else
            {
                line = line+tempWord;
            }
        }
        if (line.length() > 0){
            text = text + line;
        }
        return text;
    }
}
