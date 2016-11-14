/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.spvp.network;

/**
 *
 * @author User
 */
import org.encog.*;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.Encog;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;
import com.spvp.services.*;
import com.spvp.model.*;
import static java.lang.System.in;
import java.text.ParseException;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class NNetwork {
	
	private double inputArgs[][];
	private double outputArgs[][];
	private BasicNetwork network;
	private MLDataSet trainingSet;
        //private MLDataSet testSet;
        private double trError;
        private double maxT;
        private double minT;
        private double maxV;
        private double minV;
        private double maxBv;
        private double minBv;
        private int velProblema;
	
	public NNetwork(){
		network = new BasicNetwork();
                trError=0.0;
                maxT=0.0;
                minT=0.0;
                maxV=0.0;
                minV=0.0;
                maxBv=0.0;
                minBv=0.0;
	}
        
        private void formatirajUlaznePodatke(ArrayList<Prognoza>prognoze)
        {
            int m=prognoze.size()-1;
            int n=7;
            double input[][]=new double[m][n];
            for (int i=0;i<m;i++)                                                   //Do jucerasnjeg dana
            {
                input[i][0]=Double.parseDouble(prognoze.get(i).getTemperatura());
                input[i][1]=Double.parseDouble(prognoze.get(i).getVlaznostZraka());
                input[i][2]=Double.parseDouble(prognoze.get(i).getBrzinaVjetra());  
                input[i][3]=0.0;
                input[i][4]=0.0;
                input[i][5]=0.0;
                input[i][6]=0.0;
                
                String vr=prognoze.get(i).getVrijeme();
                if("Heavy snow".equals(vr) || "Snow".equals(vr))
                {
                    input[i][5]=1.0;
                }
                else if("Sunny".equals(vr))
                {
                    input[i][4]=1.0;
                }
                else if("Light drizzle".equals(vr) || "Light rain shower".equals(vr) || "Rainy".equals(vr))
                {
                    input[i][6]=1.0;
                }
                else if("Partly cloudy".equals(vr)||"Mist".equals(vr)||"Fog".equals(vr))
                {
                   input[i][3]=1.0; 
                }
                    
            }
            /*
            for(int i=0;i<m;i++)
            {
                for(int j=0;j<n;j++)
                {
                    System.out.print(input[i][j]+"->");
                }
                System.out.printf("%n");
            }
            */
            velProblema=prognoze.size();
            
            inputArgs=input;
            //return input;
        }
        
        private void formatirajIzlaznePodatke(ArrayList<Prognoza>prognoze)
        {
            int m=prognoze.size()-1;
            int n=5;
            double [][]output=new double [m][n];
            for (int i=0;i<m;i++)                                                   //Do jucerasnjeg dana
            {
                
                output[i][0]=Double.parseDouble(prognoze.get(i+1).getTemperatura());
                output[i][1]=0.0;
                output[i][2]=0.0;  
                output[i][3]=0.0;
                output[i][4]=0.0;
                        
                String vr=prognoze.get(i+1).getVrijeme();
                if("Heavy snow".equals(vr) || "Snow".equals(vr))
                {
                    output[i][3]=1.0;
                }
                else if("Sunny".equals(vr))
                {
                    output[i][2]=1.0;
                }
                else if("Light drizzle".equals(vr) || "Light rain shower".equals(vr) || "Rainy".equals(vr))
                {
                    output[i][4]=1.0;
                }
                else if("Partly cloudy".equals(vr)||"Mist".equals(vr)||"Fog".equals(vr))
                {
                   output[i][1]=1.0; 
                }         
            }
            /*
            for(int i=0;i<m;i++)
            {
                for(int j=0;j<n;j++)
                {
                    System.out.print(output[i][j]+" ");
                }
                System.out.printf("%n");
            }
            */
            outputArgs=output;
            //return output;
            
        }
        public void pripremiPodatke()
        {
           NormForm nf=new NormForm();
              
           double []kolonaT=NormForm.vratiKolonu(inputArgs, 0);
           double []kolonaV=NormForm.vratiKolonu(inputArgs, 1);
           double []kolonaJv=NormForm.vratiKolonu(inputArgs, 2);
           double []kolonaRt=NormForm.vratiKolonu(outputArgs, 0);
           
           maxT=NormForm.nadjiMaximum(kolonaT);
           minT=NormForm.nadjiMinimum(kolonaT);
           
           maxV=NormForm.nadjiMaximum(kolonaV);
           minV=NormForm.nadjiMinimum(kolonaV);
           
           maxBv=NormForm.nadjiMaximum(kolonaJv);
           minBv=NormForm.nadjiMinimum(kolonaJv);
           
           kolonaT=NormForm.normaliziraj(kolonaT);
           kolonaV=NormForm.normaliziraj(kolonaV);
           kolonaJv=NormForm.normaliziraj(kolonaJv);
           kolonaRt=NormForm.normaliziraj(kolonaRt);
           
           
           for(int i=0;i<kolonaT.length;i++)
           {
               inputArgs[i][0]=kolonaT[i];
               inputArgs[i][1]=kolonaV[i];
               inputArgs[i][2]=kolonaJv[i];
               outputArgs[i][0]=kolonaRt[i];
           }
           trainingSet=new BasicMLDataSet(inputArgs,outputArgs);  
        }
        
        
	public void kreirajMrezu()
	{
		network.addLayer(new BasicLayer(null,true,7));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,450));
                network.addLayer(new BasicLayer(new ActivationSigmoid(),true,20));
		network.addLayer(new BasicLayer(new ActivationSigmoid(),false,5));
		network.getStructure().finalizeStructure();
		network.reset();
	}
	
	public void trenirajMrezu()
	{
		final ResilientPropagation train = new ResilientPropagation(network, trainingSet);
		int epoch = 1;

		do {
			train.iteration();
			//System.out.println("Epoch #" + epoch + " Error:" + train.getError());
			epoch++;
                        trError=train.getError();
		} while(train.getError() > 0.0000001 && epoch<=470);
		train.finishTraining();
	}
	
	public void testirajMrezu()
	{
		System.out.println("Neural Network Results:");
		for(MLDataPair pair: trainingSet ) {
					final MLData output = network.compute(pair.getInput());
					System.out.println(NormForm.denormaliziraj(pair.getInput().getData(0),maxT,minT) +
							", actual=" + Math.round(NormForm.denormaliziraj(output.getData(0),maxT,minT))+" "+Math.round(output.getData(1))+" "+Math.round(output.getData(2))+" "+Math.round(output.getData(3))+" "+Math.round(output.getData(4))+ ",ideal=" + Math.round(NormForm.denormaliziraj(pair.getIdeal().getData(0),maxT,minT))+" "+pair.getIdeal().getData(1)+" "+pair.getIdeal().getData(2)+" "+pair.getIdeal().getData(3)+" "+pair.getIdeal().getData(4)+",greska="+trError);
				}
				Encog.getInstance().shutdown();
    
	}
       
        private double[] provjeriJedinice(double[] lista)
        {
            int br=0;
            for (int i=1;i<5;i++)
            {
                if(Math.abs(lista[i]-1.0)<0.00001)
                {
                    br++;
                }
            }
           if(br==1)
           {
               return lista;
           }
           int poz=0;
           double max=0.0;
           double []oblacno=NormForm.vratiKolonu(outputArgs, 1);
           double []suncano=NormForm.vratiKolonu(outputArgs, 2);
           double []snijeg=NormForm.vratiKolonu(outputArgs, 3);
           double []kisa=NormForm.vratiKolonu(outputArgs, 4);
                
           double []novaLista=new double[4];
           novaLista[0]=NormForm.Prosjek(oblacno, velProblema-1);
           novaLista[1]=NormForm.Prosjek(oblacno, velProblema-1);
           novaLista[2]=NormForm.Prosjek(oblacno, velProblema-1);
           novaLista[3]=NormForm.Prosjek(oblacno, velProblema-1);
      
           for(int i=0;i<4;i++)
            {
                if(max<novaLista[i])
                 {
                   max=novaLista[i];
                   poz=i;
                 }
           }
                
           poz++;
            if(br>1)
            {
                for(int i=1;i<5;i++)
                {
                    lista[i]=0.0;
                }
    
            }
            lista[poz]=1.0; 
            
            return lista;
        }
        
        public double [] testirajMrezicu(double[][]niz)
        {
            MLDataSet ml=new BasicMLDataSet(niz,niz);
            MLData m=ml.get(0).getInput();
            
            final MLData output=network.compute(m);
               
            //System.out.println("actual=" + Math.round(NormForm.denormaliziraj(output.getData(0),maxT,minT))+" "+Math.round(output.getData(1))+" "+Math.round(output.getData(2))+" "+Math.round(output.getData(3))+" "+Math.round(output.getData(4)));
               
            Encog.getInstance().shutdown();
            
            double [] rjesenje=new double[5];
            //System.out.print(Math.round(NormForm.denormaliziraj(m.getData(0),maxT,minT))+" "+Math.round(NormForm.denormaliziraj(m.getData(1),maxV,minV))+" "+Math.round(NormForm.denormaliziraj(m.getData(2),maxBv,minBv))+" "+m.getData(3)+" "+m.getData(4)+" "+m.getData(5)+" "+m.getData(6));
            
            rjesenje[0]=Math.round(NormForm.denormaliziraj(output.getData(0),maxT,minT));
            rjesenje[1]=Math.round(output.getData(1));
            rjesenje[2]=Math.round(output.getData(2));
            rjesenje[3]=Math.round(output.getData(3));
            rjesenje[4]=Math.round(output.getData(4));
            
            
            return provjeriJedinice(rjesenje);
        }
        
        private double[][] prilagodi(Prognoza p)
        {
            double[][] niz=new double[1][7];
            niz[0][0]=Double.parseDouble(p.getTemperatura());
            niz[0][1]=Double.parseDouble(p.getVlaznostZraka());
            niz[0][2]=Double.parseDouble(p.getBrzinaVjetra());
            niz[0][3]=0.0;
            niz[0][4]=0.0;
            niz[0][5]=0.0;
            niz[0][6]=0.0;  
                
            String vr=p.getVrijeme();
            if("Heavy snow".equals(vr) || "Snow".equals(vr))
            {
              niz[0][5]=1.0;
            }
            else if("Sunny".equals(vr))
            {
              niz[0][4]=1.0;
            }
            else if("Light drizzle".equals(vr) || "Light rain shower".equals(vr) || "Rainy".equals(vr))
            {
                niz[0][6]=1.0;
            }
            else if("Partly cloudy".equals(vr)||"Mist".equals(vr)||"Fog".equals(vr))
            {
                niz[0][3]=1.0; 
            }
            return niz;
        }
        
        private double[][] normNiz(double[][]niz)
        {
            niz[0][0]=NormForm.normaliziraj(niz[0][0],maxT,minT);
            niz[0][1]=NormForm.normaliziraj(niz[0][1],maxV,minV);
            niz[0][2]=NormForm.normaliziraj(niz[0][2],maxBv,minBv);
            return niz;
        }
        
        private double[][] dajNoviUlaz(double []niz)
        {
           double[][] ulaz=new double[1][7];
           ulaz[0][0]=NormForm.normaliziraj(niz[0],maxT,minT);
           ulaz[0][3]=niz[1];
           ulaz[0][4]=niz[2];
           ulaz[0][5]=niz[3];
           ulaz[0][6]=niz[4];
           double []vlaga=NormForm.vratiKolonu(inputArgs, 1);
           double []vjetar=NormForm.vratiKolonu(inputArgs, 2);
           ulaz[0][1]=NormForm.Prosjek(vlaga, velProblema-1);
           ulaz[0][2]=NormForm.Prosjek(vjetar,velProblema-1);
           
           return ulaz;
       }
       
        public double[][] weatherForecast() throws ParseException 
        {
                Location l=LocationService.getClientLocation();
                WebService ws=new WebService("http://api.worldweatheronline.com/premium/v1/past-weather.ashx", "c868e1f7b5a24e97a44211932160711");
                ArrayList<Prognoza> prognoze=ws.getHistorijskePodatkeByLocation(l, 12);
                
                formatirajUlaznePodatke(prognoze);
                formatirajIzlaznePodatke(prognoze);
                
                pripremiPodatke();
                kreirajMrezu();
                trenirajMrezu();
                
                int si=prognoze.size()-1;
                double [][]ulaz=prilagodi(prognoze.get(si));
                
                ulaz=normNiz(ulaz);
                double []rezultati=testirajMrezicu(ulaz);       //Predvidjanje za jedan dan
                
                ulaz=dajNoviUlaz(rezultati);
                double []rezultati2=testirajMrezicu(ulaz);      //Predvidjanje za dva dana
                
                ulaz=dajNoviUlaz(rezultati2);
                double[]rezultati3=testirajMrezicu(ulaz);        //Previdjanje za tri dana
                
                double [][]vrijeme=new double [3][5];
                for(int i=0;i<5;i++)
                {
                    vrijeme[0][i]=rezultati[i];
                }
                for(int i=0;i<5;i++)
                {
                    vrijeme[1][i]=rezultati2[i];
                }
                for(int i=0;i<5;i++)
                {
                    vrijeme[2][i]=rezultati3[i];
                }
                
                return vrijeme;
                
        }
       
	public static void main( String[] args ) throws ParseException
        {   
                NNetwork nn=new NNetwork();
                double [][]rezultati=new double[3][5];
                rezultati=nn.weatherForecast();
                for(int i=0;i<3;i++)
                {
                    for(int j=0;j<5;j++)
                    {
                       System.out.print(rezultati[i][j]+" "); 
                    }
                    System.out.printf("%n");
                }

        }
        
	
	
	
}
