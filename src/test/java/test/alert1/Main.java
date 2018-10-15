package test.alert1;

import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * JList basic tutorial and example
 *
 * @author wwww.codejava.net
 */
public class Main extends JFrame {

  private JList<String> countryList;

  public Main() {
    //create the model and add elements
    DefaultListModel<String> listModel = new DefaultListModel<>();
    listModel.addElement("USA");
    listModel.addElement("India");
    listModel.addElement("Vietnam");
    listModel.addElement("Canada");
    listModel.addElement("Denmark");
    listModel.addElement("France");
    listModel.addElement("Great Britain");
    listModel.addElement("Japan");

    //create the list
    countryList = new JList<>(listModel);
    countryList.addListSelectionListener(new ListSelectionListener() {
      @Override
      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          final List<String> selectedValuesList = countryList.getSelectedValuesList();
          System.out.println(selectedValuesList);
        }
      }
    });

    add(new JScrollPane(countryList));

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("JList Example");
    this.setSize(200, 200);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        new Main();
      }
    });
  }
}