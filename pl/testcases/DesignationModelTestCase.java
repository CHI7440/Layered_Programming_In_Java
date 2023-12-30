import com.thinking.machines.hr.bl.exceptions.*;
import javax.swing.*;
import java.awt.*;
class DesignationModelTestCase extends JFrame
{
private JScrollPane jsp;
private JTable table;
private DesignationModel designationModel;
private Container container;
DesignationModelTestCase()
{
designationModel = new DesignationModel();
table = new JTable(designationModel);
jsp = new JScrollPane(table,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container = getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
setLocation(100,200);
setSize(400,500);
setVisible(true);
}
public static void main(String gg[])
{
DesignationModelTestCase dmtc = new DesignationModelTestCase();
}
}