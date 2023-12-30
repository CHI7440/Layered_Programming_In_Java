import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.enums.*;
import java.util.*;
import java.math.*;
import java.text.*;

public class EmployeeAddTestCase
{
public static void main(String gg[])
{
String name = gg[0];
int designationCode = Integer.parseInt(gg[1]);
SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
Date dateOfBirth = new Date();
try
{
dateOfBirth = simpleDateFormat.parse(gg[2]);
}catch(ParseException pe)
{
System.out.println(pe.getMessage());
}
char gender = gg[3].charAt(0);
boolean isIndian = Boolean.parseBoolean(gg[4]);
BigDecimal basicSalary = new BigDecimal(gg[5]);
String panNumber = gg[6];
String aadharCardNumber = gg[7];
try
{
EmployeeDTOInterface employeeDTO;
employeeDTO = new EmployeeDTO();
employeeDTO.setName(name);
employeeDTO.setDesignationCode(designationCode);
employeeDTO.setDateOfBirth(dateOfBirth);
if(gender == 'M' || gender == 'm')
{
employeeDTO.setGender(GENDER.MALE);
}
if(gender == 'F' || gender == 'f')
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(isIndian);
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(panNumber);
employeeDTO.setAadharCardNumber(aadharCardNumber);
EmployeeDAOInterface employeeDAO;
employeeDAO = new EmployeeDAO();
employeeDAO.add(employeeDTO);
System.out.println("Employee: "+name+" added with employeeId as: "+employeeDTO.getEmployeeId());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}