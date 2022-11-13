package tric.tricproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.clusterers.HierarchicalClusterer;
import weka.core.*;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NumericToNominal;

import static weka.clusterers.HierarchicalClusterer.TAGS_LINK_TYPE;

@SpringBootApplication
public class TricProjectApplication {

    /*private static final String[][] TRAIN_DATA = {{"1","1","2","1","1"}, {"1","2","2","1","2"}, {"1","1","2","2","1"}, {"2","1","1","1","1"}, {"2","2","2","1","1"},
            {"2","1","2","2","1"}};

    private static final double[][] DATA = {{1,1,2,2}, {1,1,2,1}, {1,2,2,2}, {2,1,1,1}, {1,2,1,1},
            {2,2,2,1}, {2,1,2,1}, {2,2,2,1}, {2,2,2,1}, {1,2,1,1}, {1,1,1,2}, {2,2,2,2}, {1,1,1,1}};
    private static final String[][] TEST_DATA = {{"2","2","1","1","1"}, {"1","2","1","2","1"}, {"1","2","1","2","2"}, {"1","2","1","1","2"}};
    private static final String[][] PREDICTION_DATA = {{"1","1","2","2","2"}, {"1","2","2","2","2"}, {"2","1","2","2","2"}};
    private static final int K = 2;            // number of clusters*/

    public static void main(String[] args) {
        SpringApplication.run(TricProjectApplication.class, args);

        /*Instances dataset = loadForHierarchical(DATA);
        HierarchicalClusterer hc = new HierarchicalClusterer();
        hc.setLinkType(new SelectedTag(4, TAGS_LINK_TYPE));  // CENTROID
        hc.setNumClusters(K);

        *//*Logistic logReg = new Logistic();*//*
        try {
            hc.buildClusterer(dataset);
            for (Instance instance : dataset) {
                System.out.printf("(%.0f,%.0f,%.0f,%.0f): %s%n",
                        instance.value(0), instance.value(1), instance.value(2), instance.value(3),
                        hc.clusterInstance(instance));
            }
            process();
        } catch (Exception e) {
            System.err.println(e);
        }*/
    }

    /*private static Instances loadForHierarchical(double[][] data) {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        attributes.add(new Attribute("X"));
        attributes.add(new Attribute("Y"));
        Instances dataset = new Instances("Dataset", attributes, data.length);
        for (double[] datum : data) {
            Instance instance = new SparseInstance(2);
            instance.setValue(0, datum[0]);
            instance.setValue(1, datum[1]);
            dataset.add(instance);
        }
        return dataset;
    }


    private static Instances load(String[][] data) {
        ArrayList<Attribute> attributes = new ArrayList<Attribute>();
        ArrayList<String> classlabel = new ArrayList<String>();
        classlabel.add("1");
        classlabel.add("2");
        attributes.add(new Attribute("Vote1", classlabel));
        attributes.add(new Attribute("Vote2", classlabel));
        attributes.add(new Attribute("Vote3", classlabel));
        attributes.add(new Attribute("Vote4", classlabel));
        attributes.add(new Attribute("FinalVote", classlabel));

        Instances dataset = new Instances("Dataset", attributes, data.length);
        dataset.setClassIndex(dataset.numAttributes() - 1);

        for (String[] datum : data) {
            Instance instance = new SparseInstance(5);
            instance.setDataset(dataset);
            instance.setValue(0, datum[0]);
            instance.setValue(1, datum[1]);
            instance.setValue(2, datum[2]);
            instance.setValue(3, datum[3]);
            instance.setValue(4, datum[4]);
            dataset.add(instance);
        }
        return dataset;
    }

    public static void process() throws Exception {

        Instances trainingDataSet = load(TRAIN_DATA);
        System.out.println("loaded first set");
        Instances testingDataSet = load(TEST_DATA);
        System.out.println("loaded second set");
        Classifier classifier = new weka.classifiers.functions.Logistic();

        classifier.buildClassifier(trainingDataSet);
        System.out.println("Built classifier");

        Evaluation eval = new Evaluation(trainingDataSet);
        eval.evaluateModel(classifier, testingDataSet);

        *//** Print the algorithm summary *//*
        System.out.println("** Linear Regression Evaluation with Datasets **");
        System.out.println(eval.toSummaryString());
        System.out.print(" the expression for the input data as per alogorithm is ");
        System.out.println(classifier);

        //Instance predicationDataSet = load(PREDICTION_DATA).lastInstance();
        //double value = classifier.classifyInstance(predicationDataSet);
        *//** Prediction Output *//*
        for (Instance instance: load(PREDICTION_DATA)) {
            double value = classifier.classifyInstance(instance);
            System.out.println("PREDICTED VALUE:");
            System.out.println(value);
            System.out.println("ACTUAL VALUE:");
            System.out.println(Arrays.toString(instance.toDoubleArray()));
        }
    }*/
}


