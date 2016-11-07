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

@SuppressWarnings("unused")
public class NNetwork {
	
	private double inputArgs[][];
	private double outputArgs[][];
	private BasicNetwork network;
	private MLDataSet trainingSet;
       
        private double trError;
        private double max;
        private double min;
	
	public NNetwork(){
		double inputArg[][] = {{24,74,2,1,0,0,0},
                                       {25,73,1,1,0,0,0},
                                       {22,76,1,1,0,0,0},
                                       {24,78,3,0,1,0,0},
                                       {24,74,4,0,1,0,0},
                                       {23,77,2,0,0,0,1},
                                       {22,71,1,1,0,0,0}};
                
		double outputArg[][]= {{25,1,0,0,0},
                                       {22,1,0,0,0},
                                       {24,0,1,0,0},
                                       {24,0,1,0,0},
                                       {23,0,0,0,1},
                                       {22,0,0,0,1},
                                       {22,1,0,0,0}};
		inputArgs=inputArg;
		outputArgs=outputArg;
		network = new BasicNetwork();
		trainingSet=new BasicMLDataSet(inputArgs,outputArgs);
                
                trError=0.0;
                max=0.0;
                min=0.0;
	}
        public void pripremiPodatke()
        {
           NormForm nf=new NormForm();
              
           double []kolonaT=NormForm.vratiKolonu(inputArgs, 0);
           double []kolonaV=NormForm.vratiKolonu(inputArgs, 1);
           double []kolonaJv=NormForm.vratiKolonu(inputArgs, 2);
           double []kolonaRt=NormForm.vratiKolonu(outputArgs, 0);
           
           max=NormForm.nadjiMaximum(kolonaRt);
           min=NormForm.nadjiMinimum(kolonaRt);
           
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
		network.addLayer(new BasicLayer(new ActivationSigmoid(),true,5));
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
		} while(train.getError() > 0.0001 && epoch<=470);
		train.finishTraining();
	}
	
	public void testirajMrezu()
	{
		System.out.println("Neural Network Results:");
		for(MLDataPair pair: trainingSet ) {
					final MLData output = network.compute(pair.getInput());
					System.out.println(NormForm.denormaliziraj(pair.getInput().getData(0),max,min) +
							", actual=" + Math.round(NormForm.denormaliziraj(output.getData(0),max,min))+" "+Math.round(output.getData(1))+" "+Math.round(output.getData(2))+" "+Math.round(output.getData(3))+" "+Math.round(output.getData(4))+ ",ideal=" + Math.round(NormForm.denormaliziraj(pair.getIdeal().getData(0),max,min))+" "+pair.getIdeal().getData(1)+" "+pair.getIdeal().getData(2)+" "+pair.getIdeal().getData(3)+" "+pair.getIdeal().getData(4)+",greska="+trError);
				}
				Encog.getInstance().shutdown();
	}
	public static void main( String[] args )
        {
                NNetwork mreza=new NNetwork();
                mreza.pripremiPodatke();
                mreza.kreirajMrezu();
                mreza.trenirajMrezu();
                mreza.testirajMrezu();
        }
	
	
	
}
