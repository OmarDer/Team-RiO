/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spvp.network;
import java.lang.*;
import org.apache.commons.math3.linear.MatrixUtils;
/**
 *
 * @author User
 */
public class NormForm {
    private double Maximum;
    private double Minimum;
    
    public NormForm(){
    Maximum=0;
    Minimum=0;
    } 
    
    public double getMaximum()
    {
        return Maximum;
    }
    public double getMinimum()
    {
        return Minimum;
    }
    public static double nadjiMaximum(double []lista)
    {
       double max = 0.0;
       for(int i=0; i<lista.length;i++)
       {
           if(lista[i]>=max)
           {
               max=lista[i];
           }
       }
       return max;
    }
    
    public static double nadjiMinimum(double []lista)
    {
       double min=0;
       if(lista.length>0)
       {
            min = lista[0];
       }
       for(int i=0; i<lista.length;i++)
       {
           if(lista[i]<=min)
           {
               min=lista[i];
           }
       }
       return min;
    }
    public static double[] normaliziraj(double []lista)
    {
        double normLista[]=new double[lista.length];
        double max=nadjiMaximum(lista);
        double min=nadjiMinimum(lista);
        for (int i=0;i<lista.length;i++)
        {
            normLista[i]=(lista[i]-min)/(max-min);
        }
        return normLista;
    }
    public static double normaliziraj(double val,double max, double min)
    {
                
      return ((val-min)/(max-min));
      
    }
    public static double denormaliziraj(double value,double max,double min)
    {     
        return ((max-min)*value+min);
    }
    
    public static double[] vratiKolonu(double [][]matrica,int index)
    {   double []kolona= new double[matrica.length];
        for(int i=0;i<matrica.length;i++ )
        {
            kolona[i]=matrica[i][index];
        }
        return kolona;
    }
    
    public static double Prosjek(double []lista,int vel)
    {
        double suma=0.0;
        for (int i=0;i<vel;i++)
        {
            suma=suma+lista[i];
        }
        return suma/vel;
    }
    
   /* 
    public static void main( String[] args )
    {
      NormForm norm=new NormForm();
        double[] lista={2,1,1,3,4,2,1};
        double[] nLista=normaliziraj(lista);
       
        for (int i=0;i<nLista.length;i++)
        {
            System.out.println(nLista[i]);
        }
        double min=NormForm.nadjiMinimum(lista);
        double max=NormForm.nadjiMaximum(lista);
        
        double []listaN=new double[lista.length];
        for(int i=0;i<nLista.length;i++)
        {
            listaN[i]=NormForm.denormaliziraj(nLista[i], max, min);
        }
        
        for (int i=0;i<nLista.length;i++)
        {
            System.out.println(listaN[i]);
        }
        
        //double matrica[][]={{21.5,1},{23,2},{25,3},{25.48,4},{25,5},{27.7,7}};
        //double[] lista=NormForm.vratiKolonu(matrica, 0);
        //double[] nLista=normaliziraj(lista);
        //for (int i=0;i<nLista.length;i++)
        //{
        //    System.out.println(nLista[i]);
        //}
        
        //System.out.println(matrica);
     
    }
    */
}
