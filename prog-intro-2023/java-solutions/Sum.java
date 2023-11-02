public class Sum {
	public static void main(String[] args){
		int sumOfElem = 0;
		for (int i = 0; i < args.length; i++){
			String digitBuilder = "";
			for (int j = 0; j < args[i].length(); j++){
				if (!Character.isWhitespace(args[i].charAt(j))){
					digitBuilder += args[i].charAt(j);
				} else {
					if (!digitBuilder.equals("")){
						sumOfElem += Integer.parseInt(digitBuilder);
						digitBuilder = "";
					}
				}
				if (j + 1 == args[i].length() && !digitBuilder.equals("")) {
					sumOfElem += Integer.parseInt(digitBuilder);
					digitBuilder = "";
				}
			}
		}
		System.out.println(sumOfElem);
	}
}