/**
*
* @author Abdulkadir Abuş abdulkadir.abus@ogr.sakarya.edu.tr
* @since 02.03.2019
* <p>
* PDP 2A Grubu I. ÖDEV
* </p>
*/

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DosyadanOku {
   
    public static void main(String[] args) throws IOException{

    int sayac_operator=0;
    int sayac_fonksiyon=0;
    int sayac_parametre=0;
    int sayac=0;

    boolean degisme=true; 
    
    String[] fonksiyon_isimleri=new String[20];
    String[][] parametreler=new String[20][20];
    
     //***DOSYA OKUMA İŞLEMİ BAŞLADI***
    File file=new File("oku.c");
    String[] metin=new String[50];
    try {
	Scanner sc=new Scanner(file);
      
             //***KARAKTER OKUMA***     
            FileInputStream input=new FileInputStream(file);
            
            int size=(int) file.length();
                     
        //***FONKSİYON VE PARAMETRE BULMA***
        
        while(sc.hasNextLine()){
            metin[0]=sc.nextLine();
            
            Pattern regex_fonksiyon_isim=Pattern.compile("^\\s*(?:(?:inline|static)\\s+){0,2}(?!else|typedef|return)\\w+\\s+\\*?\\s*(\\w+)\\s*\\([^0]*\\)\\s*;?");
            
            Matcher matcher = regex_fonksiyon_isim.matcher(metin[0]);
        
            
            while(matcher.find()) {
                String a=matcher.group(); //Tüm dosya okunarak bir string değişkenine atandı
                
                String[] siplit=a.split("\\W"); //Fonksiyonların olduğu satırlarda regex ifadesi kullanılarak metot ismi bulundu
                
                sayac_fonksiyon++;

                fonksiyon_isimleri[sayac_fonksiyon]=siplit[1]; //Bulunan fonksiyon ismi bir string dizisinde saklandı
                
                String satir=a;
                
                Pattern p= Pattern.compile("(?<=\\()([^\\]]+)(?=\\))"); //Parantez içini eşleyen regex
                Matcher m=p.matcher(satir);
                
                
                //************
                while(m.find()){
                    //Fonksiyon parametrelerini bulmak için regex. Burada herhangi bir veri tipinin önüne kelimeye ulaşarak fonksiyonların parametrelerini elde ediyoruz
                    String[] fonksiyon_parametreleri=m.group(1).split("(int)|(double)|(byte)|(short)|(char)|(float)");
                    int i=0;
                    for(;i<fonksiyon_parametreleri.length;i++){                   
                            parametreler[i][sayac]=fonksiyon_parametreleri[sayac+1];
                            sayac_parametre++;                                             
                    }                  
                    sayac++;
                }    
            }
        }
        
        sayac_parametre=sayac_parametre-sayac;
        
        for(int i=0;i<size;i++){                 
                switch(input.read()){
                    case '+':
                        if(degisme){//Arttırma ve Azaltma İşlemlerini Çift Operatör olarak almasını önlemek için
                            sayac_operator++;
                            degisme=false;
                            break;
                        }
                        else{
                            break;
                        }
                    case '-':
                        if(degisme){
                            sayac_operator++;
                            break;
                        }
                        else{
                            break;
                        }
                    case '/':
                        sayac_operator++;
                        break;
                    case '*':
                        sayac_operator++;
                        break;
                    case '=':
                        sayac_operator++;
                        break;
                    case '&':
                        sayac_operator++;
                        break;
                    case '<':
                        sayac_operator++;
                        break;                        
                    case '>':
                        sayac_operator++;
                        break;
                    case '!':
                        sayac_operator++;
                        break;
                    default:     
                        degisme=true;
                }
            }
        
        System.out.println("Toplam Operatör Sayısı: " + sayac_operator);
        System.out.println("Toplam Fonksiyon Sayısı: " + sayac_fonksiyon);
        System.out.println("Toplam Parametre Sayısı: " + sayac_parametre);                  
        System.out.println("Fonksiyon İsimleri: ");
        
        /*Fonksiyonla alarak diziye attığımız parametrelerin. Boş(null) olanlarını ekrana "null" olarak yazdırmaması için böyle bir yöntem uygulandı*/
        
        for(int i=0;i<=sayac+1;i++){
            for(int j=0;j<=sayac+1;j++){
                if(parametreler[i][j]==null){
                    parametreler[i][j]="";
                }
            }
        }
        
        /*İçi boş olan değerleri yazdırmamak için böyle bir dögü yardımıya ekrana fonksiyonlar ve parametrelerle ilgili tüm bilgiler yazıldı.*/
        for(int i=0;i<=sayac+1;i++){
            for(int j=0;j<=sayac+1;j++){
                if(parametreler[i][j]!=null && fonksiyon_isimleri[i]!=null && fonksiyon_isimleri[j]==null){
                    System.out.println(fonksiyon_isimleri[i] + " - Parametreleri:"+ parametreler[i][j]+ parametreler[i][j+1]);
                }
            }
        } 
        sc.close();
    } catch (FileNotFoundException e) {
	e.printStackTrace();
    }
  
    }   
}