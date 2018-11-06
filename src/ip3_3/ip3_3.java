/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ip3_3;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import static java.awt.Component.LEFT_ALIGNMENT;
import java.awt.event.*;
import java.io.File;
import java.util.Random;
import java.util.Scanner;

public class ip3_3 extends JPanel {

    JLabel label, label2;
    JPanel panel;

    void buildUI(Container container) {

        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        Maze m = new Maze(this);
        label2 = new JLabel("Scor: 0 | Vieti: 3");

        container.add(m);
        container.add(label2);

        label2.setAlignmentX(TOP_ALIGNMENT);
        m.setAlignmentX(LEFT_ALIGNMENT);

    }

    void updateLabel(int scor, int vieti) {
        label2.setText("Scor: " + scor + " | " + "Vieti: " + vieti);
    }

    //Called only when this is run as an application.
    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        JFrame f = new JFrame();
        f.setResizable(false);
        f.setTitle("Mario Labirint");

        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        ip3_3 controller = new ip3_3();
        controller.buildUI(f.getContentPane());

        f.pack();
        f.setVisible(true);
    }

}

class Maze extends JPanel implements ActionListener {

    JLabel[][] maze = new JLabel[19][21];

    int m[][] = new int[19][21];
    int Width = 32;
    int Height = 32;
    ip3_3 controller;
    int posx = 18, posy = 1;
    int vieti = 3;
    int scor = 0;
    int bonusuri = 1, bombe = 1;

    Dimension preferredSize = new Dimension(800, 713);

    public void actionPerformed(ActionEvent e) {
        Random ran = new Random();
        int x = 0, y = 0, w = 0, z = 0;

        x = ran.nextInt(19);
        y = ran.nextInt(21);
        w = ran.nextInt(19);
        z = ran.nextInt(21);

        if (bonusuri < 4) {
            if (m[x][y] == 0) {
                
                Icon icon = new ImageIcon("D:\\Facultate\\1.FACULTATE AN $\\1.IP\\lab3\\IP_3\\src\\ip3_3\\5.png");
                maze[x][y].setIcon(icon);
                m[x][y] = 5;
                bonusuri++;
            }
        }

        if (bombe < 4) {
            if (m[w][z] == 0) {
                
                Icon icon = new ImageIcon("D:\\Facultate\\1.FACULTATE AN $\\1.IP\\lab3\\IP_3\\src\\ip3_3\\3.png");
                maze[w][z].setIcon(icon);
                m[w][z] = 3;
                bombe++;
            }
        }
       

    }

    public Maze(final ip3_3 controller) {

        Timer t = new Timer(60, this);
        t.start();
        this.controller = controller;
        String fisier = "D:\\Facultate\\1.FACULTATE AN $\\1.IP\\lab3\\IP_3\\src\\ip3_3\\maze.txt";
        final String spatiuGol = "D:\\Facultate\\1.FACULTATE AN $\\1.IP\\lab3\\IP_3\\src\\ip3_3\\\\0.png";
        final String mario = "D:\\Facultate\\1.FACULTATE AN $\\1.IP\\lab3\\IP_3\\src\\ip3_3\\\\2.png";
        int i, j;
        File f = null;
        Scanner scan = null;

        try {
            f = new File(fisier);
            scan = new Scanner(f);

        } catch (Exception e) {
            System.exit(0);
        }

        for (i = 0; i < 19; i++) {
            for (j = 0; j < 21; j++) {
                m[i][j] = scan.nextInt();
            }

        }
        for (i = 0; i < 19; i++) {
            for (j = 0; j < 21; j++) {

                Icon icon = new ImageIcon("D:\\Facultate\\1.FACULTATE AN $\\1.IP\\lab3\\IP_3\\src\\ip3_3\\\\" + m[i][j] + ".png");
                maze[i][j] = new JLabel();
                add(maze[i][j]);
                maze[i][j].setIcon(icon);

            }
        }

        Border raisedBevel = BorderFactory.createRaisedBevelBorder();
        Border loweredBevel = BorderFactory.createLoweredBevelBorder();
        Border compound = BorderFactory.createCompoundBorder(raisedBevel, loweredBevel);
        setBorder(compound);

        this.setFocusable(true);
        this.requestFocus();

        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                //Iesire Labirint
                if (m[posx][posy - 1] == 4) {
                    m[posx][posy] = 0;
                    m[posx][posy - 1] = 2;
                    maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                    maze[posx][posy - 1].setIcon(new ImageIcon(mario));
                    scor = scor+50;
                    JOptionPane.showMessageDialog(null, "Ai gasit iesirea din labirint, ai strans: " +scor+" puncte", "Joc Terminat", 1);
                }

                if (e.getKeyCode() == KeyEvent.VK_RIGHT && (m[posx][posy + 1] == 0 || m[posx][posy + 1] == 5 || m[posx][posy + 1] == 3)) {
                    if (m[posx][posy + 1] == 3) {
                       
                        m[posx][posy] = 0;
                        m[posx][posy + 1] = 0;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx][posy + 1].setIcon(new ImageIcon(spatiuGol));
                        maze[18][1].setIcon(new ImageIcon(mario));
                        posx = 18;
                        posy = 1;
                        vieti--;
                        controller.updateLabel(scor, vieti);
                        if(vieti==0){
                            JOptionPane.showMessageDialog(null, "Joc terminat ai strans: "+scor+" puncte", "Joc Terminat", 1);
                            
                        }

                    } if (m[posx][posy + 1] == 5) {
                       
                        m[posx][posy] = 0;
                        m[posx][posy + 1] = 2;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx][posy + 1].setIcon(new ImageIcon(mario));
                        scor = scor + 10;
                        controller.updateLabel(scor, vieti);
                        posy++;
                    } 
                    if (m[posx][posy + 1] == 0) {
                        m[posx][posy] = 0;
                        m[posx][posy + 1] = 2;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx][posy + 1].setIcon(new ImageIcon(mario));
                        posy++;
                    }

                }

                if (e.getKeyCode() == KeyEvent.VK_LEFT && (m[posx][posy - 1] == 0 || m[posx][posy - 1] == 3 || m[posx][posy - 1] == 5)) {
                    if (m[posx][posy - 1] == 3) {
                        
                        m[posx][posy] = 0;
                        m[posx][posy - 1] = 0;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx][posy - 1].setIcon(new ImageIcon(spatiuGol));
                        maze[18][1].setIcon(new ImageIcon(mario));
                        posx = 18;
                        posy = 1;
                        vieti--;
                        controller.updateLabel(scor, vieti);
                         if(vieti==0){
                            JOptionPane.showMessageDialog(null, "Joc terminat ai strans: "+scor+" puncte", "Joc Terminat", 1);
                            
                        }

                    } else if (m[posx][posy - 1] == 5) {
                       
                        m[posx][posy] = 0;
                        m[posx][posy - 1] = 2;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx][posy - 1].setIcon(new ImageIcon(mario));
                        scor = scor + 10;
                        controller.updateLabel(scor, vieti);
                        posy--;

                    } else if (m[posx][posy - 1] == 0) {
                        m[posx][posy] = 0;
                        m[posx][posy - 1] = 2;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx][posy - 1].setIcon(new ImageIcon(mario));
                        posy--;
                    }
                }

                if (e.getKeyCode() == KeyEvent.VK_UP && posx > 0 && (m[posx - 1][posy] == 0 || m[posx - 1][posy] == 3 || m[posx - 1][posy] == 5)) {
                    if (m[posx - 1][posy] == 3) {
                        
                        m[posx][posy] = 0;
                        m[posx - 1][posy] = 0;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx - 1][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[18][1].setIcon(new ImageIcon(mario));
                        posx = 18;
                        posy = 1;
                        vieti--;
                        controller.updateLabel(scor, vieti);
                         if(vieti==0){
                            JOptionPane.showMessageDialog(null, "Joc terminat ai strans: "+scor+" puncte", "Joc Terminat", 1);
                            
                        }

                    } 
                    else if (m[posx - 1][posy] == 5) {
                       
                        m[posx][posy] = 0;
                        m[posx - 1][posy] = 2;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx - 1][posy].setIcon(new ImageIcon(mario));
                        scor = scor + 10;
                        controller.updateLabel(scor, vieti);
                        posx--;

                    } 
                    else if (m[posx - 1][posy] == 0) {
                        m[posx][posy] = 0;
                        m[posx - 1][posy] = 2;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx - 1][posy].setIcon(new ImageIcon(mario));
                        posx--;
                    }

                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN && posx < 18 && (m[posx + 1][posy] == 0 || m[posx + 1][posy] == 3 || m[posx + 1][posy] == 5)) {
                    if (m[posx + 1][posy] == 3) {
                        
                        m[posx][posy] = 0;
                        m[posx + 1][posy] = 0;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx + 1][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[18][1].setIcon(new ImageIcon(mario));
                        posx = 18;
                        posy = 1;
                        vieti--;
                        controller.updateLabel(scor, vieti);
                         if(vieti==0){
                            JOptionPane.showMessageDialog(null, "Joc terminat ai strans: "+scor+" puncte", "Joc Terminat", 1);
                            
                        }

                    } else if (m[posx + 1][posy] == 5) {
                       
                        m[posx][posy] = 0;
                        m[posx + 1][posy] = 2;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx + 1][posy].setIcon(new ImageIcon(mario));
                        scor = scor + 1;
                        controller.updateLabel(scor, vieti);
                        posx++;

                    } else if (m[posx + 1][posy] == 0) {
                        m[posx][posy] = 0;
                        m[posx + 1][posy] = 2;
                        maze[posx][posy].setIcon(new ImageIcon(spatiuGol));
                        maze[posx + 1][posy].setIcon(new ImageIcon(mario));
                        posx++;
                    }
                }

            }
        });
    }

    public Dimension getPreferredSize() {
        return preferredSize;
    }
}
