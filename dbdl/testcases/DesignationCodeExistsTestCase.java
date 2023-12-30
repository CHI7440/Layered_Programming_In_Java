import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;

public class DesignationCodeExistsTestCase
{
public static void main(String gg[])
{
int code = Integer.parseInt(gg[0]);
try
{
DesignationDAO designationDAO;
designationDAO = new DesignationDAO();
System.out.println("Code: "+code+" exists : "+designationDAO.codeExists(code));
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}