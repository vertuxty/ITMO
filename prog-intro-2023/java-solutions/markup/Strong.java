package markup;

import java.util.List;

public class Strong extends AbstractMarkupElements {
    //Т.к. Класс начледуется от AbstractMarkupElements, то нет необходимости объявлять переменные
    //разметки и список эл.которые надо граничить разметкой, достаточно объявить констурктор и передать через предка
    //При помощи super() все необходимое.
    protected Strong(List<MarkDown> markPart) {
        super(markPart, "__", "\\textbf{", "}");
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb);
    }

    @Override
    public void toTex(StringBuilder sb) {
        super.toTex(sb);
    }
}
