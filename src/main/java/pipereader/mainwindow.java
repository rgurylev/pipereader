/*
 * Created by JFormDesigner on Sat Nov 21 20:30:10 MSK 2015
 */

package pipereader;

//import com.intellij.uiDesigner.core.*;
//import info.clearthought.layout.*;
import event.EventsDispatcher;
import org.apache.log4j.Logger;
import pipereader.model.PipeEvent;
import pipereader.model.PipeAction;
import util.TextAreaAppender;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.EventListener;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author roman roman
 */
public class mainwindow extends JFrame {
    private static final Logger log = Logger.getLogger(mainwindow.class);

    protected CopyOnWriteArrayList<EventListener> eventListenerList = new CopyOnWriteArrayList();

    public mainwindow() {
        initComponents();
    }

    public void addEventListener(EventListener el) {
        this.eventListenerList.add(el);
    }

    public void removeEventListener(EventListener el) {
        this.eventListenerList.remove(el);
    }

    // Методы генерации событий

    private void okButtonMouseClicked(MouseEvent e) {
        PipeEvent event = new PipeEvent(this, textPipeName.getText().trim(), PipeAction.STARTWRITING);
        EventsDispatcher.fireEvent(event);
        log.debug("Нажали addButton");

    }

    private void removeButtonMouseClicked(MouseEvent e) {
        PipeEvent event = new PipeEvent(this, textPipeName.getText().trim(), PipeAction.STOPWRITING);
        EventsDispatcher.fireEvent(event);
        log.debug("Нажали removeButton");
    }

    private void cancelButtonMouseClicked(MouseEvent e) {
        // TODO add your code here
        System.exit(0);
    }


    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - roman roman
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        textArea1 = new JTextArea();
        buttonBar = new JPanel();
        textPipeName = new JTextField();
        addButton = new JButton();
        cancelButton = new JButton();
        removeButton = new JButton();

        //======== this ========
        setTitle("pipereader");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

            // JFormDesigner evaluation mark
            /*
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});
            */
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));

                //======== scrollPane1 ========
                {

                    //---- textArea1 ----
                    textArea1.setBorder(null);
                    scrollPane1.setViewportView(textArea1);
                }
                contentPanel.add(scrollPane1);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));

                //---- Добавить pipe
                try {
                    InputStream in = getClass().getResourceAsStream("/resources/ico/add-icon.png");
                    //ImageIcon icon= new ImageIcon(ImageIO.read(in));
                    ImageIcon icon= new ImageIcon(ImageIO.read(in));

                    addButton.setIcon(icon);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                addButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        okButtonMouseClicked(e);
                    }
                });

                //---- Закрыть окно ----
                try {
                    InputStream in = getClass().getResourceAsStream("/resources/ico/Log-Out-icon.png");
                    //ImageIcon icon= new ImageIcon(ImageIO.read(in));
                    ImageIcon icon= new ImageIcon(ImageIO.read(in));

                    cancelButton.setIcon(icon);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                cancelButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        cancelButtonMouseClicked(e);
                    }
                });

                //---- Удалить pipe
                removeButton.setDefaultCapable(false);

                try {
                    InputStream in = getClass().getResourceAsStream("/resources/ico/Close-2-icon.png");
                    //ImageIcon icon= new ImageIcon(ImageIO.read(in));
                    ImageIcon icon= new ImageIcon(ImageIO.read(in));

                    removeButton.setIcon(icon);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                removeButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        removeButtonMouseClicked(e);
                    }
                });

                GroupLayout buttonBarLayout = new GroupLayout(buttonBar);
                buttonBar.setLayout(buttonBarLayout);
                buttonBarLayout.setHorizontalGroup(
                    buttonBarLayout.createParallelGroup()
                        .addGroup(GroupLayout.Alignment.TRAILING, buttonBarLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(textPipeName, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(addButton)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(removeButton)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancelButton)
                            .addGap(1, 1, 1))
                );
                buttonBarLayout.setVerticalGroup(
                    buttonBarLayout.createParallelGroup()
                        .addGroup(buttonBarLayout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(buttonBarLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(textPipeName, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
                                .addComponent(addButton)
                                .addComponent(removeButton)
                                .addComponent(cancelButton))
                            .addContainerGap())
                );
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        setSize(480, 395);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
        TextAreaAppender.setTextArea(textArea1);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - roman roman
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    private JTextArea textArea1;
    private JPanel buttonBar;
    private JTextField textPipeName;
    private JButton addButton;
    private JButton cancelButton;
    private JButton removeButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
