package pu.reactor.helpinfo;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by gogo on 15.12.2017 г..
 */
public class ReactorAboutWindow extends JFrame{
    private JPanel logosPanel;
    private JPanel textPanel;
    public ReactorAboutWindow() {
        this.setSize(new Dimension(1000, 700));

        this.setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setTitle("About");

        logosPanel = new JPanel();
        textPanel = new JPanel();

        textPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        Border border =  textPanel.getBorder();
        Border margin = new EmptyBorder(10,10,10,10);
        textPanel.setBorder(new CompoundBorder(margin,border));
        this.add(textPanel);
        this.add(logosPanel, BorderLayout.WEST);
        this.add(textPanel, BorderLayout.CENTER);


        logosPanel.setLayout(new BoxLayout(logosPanel, BoxLayout.PAGE_AXIS));
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));





        Image logosImage = null;
        try {
            logosImage = ImageIO.read( ClassLoader.getSystemResource("pu/images/test.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        JLabel label = new JLabel(new ImageIcon(logosImage));
      //  logosPanel.add(label);





        Image cLabLogo = null;
        try {
            cLabLogo = ImageIO.read( ClassLoader.getSystemResource("pu/images/cLabLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        cLabLogo = cLabLogo.getScaledInstance(150, 100, Image.SCALE_DEFAULT);

        Image ideaLogo = null;
        try {
            ideaLogo = ImageIO.read( ClassLoader.getSystemResource("pu/images/IdeaLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ideaLogo = ideaLogo.getScaledInstance(150, 100, Image.SCALE_DEFAULT);


        Image facultyLogo = null;
        try {
            facultyLogo = ImageIO.read( ClassLoader.getSystemResource("pu/images/ChemFacultyLogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        facultyLogo = facultyLogo.getScaledInstance(150, 100, Image.SCALE_DEFAULT);


        Image uniLogo = null;
        try {
            uniLogo = ImageIO.read( ClassLoader.getSystemResource("pu/images/PULogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        uniLogo = uniLogo.getScaledInstance(150, 150, Image.SCALE_DEFAULT);


        Icon cLabIcon = new ImageIcon(cLabLogo);
        Icon ideaIcon = new ImageIcon(ideaLogo);
        Icon facultyIcon = new ImageIcon(facultyLogo);
        Icon uniIcon = new ImageIcon(uniLogo);


        String[] columnNames = {"Picture", "Description"};
        Object[][] data =
                {
                        {cLabIcon, "cLab"},
                        {ideaIcon, "Idea Consult"},
                        {facultyIcon, "Faculty of Chemistry"},
                        {uniIcon, "Plovdiv University"},
                };

        DefaultTableModel model = new DefaultTableModel(data, columnNames)
        {
            //  Returning the Class of each column will allow different
            //  renderers to be used based on Class
            public Class getColumnClass(int column)
            {
                return getValueAt(0, column).getClass();
            }
        };
        JTable table = new JTable( model );

        logosPanel.add(table);

        table.setRowHeight(150);
        table.getColumnModel().getColumn(1).setWidth(100);
        table.setBackground(new Color(238,238,238));

        table.setShowGrid(false);
        table.setShowVerticalLines(false);
        table.setShowGrid(false);
        table.setShowHorizontalLines(false);

        table.setTableHeader(null);
        logosPanel.setPreferredSize(new Dimension(300,300));


        JLabel name = new JLabel("Reactor");
        name.setFont(new Font("Serif", Font.PLAIN, 30));
        textPanel.add(name);


        /**
         * GitHub link
         */
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        JLabel gitHub = new JLabel("Github :");
        gitHub.setFont(new Font("Serif", Font.PLAIN, 20));
        textPanel.add(gitHub);


        JLabel gitHubLink = new JLabel("https://github.com/PUCompChem/JBSMM2#jbsmm2");
        gitHubLink.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gitHubLink.setFont(new Font("Serif", Font.PLAIN, 20));
        gitHubLink.setForeground(Color.BLUE);
        textPanel.add(gitHubLink);
        gitHubLink.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                try {
                    final URI uri = new URI("https://github.com/PUCompChem/JBSMM2#jbsmm2");
                    open(uri);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));


    }
    private static void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(uri);
            } catch (IOException e) { /* TODO: error handling */ }
        } else { /* TODO: error handling */ }
    }
}
