package markup;
import java.util.List;

public abstract class AbstractMarkupElements implements MarkDown {
    protected List<MarkDown> markPart;
    protected String markSymbol;
    protected String markTexRight;// Символ разметки

    protected String markTexLeft;
    protected AbstractMarkupElements(List<MarkDown> markPart, String markSymbol, String markTexSymbol, String markTexLeft) {
        this.markPart = markPart;
        this.markSymbol = markSymbol;
        this.markTexRight = markTexSymbol;
        this.markTexLeft = markTexLeft;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(markSymbol); //Задаем начало разметки.
        for (MarkDown markup: markPart) {
            markup.toMarkdown(sb); //Запускаем "Рекусию", т.е если имеем: new Strikeout(List.of(new Text("2"),
        } // new Emphasis(List.of(new Text("3"), new Text("4"))), new Text("5"))), то проходимся по всем элементам и создаем разметку.
        sb.append(markSymbol); //Заканчиваем разметку.
    }

    @Override
    public void toTex(StringBuilder sb) {
        sb.append(markTexRight);
        for (MarkDown markup: markPart) {
            markup.toTex(sb);
        }
        sb.append(markTexLeft);
    }
}
