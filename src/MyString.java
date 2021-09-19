import java.text.DecimalFormat;
import java.util.HashMap;

public class MyString {
    private HashMap<String, Double> hashMap = new HashMap<>();
    private int startposition = 0;
    private int length = 0;
    private Double max = 0.0;
    private String maxX = "";
    private int start = 1;
    private static DecimalFormat df2 = new DecimalFormat("#.##");

    MyString (int length, int startposition)
    {
        this.length = length;
        for (int i = this.start; i<mypow(2,length); i++)
        {
            this.hashMap.put(dectotwo(i,length),5*Math.sin(i) + Math.log(i));
        }
        this.startposition = startposition;
            this.maxX = dectotwo(startposition, length);
            this.max = this.hashMap.get(maxX);
    }
    public String getIndexHash ()
    {
        String buffer = "";
        Double numbuf = 0.0;
        for (int i = 0; i<this.length; i++)
        {
            buffer = changeIndexString(maxX,i);
            numbuf = geti(buffer);
            if (geti(buffer)!=null) {
                System.out.println(buffer +"======"+df2.format(numbuf));
            }
        }
        for (int i = 0; i<this.length; i++)
        {
            buffer = changeIndexString(maxX,i);
            if (geti(buffer)!=null) {
                return buffer;
            }
        }
        return null;
    }
    public String getIndexDFS ()
    {
        return getIndexHash();
    }
public String getIndexBFS()
{
    HashMap<String,Double> hashbuf = new HashMap<>();
    String buffer = "";
    Double numbuf = 0.0;

    for (int i = 0; i<this.length; i++)
    {
        buffer = changeIndexString(maxX,i);
        numbuf = geti(buffer);
        if (geti(buffer)!=null) {
            hashbuf.put(buffer,numbuf);
            myremove(buffer);
        }
    }
    numbuf = -1.0;
    for (int i = 0; i<mypow(2,this.length);i++) {
        buffer = dectotwo(i,this.length);
        if (hashbuf.get(buffer) != null) {
            System.out.println(buffer + "======"
                    + df2.format(hashbuf.get(buffer)));
            if (hashbuf.get(buffer) > max) {
                max = hashbuf.get(buffer);
                maxX = buffer;
                numbuf = 1.0;
            }
        }
    }
    if (numbuf > 0.9) return maxX; else
        return null;
}
    public String changeIndexString(String s, int pos)
    {
        String s1 = "";
        try
        {
            s.charAt(pos);
        } catch (Exception e)
        {
            System.out.println("Вышли за границу HashMap");
            pos = -1;
        }
        if (pos>=0) {
            for (int i = 0; i < pos; i++)
                s1 += s.charAt(i);
            if (s.charAt(pos) == '1') s1 += '0';
            else s1 += '1';
            if (pos < s.length() - 1)
                s1 += s.substring(pos + 1);
        }
        return s1;
    }
    public  int twotodec(String s)
    {
        int result = 0;
        for (int i = 0; i<s.length(); i++)
        {
            result +=Integer.parseInt(String.valueOf(s.charAt(i)))*mypow(2,s.length()-i-1);
        }
        return result;
    }
    public  String dectotwo(int dec,int num)
    {
        String result = "";
        while (dec > 0)
        {
            result+=String.valueOf(dec % 2);
            dec /=2;
        }
        while (result.length()<this.length)
        {
            result += "0";
        }
        String buffer = "";
        for (int i = 0; i<result.length(); i++)
            buffer += result.charAt(result.length() - i - 1);
        return buffer;
    }
    public  int mypow(int first, int second)
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
    public void printlocal(String num)
    {
        System.out.print("Текущая окрестность: ");
        String buffer = "";
        int k = 0;
        for (int i = 0; i<this.length; i++)
        {
            buffer = changeIndexString(num,i);
            if (geti(buffer)!=null) {
                System.out.print(buffer+"  ");
                k = 1;
            }
        }
        if (k==1)
            System.out.println();
    }

    public Double geti(String num)
    {
        if (hashMap.get(num)!=null)
            return hashMap.get(num);
        else return null;
    }



    public void myremove (String del)
    {
        if (hashMap.get(del) != null)
        {
            hashMap.remove(del);
        }
    }

    public int size()
    {
        return hashMap.size();
    }

    public void print(){
        for (int i = this.start; i < mypow(2, this.length); i++) {
            if (hashMap.get(dectotwo(i, this.length)) != null) {
                System.out.println(dectotwo(i, this.length) + "======"
                        + df2.format(hashMap.get(dectotwo(i, this.length))));
            }
        }
    }
    public HashMap<String, Double> getHashMap() {
        return hashMap;
    }

    public void setHashMap(HashMap<String, Double> hashMap) {
        this.hashMap = hashMap;
    }

    public int getStartposition() {
        return startposition;
    }

    public void setStartposition(int startposition) {
        this.startposition = startposition;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public String getMaxX() {
        return maxX;
    }

    public void setMaxX(String maxX) {
        this.maxX = maxX;
    }
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}