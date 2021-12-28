public class Figure {
    private static String[] figureCode = {"•◙◙◙◙•", "◙◙••◙◙","◙◙◙◙","•◙•◙◙◙","◙••◙◙◙", "••◙◙◙◙"};

    public static char[][] getFigure(int number){
        int size = figureCode[number].length()/2;
        char[][] figure;
        figure = new char[2][size];
        figure[0]=figureCode[number].substring(0,size).toCharArray();
        figure[1]=figureCode[number].substring(size).toCharArray();
        return figure;
    }
}
