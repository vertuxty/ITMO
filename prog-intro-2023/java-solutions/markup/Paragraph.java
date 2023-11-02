package markup;

import java.util.List;

public class Paragraph implements MarkDown {
    public List<MarkDown> line;

    public Paragraph(List<MarkDown> markSeq) { //Определяем конструктор.
        this.line = markSeq;
    }

    @Override
    public void toMarkdown(StringBuilder sb) { //Переопределяем метод toMarkdown для Paragraph.
        for (MarkDown markSymbol: line) {
            markSymbol.toMarkdown(sb);
        }
    }

    @Override
    public void toTex(StringBuilder sb) {
        for (MarkDown markSym: line) {
            markSym.toTex(sb);
        }
    }
}
