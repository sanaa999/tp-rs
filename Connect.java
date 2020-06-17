package Client;


import java.nio.file.Files;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataInput;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

public class Connect {

   private String user;
   private Socket socket = null, socketData = null;
   private boolean DEBUG = true;
   private String host;
   private int port;
   
   private BufferedWriter writer, writerData;
   private BufferedInputStream readerData;
   private BufferedInputStream reader;
   private String dataIP;
   private int dataPort;
   private String psw;
   private BufferedOutputStream outputStream ;
   private PrintStream outputStream2 = null;
   /**
    * Point de retour pour les resumes de transfer
    */
   private long restartPoint = 0L;

   public Connect(String ipAddress, int pPort, String pUser,String mdp){
      user = pUser;
      port = pPort;
      host = ipAddress;
      psw=mdp;
   }
   public Connect(String pUser,String mdp){
      this("127.0.0.1", 21, pUser,mdp);      
   }
   
   /**
    * Méthode de connexion au FTP
    * @throws IOException
    */
   public void connect() throws IOException{
      //Si la socket est déjà initialisée
      if(socket != null)
           throw new IOException("La connexion au FTP est déjà activée");
      
      //On se connecte
      socket = new Socket(host, port);
     
      //On crée nos objets pour pouvoir communiquer
      reader = new BufferedInputStream(socket.getInputStream());
      writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
      Ftp f=new Ftp();
      interface2 inter=new interface2();
      	
      String response =  read2();
      
      if(!response.startsWith("220")){  
    	  
    	  f.msg(response);
         throw new IOException("Erreur de connexion au FTP : \n" + response);
      }
      send("USER " + user);
      response = read2();
      if(!response.startsWith("331")) { 
    	  f.msg(response);
      throw new IOException("Erreur de connexion avec le compte utilisateur : \n" + response);
      }
      
      
      send("PASS " + psw);
      response = read2();
      if(!response.startsWith("120")) { 
    	  inter.msg("Service ready in 2 secondes.");
     
      }
      
      if(!response.startsWith("230"))  {
    	  f.msg(response);
         throw new IOException("Erreur de connexion avec le compte utilisateur : \n" + response);
      }
      
      //Nous sommes maintenant connectés
   }
  
   
   /**
    * Méthode permettant d'initialiser le mode passif
    * et ainsi de pouvoir communiquer avec le canal dédié aux données
    * @throws IOException
    */
   public void enterPassiveMode() throws IOException{

     send("PASV");
     String response = read2();
     if(DEBUG)
        log(response);
     String ip = null;
     int port = -1;
     
     //On décortique ici la réponse retournée par le serveur pour récupérer
     //l'adresse IP et le port à utiliser pour le canal data
     int debut = response.indexOf('(');
     int fin = response.indexOf(')', debut + 1);
     if(debut > 0){
        String dataLink = response.substring(debut + 1, fin);
        StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
        try {
           //L'adresse IP est séparée par des virgules
           //on les remplace donc par des points...
           ip = tokenizer.nextToken() + "." + tokenizer.nextToken() + "."
                   + tokenizer.nextToken() + "." + tokenizer.nextToken();
          
           //Le port est un entier de type int
           //mais cet entier est découpé en deux
           //la première partie correspond aux 4 premiers bits de l'octet
           //la deuxième au 4 derniers
           //Il faut donc multiplier le premier nombre par 256 et l'additionner au second
           //pour avoir le numéro de ports défini par le serveur
           port = Integer.parseInt(tokenizer.nextToken()) * 256
                + Integer.parseInt(tokenizer.nextToken());
           
           dataIP = "127.0.0.1";
           dataPort = port; 
          
          
        } catch (Exception e) {
          throw new IOException("SimpleFTP received bad data link information: "
              + response);
        }        
     }
   }
   
   /**
    * Méthode initialisant les flux de communications
    * @throws UnknownHostException
    * @throws IOException
    */
   private void createDataSocket() throws UnknownHostException, IOException{
      socketData = new Socket(dataIP, dataPort);
      readerData = new BufferedInputStream(socketData.getInputStream());
      writerData = new BufferedWriter(new OutputStreamWriter(socketData.getOutputStream()));
      
   }
  
   /**
    * Retourne le chemin courant  sur le FTP
    * @return
    * @throws IOException
    */
   public String pwd() throws IOException{
      //On envoie la commande
      send("PWD");
      //On lit la réponse
      return read2();
   }
   
   /**
    * Permet d'aller dans le répértoire choisit (Change Working Directory)
    * @return
    * @throws IOException
    */
   public String cwd(String dir) throws IOException{
      //On envoie la commande
      send("CWD " + dir);
      //On lit la réponse
      return read2();
   }
   /**
    * Retourne le contenue du répértoire courant
    * @return
    * @throws IOException
    */
   public String list() throws IOException{
	   
	//passe en mode communication ASCII   
      send("TYPE ASCII");      
      read2();
      
      enterPassiveMode();
      createDataSocket();
      send("LIST");
          
      return readData();
	   
     
   }
   //passe en mode communication ASCII   
   public String Ascci() throws IOException {
	   send("TYPE ASCII");
	   return readData();
   }
   /**
    * Command pour supprimer un fichier.
    */
   public String deleteFile(String file)throws IOException
   {
        send("DELE " + file);
      return  read2();
   }
   


   /**
    * Méthode permettant de récupérer un fichier qui est dans le serveur
    * @param file
    * @throws IOException
    */
   public String downloadFile(String file) throws IOException {
	   enterPassiveMode();
	   createDataSocket();
	   send("RETR " + file); 
	   return read3(file);
	   
   }
   /**
    * Méthode permettant de déposer un fichier dans le serveur
    * @param file
    * @throws IOException
    */
   public String uploadFile(String file)throws IOException
   {   enterPassiveMode();
       createDataSocket();
      send("STOR " + file);
      String response=read4(file);
      return  response;
   }

   /**
    * Méthode permettant d'envoyer les commandes au FTP
    * @param command
    * @throws IOException
    */
   private void send(String command) throws IOException{
      command += "\r\n";
      if(DEBUG)
         log(command);
      writer.write(command);
      writer.flush();
    
   }
   /**
    * Méthode permettant de lire les réponses du FTP
    * @return
    * @throws IOException
    */
  private String read4(String fileName ) throws IOException{    
	  OutputStream out = socketData.getOutputStream();	
	// On ouvre le fichier en local
      RandomAccessFile infile = new RandomAccessFile(fileName, "r");
      FileInputStream fileStream = new FileInputStream(infile.getFD());
      String response = "";
      int stream,stream2,i=0;
      byte[] b = new byte[4096];
      while((stream =fileStream.read(b))>0){
    	  
          out.write(b,0,stream);
        i=i+stream;
         
       }
      stream2 = reader.read(b);
      response = new String(b, 0, stream2);
      interface2 intr=new interface2();
      String str=String.valueOf("la taille du fichier envoyer: "+i+" Octet " );
           if(DEBUG) {
        	   intr.msg(response);
        	   intr.msg(str);
           }
      return response;
   }
   
 
  
	   
  
   /**
    * Méthode permettant de lire les réponses du FTP
    * @return
    * @throws IOException
    */
  private String read3(String fileName ) throws IOException{    
	  int i=0;	
	  InputStream in = socketData.getInputStream();	
	  RandomAccessFile outfile = new RandomAccessFile(fileName, "rw");
	    FileOutputStream fileStream = new FileOutputStream(outfile.getFD());
	    
      String response = "";
      int stream;
      int stream2;
      byte[] b = new byte[4096];
      while((stream =in.read(b))>0){
    	  
          fileStream.write(b,0,stream);
         i=i+stream;
       }
       
      stream2 = reader.read(b);
      response = new String(b, 0, stream2);
      interface2 intr=new interface2();
      String str=String.valueOf("la taille du fichier télécharger: "+i+" Octet " );
           if(DEBUG) {
        	   intr.msg(response);
               intr.msg(str);
           }
      return response;
   }
   private String read2() throws IOException{      
	      String response = "";
	      int stream;
	      byte[] b = new byte[4096];
	      stream = reader.read(b);
	      response = new String(b, 0, stream);
	      interface2 intr=new interface2();
	      
	      if(DEBUG)
	         log(response);
	      intr.msg(response);
	      return response;
	   }
      
   /**
    * Méthode permettant de lire les réponses du FTP
    * @return
    * @throws IOException
    */
   private String readData() throws IOException{
	   interface2 intr=new interface2();
      String response = "";
      byte[] b = new byte[1024];
      int stream;
      
      while((stream = readerData.read(b)) != -1){
         response += new String(b, 0, stream);
      }
      
      if(DEBUG)
         log(response);
      intr.msg(response);
       socketData.close();
      return response;
    
   }
   
   
   public void debugMode(boolean active){
      DEBUG = active;
   }
   private void log(String str){
      System.out.println(">> " + str);
   }  
   /**
    * Méthode permettant de quitter 
  
    */
   public void quit(){
      try {
         send("QUIT");
         interface2 intr=new interface2();
         String  response=read2();
         if(!response.startsWith("231"))  {
       
       
       intr.msg("User logged out , service terminated");
         }
      } catch (IOException e) {
         e.printStackTrace();
      }  finally{
         if(socket != null){
            try {
               socket.close();
            } catch (IOException e) {
               e.printStackTrace();
            }
            finally{
               socket = null;
            }
         }
      }
   }
}

