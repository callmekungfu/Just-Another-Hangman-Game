package central;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;
@SuppressWarnings("serial")
public class Test extends Applet implements ActionListener{
	
	static Test ta = new Test();
	private HttpsURLConnection conn;
	static List lbl;
	static double mar[]=new double[100];
	static String point[]=new String[300];
	static Double eva[]=new Double[300];
	static String wet[]=new String[300];
	static String kno[]=new String[300];
	static String thi[]=new String[300];
	static String com[]=new String[300];
	static String appl[]=new String[300];
	static String fin[]=new String[300];
	static String username;
	static String password;
	static Label lbl2,lbl3,lbl4;
	static TextField usernameInput,passwordInput,courseIDInput;
	static Button btnLogin,click2;
	
	public void init(){
		lbl=new List();
		lbl2 = new Label("Username:");
		lbl3 = new Label("Password:");
		lbl4 = new Label("Course id:");
		
		setLayout(null);

		usernameInput=new TextField(20);
		passwordInput=new TextField(20);
		courseIDInput=new TextField(20);
		btnLogin = new Button("Login");
		click2 = new Button("Double and Print");
		btnLogin.addActionListener(this);
		click2.addActionListener(this);

		passwordInput.setEchoChar('$');

		add(usernameInput);
		add(passwordInput);
		add(courseIDInput);

		add(btnLogin);
		add(lbl);
		add(lbl3);
		add(lbl4);
		add(lbl2);

		lbl2.setBounds(150,50,100,25);
		lbl3.setBounds(150,75,100,25);
		lbl4.setBounds(150,100,100,25);
		btnLogin.setBounds(200,140,100,25);
		click2.setBounds(350,150,100,25);
		lbl.setBounds(50,170,400,330);
		usernameInput.setBounds(250,50,100,25);
		passwordInput.setBounds(250,75,100,25);
		courseIDInput.setBounds(250,100,100,25);

	}
	
	public void actionPerformed(ActionEvent evt){
		//onClick login button
		if (evt.getSource() == btnLogin)  {
			lbl.removeAll();
			lbl.add("LOGGING IN... PLEASE WAIT");
			username=usernameInput.getText();
			password=passwordInput.getText();
			
			String id;
			String teach=ta.login(username,password);
			String result="";
			try {
				result = ta.GetPageContent(teach);
				System.out.println(result);
			} catch (Exception e) {
			}
			lbl.removeAll();
			if (result.length()<5000){
				JOptionPane.showMessageDialog(null,"LOGIN ERROR, PLEASE TRY AGAIN","Error",JOptionPane.INFORMATION_MESSAGE);
				passwordInput.setText("");
			}
			else{

				int int3=0;
				ta.findMark(result,int3);

				int posi;
				posi=(ta.find(result,"student_id="))+11;
				id=(result.substring(posi,((ta.findafter(result,"\"",posi))+posi)));

				String resultw=""; 

				String a=courseIDInput.getText();
				int ab=0;
				if(!a.equals("end")){
					try {
						resultw = ta.GetPageContent("https://ta.yrdsb.ca/gamma-live/students/viewReport.php?subject_id="+a+"&student_id="+id);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ta.ex(resultw,ab,point,eva,wet);

				}
			}
		}
	}
	private String GetPageContent(String url) throws Exception {
		URL urlToSearch = new URL(url);
		conn = (HttpsURLConnection) urlToSearch.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
		return response.toString();
	}
	public void print(String a){
		lbl.add(a);
	}
	private String login(String username,String password){
		String teach = "https://ta.yrdsb.ca/gamma-live/index.php/login.jsp?username="+username+"&password="+password;
		CookieHandler.setDefault(new CookieManager());
		return teach;
	}
	public String input()throws Exception{
		return new BufferedReader(new InputStreamReader(System.in)).readLine();
	}
	private int find(String st,String need){
		int k=st.length();
		int l=need.length();
		int po=-1,i;
		for (i=0; i<k-l;i++){
			if (need.equals(st.substring(i,i+l))){
				po=i;
				break;
			}
		}
		return po;
	}
	private void findMark(String x,int int1){
		int u=x.length();
		int i,st=0,sub=0;
		String string="";
		for (i=0;i<u-100;i++){
			//search for Course Number
			if ((x.substring(i,i+3).equals(" : ")) && ((int)(x.charAt(i-1))>=48) && ((int)(x.charAt(i-1)))<=57){
				string=x.substring(i-9,i);
				int1=int1+1;
			}
			if ((x.substring(i,i+13).equals("current mark "))){
				for (int cc=1;cc<100;cc++){
					if (x.substring(i-cc,i-cc+1).equals("&")){
						st=cc;
						break;
					}
				}
				for (int cc=1;cc<100;cc++){
					if (x.substring(i-cc,i-cc+10).equals("subject_id")){
						sub=cc;
						break;
					}
				}

				String acc=string+"   "+x.substring(i,i+21)+"   Course number: "+x.substring(i-sub+11,i-st);

				lbl.add(acc);
				mar[int1]=Double.parseDouble(x.substring(i+16,i+20));
			}	
			if ((x.substring(i,i+10)).equals("Please see")){
				lbl.add(string+"   "+"Ask your teacher open Teach Assist");
				mar[int1]=0;
			}

		}
	}
	private void ex(String x,int m,String[] point,Double[] we,String[] ee){
		int u=x.length();
		String string="00";
		String stt="";
		int line=0,l,n,start1=0,end1=0,ac,cy,q;
		boolean bl=true;
		for (int i=0;i<u-100;i++){
			if (x.substring(i,i+12).equals(username+" in")){
				for (ac=0;ac<100;ac++){
					if (x.substring(i+ac,i+ac+1).equals("-")){
						break;
					}
				}
				if (((int)(x.charAt(i+ac+1))>=48 && ((int)(x.charAt(i+ac+1))<=57))){
					lbl.add("===========Student "+username+" in "+x.substring(i+ac-6,i+ac+3)+"===========");
					break;
				}
			}
		}
		for (int i=0;i<u-100;i++){
			if (x.substring(i,i+11).equals("rowspan=\"2\"")){
				line=line+1;
				for (cy=1;cy<200;cy++){
					if(x.substring(i+cy,i+cy+5).equals("</td>")){
						break;
					}
				}
				lbl.add("");
				lbl.add(x.substring(i+12,i+cy));
				ee[line]=x.substring(i+12,i+cy);
			}
			if (x.substring(i,i+31).equals("bgcolor=\"ffffaa\" align=\"center\"")){
				stt="Knowledge";
				bl=true;
			}
			if (x.substring(i,i+31).equals("bgcolor=\"c0fea4\" align=\"center\"")){
				stt="Thinking";
				bl=true;
			}
			if (x.substring(i,i+31).equals("bgcolor=\"afafff\" align=\"center\"")){
				stt="Communication";
				bl=true;
			}
			if (x.substring(i,i+31).equals("bgcolor=\"ffd490\" align=\"center\"")){
				stt="Application";
				bl=true;
			}
			if (x.substring(i,i+32).equals("bgcolor=\"#dedede\" align=\"center\"")){
				stt="Final";
				bl=true;
			}
			if (x.substring(i,i+38).equals("bgcolor=\"#ffaaaa\" align=\"center\" id=\"e")){
				bl=false;
			}
			if ((x.substring(i,i+2).equals("\">")) && ((((int)(x.charAt(i+2))>=48) && ((int)(x.charAt(i+2))<=57)) || (x.substring(i+3,i+3+1).equals("/")))){
				for (q=1;q<18;q++){
					if (x.substring(i+q,i+q+1).equals("%")){
						break;
					}
				}
				if (q<=16){
					string=x.substring(i+2,i+q+1);
					for (int ct=1;ct<q-1;ct++){
						if (((string.substring(ct,ct+1).equals("/")) && (string.substring(ct-1,ct).equals(" "))) && (string.substring(ct+1,ct+2).equals(" "))){
						}
					}
				}
				else{
					string=x.substring(i+2,i+q+1);
				}
			}
			if ((x.substring(i,i+9)).equals("no weight")){
				if (bl==false){
					lbl.add("    "+stt+" "+string+" "+"no weight"+ "!! NO MARK");
				}
				else{
					lbl.add("    "+stt+" "+string+" "+"no weight");
				}
				if (bl==true){
					if (stt.equals("Knowledge")){
						kno[line*2-1]=string;
						kno[line*2]="weight=0";
					}
					else if (stt.equals("Thinking")){
						thi[line*2-1]=string;
						thi[line*2]="weight=0";
					}
					else if (stt.equals("Communication")){
						com[line*2-1]=string;
						com[line*2]="weight=0";
					}
					else if (stt.equals("Application")){
						appl[line*2-1]=string;
						appl[line*2]="weight=0";
					}
					else if (stt.equals("Final")){
						fin[line*2-1]=string;
						fin[line*2]="weight=0";
					}
				}
			}
			if (x.substring(i,i+7).equals("weight=")){ 
				for (q=8;q<12;q++){
					if (x.substring(i+q,i+q+1).equals("<")){
						break;
					}
				}
				if (bl==false){
					lbl.setForeground(Color.RED);
					lbl.add("    "+stt+" "+string+" "+x.substring(i,i+q)+"!! NO MARKS FOUND");
				}
				else{
					lbl.add("    "+stt+" "+string+" "+x.substring(i,i+q));
				}
				if (bl=true){
					if (stt.equals("Knowledge")){
						kno[line*2-1]=string;
						kno[line*2]=x.substring(i,i+q);
					}
					if (stt.equals("Thinking")){
						thi[line*2-1]=string;
						thi[line*2]=x.substring(i,i+q);
					}
					if (stt.equals("Communication")){
						com[line*2-1]=string;
						com[line*2]=x.substring(i,i+q);
					}
					if (stt.equals("Application")){
						appl[line*2-1]=string;
						appl[line*2]=x.substring(i,i+q);
					}
					if (stt.equals("Final")){
						fin[line*2-1]=string;
						fin[line*2]=x.substring(i,i+q);
					}
				}
				l=line;
			}
			if (x.substring(i,i+16).equals("Course Weighting")){
				lbl.add("");
				lbl.add("=============Mark for Categories================");
				for (int j=1;j<u-i-50;j++){
					if (x.substring(i+j,i+j+23).equals("Knowledge/Understanding")){
						n=0;
						for (int k=1;k<u-i-j-50;k++){
							if (x.substring(i+j+k,i+j+k+14).equals("align=\"right\">") && (n<3)){
								n=n+1;
								for (l=1;l<u-i-j-k;l++){
									if (48<=(int) (x.charAt(i+j+k+l)) && (int) (x.charAt(i+j+k+l))<=57){
										start1=l;
										break;
									}
								}
								for (l=1;l<u-i-j-k-50;l++){
									if (x.substring(i+j+k+l,i+j+k+l+1).equals("%")){
										end1=l;
										break;
									}
								}
								point[n]=x.substring(start1+i+j+k,end1+i+j+k);
								if (n==1){
									lbl.add("");
									lbl.add("Knowledge");
									lbl.add("    Term Weight: "+point[n]+"%");
								}
								if (n==2){
									lbl.add("    Course Weight: "+point[n]+"%");
									we[1]=Double.parseDouble(point[n]);
								}
								if (n==3){
									lbl.add("    Student Achievement: "+point[n]+"%");
								}
							}
						}
					}
					if (x.substring(i+j,i+j+8).equals("Thinking")){
						n=0;
						for (int k=1;k<u-i-j-50;k++){
							if (x.substring(i+j+k,i+j+k+14).equals("align=\"right\">") && (n<3)){
								n=n+1;
								for (l=1;l<u-i-j-k-50;l++){
									if (48<=(int) (x.charAt(i+j+k+l)) && (int) (x.charAt(i+j+k+l))<=57){
										start1=l;
										break;
									}
								}
								for (l=1;l<u-i-j-k-50;l++){

									if (x.substring(i+j+k+l,i+j+k+l+1).equals("%")){
										end1=l;
										break;
									}
								}
								point[n]=x.substring(start1+i+j+k,end1+i+j+k);
								if (n==1){
									lbl.add("");
									lbl.add("Thinking");
									lbl.add("\tTerm Weight: "+point[n]+"%");
								}
								if (n==2){
									lbl.add("\tCourse Weight: "+point[n]+"%");
									we[2]=Double.parseDouble(point[n]);
								}
								if (n==3){
									lbl.add("\tStudent Achievement: "+point[n]+"%");
								}
							}
						}
					}
					if (x.substring(i+j,i+j+13).equals("Communication")){
						n=0;
						for (int k=1;k<u-i-j-50;k++){
							if (x.substring(i+j+k,i+j+k+14).equals("align=\"right\">") && (n<3)){
								n=n+1;
								for (l=1;l<u-i-j-k-50;l++){
									if (48<=(int) (x.charAt(i+j+k+l)) && (int) (x.charAt(i+j+k+l))<=57){
										start1=l;
										break;
									}
								}
								for (l=1;l<u-i-j-k-50;l++){

									if (x.substring(i+j+k+l,i+j+k+l+1).equals("%")){
										end1=l;
										break;
									}
								}
								point[n]=x.substring(start1+i+j+k,end1+i+j+k);
								if (n==1){
									lbl.add("");
									lbl.add("Communication");
									lbl.add("\tTerm Weight: "+point[n]+"%");
								}
								if (n==2){
									lbl.add("\tCourse Weight: "+point[n]+"%");
									we[3]=Double.parseDouble(point[n]);
								}
								if (n==3){
									lbl.add("\tStudent Achievement: "+point[n]+"%");
								}	
							}
						}
					}
					if (x.substring(i+j,i+j+11).equals("Application")){
						n=0;
						for (int k=1;k<u-i-j-50;k++){
							if (x.substring(i+j+k,i+j+k+14).equals("align=\"right\">") && (n<3)){
								n=n+1;
								for (l=1;l<u-i-j-k-50;l++){
									if (48<=(int) (x.charAt(i+j+k+l)) && (int) (x.charAt(i+j+k+l))<=57){
										start1=l;
										break;
									}
								}
								for (l=1;l<u-i-j-k-50;l++){
									if (x.substring(i+j+k+l,i+j+k+l+1).equals("%")){
										end1=l;
										break;
									}
								}
								point[n]=x.substring(start1+i+j+k,end1+i+j+k);
								if (n==1){
									lbl.add("");
									lbl.add("Application");
									lbl.add("\tTerm Weight: "+point[n]+"%");
								}
								if (n==2){
									lbl.add("\tCourse Weight: "+point[n]+"%");
									we[4]=Double.parseDouble(point[n]);
								}
								if (n==3){
									lbl.add("\tStudent Achievement: "+point[n]+"%");
								}
							}
						}
					}
					if (x.substring(i+j,i+j+5).equals("Final")){
						n=0;
						for (int k=1;k<u-i-j-25;k++){
							if (x.substring(i+j+k,i+j+k+14).equals("align=\"right\">") && (n<3)){
								n=n+1;
								for (l=1;l<u-i-j-k-25;l++){
									if (48<=(int) (x.charAt(i+j+k+l)) && (int) (x.charAt(i+j+k+l))<=57){
										start1=l;
										break;
									}
								}
								for (l=1;l<u-i-j-k-25;l++){
									if (x.substring(i+j+k+l,i+j+k+l+1).equals("%")){
										end1=l;
										break;
									}
								}
								point[n]=x.substring(start1+i+j+k,end1+i+j+k);
								if (n==1){
									lbl.add("");
									lbl.add("Final");
									lbl.add("\tCourse Weight: "+point[n]+"%");
									we[5]=Double.parseDouble(point[n]);
								}
								if (n==2){
									lbl.add("\tStudent Achievement: "+point[n]+"%");
								}

							}
						}
					}
				}
			}
		}
	}
	private int findafter(String st,String need,int a){
		int k=st.length();
		int l=need.length();
		int po=-1,i;
		for (i=0; i<k-l-a;i++){
			if (need.equals(st.substring(a+i,a+i+l))){
				po=i;
				break;
			}
		}
		return po;
	}
}		

