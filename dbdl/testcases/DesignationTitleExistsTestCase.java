import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;

public class DesignationTitleExistsTestCase
{
public static void main(String gg[])
{
String title = gg[0];
try
{
DesignationDAO designationDAO;
designationDAO = new DesignationDAO();
System.out.println("Title: "+title+" exists : "+designationDAO.titleExists(title));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}