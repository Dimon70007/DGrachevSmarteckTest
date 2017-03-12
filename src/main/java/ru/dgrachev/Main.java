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
public class Main {
    //задание:
    //сгенерировать все возможные массивы указанного размера (xLength,yLength)с указанным количеством
    //состояний (colors) для каждой ячейки массива




    public static void main(String... args) throws Exception {
        loadProperties();
        Set<Node> nodes= resultAnaliser();
    }


    private static void loadProperties() {

        // create and load default params
        String propsFileName="params.xml";
        Properties defaultProps = new Properties();
        defaultProps.setProperty("colors",Integer.toString(DEFAULT_COLORS));
        defaultProps.setProperty("xLength",Integer.toString(DEFAULT_X_LENGTH));
        defaultProps.setProperty("yLength",Integer.toString(DEFAULT_Y_LENGTH));
        defaultProps.setProperty("statesCount",Integer.toString(DEFAULT_STATES_COUNT));
        FileInputStream in = null;
        Properties params = new Properties(defaultProps);
        // now load params
        // from last invocation
        try{
            in = new FileInputStream(propsFileName);
            params.load(in);
            in.close();
        } catch (FileNotFoundException e) {
            //if file does not exist, create it in current directory
            try {
                Path path = Paths.get(new File(".").getPath(), propsFileName);
                BufferedWriter bw=Files.newBufferedWriter(path, StandardCharsets.UTF_8);
                defaultProps.store(bw, "---No Comment---");
                bw.close();
            } catch (IOException e1) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e1);
            }
        } catch (IOException  e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, e);
        }
        xLength =Integer.valueOf(params.getProperty("xLength"));
        yLength =Integer.valueOf(params.getProperty("yLength"));
        colors =Integer.valueOf(params.getProperty("colors"));
        statesCount =Integer.valueOf(params.getProperty("statesCount"));

    }

    private static Set<Node> resultAnaliser(){
        Node rootNode= taskStarter();
        final int expectedSize=(int)Math.pow(colors, xLength * yLength);
        Collection<Node> listNodes=NodeHelper.treeToSet(rootNode);
        Set<Node> uniqueNodes=new HashSet<>();
        uniqueNodes.addAll(listNodes);
        System.out.println(Arrays.asList(   "listNodes.size()=",listNodes.size()
                                            ," expectedSize=",(expectedSize)
                                            ," uniqueNodes.size()=" ,uniqueNodes.size()
                                        ).toString().replace(",",""));
        Iterator<Node> iterator=listNodes.iterator();
        try {
            for (int i = 0; i < statesCount; i++) {
                int [][] tmp=iterator.next().get2DArr(xLength);
                System.out.println(Arrays.deepToString(tmp));
            }
        }catch (NoSuchElementException e){}
        return uniqueNodes;
    }

    static Node taskStarter(){
        final ForkJoinPool pool=new ForkJoinPool();
        final Node rootNode=new Node(arrCreator(xLength, yLength), colors);
        final Builder builder=new Builder(
                rootNode
                , 0
        );
        final ForkJoinTask<Node> rootTask=builder.fork();
        pool.submit(rootTask);
        final Node rootNode2=rootTask.join();
//        System.out.println("tree of States has been build");
        assert(rootNode.equals(rootNode2));
        return rootNode2;
    }

    static int [][] arrCreator(final int xLength, final int yLength){
        int[][] result=new int[xLength][yLength];
        for (int i = 0; i < xLength; i++) {
            Arrays.fill(result[i],0);
        }
        return result;
    }

}
