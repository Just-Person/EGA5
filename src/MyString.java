import java.text.DecimalFormat;
import java.util.HashMap;

public class MyString {
    private HashMap<String, Double> hashMap = new HashMap<>();
    private HashMap<String, Double> greyMap = new HashMap<>();
    private int startposition = 0;
    private int length = 0;
    private Double max = 0.0;
    private String maxX = "";
    private int start = 0;
    private int how = 0;
    private static DecimalFormat df2 = new DecimalFormat("#.##");



    //if how == 0 - grey, another - simply
    MyString (int length, int startposition, int how)
    {
        this.how = how;
        this.length = length;
        for (int i = this.start; i<mypow(2,length); i++)
        {
            this.hashMap.put(dectotwo(i,length),5*Math.sin(i) + Math.log(i));
        }
        for (int i = this.start; i<mypow(2,length);i++)
        {
            this.greyMap.put(grey(dectotwo(i,length),1),5*Math.sin(i) + Math.log(i));
        }
        this.startposition = startposition;
        if (how == 0) {
            this.maxX = grey(dectotwo(startposition, length), 1);
            this.max = this.greyMap.get(maxX);
        } else {
            this.maxX = dectotwo(startposition, length);
            this.max = this.hashMap.get(maxX);
        }
    }
    public String plus (String s1, String s2)
    {
        String s = "";
        for (int i = 0; i<s1.length();i++)
        {
            if ((s1.charAt(i)=='1' && s2.charAt(i)=='0')
                    || (s1.charAt(i)=='0' && s2.charAt(i)=='1')) s +="1";
            else s +="0";
        }
        return s;
    }

    public String grey (String s1, int index)
    {
        String s = "";
        for (int i = 0; i<index; i++)
            s +="0";
        s+=s1.substring(0,s1.length()-index);
        s = plus(s,s1);
        return s;
    }
    public String getIndexGrey ()
    {
        String buffer = "";
        for (int i = 0; i<this.length; i++)
        {
            buffer = changeIndexString(maxX,i);
            if (getigrey(buffer)!=null) {
                return buffer;
            }
        }
        return null;
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
            myremovehash(buffer);
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
    public void printlocalgrey(String num)
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
    public void printlocalhash(String num)
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
    public void printlocal(String num)
    {
        if (this.how == 0) printlocalgrey(num);
        else printlocalhash(num);
    }
    public Double geti(String num)
    {
        if (this.how == 0)
            return getigrey(num);
        else return getihash(num);
    }
    public Double getigrey(String num)
    {
        if (greyMap.get(num)!=null)
            return greyMap.get(num);
        else return null;
    }
    public Double getihash(String num)
    {
        if (hashMap.get(num)!=null)
            return hashMap.get(num);
        else return null;
    }

    public HashMap<String, Double> getGreyMap() {
        return greyMap;
    }

    public void setGreyMap(HashMap<String, Double> greyMap) {
        this.greyMap = greyMap;
    }

    public void myremovegrey (String del)
    {
        if (greyMap.get(del) != null)
        {
            greyMap.remove(del);
        }
    }
    public void myremovehash (String del)
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
    public void printgrey() {
        for (int i = this.start; i < greyMap.size(); i++) {
            if (greyMap.get(grey(dectotwo(i,7),1)) != null)
                System.out.println(grey(dectotwo(i,length),1) + "======"
                        + df2.format(greyMap.get(grey(dectotwo(i,length),1))));
        }
    }
    public void printhashgrey() {
        double min = 1000.0;
        double buffer = 0.0;
        String index = "";
        for (int j = this.start; j < mypow(2, this.length); j++) {
            min = 1000.0;
        for (int i = this.start; i < mypow(2, this.length); i++) {
            if (hashMap.get(grey(dectotwo(i, this.length), 1)) != null
                    && hashMap.get(grey(dectotwo(i, this.length), 1)) < min) {
                min = hashMap.get(grey(dectotwo(i, this.length), 1));
                index = grey(dectotwo(i, this.length), 1);

            }
        }
            if (hashMap.get(index) != null) {
                System.out.print(index + "======"
                        + df2.format(min) + " ");
                hashMap.remove(index);
            }
    }
    }
    public void printhashnotgrey()
    {
        for (int i = this.start; i < mypow(2, this.length); i++) {
            if (hashMap.get(dectotwo(i, this.length)) != null) {
                System.out.println(dectotwo(i, this.length) + "======"
                        + df2.format(hashMap.get(dectotwo(i, this.length))));
            }
        }
    }
    public void printhash(){
        if (this.how==0) printhashgrey();
        else printhashnotgrey();
    }
    public String greyreverse(String str)
    {
        HashMap <Integer,String> buffer = new HashMap<>();
        buffer.put(0,str);
        for (int i = 1; i<this.length; i++)
        {
            str = plus(str,grey(str,1));
            buffer.put(i,str);
        }
        str = buffer.get(0);
        for (int i = 1; i<this.length; i++)
        {
            str = plus(str,buffer.get(i));
        }
        return str;
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
    public int getHow() {
        return how;
    }

    public void setHow(int how) {
        this.how = how;
    }
}