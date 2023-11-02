package markup;

public class Text implements MarkDown { //класс не является часть разметки,т.к. это просто текст, который мы и размечаем.
    protected final String string;

    public Text (String string) {
        this.string = string;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(string); //т.к класс не наследуется ни от кого, то у него свой toMarkDown (не используем super());
                            //но т.к. у нас класс реализует интерфейс MarkDown, то необходимо переопределить метод.
    }

    @Override
    public void toTex(StringBuilder sb) {
        sb.append(string);
    }
}
