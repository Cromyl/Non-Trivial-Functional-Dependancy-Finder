package functional_dep;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    static int sets;
    static ArrayList<ArrayList<Integer>> adj;
    static HashSet<Integer> h1;

    public static void main(String[] args) {
        Scanner in=new Scanner(System.in);
        adj=new ArrayList<>();
        h1=new HashSet<>();
        System.out.print("Enter the number of sets - ");
        sets=in.nextInt();


        for(int i=0;i<(int)Math.pow(2,sets);i++) {
            adj.add(new ArrayList<>());
            adj.get(i).add(i);
        }
        for(int i=0;i<(int)Math.pow(2,sets);i++){
            fillInput(i,i,0,0);
        }

        System.out.print("Enter the number of functional dependencies - ");
        int m=in.nextInt();
        System.out.println();
        for(int i=0;i<m;i++)
        {
            System.out.print("Enter the left hand side of the functional dependency - ");
            String left=in.next();
            System.out.print("Enter the right hand side of the functional dependency - ");
            String right=in.next();
            System.out.println();
            int lhs=convert_to_int(left);
            int rhs=convert_to_int(right);
            fillInput(lhs,rhs,0,0);
        }
        //for(ArrayList<Integer> l1:adj) System.out.println(l1);

        int val=(int)Math.pow(2,sets)-1;
        for(int i=1;i<=val;i++)
        {
            h1.clear();
            dfs2(i,i);
            //h1.add(i);
            combine(i);
        }

    }
    public static void combine(int lhs)
    {
        ArrayList<Integer> l1 = new ArrayList<>(h1);
        HashSet<Integer> h2=new HashSet<>();
        l1.add(lhs);
        //int n=l1.size();
        for(int i=0;i<l1.size();i++)
        {
            for(int j=i+1;j<l1.size();j++)
            {
                int temp=l1.get(i)|l1.get(j);
                if(h2.contains(temp)) continue;
                printing(lhs,temp);
                l1.add(temp);
                h2.add(temp);
                h1.add(temp);
            }
        }
    }

    public static void fillInput(int lhs,int rhs,int curr,int c)
    {
        if(c==27)
        {
            if(curr==0||curr==lhs)
                return ;

            adj.get(lhs).add(curr);
            return ;
        }
        fillInput(lhs,rhs,curr,c+1);
        if(((rhs>>c)&1)==1)
            fillInput(lhs,rhs,curr|(1<<c),c+1);
        return;
    }

    public static int convert_to_int(String inp)
    {
        int temp=0;
        int len=inp.length();
        for(int i=0;i<len;i++)
        {
            int p=inp.charAt(i)-'A';
            temp=temp|(1<<p);
        }
        return temp;
    }
    public static String convert_to_String(int temp)
    {
        StringBuilder s= new StringBuilder();
        for(int i=0;i<26;i++)
        {
            int k=(temp>>i)&1;
            if(k==0) continue;
            s.append((char) ('A' + i));
        }
        return s.toString();
    }

    public static void printing(int lhs,int curr)
    {
        if(h1.contains(curr)) return ;
        int check=lhs|curr;
        if(check!=lhs)
        {
            System.out.println(convert_to_String(lhs)+" -> "+convert_to_String(curr));
        }
        h1.add(curr);
        //dfs2(lhs,curr);

    }
    public static void dfs2(int lhs,int curr)
    {
        if(h1.contains(curr)) return;
        printing(lhs,curr);
        h1.add(curr);
        for(int i:adj.get(curr))
        {
            dfs2(lhs,i);
        }
        for(int i=0;i<26;i++)
        {
            int temp=(curr>>i)&1;
            int k=curr-(1<<i);
            if(temp==1)
            {
                for(int j:adj.get(1<<i))
                {
                    int pass=k|j;

                    dfs2(lhs,pass);
                }
            }
        }
    }
}
