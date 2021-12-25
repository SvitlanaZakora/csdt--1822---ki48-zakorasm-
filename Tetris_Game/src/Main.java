import java.util.Random;
import java.util.Scanner;

public class Main {
    final static int LENGTH = 9;
    final static int HIGH = 16;
    static char[][] FIELD;
    public static void main(String[] args) {
        FIELD = createNewField();
        game();
    }

    private static void game(){
        Random r = new Random();
        Scanner sc = new Scanner(System.in);
        String choose = "";
        char[][] figure;
        while (!choose.equals("ex")){
            figure = Figure.getFigure(r.nextInt(6));
            printField(addFigure(figure, maxDownOffset(figure, 0, 0), 0));
            System.out.println("Enter direction and offset. Example: r2, l1");
            choose = sc.next();

            int off;
            switch (choose.substring(0,1)){
                case "r":{
                    off = Integer.parseInt(choose.substring(1));
                    break;
                }
                case "l":{
                    off = Integer.parseInt(choose.substring(1))*-1;
                    break;
                }
                default:{
                    off=0;
                }
            }
            int lOffset = off<=0 ? maxLeftOffset(figure, off): maxRightOffset(figure, off);
            saveField(addFigure(figure, maxDownOffset(figure, 20, lOffset), lOffset));
            printField(FIELD);
            validateRows();
        }

    }

    private static void validateRows(){
        for (int i=HIGH-1; i>=0; i--){
            if(!new String(FIELD[i]).contains("•")){
                for(int j=i; j>0; j--){
                    FIELD[j] = FIELD[j-1];
                }
                FIELD[0]=new String(new char[LENGTH]).replace('\0', '•').toCharArray();
            }
        }
    }


    private static char[][] createNewField(){
        char[][] field = new char[HIGH][LENGTH];
        for (int i=0; i<HIGH; i++){
            for (int j=0; j<LENGTH; j++){
                field[i][j]='•';
            }
        }
        return field;
    }

    private static void printField(char[][] field){
        for (int i=0; i<field.length; i++){
            for (int j=0; j<field[0].length; j++){
                System.out.printf("\t%c", field[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    private static char[][] addFigure(char[][] figure, int offsetH, int offsetL){
        char[][] newField = deepCopy(FIELD);
        int fieldCenter = FIELD[0].length/2;
        for (int i=0; i< figure.length; i++){
            for (int j=fieldCenter-figure[0].length/2; j<fieldCenter-figure[0].length/2+figure[0].length; j++){
                if(figure[i][j - fieldCenter + figure[0].length / 2]=='◙') {
                    newField[i+offsetH][j+offsetL] = figure[i][j - fieldCenter + figure[0].length / 2];
                }
            }
        }
        return newField;
    }

    private static int maxRightOffset(char[][] figure, int offset){
        int max=0;
        char[][] rows = new char[2][LENGTH];
        rows[0] = FIELD[0];
        rows[1] = FIELD[1];
        for(int i=LENGTH/2+figure[0].length-1; i<Math.min(offset+LENGTH/2+figure[0].length-1, LENGTH); i++){
            for(int j=0; j<figure.length; j++){
                if (rows[j][i- firstMatchRight(figure[j])]=='◙'){
                    return max;
                }
            }
            max++;
        }
        return max;
    }

    private static int maxLeftOffset(char[][] figure, int offset){
        int max=0;
        char[][] rows = new char[2][LENGTH];
        rows[0] = FIELD[0];
        rows[1] = FIELD[1];
        for(int i=LENGTH/2-1; i>Math.max(LENGTH/2-1+offset, 0); i--){
            for(int j=0; j<figure.length; j++){
                if (rows[j][i+ firstMatchLeft(figure[j])]=='◙'){
                    return max;
                }
            }
            max++;
        }
        return max*-1;
    }

    private static int firstMatchRight(char[] figureRow){
        for (int i=figureRow.length-1; i>=0; i--){
            if (figureRow[i]=='◙'){
                return i;
            }
        }
        return 0;
    }

    private static int firstMatchLeft(char[] figureRow){
        for (int i=0; i<figureRow.length; i++){
            if (figureRow[i]=='◙'){
                return i;
            }
        }
        return 0;
    }

    private static int maxDownOffset( char[][] figure, int offset, int rOffset){
        int max=0;
        char[][] rows = new char[2][LENGTH];
        for (int i = 1; i<(Math.min(offset+1, FIELD.length-1)); i++){
            rows[0]=FIELD[i];
            rows[1]=FIELD[i+1];
            if(!isMoveDown(figure, rows, rOffset)){
                return max;
            }
            max++;
        }
        return max;
    }

    private static boolean isMoveDown(char[][] figure, char[][] rows, int offset){
        int startPosition = FIELD[0].length/2-1;
        for (int i=0; i<figure.length; i++){
            for (int j=0; j<figure[0].length; j++){
                if(figure[i][j]=='◙' && rows[i][startPosition+j+offset]=='◙'){
                    return false;
                }
            }
        }
        return true;
    }

    private static void saveField(char[][] field){
        FIELD = deepCopy(field);
    }

    private static char[][] deepCopy(char[][] field){
        char[][] newField = new char[HIGH][LENGTH];
        for (int i=0; i<HIGH; i++){
            newField[i] = field[i].clone();
        }
        return newField;
    }
}
