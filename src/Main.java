import java.text.DecimalFormat;
import java.util.*;


public class Main {
    private static final DecimalFormat df2 = new DecimalFormat("#.##");
    private static final int length = 7;
    public static void main(String[] args)
    {
        Random rnd = new Random();
        int randomstart = Math.abs(rnd.nextInt()) % (mypow(2,length) - 1) + 1;
        int randommethod= Math.abs(rnd.nextInt()) % 3;
        int i = 0;
        Double max = 0.0;
        String maxX = "";
        System.out.print("Введите количество шагов у программы: ");
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        MyString myString = new MyString(length,randomstart);
        myString.print();
        maxX = myString.dectotwo(randomstart,length);
        max = 5*Math.sin(randomstart) + Math.log(randomstart);
        while (i<n)
        {
            i++;
            System.out.println("Номер шага - "+ i);
            System.out.println("Текущий итоговый максимум - " + maxX + " значение - "+df2.format(max));
            randommethod= Math.abs(rnd.nextInt()) % 3;
            if (randommethod == 0)
            {
                System.out.println("Метод поиска Монте-Карло: ");
                myString = new MyString(length,randomstart);
                myrandom(myString,n);
            } else if (randommethod == 1)
            {
                System.out.println("Метод поиска в глубину: ");
                myString = new MyString(length,randomstart);
                dfs(myString,n);
            } else
            {   System.out.println("Метод поиска в глубину: ");
                myString = new MyString(length,randomstart);
                bfs(myString,n);
            }
            if (max < myString.getMax()){
                System.out.println("Мы нашли новый итоговый максимум!");
                max = myString.getMax();
                maxX =myString.getMaxX();
                System.out.println("Новый итоговый максимум - "+ maxX + " значение - " + df2.format(max));
                randomstart = Math.abs(rnd.nextInt()) % (mypow(2,length) - 1) + 1;;
            } else System.out.println("Итоговый максимум не поменялся");
        }
        System.out.println("Найденный итоговый максимум - " + maxX + " значение - " + df2.format(max));

    }
static void dfs(MyString myString, int n)
{
    System.out.println("Точка входа - "+ myString.getMaxX() + "======"
            + df2.format(myString.geti(myString.getMaxX())));
    myString.myremove(myString.getMaxX());
    int i = 0;
    String buffer = "";
    while (i < n)
    {
        System.out.println("Номер шага - "+ (i+1));
        System.out.println("Текущий max - "+ myString.getMaxX()
                + ", значение - " + df2.format(myString.getMax()));
        myString.printlocal(myString.getMaxX());
        buffer = myString.getIndexDFS();
        if (buffer!=null)
        {
            System.out.println("Мы нашли соседнюю строчку - " + buffer);
            i++;
            if (myString.geti(buffer) > myString.getMax())
            {
                myString.setMax(myString.geti(buffer));
                myString.setMaxX(buffer);
                System.out.println("Новый максимум - " + myString.getMaxX()
                        + ", значение - " + df2.format(myString.getMax()));
            } else System.out.println("Максимум не поменялся");
            myString.myremove(buffer);
        }
        else {
            i = n + 1;
            System.out.println("Больше соседей нет!");
        }
    }
    System.out.println("Максимальная строчка - " + myString.getMaxX()
            + ", её значение - " + df2.format(myString.getMax()));
}
static void bfs(MyString myString, int n)
{
        System.out.println("Точка входа - "+ myString.getMaxX() + "======"
                + df2.format(myString.geti(myString.getMaxX())));
        myString.myremove(myString.getMaxX());
        int i = 0;
        String buffer = "";
        while (i < n)
        {
            System.out.println("Номер шага - "+ (i+1));
            System.out.println("Текущий max - "+ myString.getMaxX()
                    + ", значение - " + df2.format(myString.getMax()));
            myString.printlocal(myString.getMaxX());
            buffer = myString.getIndexBFS();
            if (buffer!=null)
            {
                System.out.println("Мы нашли новый max - "
                        + myString.getMaxX() +", его значение - "
                        + df2.format(myString.getMax()));
                i++;
            }
            else {
                i = n + 1;
                System.out.println("Мы нашли локальный максимум!");
            }
        }
        System.out.println("Максимальная строчка - " + myString.getMaxX()
                + ", её значение - " + df2.format(myString.getMax()));

    }
static void myrandom(MyString myString, int n)
{
        String bufstr = "";
        Random rnd = new Random();
        int randomstart = 0;
        System.out.println("Точка входа - "+ myString.getMaxX() + "======"
                + df2.format(myString.geti(myString.getMaxX())));
        myString.myremove(myString.getMaxX());
        for (int i = 0; i<n; i++)
        {
            randomstart = Math.abs(rnd.nextInt()) % mypow(2,length);
            System.out.println("Номер шага - "+ (i+1));
            System.out.println("Текущий max - "+ myString.getMaxX()
                    + ", значение - " + df2.format(myString.getMax()));
            bufstr = myString.dectotwo(randomstart,length);
            System.out.println("Найденная кодировка - " + bufstr
            +" значение - " + df2.format(5*Math.sin(randomstart) + Math.log(randomstart)));
            if (myString.geti(bufstr)!=null)
            {
                if (myString.geti(bufstr)>myString.getMax())
                {
                    myString.setMaxX(bufstr);
                    myString.setMax(myString.geti(bufstr));
                    System.out.println("Мы нашли новый max - "
                            + myString.getMaxX() +", его значение - "
                            + df2.format(myString.getMax()));
                } else System.out.println("Максимум не поменялся");
                myString.myremove(bufstr);
            } else System.out.println("Мы уже посещали эту точку!");
        }
        System.out.println("Максимальная строчка - " + myString.getMaxX()
                + ", её значение - " + df2.format(myString.getMax()));
    }
static int mypow(int first, int second)
{
        int buffer = first;
        if (second == 0) return 1;
        else {
            for (int i = 0; i < second - 1; i++) {
                first = first * buffer;
            }
            return first;
        }
    }
}