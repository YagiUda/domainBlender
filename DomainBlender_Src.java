import java.io.*; 
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.File;
import javax.swing.filechooser.FileSystemView;
import java.awt.Graphics;
import javax.swing.*;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array; 


/**
DOMAIN BLENDER

Takes 3 third party programs (Squat Cobbler, URL Crazy, and Typofinder, and runs then against a list of domains that the user selects. Afterwards, a merge can be performed to consolodate the results")

16 JULY 2020

**/

public class DomainBlender  
{  

private JLabel info;	

//For the check box, we need to know what the user has selected so a loop is run against an array called arguments
	
boolean[] arguments;

String[] results = new String[3];

String blendedResults="";	
	




public DomainBlender(){

//Commands that are required for the program to perform
	
String initSquatCobbler = "~/domainBlender/squatcobbler/main -i ~/domainBlender/domains -o ~/domainBlender/squatcobbler_results.json -whois";
String initURLCrazy = "~/domainBlender/urlcrazy/custom_urlcrazy";
String initTYPOFinder="~/domainBlender/typofinder_starter.sh";
String runMerge = "~/domainBlender/./data_merger.py";
String runPurge ="~/domainBlender/rm *.csv *.xlsx *.json";
String setDomain = "~/domainBlender/cat ";



results[0] = "'squatcobbler'";
results[1] = "'typofinder'";
results[2] = "'urlcrazy'";
		
arguments = new boolean[3];
Array.setBoolean(arguments, 0, true); //arguments[0]==true; //SQUATCOBBLER
Array.setBoolean(arguments, 1, true); //arguments[1]=true; //TYPOFINDER
Array.setBoolean(arguments, 2, true); //arguments[1]=true; //URLCRAZY

 
	//BUILD FRAME
        JFrame frame = new JFrame("domainBlender");
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout(0,0));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    //BUILD ACTIONS PANEL
        JPanel actions = new JPanel(new GridBagLayout());
    	 actions.setBackground(Color.black);


	//BUILD CHECKBUTTONS PANEL
        JPanel checkButtons = new JPanel(new GridBagLayout());
        checkButtons.setBackground(Color.black);
        //GridBagLayout layout = new GridBagLayout();
        GridBagConstraints c = new GridBagConstraints();
       
	//ACTIONS PANEL BUTTONS	
		c.weightx = 0.5;
        c.weighty = 2;
        c.gridx = 0;
        c.gridy = 0;
        JButton domainLoad = new JButton("DOMAIN LIST");
	
	// add the listener to the jbutton to handle the "pressed" event
	domainLoad.addActionListener(new ActionListener()
	{
		@Override public void actionPerformed(ActionEvent e)
      {
		loadDomain();
      }
	});
	    domainLoad.setToolTipText("Click to load a list of domains");
        actions.add(domainLoad, c);
		
        c.weightx = 0.5;
        c.weighty = 2;
        c.gridx = 0;
        c.gridy = 1;
        JButton squatCobbler = new JButton("Squat Cobbler");
		
	 // add the listener to the jbutton to handle the "pressed" event
	squatCobbler.addActionListener(new ActionListener()
	{
		@Override public void actionPerformed(ActionEvent e)
      {
		command(initSquatCobbler);
      }
	});
        squatCobbler.setToolTipText("Click to run SquatCobbler solo");
        actions.add(squatCobbler, c);
        
		
		
        c.gridx = 1;
        c.gridy = 1;
		
        JButton typoFinder= new JButton("Typo Finder");  
		// add the listener to the jbutton to handle the "pressed" event
	typoFinder.addActionListener(new ActionListener()
	{
		@Override public void actionPerformed(ActionEvent e)
      {
		System.out.println(initTYPOFinder);
      }
	});
		 typoFinder.setToolTipText("Click to run TypoFinder solo");
		
        actions.add(typoFinder, c);
		
        c.gridx = 2;
        c.gridy = 1;
        JButton urlCrazy = new JButton("URL Crazy");
        urlCrazy.setToolTipText("Click to run URL Crazy solo");
        actions.add(urlCrazy, c);
        	urlCrazy.addActionListener(new ActionListener()
	{
		@Override public void actionPerformed(ActionEvent e)
      {
		command(initURLCrazy);
      }
	});
		
		
        c.gridx = 1;
        c.gridy = 2;
        JButton runAll= new JButton("Run All");
        runAll.setToolTipText("Click to run all programs");
        actions.add(runAll, c);
        	 // add the listener to the jbutton to handle the "pressed" event
	runAll.addActionListener(new ActionListener()
	{
		@Override public void actionPerformed(ActionEvent e)
      {
		command(initSquatCobbler);
		command(initURLCrazy);
		command(initTYPOFinder);
      }
	});
		
		
        c.gridx = 0;
        c.gridy = 0;
        JLabel mergeConfirm = new JLabel("Combine Results for:");
        mergeConfirm.setForeground(Color.white);
        
     
        mergeConfirm.setToolTipText("Enable for the results of each respective script to be merged");
        checkButtons.add(mergeConfirm, c);
        
        c.gridx = 0;
        c.gridy = 1;
		JCheckBox enableSquatCobbler = new JCheckBox("Squat Cobbler", true);
		//g1.add(enableTypoFinder);
		//enableSquatCobbler.setBackground(Color.black);
		//enableSquatCobbler.setForeground(Color.white);
		checkButtons.add(enableSquatCobbler, c);
		enableSquatCobbler.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
				System.out.println("\nSquat Cobbler selected");
				Array.setBoolean(arguments, 0, true); //arguments[0]==true; //SQUATCOBBLER
				System.out.println("SquatCobbler's results will be merged when operation is performed.");
				System.out.println(arguments[0]);
        } else {//checkbox has been deselected
				System.out.println("\nSquat Cobbler deselected");
				Array.setBoolean(arguments, 0, false); //arguments[0]==true; //SQUATCOBBLER
				System.out.println("SquatCobbler's results will NOT be merged when operation is performed.");
				System.out.println(arguments[0]);
        };
    }
});

		c.gridx = 0;
        c.gridy = 2;
		JCheckBox enableTypoFinder = new JCheckBox("Typo Finder", true);
		//g1.add(enableTypoFinder);
        //enableTypoFinder.setBackground(Color.black);
		//enableTypoFinder.setForeground(Color.white);
		checkButtons.add(enableTypoFinder, c);
			enableTypoFinder.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
				System.out.println("\nTypoFinder selected");
				Array.setBoolean(arguments, 1, true); //arguments[0]==true; //SQUATCOBBLER
				System.out.println("TypoFinder's results will be merged when operation is performed.");
				System.out.println(arguments[1]);
        } else {//checkbox has been deselected
				System.out.println("\nTypoFinder deselected");
				Array.setBoolean(arguments, 1, false); //arguments[0]==true; //SQUATCOBBLER
				System.out.println("TypoFinder's results will NOT be merged when operation is performed.");
				System.out.println(arguments[1]);
        };
    }
});
		
		c.gridx = 0;
        c.gridy = 3;
		JCheckBox enableURLCrazy = new JCheckBox("URL Crazy", true);
		
        //g1.add(enableURLCrazy);
		//enableURLCrazy.setBackground(Color.black);
		//enableURLCrazy.setForeground(Color.white);
				checkButtons.add(enableURLCrazy, c);
			enableURLCrazy.addItemListener(new ItemListener() {
    @Override
    public void itemStateChanged(ItemEvent e) {
        if(e.getStateChange() == ItemEvent.SELECTED) {//checkbox has been selected
				System.out.println("\nURLCrazy selected");
				Array.setBoolean(arguments, 2, true); //arguments[0]==true; //SQUATCOBBLER
				System.out.println("URLCrazy's results will be merged when operation is performed.");
				System.out.println(arguments[2]);
        } else {//checkbox has been deselected
				System.out.println("\nURLCrazy deselected");
				Array.setBoolean(arguments, 2, false); //arguments[0]==true; //SQUATCOBBLER
				System.out.println("URLCrazy's results will NOT be merged when operation is performed.");
				System.out.println(arguments[2]);
        };
    }
});
		checkButtons.add(enableURLCrazy, c);
		

		c.gridx = 0;
        c.gridy = 3;
		actions.add(checkButtons, c);
		
		
        c.gridx = 1;
        c.gridy = 3;
        JButton blend = new JButton("BLEND!");
        blend.setToolTipText("Click to merge results");
		actions.add(blend, c);
        

		blend.addActionListener(new ActionListener()
		{
		@Override public void actionPerformed(ActionEvent e)
      {
			int empty=0;			
			blendedResults=("");
			blendedResults+=(" \"[");
			for(int i=0; i<=2;i++)
			{
				if(arguments[i]==false)
				{
					empty++;
				}
				if(arguments[i]==true)
				{
				System.out.println("\n"+results[i]);
				blendedResults+=(results[i]);
					if(i<2)
					{
					if(i+1<arguments.length){
						
						if(arguments[i+1]==true)
						{
						blendedResults+=(",");
						}
						else if(arguments[i+1]==false)
						{
								if(i+2<arguments.length){
									if(arguments[i+2]==true)
										{
										blendedResults+=(",");
										}
								}
						}
						
					}
					
					}

				}
		}
		blendedResults+=("]\"");
		//IF THE USER HAS ACTUALL SELECTED SOMETHING TO BE FORMATTED AND OR MERGED THIS WILL RUN, OTHERWISE NO POINT
			
		if(empty<3){
		command(runMerge+blendedResults);
		}
		if(empty==3)
		{
		System.out.println("\n\nNo operation was performed. Please select minimum of one option to format and/or merge.");
		}
	  }
			
		});
	
        c.gridx = 2;
        c.gridy = 3;
        JButton purge = new JButton("PURGE RESULTS");
        purge.setToolTipText("Click topurge all results");
		actions.add(purge, c);		
				// add the listener to the jbutton to handle the "pressed" event
	purge.addActionListener(new ActionListener()
	{
		@Override public void actionPerformed(ActionEvent e)
      {
		command(runPurge);
      }
	});
		
	//BUILD INFOPANEL
	//TO DO: ADD STATUS BAR THAT GIVES THE USER MORE SITUATION AWARENESS.
	//CURRENTLY INFORMATION IS PROVIDED IN THE CONSOLE
	/**JPanel infoPanel = new JPanel();
	
        c.weighty = 10;
        c.gridx = 0;
        c.gridy = 4;
        JLabel status = new JLabel ("Status: ");
        status.setForeground(Color.white);
        status.setToolTipText("Additional Information");
        infoPanel.add(status, c);
		
        c.weightx = 0;		
		c.gridx = 1;
        c.gridy = 4;
		JLabel info= new JLabel("test");
        info.setForeground(Color.white);
        info.setToolTipText("");
        infoPanel.add(info, c);
       
        infoPanel.setBackground(Color.black);
     **/   
		
    //BUILD BANNER PANEL
        JPanel banner = new JPanel();
        
        //BufferedImage myPicture = ImageIO.read(new File("test.png"));
        JLabel picLabel = new JLabel(new ImageIcon("/home/cameron/domainBlender/blender.jpg"));
               
        banner.add(picLabel);
        banner.setBackground(Color.black);
	
	//BUILD FRAME
    	frame.add(actions, BorderLayout.CENTER);
		frame.add(banner, BorderLayout.NORTH);
		//frame.add(infoPanel, BorderLayout.SOUTH);
        
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	
public static void banner(){

System.out.println("\n\nWelcome to Domain Blender beta edition\n\n");
System.out.println("\n\n\n    __                        __         ______ __                 __           ");  
System.out.println(".--|  |.-----.--------.---.-.|__|.-----.|   __ \\  |.-----.-----.--|  |.-----.----.");
System.out.println("|  _  ||  _  |        |  _  ||  ||     ||   __ <  ||  -__|     |  _  ||  -__|   _|");
System.out.println("|_____||_____|__|__|__|___._||__||__|__||______/__||_____|__|__|_____||_____|__| \n\n\n"); 
                                                                                  
	
}

public static String fileChooser(){

		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		String fileName="";
		int returnValue = jfc.showOpenDialog(null);
		// int returnValue = jfc.showSaveDialog(null);

		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			System.out.println("\n\n"+selectedFile.getAbsolutePath()+"\n\n");
			fileName=selectedFile.getAbsolutePath();
		}
	
	return fileName;
	
	
}
	
public static void loadDomain()
{
String file = fileChooser();
String s="cat ";
if (file.isEmpty()==false)
{
command(s+file+" > ~/domainBlender/domains");
}
else{
	System.out.println("\n\nPlease select a valid file for a domain list!\n\n");
}
}

public static void command(String commandLine){

System.out.println("Running the command: \n\n"+commandLine+"\n\n");
	
String s;
Process p;
        try {
            p = Runtime.getRuntime().exec(new String[]{"bash","-c",commandLine});
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println(s);
            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {}
	
}

public static void setStatus(String S, JLabel j){
j.setText("Running Squat Cobbler");
}




public static void csvJSON(String input, String output){

System.out.println("\n\nConverting CSV to JSON: \n\n"+input+ "  into  "+output+"\n\n");
String syntax = "python csv2json.py "+ input + " " +output;
String s;
Process p;
        try {
            p = Runtime.getRuntime().exec(new String[]{"bash","-c",syntax});
            BufferedReader br = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            while ((s = br.readLine()) != null)
                System.out.println(s);
            p.waitFor();
            System.out.println ("exit: " + p.exitValue());
            p.destroy();
        } catch (Exception e) {}
	
}

public static void main(String args[])  
{
//if we are ever adding arguments...
//String input = args[0];

DomainBlender domainBlender = new DomainBlender();

/**DECLARE THE COMMANDS**/

//String typoFinder="~/domainBlender/typofinder/TypoMagic/custom_typofinder.py";
//String dnsTwist="cat ~/domainBlender/domains | while read line; do dnstwist -r -t 4000 -f csv \"$line\" >> ~/domainBlender/dnstwist_results.csv; done";
//./data_merger.py "['value1', 'value2']"


/**BANNER DISPLAY FOR CONSOLE**/	
banner();



/**Commands**/	
//command(squatCobbler);
//command(urlCrazy);
//command(typoFinder);
//command(dnsTwist);
	
	
/**CONVERT CSV files to JSON
This is just running a python script csv2json.py that takes two arguments an input and an output which you can see below:
csvJSON("~/domainBlender/dnstwist_results.csv","~/domainBlender/dnstwist_results.json");
csvJSON("~/domainBlender/urlcrazy_results.csv","~/domainBlender/urlcrazy_results.json");
**/
} 


	
} 
