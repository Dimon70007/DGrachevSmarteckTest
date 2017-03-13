package ru.dgrachev;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.logging.Level;
import java.util.logging.Logger;

import static ru.dgrachev.Params.*;

/**
 * Created by OTBA}|{HbIu` on 09.02.17.
 */
public class MatrixGenerator {
    //задание:
    //сгенерировать все возможные массивы указанного размера (xLength,yLength)с указанным количеством
    //состояний (colors) для каждой ячейки массива
    //только дописав библиотеку понял что сгенерировать граф состояний - задача
    // чуть попроще да и состояний там будет намного больше
    //так как в одно и то же состояние можно прийти разными путями,
    //но за идею спасибо

    private final static String PARAMS_INI ="params.ini";

    public Collection<Node> generate(Integer...args) {
        if (loadArgs(args)) {
            saveProperties();
        }else{
            loadProperties();
        }
        Collection<Node> nodes= resultAnalyser();
        return nodes;
    }

    private void saveProperties() {
        try {
            Properties props=getProps(xLength,yLength,colors,statesCount);
            Path path = Paths.get(new File(".").getPath(), PARAMS_INI);
            BufferedWriter bw= Files.newBufferedWriter(path, StandardCharsets.UTF_8);
            props.store(bw, "---No Comment---");
            bw.close();
        } catch (IOException e1) {
            Logger.getLogger(MatrixGenerator.class.getName()).log(Level.SEVERE, null, e1);
        }
    }

    public static void main(String[] args) {
        MatrixGenerator mg=new MatrixGenerator();
        Collection<Node> nodes=mg.generate(3,3,3);
        int counter=0;
        for (Node node:nodes
             ) {
            System.out.println(node.toString());
            counter++;
            if (counter==statesCount)
                break;
        }
    }

    private boolean loadArgs(Integer[] args) {
        int argsLength=args.length;
        switch (argsLength){
            case 4:
                statesCount=args[3];
            case 3:
                colors=args[2];
            case 2:
                yLength=args[1];
            case 1:
                xLength=args[0];
                break;
            default:
                if(argsLength>0){
                    generate(Arrays.copyOf(args,4));
                }
                return false;
        }
        return true;
    }


    private void loadProperties() {
        FileInputStream in = null;
        Properties params = new Properties(getProps(DEFAULT_X_LENGTH
                ,DEFAULT_Y_LENGTH,DEFAULT_COLORS,DEFAULT_STATES_COUNT));
        // now load params
        // from last invocation
        try{
            in = new FileInputStream(PARAMS_INI);
            params.load(in);
            in.close();
        } catch (FileNotFoundException e) {
            //if file does not exist, create it in current directory
            saveProperties();
        } catch (IOException  e) {
            Logger.getLogger(MatrixGenerator.class.getName()).log(Level.SEVERE, null, e);
        }
        Params.xLength =Integer.valueOf(params.getProperty("xLength"));
        Params.yLength =Integer.valueOf(params.getProperty("yLength"));
        Params.colors =Integer.valueOf(params.getProperty("colors"));
        statesCount =Integer.valueOf(params.getProperty("statesCount"));

    }

    private Collection<Node> resultAnalyser(){
        Node rootNode= taskStarter();
        Set<Node> uniqueNodes=new HashSet<>();
        List<Node> nodes=(List)NodeHelper.treeToCollection(rootNode);
//        uniqueNodes.addAll(nodes);
        return nodes;//uniqueNodes;
    }

    private Node taskStarter(){
        final ForkJoinPool pool=new ForkJoinPool();
        final Node rootNode=new Node(NodeHelper.zeroArrCreator(xLength, yLength), colors);
        final Builder builder=new Builder(rootNode, 0);
        final ForkJoinTask<Node> rootTask=builder.fork();
        pool.submit(rootTask);
        final Node rootNode2=rootTask.join();
//        System.out.println("tree of States has been build");
        assert(rootNode.equals(rootNode2));
        return rootNode2;
    }

    public Properties getProps(int xLength, int yLength, int colors, int statesCount) {
        // create default params
        Properties defaultProps = new Properties();
        defaultProps.setProperty("xLength",Integer.toString(xLength));
        defaultProps.setProperty("yLength",Integer.toString(yLength));
        defaultProps.setProperty("colors",Integer.toString(colors));
        defaultProps.setProperty("statesCount",Integer.toString(statesCount));
        return defaultProps;
    }

}
