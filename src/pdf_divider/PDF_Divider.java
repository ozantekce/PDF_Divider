package pdf_divider;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;

public class PDF_Divider {
    
    
    
    private String PDFPath;
    
    private PDDocument document;
    
    public static boolean isItPDF(String path){
        
        File tempFile = new File(path);
        try {
            PDDocument tempDocument=PDDocument.load(tempFile);
            tempDocument.close();
        } catch (IOException ex) {
            return false;
        }
        
        return true;
        
    }
    
    public static int npofPDF(String path){
        
        int rtn=0;
        File tempFile = new File(path);
        try {
            PDDocument tempDocument=PDDocument.load(tempFile);
            rtn =tempDocument.getNumberOfPages();
            tempDocument.close();
        } catch (IOException ex) {
            return rtn;
        }
        
        return rtn;
        
    }
    
    
    
    public void setPDFPath(String PDFPath) {
        this.PDFPath = PDFPath;
    }
     
    
    public void LoadPdf() throws IOException{
        
        File file = new File(PDFPath);
        document=PDDocument.load(file);
        
    }
    
    
    public void type1(int n, String outPath,String name,boolean allpages,int fp,int lp) throws Exception{
        
        if(allpages){
            fp=0;
            lp=document.getNumberOfPages();
        }
        else{
            if(fp>=lp){
                document.close();
                throw new Exception("First page cannot be bigger than last page.");
            }
            if(lp>document.getNumberOfPages()){
                document.close();
                throw new Exception("Last page cannot be bigger than last page of pdf.");
            }
            
            fp=fp-1;
        }
        
        int numPageofparts=n;
        
        ArrayList<PDDocument> list = new ArrayList<>();
        

        PDDocument temp = new PDDocument();

        for (int i = fp,cnt = 0; i < lp; i++,cnt++) {

            temp.addPage(document.getPage(i));

            if((cnt+1)%numPageofparts==0){
                list.add(temp);
                temp = new PDDocument();
            }

        }
        list.add(temp);

        //kayıt yapıalcak
        for (int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            if(temp.getNumberOfPages()!=0)
            temp.save(outPath+"/"+name+"_part_"+(i+1)+".pdf");
            temp.close();
        }
        
        document.close();
        
        
    }
    
    
    public void type2(int n, String outPath,String name,boolean allpages,int fp,int lp) throws Exception{
        
        if(allpages){
            fp=0;
            lp=document.getNumberOfPages();
        }
        else{
            if(fp>=lp){
                document.close();
                throw new Exception("First page cannot be bigger than last page.");
            }
            if(lp>document.getNumberOfPages()){
                document.close();
                throw new Exception("Last page cannot be bigger than last page of pdf.");
            }
            
            fp=fp-1;
        }
        
        ArrayList<PDDocument> list = new ArrayList<>();
        

        PDDocument temp = new PDDocument();
        
        int numPageofparts=(int) Math.ceil(1.0*(lp-fp)/n);
        System.out.println(numPageofparts);
        for (int i = fp , cnt=0; i < lp; i++,cnt++) {
            
            temp.addPage(document.getPage(i));

            if((cnt+1)%numPageofparts==0){
                list.add(temp);
                temp = new PDDocument();
            }
            
        }
        list.add(temp);
        //kayıt yapıalcak
        for (int i = 0; i < list.size(); i++) {
            temp = list.get(i);
            if(temp.getNumberOfPages()!=0)
            temp.save(outPath+"/"+name+"_part_"+(i+1)+".pdf");
            temp.close();
        }
        
        document.close();
        
        
    }
    
    
    public void type3(int n,String outPath,String name,boolean allpages,int fp,int lp, boolean a) throws IOException, Exception{
        
        PDDocument part1 = new PDDocument();
        PDDocument part2 = new PDDocument();
        
        if(allpages){
            fp=0;
            lp=document.getNumberOfPages();
        }
        else{
            
            if(fp>=lp){
                part1.close();
                part2.close();
                document.close();
                throw new Exception("First page cannot be bigger than last page.");
            }
            if(lp>document.getNumberOfPages()){
                part1.close();
                part2.close();
                document.close();
                throw new Exception("Last page cannot be bigger than last page of pdf.");
            }
            
            fp=fp-1;
        }
        
        if(a){
            n=2;
        }
        
        PDDocument temp =part1;
        
        for (int i = fp,cnt=0; i < lp; i++, cnt++) {
            
            temp.addPage(document.getPage(i));

            if((cnt+1)%n==0){
                if(temp==part1)
                    temp = part2;
                else
                    temp = part1;
            }
            
        }
        
        //kayıt yapılıyor
        part1.save(outPath+"/"+name+"_part_1"+".pdf");
        part1.close();
        
        part2.save(outPath+"/"+name+"_part_2"+".pdf");
        part2.close();
        
        document.close();

        
    }
    
    
    
}
