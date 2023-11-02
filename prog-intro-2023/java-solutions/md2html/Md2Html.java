package md2html;
import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) {
        try {
            BufferedReader inFileRead = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), StandardCharsets.UTF_8));
            BufferedWriter outFileWrite = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            try {
//            BufferedWriter outFileTest = new BufferedWriter( new OutputStreamWriter(new FileOutputStream("C:/Users/Владимир/OneDrive/Рабочий стол/testsMod.txt"), StandardCharsets.UTF_8));
                while (inFileRead.ready()) { //Считываем фаил пока он не кончился.
                    StringBuilder currLines = new StringBuilder();
                    String line = inFileRead.readLine();
//                outFileTest.write(line);
                    while (line != null && !line.isEmpty()) {
                        if (currLines.isEmpty()) {
                            currLines.append(line); //Хотел сначала прибавлять lineSeparator и тут, но возникали баги когда в параграфе или заголовке всего 1 строчка (съезжала разметка)
                        } else {
                            currLines.append(System.lineSeparator());
                            currLines.append(line);
                        }//И если мы встретили пустую строку, то значит конец абзаца --> не запускаемся
                        // Обрабатываем текст, сохраняя информацию о том, где конце каждой строки --> начало следующей.
                        line = inFileRead.readLine();
                    }
                    if (!currLines.isEmpty()) {
                        ConvertToHtml convertedLines = new ConvertToHtml(currLines);//Запускать ConvertToHtml -- > записывать все в фаил
                        String convert = convertedLines.getter();
                        outFileWrite.write(convert);
                        outFileWrite.newLine();
                    }
                }
            } finally {
                inFileRead.close();
                outFileWrite.close();
            }
        }catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
//            outFileTest.close();
    }
}
//"C:/Users/Владимир/OneDrive/Рабочий стол/testing.txt"
//inFileRead.close();
//        outFileWrite.close();