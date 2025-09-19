package pos.service;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static List<String> readAllLines(String path){
        List<String> out = new ArrayList<String>();
        File f = new File(path);
        if (!f.exists()) return out;
        try(BufferedReader br = new BufferedReader(new FileReader(f))){
            String line;
            while((line=br.readLine())!=null){
                if (line.trim().length()==0) continue;
                out.add(line);
            }
        } catch(Exception ex){ ex.printStackTrace(); }
        return out;
    }
    public static void appendLine(String path, String line){
        try(PrintWriter pw = new PrintWriter(new FileWriter(path,true))){
            pw.println(line);
        } catch(Exception ex){ ex.printStackTrace(); }
    }
    public static void writeAll(String path, List<String> lines){
        try(PrintWriter pw = new PrintWriter(new FileWriter(path,false))){
            for(String l: lines) pw.println(l);
        } catch(Exception ex){ ex.printStackTrace(); }
    }
}
