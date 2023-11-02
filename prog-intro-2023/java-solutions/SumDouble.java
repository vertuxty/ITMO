public class SumDouble {
    public static void main(String[] args){
        double sumOfElem = 0;
        for (int i = 0; i < args.length; i++) {
            int indexLeft = 0;
            int indexRight = 0;
            for (int j = 0; j < args[i].length(); j++) {
                if (Character.isWhitespace(args[i].charAt(j))) {
                    indexRight = j;
                    if (!args[i].substring(indexLeft, indexRight).isEmpty()) {
                        sumOfElem += Double.parseDouble(args[i].substring(indexLeft, indexRight));
                        indexLeft = j + 1;
                    } else {
                        indexLeft = j + 1;
                    }
                }
                if (j + 1 == args[i].length() && !args[i].substring(indexLeft).isEmpty()) {
                    sumOfElem += Double.parseDouble(args[i].substring(indexLeft));
                }
            }
        }
        System.out.println(sumOfElem);
    }
}