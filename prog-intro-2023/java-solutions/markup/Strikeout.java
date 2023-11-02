package markup;

import java.util.List;

public class Strikeout extends AbstractMarkupElements {
    //Т.к. Класс начледуется от AbstractMarkupElements, то нет необходимости объявлять переменные
    //разметки и список эл.которые надо граничить разметкой, достаточно объявить констурктор и передать через предка
    //При помощи super() все необходимое.
    protected Strikeout(List<MarkDown> markPart) {
        super(markPart, "~", "\\textst{", "}");
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb); // В классе предка есть метод разметки, наследуемся от него.
    }

    @Override
    public void toTex(StringBuilder sb) {
        super.toTex(sb);
    }
}
