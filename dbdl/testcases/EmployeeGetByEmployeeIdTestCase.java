import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.text.*;

public class EmployeeGetByEmployeeIdTestCase
{
public static void main(String gg[])
{
String employeeId = gg[0];
try
{
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
EmployeeDTOInterface employeeDTO;
employeeDTO = new EmployeeDTO();
employeeDTO = employeeDAO.getByEmployeeId(employeeId);
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
System.out.println("Employee Id : "+employeeDTO.getEmployeeId());
System.out.println("Name : "+employeeDTO.getName());
System.out.println("Designation Code : "+employeeDTO.getDesignationCode());
System.out.println("Date Of Birth : "+simpleDateFormat.format(employeeDTO.getDateOfBirth()));
System.out.println("Gender : "+employeeDTO.getGender());
System.out.println("Is Indian : "+employeeDTO.getIsIndian());
System.out.println("Basic Salary : "+employeeDTO.getBasicSalary());
System.out.println("PAN Number : "+employeeDTO.getPANNumber());
System.out.println("Aadhar Card Number : "+employeeDTO.getAadharCardNumber());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}