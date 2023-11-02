package md2html;

class ConvertToHtml {
    // camelCase
    private final String convertLines; //идея сама сказала сделать ее final.
    private int headLevel; //Глубина загаловка.
    private int num; //это номер текущего char'a в строке.
    // private -> getter
    private StringBuilder currLines; //Строим новую разметку.
    
    public ConvertToHtml(StringBuilder Lines) {
        this.convertLines = Lines.toString();
        this.headLevel = 0;
        currLines = new StringBuilder();
        headLevel = LevelOfHeader();
        if (headLevel > 0) {
            this.num = headLevel + 1; // Тут говорим, что если есть заголовок, то начнем парсить не с пробела, а с символа после него.
        } else {
            this.num = 0; // Ну тут говорим что, раз нет заголовка -> параграф,  а значит парсим с 1 символа.
        }
        if (headLevel > 0) {
            Header();
        } else {
            Paragraph();
        }
    }

    public String getter() {
        return currLines.toString();
    }
    private int LevelOfHeader() { //Ищем глубину загаловка
        while (headLevel < convertLines.length() && convertLines.charAt(headLevel) == '#') {
            headLevel++;
        }
        if (headLevel < convertLines.length() && convertLines.charAt(headLevel) == ' ') {
            return headLevel;
        } else {
            return 0;
        }
    }

    private void appending(StringBuilder sb, String begin, String line, String end) {
        sb.append(begin);
        sb.append(line);
        sb.append(end);
    }

    private void Header() { //Парсим, если это загаловок.
        String headerBegin = "<h" + headLevel + ">";
        String headerEnd = "</h" + headLevel + ">";
        String line = scanTextOnMarkdown("");
        appending(currLines, headerBegin, line, headerEnd);
    }

    private void Paragraph() { //Парсим, если это параграф.
        String line = scanTextOnMarkdown("");
        appending(currLines, "<p>", line, "</p>");
    }

    // copy-paste :: Header()
    private void fillSb(StringBuilder sb, String temp) { //Создаем HTML разметку.
        String markHeader = markupElements(temp);
        String str = "<" + markHeader + ">";
        sb.insert(0, str);
        appending(sb, "</", markHeader, ">");
    }

    private boolean isMarkupPart(char symbol) { // Проверяем на символ разметки (спасибо switch за уменьшение размера if )
        return switch (symbol) {
            case '*', '\\', '<', '>', '&', '-', '`', '_', '+' -> true;
            default -> false;
        };
    }

    private void specialSymbols(char symbol, StringBuilder sb) { //Обрабатываем спицеальные символы. Заменяет много if else конструкций.
        switch (symbol) {
            case '<' -> sb.append("&lt;");
            case '>' -> sb.append("&gt;");
            case '&' -> sb.append("&amp;");
            //default -> currLines.append(""); //default не нужен, т.к. у меня гарантируется, что будет подаваться в switch только спец. символ.
        }
    }

    private String markupElements(String symbol) { //Нужен что бы избавиться от конструкций типа else if (ну мы его юзаем в fillSb, котороый тоже помогает от копипасты)
        return switch (symbol) {
            case "`" -> "code";//Код
            case "*", "_" -> "em";//Выделение
            case "**", "__" -> "strong";//Сильное выделение
            case "--" -> "s";//Зачеркивание
            case "++" -> "u"; //Подчеркивание
            default -> "";
        };
    }

    //Отделаьно вынес if словия для красоты, что бы сделать while проще
    private boolean isSpecialSymbols(char symbol) {
        return (symbol == '<' || symbol == '>' || symbol == '&');
    }

    private boolean isStrong(char symbol) {
        return  (num + 1 < convertLines.length()) && (symbol == '*' && convertLines.charAt(num + 1) == '*') || (symbol == '_' && convertLines.charAt(num + 1) == '_');
    }

    private boolean isEm(char symbol) {
        return (symbol == '*' && convertLines.charAt(num - 1) != '*') || (convertLines.charAt(num - 1) != '_' && symbol == '_');
    }

    private boolean isStrikeoutOrUnderline(char symbol) {
        return (num + 1 < convertLines.length()) && ((symbol == '-' && convertLines.charAt(num + 1) == '-') || (symbol == '+' && convertLines.charAt(num + 1) == '+'));
    }

    private boolean isDash(char symbol) {
        return (symbol == '-' && convertLines.charAt(num + 1) != '-' && convertLines.charAt(num - 1) != '-');
    }

    private boolean isPlus(char symbol) { //Да прикольное лог.выражение, кстати странно, что вообще такая проблема с ним возникла:)
        return ((num + 1 < convertLines.length() && convertLines.charAt(num + 1) != '+' && symbol == '+' && convertLines.charAt(num - 1) != '+')
                || (num < convertLines.length() && num + 1 > convertLines.length() && symbol == '+' && convertLines.charAt(num - 1) != '+') ||
                (num - 2 >= 0 && num + 1 < convertLines.length() && convertLines.charAt(num + 1) != '+' && symbol == '+' && convertLines.charAt(num - 1) == '+' && convertLines.charAt(num - 2) == '\\')
                || ((num - 2 >=0 && num < convertLines.length() && num + 1 > convertLines.length() && symbol == '+' && convertLines.charAt(num - 1) == '+') && convertLines.charAt(num - 2) == '\\'));
    }

    private String scanTextOnMarkdown(String markdownFlag) { //Рекурсивная функция, принимающая в аргумент предыдущий флаг разметки при вызове.
        StringBuilder sb = new StringBuilder();
        String recursiveLine = "";
        String temp = "";
        while (num < convertLines.length()) {
            if (isMarkupPart(convertLines.charAt(num))) {
                if (isSpecialSymbols(convertLines.charAt(num))) {
                    specialSymbols(convertLines.charAt(num), sb);
                } else if (convertLines.charAt(num) == '\\') {
                    sb.append(convertLines.charAt(++num));
                } else if (convertLines.charAt(num) == '`') { // Проверяем на CODE
                    if (markdownFlag.equals("`")) {
                        fillSb(sb, "`"); // Создаем разметочку HTML
                        return sb.toString();
                    } else {
                        num++;
                        recursiveLine = scanTextOnMarkdown("`"); // Мы запускаемся рекурсивно и получаем строку (уже переведенную в HTML разметку), проверки на одиночный символ не надо по ТЗ
                        sb.append(recursiveLine);                             // Потому проверку на одиночный символ я сделал только для обычного выделения
                    }
                } else if (isStrikeoutOrUnderline(convertLines.charAt(num)) || isStrong(convertLines.charAt(num))) { //на Strikeout
                    temp = (Character.toString(convertLines.charAt(num)) + convertLines.charAt(num + 1));
                    if (markdownFlag.equals(temp)) {
                        fillSb(sb, temp); // Даем разметку HTML.
                        num++;
                        return sb.toString();
                    } else {
                        num++;
                        recursiveLine = scanTextOnMarkdown(temp); //Мы запускаемся рекурсивно и получаем строку (уже переведенную в HTML разметку)
                        sb.append(recursiveLine);
                    }
                } else if (isDash(convertLines.charAt(num)) || isPlus(convertLines.charAt(num))) /*| isPlus(ConvertLines.charAt(num))*/ { // на знак минуса
                    sb.append(convertLines.charAt(num));
                } else if (isEm(convertLines.charAt(num))) { //на обычное выделение
                    temp = Character.toString(convertLines.charAt(num));
                    if (markdownFlag.equals(temp)) {
                        fillSb(sb, temp); //Даем разметку HTML.
                        return sb.toString();
                    } else {
                        num++;
                        recursiveLine = scanTextOnMarkdown(temp); //Мы запускаемся рекурсивно и получаем строку (уже переведенную в HTML разметку)
                        if (recursiveLine.charAt(0) != '<') { //проверяем на одиночные символы, мы знаем, что в начале стоит спец символ "<" в HTML разметке: <code>t</code>
                            recursiveLine = temp + recursiveLine;
                        }
                        sb.append(recursiveLine);
                    }
                }
            } else {
                sb.append(convertLines.charAt(num));
            }
            num++;
        }
        return sb.toString();
    }
}
