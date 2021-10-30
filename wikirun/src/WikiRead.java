import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class WikiRead {
    static int layer = 0;
    boolean running = true;
    String l1 = "not needed";
    String l2 = "not needed";
    String l3 = "not needed";
    String l4 = "not needed";
    String lookingfor;

    public WikiRead() {
        Scanner myLink = new Scanner(System.in);
        System.out.println("Enter Link:");
        String link = myLink.nextLine();
        Scanner myLook = new Scanner(System.in);
        System.out.println("What are you looking for?");
        lookingfor = myLook.nextLine();
        WikiPull(4, link);
    }


    public void WikiPull(int order, String pull) {

        ArrayList<String> layer1 = new ArrayList<String>();

        try {
            URL url = new URL(pull);


            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null && running) {

//                    System.out.println(line);

                if (line.contains("<a href=")) {
//                        System.out.println(line);
                    int index1 = line.indexOf("href=") + 6;
                    int index2 = 0;
                    boolean foundit = true;
                    int linkscount = 0;

                    for (int i = index1; i < line.length(); i++) {

//                            if(line.charAt(index1) != 'h'){
//                                foundit = false;
//
//                            }
                        if (line.contains("wp-") || line.contains(".php") || line.contains("googleapis") || line.contains(".org") || line.contains(".com") || line.contains("Template:")) {
                            foundit = false;
                        }
                        if (line.contains("/wiki/") != true) {
                            foundit = false;
                        }
                        if (line.charAt(i) == '(') {
                            index2 = i;
                            linkscount++;
                            break;
                        }
                        if (line.charAt(i) == '\"') {
                            index2 = i;
                            linkscount++;
                            break;

                        }
                        if (line.charAt(i) == '\'') {
                            index2 = i;
                            linkscount++;
                            break;

                        } else if (i == line.length() - 1) {
                            index2 = i;
                            foundit = false;
                        }


                    }

                    if (foundit == true) {

                        String mysublink = line.substring(index1, index2);
                        if (linkscount <= 0) {
                            break;
                        }
                        String mylink = ("https://en.wikipedia.org" + mysublink);

                        if (pull.contains(mysublink) == false) {


                            layer++;

                            layer1.add(mylink);

                        }


                        if (order == 4) {
                            l4 = mylink;
                            l1 = "not needed";
                            l2 = "not needed";
                            l3 = "not needed";
                        }
                        if (order == 3) {
                            l3 = mylink;
                            l1 = "not needed";
                            l2 = "not needed";
                        }
                        if (order == 2) {
                            l2 = mylink;
                            l1 = "not needed";
                        }
                        if (order == 1) {
                            l1 = mylink;
                        }
                        if (order == 0) {

//

                            if (mylink.contains(lookingfor)) {
                                System.out.println("done");
                                System.out.println("First Link: " + l4);
                                System.out.println("Second Link: " + l3);
                                System.out.println("Third Link: " + l2);
                                System.out.println("Fourth Link: " + l1);
                                running = false;
                                return;
                            } else {


                                return;

                            }
                        } else {

                            if (mylink.contains(lookingfor)) {
                                System.out.println("done");
                                System.out.println("First Link: " + l4);
                                System.out.println("Second Link: " + l3);
                                System.out.println("Third Link: " + l2);
                                System.out.println("Fourth Link: " + l1);
                                running = false;
                                return;
                            }

                            if (running && foundit == true && !mylink.contains(pull)) {
                                WikiPull(order - 1, mylink);

                            }

                        }


                    }


                }
            }


            reader.close();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
